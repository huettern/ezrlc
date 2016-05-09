package pro2.ModelCalculation;

import pro2.util.Complex;

/**
 * Functions to get the error sum
 * @author noah
 *
 */
public class MCErrorSum {

	public MCErrorSum() {
		// TODO Auto-generated constructor stub
	}

	//================================================================================
    // Private Functions
    //================================================================================
	private static double leastSquare (int idx, double[] w, Complex[] ys, double[] param) {
		double error = 0;
		
		for(int ctr = 0; ctr < w.length; ctr++) {
			
		}
		
		return error;
	}
	
	//================================================================================
    // Public static Functions
    //================================================================================
	/**
	 * Returns the Error sum
	 * @param idx model index
	 * @param w frequency vector
	 * @param ys scattering data
	 * @param param parameter array
	 * @return double value of the error
	 */
	public static final double getError (int idx, double[] w, Complex[] ys, double[] param) {
		return leastSquare(idx,w,ys,param);
	}

	
	
}
