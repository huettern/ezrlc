package pro2.ModelCalculation;

import pro2.RFData.RFData;
import pro2.util.Complex;
import pro2.util.MathUtil;

/**
 * Calculates the bes reference resistance for scattering parameters
 * @author noah
 *
 */
public class MCSScaler {

	//================================================================================
    // Private variables
    //================================================================================

	//================================================================================
    // Constructors
    //================================================================================
	public MCSScaler() {

	}

	//================================================================================
    // Public functions
    //================================================================================
	/**
	 * Scales the ys data to find the best reference resistance
	 */

	public static double scale(Complex[] ys) {
		Complex[] yz = new Complex[ys.length];
		double r0 = 0.00001;	// start value
		yz=RFData.s2z(50, ys);
		ys=RFData.z2s(r0, yz);
		
		double[] ysabs= new double[ys.length];
		ysabs=MathUtil.abs(ys);
		
		while((Math.abs(Math.abs(MathUtil.getMax(ysabs))-Math.abs(MathUtil.getMin(ysabs)))<0.6)&(r0<10000)){
			yz=RFData.s2z(r0,ys);
			r0 *= 2;
			ys=RFData.z2s(r0,yz);
			ysabs=MathUtil.abs(ys);
			System.out.println("Rref="+r0);
		}
		return r0;
	}

}
