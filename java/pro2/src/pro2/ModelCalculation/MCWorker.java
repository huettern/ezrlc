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
    //Public Data
    //================================================================================
	public enum WorkerMode {NORMAL, OPT_ONLY};
	
	//================================================================================
    //Private Data
    //================================================================================
	private Model parentModel;
	private Thread t;
	private String workerName;
	
	private RFData rfData;
	private MCOptions ops;
	private MCEqCircuit eqCircuit;
	
	private WorkerMode workerMode;

	//================================================================================
    //Constructor
    //================================================================================
	public MCWorker(Model parent, String s) {
		this.parentModel = parent;
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
		workerMode = WorkerMode.NORMAL;
	}

	/**
	 * Set the model to be optimized
	 * @param mcEqCircuit MCEqCircuit
	 */
	public void setEQCircuit(MCEqCircuit mcEqCircuit) {
		eqCircuit = mcEqCircuit;
		ops = eqCircuit.getOps();
		workerMode = WorkerMode.OPT_ONLY;
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
		
		// S Scaler
		double rref = MCSScaler.scale(s);
		
		// Get new S Data
		s = rfData.getSData(rref);
		//s = rfData.getSData(50.0);
		
		// apply ops
		double[] w = MCUtil.applyMCOpsToF(ops, f, MCUtil.DATA_FORMAT.OMEGA);
		Complex[] ys = MCUtil.applyMCOpsToData(ops, f, s);
		Complex[] yz = MCUtil.applyMCOpsToData(ops, f, z);
		
		// if mode is optimize only, do it now
		if(workerMode == WorkerMode.OPT_ONLY) {
			eqCircuit.optimize(ys);
			success(eqCircuit);
			return;
		}
		
		// Create model index list
		CircuitType[] circuitIdxes;
		circuitIdxes = MCUtil.createModelList(ops);

		// Create equivalent circuit models
		List<MCEqCircuit> userCircuits = new ArrayList<MCEqCircuit>(circuitIdxes.length);
		for (CircuitType type : circuitIdxes) {
			userCircuits.add(new MCEqCircuit(type));
			userCircuits.get(userCircuits.size()-1).setWVector(w);
			userCircuits.get(userCircuits.size()-1).setZ0(rref);
		}
		
		// Create circuit list for analytical solver
		CircuitType[] solverCircuitTypes = {CircuitType.MODEL0, CircuitType.MODEL1, CircuitType.MODEL2, 
				CircuitType.MODEL3, CircuitType.MODEL4, CircuitType.MODEL5,
				CircuitType.MODEL6, CircuitType.MODEL7 };
		List<MCEqCircuit> solverCircuits = new ArrayList<MCEqCircuit>(8);
		for(int i = 0; i < 8; i++) {
			solverCircuits.add(new MCEqCircuit(solverCircuitTypes[i]));
			solverCircuits.get(solverCircuits.size()-1).setWVector(w);
			solverCircuits.get(solverCircuits.size()-1).setZ0(rref);
		}
		
		// Do analytical solving
		analyticalSolver(w, yz, ys, solverCircuits);
		
		// Apply solver parameters
		for(int i = 0; i < solverCircuits.size(); i++){
			for(int j = 0; j < userCircuits.size(); j++) {
				if(userCircuits.get(j).getCircuitType() == solverCircuits.get(i).getCircuitType()){
					userCircuits.get(j).setParameters(solverCircuits.get(i).getParameters());
				}
			}
		}
		
		// apply manual parameters
		for(int i = 0; i < ops.paramsAuto.length; i++){
			if(ops.paramsAuto[i] == false) {
				for (MCEqCircuit circuit : userCircuits) {
					circuit.setParameter(i,ops.params[i]);
				}
			}
		}
		
		// Generate rank of equivalent circuits
		ArrayList<MCEqCircuit> sortedList = MCRank.sortByError(ys, userCircuits);
		
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
		sortedList.get(0).setOps(ops);
		sortedList.get(0).optimize(ys);
		sortedList.get(0).printParameters();
		success(sortedList.get(0));
	}

	//================================================================================
    // Private methods
    //================================================================================
	private void success (MCEqCircuit eqc) {
		System.out.println("MCWorker " +workerName +" has successfully completed");
		parentModel.mcWorkerSuccess(eqc, this.workerMode);
	}

	/**
	 * Does analytical solving stuff
	 * @param w frequency vecotr in omega
	 * @param yz measured impedance data
	 * @param ys measured scattering data
	 * @param circuits list of possible circuit models
	 */
	private void analyticalSolver(double[] w, Complex[] yz, Complex[] ys, List<MCEqCircuit> circuits) {
		System.out.println("*****AnalyticalSolver*****");
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
		
		//Sinnvolle Messpunkte ausw�hlen
		diffSAbs=MathUtil.diff(ysAbs);
		int index1=MathUtil.getMaxIndex(diffSAbs);
		if (index1==0){
			index1=1;
		}
		int index2=MathUtil.getMinIndex(diffSAbs);
		if (index2==0){
			index2=1;
		}
		
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
		circuits.get(1).setParameter(0, 1/realy21); 		//R
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
		circuits.get(5).setParameter(0, 1/realy21); 		//R
		circuits.get(5).setParameter(5, C); 				//C
		circuits.get(5).setParameter(4, L); 				//L
		System.out.println("R="+(1/realy21));
		System.out.println("C="+C);
		System.out.println("L="+L);
		
		//EqCircuit 6
		L=Math.sqrt(-(Math.pow(w21,2)*realy21-Math.pow(w22,2)*realy22)*(realy21-realy22))/((Math.pow(w21,2)-Math.pow(w22,2))*realy21*realy22);
		L=Math.abs(L);
		R=(Math.sqrt(1-4*Math.pow(L, 2)*Math.pow(w21, 2)*Math.pow(realy21, 2))+1)/(2*realy21);
		R=Math.abs(R);
		C=(imagy21+(w21*L/(Math.pow(w21*L, 2)+Math.pow(R, 2))))/w21;
		C=Math.abs(C);
		circuits.get(6).setParameter(0, R); 				//R
		circuits.get(6).setParameter(5, C); 				//C
		circuits.get(6).setParameter(4, L); 				//L
		System.out.println("R="+R);
		System.out.println("C="+C);
		System.out.println("L="+L);
		
		//EqCircuit 7
		C=Math.sqrt(-(Math.pow(w21,2)*realz21-Math.pow(w22,2)*realz22)*(realz21-realz22))/((Math.pow(w21,2)-Math.pow(w22,2))*realz21*realz22);
		C=Math.abs(C);
		Y=(Math.sqrt(1-4*Math.pow(C, 2)*Math.pow(w21, 2)*Math.pow(realz21, 2))+1)/(2*realz21);
		Y=Math.abs(Y);
		R=1/Y;
		L=(imagz21+(w21*C/(Math.pow(w21*C, 2)+Math.pow(Y, 2))))/w21;
		L=Math.abs(L);
		circuits.get(7).setParameter(0, R); 				//R
		circuits.get(7).setParameter(5, C); 				//C
		circuits.get(7).setParameter(4, L); 				//L
		System.out.println("R="+R);
		System.out.println("C="+C);
		System.out.println("L="+L);
				
	}

}