package pro2.ModelCalculation;

import pro2.util.Complex;

/**
 * Calculates the bes reference resistance for scattering parameters
 * @author noah
 *
 */
public class MCSScaler {

	//================================================================================
    // Private variables
    //================================================================================
	private double z0;
	private Complex[] yz;
	private Complex[] ys;

	//================================================================================
    // Constructors
    //================================================================================
	public MCSScaler(Complex[] yz) {
		yz = new Complex[yz.length];
		ys = new Complex[yz.length];
		System.arraycopy(yz, 0, this.yz, 0, yz.length);
		z0 = 50;	// start value
	}

	//================================================================================
    // Public functions
    //================================================================================
	/**
	 * Scales the ys data to find the best reference resistance
	 */
	public void scale() {
	}

	public static double scale(Complex[] s, Complex[] z) {
		// TODO Auto-generated method stub
		return 0;
	}

}
