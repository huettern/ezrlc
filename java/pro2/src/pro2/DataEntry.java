package pro2;

import pro2.RFData.*;

public class DataEntry {

	private MeasurementType measType;
	private MeasurementUnit measUnit;
	private double freq;
	private double data1;
	private double data2;
	
	/**
	 * Saves intput values
	 * @param type
	 * @param unit
	 * @param freq
	 * @param data1
	 * @param data2
	 */
	public DataEntry(MeasurementType type, MeasurementUnit unit, double freq, double data1, double data2) {
		this.measType=type;
		this.measUnit=unit;
		this.freq=freq;
		this.data1=data1;
		this.data2=data2;
	}
	
	/**
	 * Returns the first data entry
	 * @return
	 */
	public double getData1 () {
		return this.data1;
	}
	
	/** Returns the second data entry
	 * @return
	 */
	public double getData2 () {
		return this.data2;
	}

	public Double getFreq() {
		return this.freq;
	}
}
