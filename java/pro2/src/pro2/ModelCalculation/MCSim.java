package pro2.ModelCalculation;

import pro2.util.Complex;

/**
 * Calculates the frequency-response of a model
 * Parameter array format
 * 
 * R0	f0	alpha	R1	L	C0	C2
 * 
 * @author noah
 *
 */
public class MCSim {

	//================================================================================
    // Private Data
    //================================================================================
	
	
	
	

	//================================================================================
    // Constructor
    //================================================================================
	public MCSim() {
		// TODO Auto-generated constructor stub
	}
	
	//================================================================================
    // Private Functions
    //================================================================================
	private static double[][] model0 (double[] w, double[] p) {
		return null;
	}
	private static double[][] model1 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model2 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model3 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model4 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model5 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model6 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model7 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model8 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model9 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model10 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model11 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model12 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model13 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model14 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model15 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model16 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model17 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model18 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model19 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	private static double[][] model20 (double[] w, double[] p) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//================================================================================
    // Public Static Functions
    //================================================================================
	
	/**
	 * Returns the scattering parameters of a given model, freq and params
	 * @param idx model index
	 * @param w frequency vecotr in omega
	 * @param a parameter list
	 * @return Complex array with scattering parameters
	 */
	public static final Complex[] getS (int idx, double[] w, double[] param) {
		double[][] pol;
		
		switch(idx) {
			case 0: pol = model0(w,param); break;
			case 1: pol = model1(w,param); break;
			case 2: pol = model2(w,param); break;
			case 3: pol = model3(w,param); break;
			case 4: pol = model4(w,param); break;
			case 5: pol = model5(w,param); break;
			case 6: pol = model6(w,param); break;
			case 7: pol = model7(w,param); break;
			case 8: pol = model8(w,param); break;
			case 9: pol = model9(w,param); break;
			case 10: pol = model10(w,param); break;
			case 11: pol = model11(w,param); break;
			case 12: pol = model12(w,param); break;
			case 13: pol = model13(w,param); break;
			case 14: pol = model14(w,param); break;
			case 15: pol = model15(w,param); break;
			case 16: pol = model16(w,param); break;
			case 17: pol = model17(w,param); break;
			case 18: pol = model18(w,param); break;
			case 19: pol = model19(w,param); break;
			case 20: pol = model20(w,param); break;
		}
		return null;
	}

	
	

}
