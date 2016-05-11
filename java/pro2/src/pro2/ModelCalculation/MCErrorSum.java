package pro2.ModelCalculation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.analysis.MultivariateFunction;

import pro2.util.Complex;

/**
 * Functions to get the error sum
 * @author noah
 *
 */
public class MCErrorSum implements MultivariateFunction {

	//================================================================================
    // Private Data
    //================================================================================
	private MCEqCircuit circuit;
	private Complex[] measured;

	//================================================================================
    // Constructors
    //================================================================================
	/**
	 * Create new error sum object
	 * @param measured measured data
	 * @param circuit equivalent circuit object
	 */
	public MCErrorSum(Complex[] measured, MCEqCircuit circuit) {
		this.circuit = circuit;
		this.measured = new Complex[measured.length];
		System.arraycopy(measured, 0, this.measured, 0, measured.length);
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

	

	//================================================================================
    // Interface methods
    //================================================================================
	/**
	 * Gets called by optimizer to calculate error
	 * @param x: parameter array from optimizer
	 * @return error
	 */
	@Override
	public double value(double[] params) {
		System.out.println("Interface value, params=" +Arrays.toString(params));
		// set new parameter
		circuit.setParameters(params);
		// get s parameters
		ArrayList<Complex> s = circuit.getS();
		// build magnitude
		double[] magS = new double[s.size()];
		double[] magmeas = new double[s.size()];
		for(int i = 0; i < s.size(); i++) {
			magS[i] = s.get(i).abs();
			magmeas[i] = this.measured[i].abs();
		}
		// calc error
		return MCErrorSum.getError(magmeas, magS);
	}

	
	
}
