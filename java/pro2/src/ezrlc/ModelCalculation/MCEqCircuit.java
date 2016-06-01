package ezrlc.ModelCalculation;

import org.apache.commons.math3.analysis.MultivariateFunction;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.optim.InitialGuess;
import org.apache.commons.math3.optim.MaxEval;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;
import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.SimplexOptimizer;

import ezrlc.RFData.RFData;
import ezrlc.util.Complex;

/**
 * Model Calculation Equivalent Circuit
 * 
 * Represents an equivalent circuit model
 * 
 * Calculates the frequency-response of a model Parameter array format
 * 
 * R0 f0 alpha R1 L C0 C1 [0] [1] [2] [3] [4] [5] [6]
 * 
 * @author noah
 *
 */
public class MCEqCircuit implements Runnable {

	// ================================================================================
	// Public Data
	// ================================================================================
	public enum CircuitType {
		MODEL0, MODEL1, MODEL2, MODEL3, MODEL4, MODEL5, MODEL6, MODEL7, MODEL8, MODEL9, MODEL10, MODEL11, MODEL12, MODEL13, MODEL14, MODEL15, MODEL16, MODEL17, MODEL18, MODEL19, MODEL20
	}

	// ================================================================================
	// Private Data
	// ================================================================================
	private CircuitType circuitType;
	private MCOptions ops;

	private double[] parameters;
	private double[] shortParameters;

	private double[] wvector;

	private double z0 = 50.0;

	// Used for threadded optimizing
	private Complex[] ys;

	private double optStepDefault = 0.001;
	// private double optRelThDefault = 1e-11;
	// private double optAbsThDefault = 1e-14;
	private double optRelThDefault = 1e-12;
	private double optAbsThDefault = 1e-15;

	private SimplexOptimizer optimizer;
	private PointValuePair optimum;
	private MultivariateFunction errorFunction;
	private double[] optStep;

	// ================================================================================
	// Constructor
	// ================================================================================
	public MCEqCircuit(CircuitType circuitType) {
		this.circuitType = circuitType;
		initOptimizer();
		parameters = new double[7];
		for (int i = 0; i < 7; i++) {
			parameters[i] = 0.0;
		}
	}

	public MCEqCircuit(CircuitType circuitType, double[] params) {
		this.circuitType = circuitType;
		initOptimizer();
		System.arraycopy(params, 0, parameters, 0, params.length);
	}

	// ================================================================================
	// Getter and Setter Functions
	// ================================================================================
	/**
	 * Set the frequency vector in omega
	 * 
	 * @param w
	 *            f vecotr
	 */
	public final void setWVector(double[] w) {
		this.wvector = new double[w.length];
		System.arraycopy(w, 0, this.wvector, 0, w.length);
	}

	/**
	 * Sets the parameter array of the circuit
	 * 
	 * @param params
	 *            Parameter array [7]
	 */
	public final void setParameters(double[] params) {
		System.arraycopy(params, 0, parameters, 0, params.length);
	}

	/**
	 * Sets a single parameter in the parameter list
	 * 
	 * @param i
	 *            index of parameter
	 * @param d
	 *            parameter value
	 */
	public void setParameter(int i, double d) {
		// this.parameters[MCUtil.parameter2TopoIdx[this.circuitType.ordinal()][i]]
		// = d;
		parameters[i] = d;
	}

	/**
	 * Returns a copy of the circuit parameters
	 * 
	 * @return
	 */
	public double[] getParameters() {
		double[] res = new double[7];
		System.arraycopy(parameters, 0, res, 0, 7);
		return res;
	}

	public CircuitType getCircuitType() {
		return this.circuitType;
	}
	// ================================================================================
	// Public Functions
	// ================================================================================

	/**
	 * Returns the scattering parameters to the given freq parameters
	 * 
	 * @return Complex array with scattering parameters
	 */
	public final Complex[] getS() {
		// convert to s parameter
		Complex[] ys = RFData.z2s(z0, this.getZ());

		return ys;
	}

	/**
	 * Returns the admittance parameters to the given freq parameters
	 * 
	 * @return Complex array with admittance parameters
	 */
	public final Complex[] getY() {
		// convert to s parameter
		Complex[] yy = RFData.z2y(this.getZ());

		return yy;
	}

	/**
	 * Returns the impedance parameters to the given freq parameters
	 * 
	 * @return Complex array with impedance parameters
	 */
	public final Complex[] getZ() {
		Complex[] yz = null;

		switch (this.circuitType) {
		case MODEL0:
			yz = model0();
			break;
		case MODEL1:
			yz = model1();
			break;
		case MODEL2:
			yz = model2();
			break;
		case MODEL3:
			yz = model3();
			break;
		case MODEL4:
			yz = model4();
			break;
		case MODEL5:
			yz = model5();
			break;
		case MODEL6:
			yz = model6();
			break;
		case MODEL7:
			yz = model7();
			break;
		case MODEL8:
			yz = model8();
			break;
		case MODEL9:
			yz = model9();
			break;
		case MODEL10:
			yz = model10();
			break;
		case MODEL11:
			yz = model11();
			break;
		case MODEL12:
			yz = model12();
			break;
		case MODEL13:
			yz = model13();
			break;
		case MODEL14:
			yz = model14();
			break;
		case MODEL15:
			yz = model15();
			break;
		case MODEL16:
			yz = model16();
			break;
		case MODEL17:
			yz = model17();
			break;
		case MODEL18:
			yz = model18();
			break;
		case MODEL19:
			yz = model19();
			break;
		case MODEL20:
			yz = model20();
			break;
		default:
			System.err.println("FATAL: Model idx not found");
			;
		}
		return yz;
	}

	/**
	 * Returns the size of the w vector
	 * 
	 * @return size of w vector
	 */
	public int getWSize() {
		return this.wvector.length;
	}

	/**
	 * Sets all unused parameters to zero
	 */
	public void clean() {
		switch (this.circuitType) {
		case MODEL0:
		case MODEL1:
			parameters[1] = 0.0;
			parameters[2] = 0.0;
			parameters[3] = 0.0;
			parameters[5] = 0.0;
			parameters[6] = 0.0;
			break;
		case MODEL2:
		case MODEL3:
			parameters[1] = 0.0;
			parameters[2] = 0.0;
			parameters[3] = 0.0;
			parameters[4] = 0.0;
			parameters[6] = 0.0;
			break;
		case MODEL4:
		case MODEL5:
		case MODEL6:
		case MODEL7:
		case MODEL8:
			parameters[1] = 0.0;
			parameters[2] = 0.0;
			parameters[3] = 0.0;
			parameters[6] = 0.0;
			break;
		case MODEL9:
		case MODEL10:
		case MODEL11:
			parameters[1] = 0.0;
			parameters[2] = 0.0;
			parameters[6] = 0.0;
			break;
		case MODEL12:
			parameters[1] = 0.0;
			parameters[2] = 0.0;
			parameters[3] = 0.0;
			break;
		case MODEL13:
		case MODEL14:
		case MODEL15:
			parameters[3] = 0.0;
			parameters[6] = 0.0;
			break;
		case MODEL16:
			parameters[4] = 0.0;
			parameters[6] = 0.0;
			break;
		case MODEL17:
		case MODEL18:
		case MODEL19:
			parameters[6] = 0.0;
			break;
		case MODEL20:
			parameters[3] = 0.0;
			break;
		}
	}

	/**
	 * Prints the stored parameters to syso
	 */
	public void printParameters() {
		double[] res = parameters;
		System.out.println("-------------------------");
		System.out.println(this.circuitType.toString() + " Results");
		System.out.println("-------------------------");
		System.out.println("R0= " + res[0]);
		System.out.println("f0= " + res[1]);
		System.out.println("a = " + res[2]);
		System.out.println("R1= " + res[3]);
		System.out.println("L = " + res[4]);
		System.out.println("C0= " + res[5]);
		System.out.println("C1= " + res[6]);
		System.out.println("-------------------------");
	}

	/**
	 * Optimizes the circuit to the given ys vector
	 * 
	 * @param ys
	 *            complex scattering parameters to which the model is optimized
	 */
	public void optimize(Complex[] ys) {
		// shorten parameters to optimize
		shortParameters = MCUtil.shortenParam(circuitType, parameters);
		errorFunction = new MCErrorSum(ys, this);
		optimum = null;
		try {
			System.out.println("Starting Optimizer on Thread" + Thread.currentThread().getName());
			optimum = optimizer.optimize(new MaxEval(100000), new ObjectiveFunction(errorFunction), GoalType.MINIMIZE,
					new InitialGuess(shortParameters), new NelderMeadSimplex(optStep));
		} catch (TooManyEvaluationsException ex) {
			System.out.println("Optimizer reached MaxEval");
		}
		// save new parameters
		parameters = MCUtil.topo2Param(circuitType, optimum.getPoint());
		System.out.println("Optimizer done on Thread" + Thread.currentThread().getName());
	}

	/**
	 * Optimizes the circuit to the given ys vector in a threaded operation
	 * Usage: eqc.optimizeThreaded(ys); Thread t2_1 = new Thread(eqc,
	 * "EQC-Thread-t2_1"); t2_1.run();
	 * 
	 * @param ys
	 *            complex scattering parameters to which the model is optimized
	 */
	public void optimizeThreaded(Complex[] ys) {
		this.ys = ys;
	}

	@Override
	public void run() {
		optimize(ys);
	}

	/**
	 * Returns a copy of the stored frequency vector
	 * 
	 * @return frequency vector
	 */
	public double[] getF() {
		double[] res = new double[wvector.length];
		for (int i = 0; i < wvector.length; i++) {
			res[i] = wvector[i] / (2.0 * Math.PI);
		}
		return res;
	}

	// ================================================================================
	// Private Functions
	// ================================================================================
	/**
	 * Inits the optimizer
	 */
	private void initOptimizer() {
		int nelements = MCUtil.modelNElements[this.circuitType.ordinal()];
		optStep = new double[nelements];
		for (int i = 0; i < nelements; i++) {
			optStep[i] = optStepDefault;
		}
		optimizer = new SimplexOptimizer(optRelThDefault, optAbsThDefault);
	}

	/**
	 * Calculates the impedance parameters of the model 0
	 * 
	 * @return
	 */
	private Complex[] model0() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, 0, p[4], p[0]);
		Polynomial pd = new Polynomial(0, 0, 0, 1);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model1
	 * 
	 * @return
	 */
	private Complex[] model1() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, 0, p[4] * p[0], 0);
		Polynomial pd = new Polynomial(0, 0, p[4], p[0]);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model 2
	 * 
	 * @return
	 */
	private Complex[] model2() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, 0, p[5] * p[0], 1);
		Polynomial pd = new Polynomial(0, 0, p[5], 0);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model 3
	 * 
	 * @return
	 */
	private Complex[] model3() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, 0, 0, p[0]);
		Polynomial pd = new Polynomial(0, 0, p[5] * p[0], 1);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model 4
	 * 
	 * @return
	 */
	private Complex[] model4() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, p[4] * p[5], p[0] * p[5], 1);
		Polynomial pd = new Polynomial(0, 0, p[5], 0);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model 5
	 * 
	 * @return
	 */
	private Complex[] model5() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, 0, p[0] * p[4], 0);
		Polynomial pd = new Polynomial(0, p[5] * p[4] * p[0], p[4], p[0]);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model 6
	 * 
	 * @return
	 */
	private Complex[] model6() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, 0, p[4], p[0]);
		Polynomial pd = new Polynomial(0, p[5] * p[4], p[5] * p[0], 1);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model 7
	 * 
	 * @return
	 */
	private Complex[] model7() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, p[5] * p[4] * p[0], p[4], p[0]);
		Polynomial pd = new Polynomial(0, 0, p[5] * p[0], 1);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model 8
	 * 
	 * @return
	 */
	private Complex[] model8() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, 0, p[5] * p[0] * p[3], p[0] + p[3]);
		Polynomial pd = new Polynomial(0, 0, p[5] * p[0], 1);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model 9
	 * 
	 * @return
	 */
	private Complex[] model9() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, p[4] * p[5] * p[0], p[4] + p[5] * p[0] * p[3], p[0] + p[3]);
		Polynomial pd = new Polynomial(0, 0, p[5] * p[0], 1);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model 10
	 * 
	 * @return
	 */
	private Complex[] model10() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, p[5] * p[4] * p[0] * p[3], p[4] * p[0] + p[4] * p[3], p[3] * p[0]);
		Polynomial pd = new Polynomial(0, p[5] * p[4] * p[0], p[4], p[0]);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model 11
	 * 
	 * @return
	 */
	private Complex[] model11() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, 0, p[4], p[3]);
		Polynomial pd = new Polynomial(0, p[5] * p[4], p[5] * p[3] + p[4] * p[0], p[3] * p[0] + 1);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model 12
	 * 
	 * @return
	 */
	private Complex[] model12() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, p[4] * p[5], p[0] * p[5], 1);
		Polynomial pd = new Polynomial(p[5] * p[6] * p[4], p[5] * p[6] * p[0], p[5] + p[6], 0);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model 13
	 * 
	 * @return
	 */
	private Complex[] model13() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, p[4] * p[5], p[5], 1);
		Polynomial pd = new Polynomial(0, 0, p[5], 0);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model 14
	 * 
	 * @return
	 */
	private Complex[] model14() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, 0, p[4], 1);
		Polynomial pd = new Polynomial(0, p[5] * p[4], p[5], 1);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model 15
	 * 
	 * @return
	 */
	private Complex[] model15() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, p[5] * p[4], p[4], 1);
		Polynomial pd = new Polynomial(0, 0, p[5], 1);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model 16
	 * 
	 * @return
	 */
	private Complex[] model16() {
		double[] p = this.parameters;
		Polynomial pn = new Polynomial(0, p[5] * p[4], p[4], 1);
		Polynomial pd = new Polynomial(0, 0, p[5] * p[0], 1);
		Complex[] res;
		res = pn.polydiv(pd, wvector);
		return res;
	}

	/**
	 * Calculates the impedance parameters of the model 17
	 * 
	 * @return
	 */
	private Complex[] model17() {
		double[] p = this.parameters;
		return null;
	}

	/**
	 * Calculates the impedance parameters of the model 18
	 * 
	 * @return
	 */
	private Complex[] model18() {
		double[] p = this.parameters;
		return null;
	}

	/**
	 * Calculates the impedance parameters of the model 19
	 * 
	 * @return
	 */
	private Complex[] model19() {
		double[] p = this.parameters;
		return null;
	}

	/**
	 * Calculates the impedance parameters of the model 20
	 * 
	 * @return
	 */
	private Complex[] model20() {
		double[] p = this.parameters;
		return null;
	}

	public void setZ0(double rref) {
		this.z0 = rref;
	}

	public void setOps(MCOptions ops) {
		this.ops = ops;
	}

	public MCOptions getOps() {
		return ops;
	}

}
