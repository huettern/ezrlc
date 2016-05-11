package pro2.ModelCalculation;


/**
 * Holds the options, given by the user, needed to calculate an equivelant model 
 * @author noah
 *
 */
public class MCOptions {

	//================================================================================
    // Public Data
    //================================================================================
	public double fMax;
	public double fMin;

	public int nElementsMax;
	public int nElementsMin;
	
	public double inductor;
	public double capacitor0;
	public double resistor1;
	public double capacitor1;
	public double resistor0;
	public double alpha;
	public double fSkin;
	
	public boolean skinEffectEnabled;
	

	//================================================================================
    // Constructor
    //================================================================================
	public MCOptions() {
		fMax = Double.MAX_VALUE;
		fMin = 0;
		nElementsMax = Integer.MAX_VALUE;
		nElementsMin = 0;
		skinEffectEnabled = false;
	}

}
