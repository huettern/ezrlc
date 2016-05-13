package pro2.ModelCalculation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;

import pro2.MVC.Model;
import pro2.ModelCalculation.MCEqCircuit.CircuitType;
import pro2.RFData.RFData;
import pro2.util.Complex;
import pro2.util.MathUtil;
import pro2.util.UIUtil;

import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;

/**
 * Worker Class that calculates a new equivalent circuit model
 * Usage:
 * MCWorker worker = new MCWorker("MCWorker-1");
 * worker.start();
 * 
 * @author noah
 *
 */
public class MCWorker extends Thread {

	//================================================================================
    //Private Data
    //================================================================================
	private Model parent;
	private Thread t;
	private String workerName;
	
	private RFData rfData;
	private MCOptions ops;

	//================================================================================
    //Constructor
    //================================================================================
	public MCWorker(Model parent, String s) {
		this.parent = parent;
		this.workerName = s;
	}
	


	//================================================================================
    // Public methods
    //================================================================================
	/**
	 * Set the data set to which the model should be created
	 * @param set
	 */
	public void setRFDataSet(RFData set) {
		rfData = set;
	}
	
	/**
	 * Sets the model creation options
	 * @param options
	 */
	public void setMCOptions (MCOptions options) {
		ops = options;
	}


	//================================================================================
    // Start Method
    //================================================================================
	/**
	 * Starts the thread
	 */
	public void start () {
		System.out.println("Starting " +  workerName );
	      if (t == null) {
	         t = new Thread (this, workerName);
	         t.start ();
	      }
	}

	
	//================================================================================
    // Run method
    //================================================================================
	/**
	 * Is called after the thread is started, thread is stopped after exit of this 
	 * method
	 */
	public void run() {
		// get necessary data
		double[] f = rfData.getfData();
		Complex[] s = rfData.getSData(50);
		Complex[] z = rfData.getzData();
		
		// apply ops
		double[] w = MCUtil.applyMCOpsToF(ops, f, MCUtil.DATA_FORMAT.OMEGA);
		Complex[] ys = MCUtil.applyMCOpsToData(ops, f, s);
		Complex[] yz = MCUtil.applyMCOpsToData(ops, f, z);
		
		// Create model index list
		CircuitType[] circuitIdxes;
		circuitIdxes = MCUtil.createModelList(ops);
		
		// Create equivalent circuit models
		List<MCEqCircuit> circuits = new ArrayList<MCEqCircuit>(circuitIdxes.length);
		for (CircuitType type : circuitIdxes) {
			circuits.add(new MCEqCircuit(type));
			circuits.get(circuits.size()-1).setWVector(w);
		}
		
		// Do analytical solving
		analyticalSolver(w, yz, ys, circuits);
		
		// apply manual parameters
		for(int i = 0; i < ops.paramsAuto.length; i++){
			if(ops.paramsAuto[i] == false) {
				for (MCEqCircuit circuit : circuits) {
					circuit.setParameter(i,ops.params[i]);
				}
			}
		}
		
		// Generate rank of equivalent circuits
		ArrayList<MCEqCircuit> sortedList = MCRank.sortByError(ys, circuits);
		
//		// Run optimizer
//		MultivariateFunction e = new MCErrorSum(ys, sortedList.get(0));
//		SimplexOptimizer optimizer = new SimplexOptimizer(1e-11, 1e-14);
//		PointValuePair optimum = null;
//		try {
//			optimum = optimizer.optimize(
//				new MaxEval(100000), 
//				new ObjectiveFunction(e),
//				GoalType.MINIMIZE, 
//				new InitialGuess(sortedList.get(0).getParameters()), 
//				new NelderMeadSimplex(new double[] { 0.001, 0.001, 0.001, 0.001, 0.001, 0.001, 0.001 }));
//		} catch(TooManyEvaluationsException ex) {
//			System.out.println("Optimizer reached MaxEval");
//		}
//
//		double[] res = optimum.getPoint();
		
		// Run optimizer
		sortedList.get(0).optimize(ys);
		sortedList.get(0).printParameters();
		success(sortedList.get(0));
	}

	//================================================================================
    // Private methods
    //================================================================================
	private void success (MCEqCircuit eqc) {
		System.out.println("MCWorker " +workerName +" has successfully completed");
	}

	/**
	 * Does analytical solving stuff
	 * @param w frequency vecotr in omega
	 * @param yz measured impedance data
	 * @param ys measured scattering data
	 * @param circuits list of possible circuit models
	 */
	private void analyticalSolver(double[] w, Complex[] yz, Complex[] ys, List<MCEqCircuit> circuits) {
		//circuits.get(0).getS()[0].abs()
		double yzAbs[]=new double[yz.length];
		double ysAbs[]=new double[ys.length];
		for (int i=0;i<ys.length;i++){ //ysAbs
			ysAbs[i]=ys[i].abs();
		}
		for (int i=0;i<yz.length;i++){ //yzAbs
			yzAbs[i]=yz[i].abs();
		}
		
		double[] diffSAbs = new double[ys.length-1];
		
		double R;
		double Y;
		double L;
		double C;
		
		//Sinnvolle Messpunkte auswählen
		diffSAbs=MathUtil.diff(ysAbs);
		int index1=MathUtil.getMaxIndex(diffSAbs);
		int index2=MathUtil.getMinIndex(diffSAbs);
		
		double w21=w[index1];
		double w22=w[index2];
		
		double realz21=yz[index1].re();
		double realz22=yz[index2].re();
		double imagz21=yz[index1].im();
		double imagz22=yz[index2].im();
		
		double realy21=Complex.div(new Complex(1,0), yz[index1]).re();
		double realy22=Complex.div(new Complex(1,0), yz[index2]).re();
		double imagy21=Complex.div(new Complex(1,0), yz[index1]).im();
		double imagy22=Complex.div(new Complex(1,0), yz[index2]).im();
		
		//EqCircuit 0
		circuits.get(0).setParameter(4, imagz21/w21); 		//L
		circuits.get(0).setParameter(0, realz21); 			//R
		
		//EqCircuit 1
		circuits.get(1).setParameter(0, 1/imagy21); 			//R
		circuits.get(1).setParameter(4, -1/(w21*imagy21)); 	//L
		
		//EqCircuit 2
		circuits.get(2).setParameter(0, realz21); 			//R
		circuits.get(2).setParameter(5, -1/(w21*imagz21)); 	//C
		
		//EqCircuit 3
		circuits.get(3).setParameter(5, imagy21/w21); 		//C
		circuits.get(3).setParameter(0, 1/(realy21)); 		//R
		
		//EqCircuit 4
		C=(-(Math.pow(w21,2)-Math.pow(w22,2)))/(w21*w22*(w21*imagz22-w22*imagz21));
		L=(imagz21+1/(w21*C))/w21;
		circuits.get(4).setParameter(0, realz21); 			//R
		circuits.get(4).setParameter(5, C); 				//C
		circuits.get(4).setParameter(4, L); 				//L
		
		//EqCircuit 5
		L=(-(Math.pow(w21,2)-Math.pow(w22,2)))/(w21*w22*(w21*imagy22-w22*imagy21));
		C=(imagy21+1/(w21*L))/w21;
		circuits.get(5).setParameter(0, realz21); 			//R
		circuits.get(5).setParameter(5, C); 				//C
		circuits.get(5).setParameter(4, L); 				//L
		
		//EqCircuit 6
		L=Math.sqrt(-(Math.pow(w21,2)*realy21-Math.pow(w22,2)*realy22)*(realy21-realy22))/((Math.pow(w21,2)-Math.pow(w22,2))*realy21*realy22);
		R=(Math.sqrt(1-4*Math.pow(L, 2)*Math.pow(w21, 2)*Math.pow(realy21, 2))+1)/(2*realy21);
		C=(imagy21+(w21*L/(Math.pow(w21*L, 2)+Math.pow(R, 2))))/w21;
		circuits.get(6).setParameter(0, R); 				//R
		circuits.get(6).setParameter(5, C); 				//C
		circuits.get(6).setParameter(4, L); 				//L
		
		//EqCircuit 7
		C=Math.sqrt(-(Math.pow(w21,2)*realz21-Math.pow(w22,2)*realz22)*(realz21-realz22))/((Math.pow(w21,2)-Math.pow(w22,2))*realz21*realz22);
		Y=(Math.sqrt(1-4*Math.pow(C, 2)*Math.pow(w21, 2)*Math.pow(realz21, 2))+1)/(2*realz21);
		R=1/Y;
		L=(imagz21+(w21*C/(Math.pow(w21*C, 2)+Math.pow(Y, 2))))/w21;
		circuits.get(7).setParameter(0, R); 				//R
		circuits.get(7).setParameter(5, C); 				//C
		circuits.get(7).setParameter(4, L); 				//L
		
	}


}
