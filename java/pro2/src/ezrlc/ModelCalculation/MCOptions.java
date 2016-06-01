package ezrlc.ModelCalculation;

/**
 * Holds the options, given by the user, needed to calculate an equivelant model
 * 
 * @author noah
 *
 */
public class MCOptions {

	// ================================================================================
	// Public Data
	// ================================================================================
	public double fMax;
	public double fMin;
	public boolean fMaxAuto;
	public boolean fMinAuto;

	public int nElementsMax;
	public int nElementsMin;
	public boolean nElementsMaxAuto;
	public boolean nElementsMinAuto;
	
	public double[] params = new double[7];
	public boolean[] paramsAuto = new boolean[7];

	public boolean modelAutoSelect;
	public int modelID;

	// ================================================================================
	// Constructor
	// ================================================================================
	public MCOptions() {
		// Set all to auto
		fMaxAuto = true;
		fMinAuto = true;
		nElementsMaxAuto = true;
		nElementsMinAuto = true;
		for (int i = 0; i < 7; i++) {
			paramsAuto[i] = true;
		}
		modelAutoSelect = true;
	}

}
