package pro2.ModelCalculation;

import java.util.ArrayList;
import java.util.List;

import pro2.RFData.RFData;
import pro2.util.Complex;

/**
 * Model Calculation Equivalent Circuit
 * 
 * Represents an equivalent circuit model
 * 
 * Calculates the frequency-response of a model
 * Parameter array format
 * 
 * R0	f0		alpha	R1		L		C0		C1
 * [0]	[1]		[2]		[3]		[4]		[5]		[6]
 * @author noah
 *
 */
public class MCEqCircuit {

	//================================================================================
    // Public Data
    //================================================================================
	public enum CIRCUIT_TYPE {
		MODEL0, MODEL1, MODEL2, MODEL3, MODEL4, MODEL5, MODEL6, MODEL7, MODEL8, MODEL9,
		MODEL10, MODEL11, MODEL12, MODEL13, MODEL14, MODEL15, MODEL16, MODEL17, 
		MODEL18, MODEL19, MODEL20
	}

	//================================================================================
    // Private Data
    //================================================================================
	private CIRCUIT_TYPE circuitType;
	
	private double[] parameters = new double[7];
	
	private double[] wvector;

	//================================================================================
    // Constructor
    //================================================================================
	public MCEqCircuit(CIRCUIT_TYPE circuitType) {
		this.circuitType = circuitType;
	}

	public MCEqCircuit(CIRCUIT_TYPE circuitType, double[] params) {
		this.circuitType = circuitType;
		System.arraycopy(params, 0, parameters, 0, params.length);
	}

	//================================================================================
    // Setter Functions
    //================================================================================
	/**
	 * Set the frequency vector in omega
	 * @param w f vecotr
	 */
	public final void setWVector(double[] w) {
		this.wvector = new double[w.length];
		System.arraycopy(w, 0, this.wvector, 0, w.length);
	}
	
	/**
	 * Sets the parameter array of the circuit
	 * @param params Parameter array [7]
	 */
	public final void setParameters (double[] params) {
		System.arraycopy(params, 0, parameters, 0, params.length);
	}
	
	
	//================================================================================
    // Public Functions
    //================================================================================

	/**
	 * Returns the scattering parameters to the given freq parameters
	 * @param w frequency vecotr in omega
	 * @return Complex array with scattering parameters
	 */
	public final Complex[] getS () {
		// convert to s parameter
		Complex[] ys = RFData.z2s(50, this.getZ());
		
		return ys;
	}
	
	/**
	 * Returns the admittance parameters to the given freq parameters
	 * @param w frequency vecotr in omega
	 * @return Complex array with admittance parameters
	 */
	public final Complex[] getY () {
		// convert to s parameter
		Complex[] yy = RFData.z2y(this.getZ());
		
		return yy;
	}

	/**
	 * Returns the impedance parameters to the given freq parameters
	 * @param w frequency vecotr in omega
	 * @return Complex array with impedance parameters
	 */
	public final Complex[] getZ () {
		Complex[] yz = null;
		
		switch(this.circuitType) {
			case MODEL0: yz = model0(); break;
			case MODEL1: yz = model1(); break;
			case MODEL2: yz = model2(); break;
			case MODEL3: yz = model3(); break;
			case MODEL4: yz = model4(); break;
			case MODEL5: yz = model5(); break;
			case MODEL6: yz = model6(); break;
			case MODEL7: yz = model7(); break;
			case MODEL8: yz = model8(); break;
			case MODEL9: yz = model9(); break;
			case MODEL10: yz = model10(); break;
			case MODEL11: yz = model11(); break;
			case MODEL12: yz = model12(); break;
			case MODEL13: yz = model13(); break;
			case MODEL14: yz = model14(); break;
			case MODEL15: yz = model15(); break;
			case MODEL16: yz = model16(); break;
			case MODEL17: yz = model17(); break;
			case MODEL18: yz = model18(); break;
			case MODEL19: yz = model19(); break;
			case MODEL20: yz = model20(); break;
			default: System.err.println("FATAL: Model idx not found");;
		}
		return yz;
	}
	
	/**
	 * Returns the size of the w vector
	 * @return size of w vector
	 */
	public int getWSize (){
		return this.wvector.length;
	}
	
	//================================================================================
    // Private Functions
    //================================================================================
	
	
	//================================================================================
    // Private Functions 
    //================================================================================
	/**
	 * Calculates the impedance parameters of the model 0
	 * @return
	 */
	private Complex[] model0 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,p[4],p[0]);
	    Polynomial pd =new Polynomial(0,0,0,1);
	    Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model1 
	 * @return
	 */
	private Complex[] model1 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,p[4]*p[0],0);
	    Polynomial pd =new Polynomial(0,0,p[4],p[0]);
		Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model 2
	 * @return
	 */
	private Complex[] model2 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,p[5]*p[0],1);
	    Polynomial pd =new Polynomial(0,0,p[5],0);
		Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model 3
	 * @return
	 */
	private Complex[] model3 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,0,p[0]);
	    Polynomial pd =new Polynomial(0,0,p[5]*p[0],1);
		Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model 4
	 * @return
	 */
	private Complex[] model4 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,p[4]*p[5],p[0]*p[5],1);
	    Polynomial pd =new Polynomial(0,0,p[5],0);
		Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model 5
	 * @return
	 */
	private Complex[] model5 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,p[0]*p[4],0);
	    Polynomial pd =new Polynomial(0,p[5]*p[4]*p[0],p[4],p[0]);
		Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model 6
	 * @return
	 */
	private Complex[] model6 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,p[4],p[0]);
	    Polynomial pd =new Polynomial(0,p[5]*p[4],p[5]*p[0],1);
		Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model 7
	 * @return
	 */
	private Complex[] model7 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,p[5]*p[4]*p[0],p[4],p[0]);
	    Polynomial pd =new Polynomial(0,0,p[5]*p[0],1);
		Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model 8
	 * @return
	 */
	private Complex[] model8 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,p[5]*p[0]*p[3],p[0]+p[3]);
	    Polynomial pd =new Polynomial(0,0,p[5]*p[0],1);
		Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model 9
	 * @return
	 */
	private Complex[] model9 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,p[4]*p[5]*p[0],p[4]+p[5]*p[0]*p[3],p[0]+p[3]);
	    Polynomial pd =new Polynomial(0,0,p[5]*p[0],1);
		Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model 10
	 * @return
	 */
	private Complex[] model10 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,p[5]*p[4]*p[0]*p[3],p[4]*p[0]+p[4]*p[3],p[3]*p[0]);
	    Polynomial pd =new Polynomial(0,p[5]*p[4]*p[0],p[4],p[0]);
		Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model 11
	 * @return
	 */
	private Complex[] model11 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,p[4],p[3]);
	    Polynomial pd =new Polynomial(0,p[5]*p[4],p[5]*p[3]+p[4]*p[0],p[3]*p[0]+1);
		Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model 12
	 * @return
	 */
	private Complex[] model12 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,p[4]*p[5],p[0]*p[5],1);
	    Polynomial pd =new Polynomial(p[5]*p[6]*p[4],p[5]*p[6]*p[0],p[5]+p[6],0);
		Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model 13
	 * @return
	 */
	private Complex[] model13 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,p[4]*p[5],p[5],1);
	    Polynomial pd =new Polynomial(0,0,p[5],0);
		Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model 14
	 * @return
	 */
	private Complex[] model14 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,p[4],1);
	    Polynomial pd =new Polynomial(0,p[5]*p[4],p[5],1);
		Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model 15
	 * @return
	 */
	private Complex[] model15 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,p[5]*p[4],p[4],1);
	    Polynomial pd =new Polynomial(0,0,p[5],1);
		Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model 16
	 * @return
	 */
	private Complex[] model16 () {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,p[5]*p[4],p[4],1);
	    Polynomial pd =new Polynomial(0,0,p[5]*p[0],1);
		Complex[] res;
	    res = pn.polydiv(pd, wvector);
		return res;
	}
	/**
	 * Calculates the impedance parameters of the model 17
	 * @return
	 */
	private Complex[] model17 () {
		double[] p = this.parameters;
		return null;
	}
	/**
	 * Calculates the impedance parameters of the model 18
	 * @return
	 */
	private Complex[] model18 () {
		double[] p = this.parameters;
		return null;
	}
	/**
	 * Calculates the impedance parameters of the model 19
	 * @return
	 */
	private Complex[] model19 () {
		double[] p = this.parameters;
		return null;
	}
	/**
	 * Calculates the impedance parameters of the model 20
	 * @return
	 */
	private Complex[] model20 () {
		double[] p = this.parameters;
		return null;
	}
	
	
	

}
