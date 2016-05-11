package pro2.ModelCalculation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pro2.ModelCalculation.MCEqCircuit.CIRCUIT_TYPE;
import pro2.ModelCalculation.MCUtil.DATA_FORMAT;
import pro2.util.Complex;

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
	 * @param opt MCOptions given by user
	 * @param f frequency data in Hertz
	 * @patam format Data output format omega or hertz
	 * @return w data
	 */
	public static final double[] applyMCOpsToF (MCOptions opt, double[] f, DATA_FORMAT format) {
		// conver f to w
		double mul = 1;
		if(format == DATA_FORMAT.OMEGA) mul = 2.0*Math.PI; 
		
		double wMin = mul*opt.fMin;
		double wMax = mul*opt.fMax; 
		double[] w = new double[f.length];
		for(int ctr = 0; ctr < w.length; ctr++) {
			w[ctr] = mul*f[ctr];
		}
		
		// Limit f-range
		if (opt.fMax < opt.fMin) { System.err.println("Max smaller min"); return null; }
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
	 * @param opt MCOptions given by user
	 * @param f frequency data in Hertz
	 * @param data 
	 * @return data array out, cut to the f-range
	 */
	public static final double[] applyMCOpsToData (MCOptions opt, double[] f, double[] data) {
		// conver f to w
		double wMin = 2.0*Math.PI*opt.fMin;
		double wMax = 2.0*Math.PI*opt.fMax; 
		double[] w = new double[f.length];
		for(int ctr = 0; ctr < w.length; ctr++) {
			w[ctr] = 2.0*Math.PI*f[ctr];
		}
		
		// Limit f-range
		if (opt.fMax < opt.fMin) { System.err.println("Max smaller min"); return null; }
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
		for(int i = 0; i < data.length; i++) {
			res[i]=(new Complex(real[i], imag[i]));
		}
		
		return res;
	}
	
	
	
	/**
	 * Creates a list of possible equivalent model indexes
	 * @param opt MCOptions given by user
	 * @return integer array, holding the possible equivalent model indexes
	 */
	public static final CIRCUIT_TYPE[] createModelList (MCOptions opt) {
		// create list of equivalent models
		int num_models = 0;
		// count how many there are without skin effect
		for(int ctr = 0; ctr < MCUtil.nModelSkinStart; ctr++) {
			if(modelNElements[ctr] >= opt.nElementsMin && modelNElements[ctr] <= opt.nElementsMax) 
				num_models++;
		}
		// count how many there are without skin effect
		int num_models_skin = 0;
		if(opt.skinEffectEnabled == true) {
			for(int ctr = MCUtil.nModelSkinStart; ctr < MCUtil.nModels; ctr++) {
				if(modelNElements[ctr] >= opt.nElementsMin && modelNElements[ctr] <= opt.nElementsMax) 
					num_models_skin++;
			}
		}
		
		// save the indexes in a array
		int[] modelIdx = new int[num_models + num_models_skin];
		int modelIdxCtr = 0;
		for(int ctr = 0; ctr < MCUtil.nModelSkinStart; ctr++) {
			if(modelNElements[ctr] >= opt.nElementsMin && modelNElements[ctr] <= opt.nElementsMax) 
				modelIdx[modelIdxCtr++]=ctr;
		}
		if(opt.skinEffectEnabled == true) {
			for(int ctr = MCUtil.nModelSkinStart; ctr < MCUtil.nModels; ctr++) {
				if(modelNElements[ctr] >= opt.nElementsMin && modelNElements[ctr] <= opt.nElementsMax) 
					modelIdx[modelIdxCtr++]=ctr;
			}
		}
		
		CIRCUIT_TYPE[] circuitList = new CIRCUIT_TYPE[modelIdx.length];
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
	public static final CIRCUIT_TYPE modelIdxToCircuitType (int idx){
		return MCEqCircuit.CIRCUIT_TYPE.values()[idx];
	}
	
	
}
