package ezrlc.RFData;

/**
 * Single data entry of a touchstone file
 * @author noah
 *
 */
public class DataEntry {
	private double freq;
	private double data1;
	private double data2;

	/**
	 * New data entry
	 * @param freq frequency
	 * @param data1 data entry one
	 * @param data2 data entry two
	 */
	public DataEntry(double freq, double data1, double data2) {
		this.freq = freq;
		this.data1 = data1;
		this.data2 = data2;
	}

	/**
	 * Returns the first data entry
	 * 
	 * @return return data entry 1
	 */
	public double getData1() {
		return this.data1;
	}

	/**
	 * Returns the second data entry
	 * 
	 * @return return data entry 1
	 */
	public double getData2() {
		return this.data2;
	}

	public Double getFreq() {
		return this.freq;
	}
}
