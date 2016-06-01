package ezrlc.ModelCalculation;

import ezrlc.util.Complex;

public class MCErrorArray {
	// ================================================================================
	// Private Data
	// ================================================================================
	private Complex[] measured;

	// ================================================================================
	// Constructors
	// ================================================================================
	/**
	 * Create new error array object
	 * 
	 * @param measured
	 *            measured data
	 * @param circuit
	 *            equivalent circuit object
	 */
	public MCErrorArray(Complex[] measured) {
		this.measured = new Complex[measured.length];
		System.arraycopy(measured, 0, this.measured, 0, measured.length);
	}

	// ================================================================================
	// Private Functions
	// ================================================================================
	/**
	 * Builds the square of the delta between measured and simulated and builds
	 * an Array
	 * 
	 * @param measured
	 * @param simulated
	 * @return error sum
	 */
	private static double[] deltaArray(double[] measured, double[] simulated) {
		double[] delta = new double[measured.length];

		for (int ctr = 0; ctr < measured.length; ctr++) {
			delta[ctr] = simulated[ctr] - measured[ctr];
		}

		return delta;
	}

	// ================================================================================
	// Public static Functions
	// ================================================================================
	/**
	 * Returns the Error sum
	 * 
	 * @param measured
	 *            measured data
	 * @param simulated
	 *            simulated data
	 * @return
	 */
	public static final double[] getErrorArray(double[] measured, double[] simulated) {
		return deltaArray(measured, simulated);
	}

}
