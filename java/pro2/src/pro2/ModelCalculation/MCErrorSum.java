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
	/**
	 * Builds the square of the delta between measured and simulated and sums them up
	 * @param measured
	 * @param simulated
	 * @return error sum
	 */
	private static double leastSquare (double[] measured, double[] simulated) {
		double error = 0;
		double delta = 0;
		
		for(int ctr = 0; ctr < measured.length; ctr++) {
			delta = simulated[ctr] - measured[ctr];
			error = error + Math.pow(delta, 2);
		}
		
		return error;
	}
	
	//================================================================================
    // Public static Functions
    //================================================================================
	/**
	 * Returns the Error sum
	 * @param measured measured data
	 * @param simulated simulated data
	 * @return
	 */
	public static final double getError (double[] measured, double[] simulated) {
		return leastSquare(measured, simulated);
	}

	
	
}
