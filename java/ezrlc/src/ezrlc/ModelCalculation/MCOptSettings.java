package ezrlc.ModelCalculation;

import ezrlc.RFData.RFData.ComplexModifier;
import ezrlc.RFData.RFData.MeasurementType;

/**
 * Stores the settings for the optimizer
 * @author noah
 *
 */
public class MCOptSettings {

	/**
	 * Optimizer step size
	 */
	public double step = 0.001;
	/**
	 * Relative Threshold
	 */
	public double relTh = 1e-12;
	/**
	 * Absolute Threshold
	 */
	public double absTh = 1e-15;
	/**
	 * Maximum number of evals to do
	 */
	public int maxEval = 10000;
	
	/**
	 * Measurement type of error sum
	 */
	public MeasurementType measType = MeasurementType.S;
	/**
	 * Complex Modifier of measurement type for error sum
	 */
	public ComplexModifier cpxMod = ComplexModifier.MAG;
	
	public MCOptSettings() {
	}

}
