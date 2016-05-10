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
    // Public Functions
    //================================================================================
	
	/**
	 * Returns the scattering parameters of a given model, freq and params
	 * @param idx model index
	 * @param w frequency vecotr in omega
	 * @param a parameter list
	 * @return Complex array with scattering parameters
	 */
	public final List<Complex> getS (double[] w) {
		List<Complex> yz = null;
		
		switch(this.circuitType) {
			case MODEL0: yz = model0(w); break;
			case MODEL1: yz = model1(w); break;
			case MODEL2: yz = model2(w); break;
			case MODEL3: yz = model3(w); break;
			case MODEL4: yz = model4(w); break;
			case MODEL5: yz = model5(w); break;
			case MODEL6: yz = model6(w); break;
			case MODEL7: yz = model7(w); break;
			case MODEL8: yz = model8(w); break;
			case MODEL9: yz = model9(w); break;
			case MODEL10: yz = model10(w); break;
			case MODEL11: yz = model11(w); break;
			case MODEL12: yz = model12(w); break;
			case MODEL13: yz = model13(w); break;
			case MODEL14: yz = model14(w); break;
			case MODEL15: yz = model15(w); break;
			case MODEL16: yz = model16(w); break;
			case MODEL17: yz = model17(w); break;
			case MODEL18: yz = model18(w); break;
			case MODEL19: yz = model19(w); break;
			case MODEL20: yz = model20(w); break;
			default: System.err.println("FATAL: Model idx not found");;
		}
		// convert to s parameter
		List<Complex> ys = RFData.z2s(50, yz);
		
		return ys;
	}

	
	//================================================================================
    // Private Functions
    //================================================================================
	
	
	//================================================================================
    // Private Functions 
    //================================================================================
	private List<Complex> model0 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,p[4],p[0]);
	    Polynomial pd =new Polynomial(0,0,0,1);
	    List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model1 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,p[4]*p[0],0);
	    Polynomial pd =new Polynomial(0,0,p[4],p[0]);
		List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model2 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,p[5]*p[0],1);
	    Polynomial pd =new Polynomial(0,0,p[5],0);
		List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model3 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,0,p[0]);
	    Polynomial pd =new Polynomial(0,0,p[5]*p[0],1);
		List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model4 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,p[4]*p[5],p[0]*p[5],1);
	    Polynomial pd =new Polynomial(0,0,p[5],0);
		List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model5 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,p[0]*p[4],0);
	    Polynomial pd =new Polynomial(0,p[5]*p[4]*p[0],p[4],p[0]);
		List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model6 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,p[4],p[0]);
	    Polynomial pd =new Polynomial(0,p[5]*p[4],p[5]*p[0],1);
		List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model7 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,p[5]*p[4]*p[0],p[4],p[0]);
	    Polynomial pd =new Polynomial(0,0,p[5]*p[0],1);
		List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model8 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,p[5]*p[0]*p[3],p[0]+p[3]);
	    Polynomial pd =new Polynomial(0,0,p[5]*p[0],1);
		List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model9 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,p[4]*p[5]*p[0],p[4]+p[5]*p[0]*p[3],p[0]+p[3]);
	    Polynomial pd =new Polynomial(0,0,p[5]*p[0],1);
		List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model10 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,p[5]*p[4]*p[0]*p[3],p[4]*p[0]+p[4]*p[3],p[3]*p[0]);
	    Polynomial pd =new Polynomial(0,p[5]*p[4]*p[0],p[4],p[0]);
		List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model11 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,p[4],p[3]);
	    Polynomial pd =new Polynomial(0,p[5]*p[4],p[5]*p[3]+p[4]*p[0],p[3]*p[0]+1);
		List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model12 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,p[4]*p[5],p[0]*p[5],1);
	    Polynomial pd =new Polynomial(p[5]*p[6]*p[4],p[5]*p[6]*p[0],p[5]+p[6],0);
		List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model13 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,p[4]*p[5],p[5],1);
	    Polynomial pd =new Polynomial(0,0,p[5],0);
		List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model14 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,0,p[4],1);
	    Polynomial pd =new Polynomial(0,p[5]*p[4],p[5],1);
		List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model15 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,p[5]*p[4],p[4],1);
	    Polynomial pd =new Polynomial(0,0,p[5],1);
		List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model16 (double[] w) {
		double[] p = this.parameters;
	    Polynomial pn =new Polynomial(0,p[5]*p[4],p[4],1);
	    Polynomial pd =new Polynomial(0,0,p[5]*p[0],1);
		List<Complex> res = new ArrayList<Complex>();
	    res = pn.polydiv(pd, w);
		return res;
	}
	private List<Complex> model17 (double[] w) {
		double[] p = this.parameters;
		return null;
	}
	private List<Complex> model18 (double[] w) {
		double[] p = this.parameters;
		return null;
	}
	private List<Complex> model19 (double[] w) {
		double[] p = this.parameters;
		return null;
	}
	private List<Complex> model20 (double[] w) {
		double[] p = this.parameters;
		return null;
	}
	
	
	

}
