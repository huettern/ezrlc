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
		
	}


}
