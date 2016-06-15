package ezrlc.ModelCalculation;

import ezrlc.ModelCalculation.MCEqCircuit.CircuitType;
import ezrlc.util.Complex;

/**
 * Helper Functions and definitions for Model calculation
 * 
 * @author noah
 *
 */
public class MCUtil {

	// ================================================================================
	// Public Data
	// ================================================================================
	/**
	 * Holds the number of equivalent circuits
	 */
	public final static int nModels = 21;

	/**
	 * Where the models using skin effect start
	 */
	public final static int nModelSkinStart = 13;

	/**
	 * holds the number of elements in a model
	 */
	public final static int[] modelNParameters = { 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6 };

	public static enum DATA_FORMAT {
		HZ, OMEGA
	}

	/**
	 * LUT to convert full parameter list to short parameter list for optimizing
	 * and vice versa
	 */
	public final static int[][] parameter2TopoIdx = { { 0, 99, 99, 99, 1, 99, 99 }, { 0, 99, 99, 99, 1, 99, 99 },
			{ 0, 99, 99, 99, 99, 1, 99 }, { 0, 99, 99, 99, 99, 1, 99 }, { 0, 99, 99, 99, 1, 2, 99 },
			{ 0, 99, 99, 99, 1, 2, 99 }, { 0, 99, 99, 99, 1, 2, 99 }, { 0, 99, 99, 99, 1, 2, 99 },
			{ 0, 99, 99, 1, 99, 2, 99 }, { 0, 99, 99, 1, 2, 3, 99 }, { 0, 99, 99, 1, 2, 3, 99 },
			{ 0, 99, 99, 1, 2, 3, 99 }, { 0, 99, 99, 99, 1, 2, 3 }, { 0, 1, 2, 99, 3, 4, 99 },
			{ 0, 1, 2, 99, 3, 4, 99 }, { 0, 1, 2, 99, 3, 4, 99 }, { 0, 1, 2, 3, 99, 4, 99 }, { 0, 1, 2, 3, 4, 5, 99 },
			{ 0, 1, 2, 3, 4, 5, 99 }, { 0, 1, 2, 3, 4, 5, 99 }, { 0, 1, 2, 99, 3, 4, 5 } };

	// ================================================================================
	// Constructor
	// ================================================================================
	public MCUtil() {
	}

	// ================================================================================
	// Public static functions
	// ================================================================================

	/**
	 * Applies the MCOptions to the frequency vector
	 * 
	 * @param ops
	 *            MCOptions given by user
	 * @param f
	 *            frequency data in Hertz
	 * @param format
	 *            Data output format omega or hertz
	 * @return w data
	 */
	public static final double[] applyMCOpsToF(MCOptions ops, double[] f, DATA_FORMAT format) {
		// conver f to w
		double mul = 1;
		if (format == DATA_FORMAT.OMEGA)
			mul = 2.0 * Math.PI;

		double wMin = (2.0 * Math.PI * ops.fMin) + Double.MIN_VALUE;
		double wMax = 2.0 * Math.PI * ops.fMax;
		if (ops.fMaxAuto)
			wMax = Double.MAX_VALUE;
		if (ops.fMinAuto)
			wMin = Double.MIN_VALUE;

		double[] w = new double[f.length];
		for (int ctr = 0; ctr < w.length; ctr++) {
			w[ctr] = mul * f[ctr];
		}

		// Limit f-range
		if (ops.fMax < ops.fMin) {
			System.err.println("Max smaller min");
			return null;
		}
		int idxLow = 0;
		int idxHigh = w.length;
		// search low limit
		for (int ctr = 0; ctr < w.length; ctr++) {
			if (w[ctr] >= wMin) {
				idxLow = ctr;
				break;
			}
		}
		// search high limit
		for (int ctr = w.length - 1; ctr > -1; ctr--) {
			if (w[ctr] <= wMax) {
				idxHigh = ctr;
				break;
			}
		}
		// // copy to new array
		double[] w_out = new double[idxHigh - idxLow + 1];
		if (format == DATA_FORMAT.OMEGA) {
			System.arraycopy(w, idxLow, w_out, 0, idxHigh - idxLow + 1);
		}
		// convert back to HZ
		int idx_ctr = 0;
		if (format == DATA_FORMAT.HZ) {
			for (int ctr = idxLow; ctr < idxHigh + 1; ctr++) {
				w_out[idx_ctr++] = w[ctr] / (mul);
			}
		}
		return w_out;
	}

	/**
	 * Returns the first index of the given f-vector that is affected by the
	 * MCOptions, including cutting all 0-frequency points
	 * 
	 * @param ops
	 *            MCOptions
	 * @param f
	 *            frequency vector being processed in Hertz
	 * @return first index of the f array that is in the allowed range
	 */
	public static int getFirstFIndex(MCOptions ops, double[] f) {
		int idx = 0;

		double fMin = (ops.fMin) + Double.MIN_VALUE;
		if (ops.fMinAuto)
			fMin = Double.MIN_VALUE;

		// search low limit
		for (int ctr = 0; ctr < f.length; ctr++) {
			if (f[ctr] >= fMin) {
				idx = ctr;
				break;
			}
		}

		return idx;
	}

	/**
	 * Applies MCOptions to data
	 * 
	 * @param ops
	 *            MCOptions given by user
	 * @param f
	 *            frequency data in Hertz
	 * @param data
	 *            data
	 * @return data array out, cut to the f-range
	 */
	public static final double[] applyMCOpsToData(MCOptions ops, double[] f, double[] data) {
		// conver f to w
		double wMin = 2.0 * Math.PI * ops.fMin + Double.MIN_VALUE;
		double wMax = 2.0 * Math.PI * ops.fMax;
		if (ops.fMaxAuto)
			wMax = Double.MAX_VALUE;
		if (ops.fMinAuto)
			wMin = Double.MIN_VALUE;
		double[] w = new double[f.length];
		for (int ctr = 0; ctr < w.length; ctr++) {
			w[ctr] = 2.0 * Math.PI * f[ctr];
		}

		// Limit f-range
		if (ops.fMax < ops.fMin) {
			System.err.println("Max smaller min");
			return null;
		}
		int idxLow = 0;
		int idxHigh = w.length;
		// search low limit
		for (int ctr = 0; ctr < w.length; ctr++) {
			if (w[ctr] >= wMin) {
				idxLow = ctr;
				break;
			}
		}
		// search high limit
		for (int ctr = w.length - 1; ctr > -1; ctr--) {
			if (w[ctr] <= wMax) {
				idxHigh = ctr;
				break;
			}
		}

		// copy to new array
		double[] data_out = new double[idxHigh - idxLow + 1];
		System.arraycopy(data, idxLow, data_out, 0, idxHigh - idxLow + 1);
		return data_out;
	}

	/**
	 * Applies MCOptions to data
	 * 
	 * @param opt
	 *            MCOptions given by user
	 * @param f
	 *            frequency data in Hertz
	 * @param data
	 *            data int
	 * @return data array out, cut to the f-range
	 */
	public static final Complex[] applyMCOpsToData(MCOptions opt, double[] f, Complex[] data) {
		// extract real and imag data
		double[] real = new double[data.length];
		double[] imag = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			real[i] = data[i].re();
			imag[i] = data[i].im();
		}

		// apply ops
		real = applyMCOpsToData(opt, f, real);
		imag = applyMCOpsToData(opt, f, imag);

		// rebuild array
		Complex[] res = new Complex[real.length];
		for (int i = 0; i < real.length; i++) {
			res[i] = (new Complex(real[i], imag[i]));
		}

		return res;
	}

	/**
	 * Creates a list of possible equivalent model indexes
	 * 
	 * @param ops
	 *            MCOptions given by user
	 * @return integer array, holding the possible equivalent model indexes
	 */
	public static final CircuitType[] createModelList(MCOptions ops) {
		CircuitType[] circuitList;

		// create list of equivalent models
		int num_models = 0;

		// if manual model selection
		if (ops.modelAutoSelect == false) {
			circuitList = new CircuitType[1];
			circuitList[0] = CircuitType.values()[ops.modelID];
			return circuitList;
		}

		if (ops.nElementsMaxAuto)
			ops.nElementsMax = Integer.MAX_VALUE;
		if (ops.nElementsMinAuto)
			ops.nElementsMin = 0;

		// count how many there are without skin effect
		for (int ctr = 0; ctr < MCUtil.nModelSkinStart; ctr++) {
			if (modelNParameters[ctr] >= ops.nElementsMin && modelNParameters[ctr] <= ops.nElementsMax)
				num_models++;
		}
		// count how many there are without skin effect
		int num_models_skin = 0;

		// save the indexes in a array
		int[] modelIdx = new int[num_models + num_models_skin];
		int modelIdxCtr = 0;
		for (int ctr = 0; ctr < MCUtil.nModelSkinStart; ctr++) {
			if (modelNParameters[ctr] >= ops.nElementsMin && modelNParameters[ctr] <= ops.nElementsMax)
				modelIdx[modelIdxCtr++] = ctr;
		}

		circuitList = new CircuitType[modelIdx.length];
		for (int i = 0; i < modelIdx.length; i++) {
			circuitList[i] = modelIdxToCircuitType(modelIdx[i]);
		}
		return circuitList;
	}

	/**
	 * Converts an integer index to the coresponding model enumeration entry
	 * 
	 * @param idx
	 *            index of equivalent circuit
	 * @return CIRCUIT_TYPE
	 */
	public static final CircuitType modelIdxToCircuitType(int idx) {
		return MCEqCircuit.CircuitType.values()[idx];
	}

	/**
	 * Converts a shortenned, CircuitType specific parameterlist p to the
	 * universal parameter list
	 * 
	 * @param t
	 *            topology
	 * @param p
	 *            short form parameters
	 * @return parameter list
	 */
	public static final double[] topo2Param(CircuitType t, double[] p) {
		boolean[] lock = { false, false, false, false, false, false, false };
		double[] pzero = { 0, 0, 0, 0, 0, 0, 0 };
		return MCUtil.topo2Param(t, p, lock, pzero);
	}

	/**
	 * Converts a shortenned, CircuitType specific parameterlist p to the
	 * universal parameter list, adding locked parameters
	 * 
	 * @param t
	 *            topology
	 * @param p
	 *            short form parameters
	 * @param lock
	 *            parameter lock array
	 * @param orig
	 *            original parameters
	 * @return parameter list
	 */
	public static final double[] topo2Param(CircuitType t, double[] p, boolean[] lock, double[] orig) {
		double[] res = new double[7];
		for(int i = 0; i < 7; i++) res[i] = orig[i];

		int ctr = 0;
		ctr = 0;
		switch (t) {
		case MODEL0:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			break;
		case MODEL1:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			break;
		case MODEL2:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL3:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL4:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL5:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL6:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL7:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL8:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[3] == false) res[3] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL9:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[3] == false) res[3] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL10:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[3] == false) res[3] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL11:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[3] == false) res[3] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL12:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			if(lock[6] == false) res[6] = p[ctr++];
			break;
		case MODEL13:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[1] == false) res[1] = p[ctr++];
			if(lock[2] == false) res[2] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL14:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[1] == false) res[1] = p[ctr++];
			if(lock[2] == false) res[2] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL15:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[1] == false) res[1] = p[ctr++];
			if(lock[2] == false) res[2] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL16:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[1] == false) res[1] = p[ctr++];
			if(lock[2] == false) res[2] = p[ctr++];
			if(lock[3] == false) res[3] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL17:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[1] == false) res[1] = p[ctr++];
			if(lock[2] == false) res[2] = p[ctr++];
			if(lock[3] == false) res[3] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL18:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[1] == false) res[1] = p[ctr++];
			if(lock[2] == false) res[2] = p[ctr++];
			if(lock[3] == false) res[3] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL19:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[1] == false) res[1] = p[ctr++];
			if(lock[2] == false) res[2] = p[ctr++];
			if(lock[3] == false) res[3] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			break;
		case MODEL20:
			if(lock[0] == false) res[0] = p[ctr++];
			if(lock[1] == false) res[1] = p[ctr++];
			if(lock[2] == false) res[2] = p[ctr++];
			if(lock[4] == false) res[4] = p[ctr++];
			if(lock[5] == false) res[5] = p[ctr++];
			if(lock[6] == false) res[6] = p[ctr++];
			break;
		}

		return res;
	}

	/**
	 * Shortens the input array by deleting all zero valeus
	 * 
	 * @param p
	 *            parameter array
	 * @param type
	 *            Circuit type
	 * @return shortenned parameter array
	 */
	public static final double[] shortenParam(CircuitType type, double[] p) {
		int n = modelNParameters[type.ordinal()];
		double[] res = new double[n];
		for (int i = 0; i < 7; i++) {
			if (parameter2TopoIdx[type.ordinal()][i] <= n) {
				res[parameter2TopoIdx[type.ordinal()][i]] = p[i];
			}
		}
		return res;
	}

	/**
	 * Shortens the input array by deleting all zero valeus and locked values
	 * 
	 * @param p
	 *            parameter array
	 * @param type
	 *            Circuit type
	 * @param lock
	 *            parameter lock array
	 * @return shortenned parameter array
	 */
	public static final double[] shortenParam(CircuitType type, double[] p, boolean[] lock) {
		int n = 0;
		for (int i = 0; i < 7; i++) {
			if ((lock[i] == false) && (parameter2TopoIdx[type.ordinal()][i] < 7))
				n++;
		}
		double[] res = new double[n];
		int ctr = 0;
		for (int i = 0; i < 7; i++) {
			if ((parameter2TopoIdx[type.ordinal()][i] < 7) && (lock[i] == false)) {
				res[ctr++] = p[i];
			}
		}
		return res;
	}
}
