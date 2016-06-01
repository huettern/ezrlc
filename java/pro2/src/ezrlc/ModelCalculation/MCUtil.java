package ezrlc.ModelCalculation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ezrlc.ModelCalculation.MCEqCircuit.CircuitType;
import ezrlc.ModelCalculation.MCUtil.DATA_FORMAT;
import ezrlc.util.Complex;

/**
 * Helper Functions and definitions for Model calculation
 * @author noah
 *
 */
public class MCUtil {

	//================================================================================
    // Public Data
    //================================================================================
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
	public final static int[] modelNElements = {2,2,2,2,3,3,3,3,3,4,4,4,4,3,3,3,3,4,4,4,4};
	
	public static enum DATA_FORMAT {HZ, OMEGA}

	public final static int[][] parameter2TopoIdx = {
			{0,99,99,99,1,99,99},
			{0,99,99,99,1,99,99},
			{0,99,99,99,99,1,99},
			{0,99,99,99,99,1,99},
			{0,99,99,99,1,2,99},
			{0,99,99,99,1,2,99},
			{0,99,99,99,1,2,99},
			{0,99,99,99,1,2,99},
			{0,99,99,1,99,2,99},
			{0,99,99,1,2,3,99},
			{0,99,99,1,2,3,99},
			{0,99,99,1,2,3,99},
			{0,99,99,99,1,2,3},
			{0,1,2,99,3,4,99},
			{0,1,2,99,3,4,99},
			{0,1,2,99,3,4,99},
			{0,1,2,3,99,4,99},
			{0,1,2,3,4,5,99},
			{0,1,2,3,4,5,99},
			{0,1,2,3,4,5,99},
			{0,1,2,99,3,4,5}
	};


	//================================================================================
    // Constructor
    //================================================================================
	public MCUtil() {
		// TODO Auto-generated constructor stub
	}

	//================================================================================
    // Public static functions
    //================================================================================
	
	/**
	 * Applies the MCOptions to the frequency vector
	 * @param ops MCOptions given by user
	 * @param f frequency data in Hertz
	 * @param format Data output format omega or hertz
	 * @return w data
	 */
	public static final double[] applyMCOpsToF (MCOptions ops, double[] f, DATA_FORMAT format) {
		// conver f to w
		double mul = 1;
		if(format == DATA_FORMAT.OMEGA) mul = 2.0*Math.PI; 

		double wMin = mul*ops.fMin;
		double wMax = mul*ops.fMax; 
		if(ops.fMaxAuto) wMax = Double.MAX_VALUE;
		if(ops.fMinAuto) wMin = -Double.MAX_VALUE;
		
		double[] w = new double[f.length];
		for(int ctr = 0; ctr < w.length; ctr++) {
			w[ctr] = mul*f[ctr];
		}
		
		// Limit f-range
		if (ops.fMax < ops.fMin) { System.err.println("Max smaller min"); return null; }
		int idxLow = 0;
		int idxHigh = w.length;
		// search low limit
		for(int ctr = 0; ctr < w.length; ctr++) {
			if(w[ctr] >= wMin) {
				idxLow = ctr;
				break;
			}
		}
		// search high limit
		for(int ctr = w.length-1; ctr > -1 ; ctr--) {
			if(w[ctr] <= wMax) {
				idxHigh = ctr;
				break;
			}
		}
//		// copy to new array
		double[] w_out = new double[idxHigh-idxLow+1];
		if(format == DATA_FORMAT.OMEGA) {
			System.arraycopy(w, idxLow, w_out, 0, idxHigh-idxLow+1);
		}
		// convert back to HZ
		int idx_ctr = 0;
		if(format == DATA_FORMAT.HZ) {
			for(int ctr = idxLow; ctr < idxHigh+1; ctr++) {
				w_out[idx_ctr++] = w[ctr]/(mul);
			}
		}
		return w_out;
	}

	/**
	 * Applies MCOptions to data
	 * @param ops MCOptions given by user
	 * @param f frequency data in Hertz
	 * @param data 
	 * @return data array out, cut to the f-range
	 */
	public static final double[] applyMCOpsToData (MCOptions ops, double[] f, double[] data) {
		// conver f to w
		double wMin = 2.0*Math.PI*ops.fMin;
		double wMax = 2.0*Math.PI*ops.fMax; 
		if(ops.fMaxAuto) wMax = Double.MAX_VALUE;
		if(ops.fMinAuto) wMin = -Double.MAX_VALUE;
		double[] w = new double[f.length];
		for(int ctr = 0; ctr < w.length; ctr++) {
			w[ctr] = 2.0*Math.PI*f[ctr];
		}
		
		// Limit f-range
		if (ops.fMax < ops.fMin) { System.err.println("Max smaller min"); return null; }
		int idxLow = 0;
		int idxHigh = w.length;
		// search low limit
		for(int ctr = 0; ctr < w.length; ctr++) {
			if(w[ctr] >= wMin) {
				idxLow = ctr;
				break;
			}
		}
		// search high limit
		for(int ctr = w.length-1; ctr > -1 ; ctr--) {
			if(w[ctr] <= wMax) {
				idxHigh = ctr;
				break;
			}
		}
		
		// copy to new array
		double[] data_out = new double[idxHigh-idxLow+1];
		System.arraycopy(data, idxLow, data_out, 0, idxHigh-idxLow+1);
		return data_out;
	}
	/**
	 * Applies MCOptions to data
	 * @param opt MCOptions given by user
	 * @param f frequency data in Hertz
	 * @param data data int
	 * @return data array out, cut to the f-range
	 */
	public static final Complex[] applyMCOpsToData (MCOptions opt, double[] f, Complex[] data) {
		// extract real and imag data
		double[] real = new double[data.length];
		double[] imag = new double[data.length];
		for(int i = 0; i < data.length; i++) {
			real[i] = data[i].re();
			imag[i] = data[i].im();
		}
		
		// apply ops
		real = applyMCOpsToData(opt, f, real);
		imag = applyMCOpsToData(opt, f, imag);
		
		// rebuild array
		Complex[] res = new Complex[real.length];
		for(int i = 0; i < real.length; i++) {
			res[i]=(new Complex(real[i], imag[i]));
		}
		
		return res;
	}
	
	
	
	/**
	 * Creates a list of possible equivalent model indexes
	 * @param ops MCOptions given by user
	 * @return integer array, holding the possible equivalent model indexes
	 */
	public static final CircuitType[] createModelList (MCOptions ops) {
		CircuitType[] circuitList;
		
		// create list of equivalent models
		int num_models = 0;
		
		// if manual model selection
		if(ops.modelAutoSelect == false){
			circuitList = new CircuitType[1];
			circuitList[0] = CircuitType.values()[ops.modelID];
			return circuitList;
		}

		if(ops.nElementsMaxAuto) ops.nElementsMax = Integer.MAX_VALUE;
		if(ops.nElementsMinAuto) ops.nElementsMin = 0;
		
		// count how many there are without skin effect
		for(int ctr = 0; ctr < MCUtil.nModelSkinStart; ctr++) {
			if(modelNElements[ctr] >= ops.nElementsMin && modelNElements[ctr] <= ops.nElementsMax) 
				num_models++;
		}
		// count how many there are without skin effect
		int num_models_skin = 0;
//		if(opt.skinEffectEnabled == true) {
//			for(int ctr = MCUtil.nModelSkinStart; ctr < MCUtil.nModels; ctr++) {
//				if(modelNElements[ctr] >= opt.nElementsMin && modelNElements[ctr] <= opt.nElementsMax) 
//					num_models_skin++;
//			}
//		}
		
		// save the indexes in a array
		int[] modelIdx = new int[num_models + num_models_skin];
		int modelIdxCtr = 0;
		for(int ctr = 0; ctr < MCUtil.nModelSkinStart; ctr++) {
			if(modelNElements[ctr] >= ops.nElementsMin && modelNElements[ctr] <= ops.nElementsMax) 
				modelIdx[modelIdxCtr++]=ctr;
		}
//		if(opt.skinEffectEnabled == true) {
//			for(int ctr = MCUtil.nModelSkinStart; ctr < MCUtil.nModels; ctr++) {
//				if(modelNElements[ctr] >= opt.nElementsMin && modelNElements[ctr] <= opt.nElementsMax) 
//					modelIdx[modelIdxCtr++]=ctr;
//			}
//		}
		
		circuitList = new CircuitType[modelIdx.length];
		for (int i = 0; i < modelIdx.length; i++) {
			circuitList[i]=modelIdxToCircuitType(modelIdx[i]);
		}
		return circuitList;
	}
	
	/**
	 * Converts an integer index to the coresponding model enumeration entry
	 * @param idx index of equivalent circuit
	 * @return CIRCUIT_TYPE
	 */
	public static final CircuitType modelIdxToCircuitType (int idx){
		return MCEqCircuit.CircuitType.values()[idx];
	}
	
	/**
	 * Converts a shortenned, CircuitType specific parameterlist p to the universal parameter list
	 * @param t topology
	 * @param p short form parameters
	 * @return
	 */
	public static final double[] topo2Param (CircuitType t, double[] p) {
		double[] res = {0,0,0,0,0,0,0};
		
		switch(t){
		case MODEL0: res[0] = p[0]; res[4] = p[1];
			break;
		case MODEL1: res[0] = p[0]; res[4] = p[1];
			break;
		case MODEL2: res[0] = p[0]; res[5] = p[1];
			break;
		case MODEL3: res[0] = p[0]; res[4] = p[1];
			break;
		case MODEL4: res[0] = p[0]; res[4] = p[1]; res[5] = p[2];
			break;
		case MODEL5: res[0] = p[0]; res[4] = p[1]; res[5] = p[2];
			break;
		case MODEL6: res[0] = p[0]; res[4] = p[1]; res[5] = p[2];
			break;
		case MODEL7: res[0] = p[0]; res[4] = p[1]; res[5] = p[2];
			break;
		case MODEL8: res[0] = p[0]; res[3] = p[1]; res[5] = p[2];
			break;
		case MODEL9: res[0] = p[0]; res[3] = p[1]; res[5] = p[2]; res[5] = p[3];
			break;
		case MODEL10: res[0] = p[0]; res[3] = p[1]; res[4] = p[2]; res[5] = p[3];
			break;
		case MODEL11: res[0] = p[0]; res[3] = p[1]; res[4] = p[2]; res[5] = p[3];
			break;
		case MODEL12: res[0] = p[0]; res[4] = p[1]; res[5] = p[2]; res[6] = p[3];
			break;
		case MODEL13: res[0] = p[0]; res[1] = p[1]; res[2] = p[2]; res[4] = p[3]; res[5] = p[4];
			break;
		case MODEL14: res[0] = p[0]; res[1] = p[1]; res[2] = p[2]; res[4] = p[3]; res[5] = p[4];
			break;
		case MODEL15: res[0] = p[0]; res[1] = p[1]; res[2] = p[2]; res[4] = p[3]; res[5] = p[4];
			break;
		case MODEL16: res[0] = p[0]; res[1] = p[1]; res[2] = p[2]; res[3] = p[3]; res[5] = p[4];
			break;
		case MODEL17: res[0] = p[0]; res[1] = p[1]; res[2] = p[2]; res[3] = p[3]; res[4] = p[4]; res[5] = p[5];
			break;
		case MODEL18: res[0] = p[0]; res[1] = p[1]; res[2] = p[2]; res[3] = p[3]; res[4] = p[4]; res[5] = p[5];
			break;
		case MODEL19: res[0] = p[0]; res[1] = p[1]; res[2] = p[2]; res[3] = p[3]; res[4] = p[4]; res[5] = p[5];
			break;
		case MODEL20: res[0] = p[0]; res[1] = p[1]; res[2] = p[2]; res[4] = p[3]; res[5] = p[4]; res[6] = p[5];
			break;
		}
		
		return res;
	}
	
	/**
	 * Shortens the input array by deleting all zero valeus
	 * @param p parameter array
	 * @param type Circuit type
	 * @return shortenned parameter array
	 */
	public static final double[] shortenParam (CircuitType type, double[] p) {
//		int ctr = 0;
//		int ctr2 = 0;
//		for (int i = 0; i < p.length; i++){
//			if(p[i] != 0.0) ctr++;
//		}
		// copy
		int n = modelNElements[type.ordinal()];
		double[] res = new double[n];
//		for (int i = 0; i < p.length; i++){
//			if(p[i] != 0.0) res[ctr2++] = p[i];
//		}
//		
		for(int i = 0; i < 7; i++) {
			if(parameter2TopoIdx[type.ordinal()][i] <= n){
				res[parameter2TopoIdx[type.ordinal()][i]] = p[i];
			}
		}
		
		return res;
	}
}
