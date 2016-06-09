package ezrlc.ModelCalculation;

import java.util.ArrayList;
import java.util.List;

import ezrlc.Model.Model;
import ezrlc.ModelCalculation.MCEqCircuit.CircuitType;
import ezrlc.RFData.RFData;
import ezrlc.util.Complex;
import ezrlc.util.MathUtil;

/**
 * Worker Class that calculates a new equivalent circuit model Usage: MCWorker
 * worker = new MCWorker("MCWorker-1"); worker.start();
 * 
 * @author noah
 *
 */
public class MCWorker extends Thread {

	// ================================================================================
	// Public Data
	// ================================================================================
	public enum WorkerMode {
		NORMAL, OPT_ONLY
	};

	// ================================================================================
	// Private Data
	// ================================================================================
	private Model parentModel;
	private Thread t;
	private String workerName;

	private MCOptions ops;
	private MCEqCircuit eqCircuit;

	private WorkerMode workerMode;

	private boolean stopWorker = false;
	
	// ================================================================================
	// Constructor
	// ================================================================================
	public MCWorker(Model parent, String s) {
		this.parentModel = parent;
		this.workerName = s;
	}

	// ================================================================================
	// Public methods
	// ================================================================================
	/**
	 * Sets the model creation options
	 * 
	 * @param options options
	 */
	public void setMCOptions(MCOptions options) {
		ops = options;
		workerMode = WorkerMode.NORMAL;
	}

	/**
	 * Set the model to be optimized
	 * 
	 * @param mcEqCircuit
	 *            MCEqCircuit
	 */
	public void setEQCircuit(MCEqCircuit mcEqCircuit) {
		eqCircuit = mcEqCircuit;
		ops = eqCircuit.getOps();
		workerMode = WorkerMode.OPT_ONLY;
	}

	// ================================================================================
	// Start Method
	// ================================================================================
	/**
	 * Starts the thread
	 */
	public void start() {
		System.out.println("Starting " + workerName);
		if (t == null) {
			t = new Thread(this, workerName);
			t.start();
		}
	}

	// ================================================================================
	// Run method
	// ================================================================================
	/**
	 * Is called after the thread is started, thread is stopped after exit of
	 * this method
	 */
	public void run() {
		// Data
		RFData rfData = parentModel.getRFData();
		// Results of analytical solver
		List<MCEqCircuit> solverCircuits;
		// Results of the three branches
		List<MCEqCircuit> branch1, branch2, branch3;
		// three branches merged and sorted
		List<MCEqCircuit> rank;

		Thread t1_0 = null;
		Thread t1_1 = null;
		Thread t2_0 = null;
		Thread t2_1 = null;
		boolean do3ElementOptimize = false;
		boolean doExpand = true;

		// ----------------------------------------
		// Get Data and scale
		// ----------------------------------------
		// get necessary data
		double[] f = rfData.getfData();
		Complex[] s = rfData.getSData(50);
		Complex[] z = rfData.getzData();

		// S Scaler
		double rref = MCSScaler.scale(s);

		// Get new S Data
		s = rfData.getSData(rref);
		// s = rfData.getSData(50.0);

		// if mode is optimize only, do it now
		if (workerMode == WorkerMode.OPT_ONLY) {
			Complex[] ys = MCUtil.applyMCOpsToData(eqCircuit.getOps(), f, s);
			eqCircuit.setZ0(rref);
			eqCircuit.optimize(ys);
			success(eqCircuit);
			return;
		}

		// ----------------------------------------
		// Apply OPS
		// ----------------------------------------
		// apply ops
		double[] w = MCUtil.applyMCOpsToF(ops, f, MCUtil.DATA_FORMAT.OMEGA);
		Complex[] ys = MCUtil.applyMCOpsToData(ops, f, s);
		Complex[] yz = MCUtil.applyMCOpsToData(ops, f, z);

		// Create model index list
		CircuitType[] circuitIdxes = MCUtil.createModelList(ops);

		// Create equivalent circuit models
		List<MCEqCircuit> userCircuits = new ArrayList<MCEqCircuit>(circuitIdxes.length);
		for (CircuitType type : circuitIdxes) {
			userCircuits.add(new MCEqCircuit(type));
			userCircuits.get(userCircuits.size() - 1).setWVector(w);
			userCircuits.get(userCircuits.size() - 1).setZ0(rref);
		}

		// Create circuit list for analytical solver
		CircuitType[] solverCircuitTypes = { CircuitType.MODEL0, CircuitType.MODEL1, CircuitType.MODEL2,
				CircuitType.MODEL3, CircuitType.MODEL4, CircuitType.MODEL5, CircuitType.MODEL6, CircuitType.MODEL7 };
		solverCircuits = new ArrayList<MCEqCircuit>(8);
		for (int i = 0; i < 8; i++) {
			solverCircuits.add(new MCEqCircuit(solverCircuitTypes[i]));
			solverCircuits.get(solverCircuits.size() - 1).setWVector(w);
			solverCircuits.get(solverCircuits.size() - 1).setZ0(rref);
		}

		// Do analytical solving
		analyticalSolver(w, yz, ys, solverCircuits);
		// solverCircuits contains solved circuits 0..8

		// Apply solver parameters
		for (int i = 0; i < solverCircuits.size(); i++) {
			for (int j = 0; j < userCircuits.size(); j++) {
				if (userCircuits.get(j).getCircuitType() == solverCircuits.get(i).getCircuitType()) {
					userCircuits.get(j).setParameters(solverCircuits.get(i).getParameters());
				}
			}
		}

		// apply manual parameters
		for (int i = 0; i < ops.paramsAuto.length; i++) {
			if (ops.paramsAuto[i] == false) {
				for (MCEqCircuit circuit : userCircuits) {
					circuit.setParameter(i, ops.params[i]);
				}
			}
		}

		// Generate rank of equivalent circuits
		ArrayList<MCEqCircuit> sortedList = MCRank.sortByErrorZAbs(ys, solverCircuits);

		// ----------------------------------------
		// Check which mode is requested
		// ----------------------------------------
		if (ops.modelAutoSelect == false) {
			MCEqCircuit eqc = createManualCircuit();
			eqc.setWVector(w);
			eqc.setOps(ops);
			success(eqc);
			return;
		}

		int min = ops.nElementsMin;
		int max = ops.nElementsMax;
		if (((min < 4) && (max > 2)) || ops.nElementsMinAuto)
			do3ElementOptimize = true;

		if (max < 3)
			doExpand = false;

		// ----------------------------------------
		// Branch 2
		// Models with 2 elements optimize
		// ----------------------------------------
		branch2 = new ArrayList<MCEqCircuit>(4);
		// get 4 best circuits with 3 elements
		for (int i = 0; i < sortedList.size(); i++) {
			if (MCUtil.modelNParameters[sortedList.get(i).getCircuitType().ordinal()] == 2) {
				branch2.add(sortedList.get(i));
			}
			// This should never happen, but just for safety..
			if (branch2.size() >= 4)
				break;
		}
		// get 2 best
		branch2 = MCRank.sortByErrorZAbs(yz, branch2, 2);
		// Optimize them
		branch2.get(0).optimizeThreaded(ys);
		t2_0 = new Thread(branch2.get(0), "EQC-Thread-t2_0");
		t2_0.start();
		branch2.get(1).optimizeThreaded(ys);
		t2_1 = new Thread(branch2.get(1), "EQC-Thread-t2_1");
		t2_1.start();

		// ----------------------------------------
		// Branch 1
		// Models with 3 elements optimize
		// ----------------------------------------
		branch1 = new ArrayList<MCEqCircuit>(4);
		if (do3ElementOptimize) {
			// get 4 best circuits with 3 elements
			for (int i = 0; i < sortedList.size(); i++) {
				if (MCUtil.modelNParameters[sortedList.get(i).getCircuitType().ordinal()] == 3) {
					branch1.add(sortedList.get(i));
				}
				// This should never happen, but just for safety..
				if (branch1.size() >= 4)
					break;
			}
			// get 2 best
			branch1 = MCRank.sortByErrorZAbs(yz, branch1, 2);
			// Optimize them
			branch1.get(0).optimizeThreaded(ys);
			t1_0 = new Thread(branch1.get(0), "EQC-Thread-t1_0");
			t1_0.start();
			branch1.get(1).optimizeThreaded(ys);
			t1_1 = new Thread(branch1.get(1), "EQC-Thread-t1_1");
			t1_1.start();
		}

		// ----------------------------------------
		// Branch 3
		// extend optimized Models with 2 elements
		// ----------------------------------------
		branch3 = new ArrayList<MCEqCircuit>(2);
		if (doExpand) {
			// Wait for thread completion
			try {
				t2_0.join();
				t2_1.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Calculate rank
			branch2 = MCRank.sortByErrorZAbs(yz, branch2, 2);

			branch3.add(MCExpander.expand(branch2.get(0), yz, w));
			branch3.add(MCExpander.expand(branch2.get(1), yz, w));
		}

		// ----------------------------------------
		// Last rank
		// ----------------------------------------

		// Wait for all threads completion
		try {
			if (do3ElementOptimize) {
				t1_0.join();
				t1_1.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// merge three branches
		rank = new ArrayList<MCEqCircuit>(6);
		if (do3ElementOptimize) {
			rank.add(branch1.get(0));
			rank.add(branch1.get(1));
		}
		rank.add(branch2.get(0));
		rank.add(branch2.get(1));
		if (doExpand) {
			rank.add(branch3.get(0));
			rank.add(branch3.get(1));
		}
		// Sort them
		rank = MCRank.sortByErrorZAbs(yz, rank, 1);

		// Done doing magic
		rank.get(0).setOps(ops);
		success(rank.get(0));
	}

	/**
	 * Creates an equivalent circuit without optimizing. adds default parameters
	 * @return
	 */
	private MCEqCircuit createManualCircuit() {
		MCEqCircuit eqc = new MCEqCircuit(CircuitType.values()[ops.modelID]);
		// Copy default parameters
		double[] p = { 1e-3, 1e3, 1.0, 1e-3, 1e-9, 1e-12, 1e-12 };
		if (ops.params[0] != 0.0)
			p[0] = ops.params[0];
		if (ops.params[1] != 0.0)
			p[1] = ops.params[1];
		if (ops.params[2] != 0.0)
			p[2] = ops.params[2];
		if (ops.params[3] != 0.0)
			p[3] = ops.params[3];
		if (ops.params[4] != 0.0)
			p[4] = ops.params[4];
		if (ops.params[5] != 0.0)
			p[5] = ops.params[5];
		if (ops.params[6] != 0.0)
			p[6] = ops.params[6];
		eqc.setParameters(p);
		return eqc;
	}

	// ================================================================================
	// Private methods
	// ================================================================================
	private void success(MCEqCircuit eqc) {
		if(stopWorker == false) {
			System.out.println("MCWorker " + workerName + " has successfully completed");
			parentModel.mcWorkerSuccess(eqc, this.workerMode);
		}
	}

	/**
	 * Does analytical solving stuff
	 * 
	 * @param w
	 *            frequency vecotr in omega
	 * @param yz
	 *            measured impedance data
	 * @param ys
	 *            measured scattering data
	 * @param circuits
	 *            list of possible circuit models
	 */
	private void analyticalSolver(double[] w, Complex[] yz, Complex[] ys, List<MCEqCircuit> circuits) {
		// circuits.get(0).getS()[0].abs()
		double yzAbs[] = new double[yz.length];
		double ysAbs[] = new double[ys.length];
		for (int i = 0; i < ys.length; i++) { // ysAbs
			ysAbs[i] = ys[i].abs();
		}
		for (int i = 0; i < yz.length; i++) { // yzAbs
			yzAbs[i] = yz[i].abs();
		}

		double[] diffSAbs = new double[ys.length - 1];

		double R;
		double Y;
		double L;
		double C;

		// Sinnvolle Messpunkte ausw�hlen
		diffSAbs = MathUtil.diff(ysAbs);
		int index1 = MathUtil.getMaxIndex(diffSAbs);
		if (index1 == 0) {
			index1 = 1;
		}
		int index2 = MathUtil.getMinIndex(diffSAbs);
		if (index2 == 0) {
			index2 = 1;
		}

		double w21 = w[index1];
		double w22 = w[index2];

		double realz21 = yz[index1].re();
		double realz22 = yz[index2].re();
		double imagz21 = yz[index1].im();
		double imagz22 = yz[index2].im();

		double realy21 = Complex.div(new Complex(1, 0), yz[index1]).re();
		double realy22 = Complex.div(new Complex(1, 0), yz[index2]).re();
		double imagy21 = Complex.div(new Complex(1, 0), yz[index1]).im();
		double imagy22 = Complex.div(new Complex(1, 0), yz[index2]).im();

		// EqCircuit 0
		circuits.get(0).setParameter(4, imagz21 / w21); // L
		circuits.get(0).setParameter(0, realz21); // R

		// EqCircuit 1
		circuits.get(1).setParameter(0, 1 / realy21); // R
		circuits.get(1).setParameter(4, -1 / (w21 * imagy21)); // L

		// EqCircuit 2
		circuits.get(2).setParameter(0, realz21); // R
		circuits.get(2).setParameter(5, -1 / (w21 * imagz21)); // C

		// EqCircuit 3
		circuits.get(3).setParameter(5, imagy21 / w21); // C
		circuits.get(3).setParameter(0, 1 / (realy21)); // R

		// EqCircuit 4
		C = (-(Math.pow(w21, 2) - Math.pow(w22, 2))) / (w21 * w22 * (w21 * imagz22 - w22 * imagz21));
		L = (imagz21 + 1 / (w21 * C)) / w21;
		circuits.get(4).setParameter(0, realz21); // R
		circuits.get(4).setParameter(5, C); // C
		circuits.get(4).setParameter(4, L); // L

		// EqCircuit 5
		L = (-(Math.pow(w21, 2) - Math.pow(w22, 2))) / (w21 * w22 * (w21 * imagy22 - w22 * imagy21));
		C = (imagy21 + 1 / (w21 * L)) / w21;
		circuits.get(5).setParameter(0, 1 / realy21); // R
		circuits.get(5).setParameter(5, C); // C
		circuits.get(5).setParameter(4, L); // L

		// EqCircuit 6
		L = Math.sqrt(-(Math.pow(w21, 2) * realy21 - Math.pow(w22, 2) * realy22) * (realy21 - realy22))
				/ ((Math.pow(w21, 2) - Math.pow(w22, 2)) * realy21 * realy22);
		L = Math.abs(L);
		R = (Math.sqrt(1 - 4 * Math.pow(L, 2) * Math.pow(w21, 2) * Math.pow(realy21, 2)) + 1) / (2 * realy21);
		R = Math.abs(R);
		C = (imagy21 + (w21 * L / (Math.pow(w21 * L, 2) + Math.pow(R, 2)))) / w21;
		C = Math.abs(C);
		circuits.get(6).setParameter(0, R); // R
		circuits.get(6).setParameter(5, C); // C
		circuits.get(6).setParameter(4, L); // L

		// EqCircuit 7
		C = Math.sqrt(-(Math.pow(w21, 2) * realz21 - Math.pow(w22, 2) * realz22) * (realz21 - realz22))
				/ ((Math.pow(w21, 2) - Math.pow(w22, 2)) * realz21 * realz22);
		C = Math.abs(C);
		Y = (Math.sqrt(1 - 4 * Math.pow(C, 2) * Math.pow(w21, 2) * Math.pow(realz21, 2)) + 1) / (2 * realz21);
		Y = Math.abs(Y);
		R = 1 / Y;
		L = (imagz21 + (w21 * C / (Math.pow(w21 * C, 2) + Math.pow(Y, 2)))) / w21;
		L = Math.abs(L);
		circuits.get(7).setParameter(0, R); // R
		circuits.get(7).setParameter(5, C); // C
		circuits.get(7).setParameter(4, L); // L
	}

	/**
	 * Stops the worker
	 */
	public void stopWork() {
		this.stopWorker = true;
	}

}
