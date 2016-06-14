package ezrlc.Model;

import ezrlc.util.Complex;

/**
 * This class stores generated Datasets
 * @author noah
 *
 */
public class DataSet {

	// ================================================================================
	// Private Data
	// ================================================================================
	private double[] y_data;
	private Complex[] y_data_cpx;
	private double[] x_data;
	
	private boolean yDataComplex = false;

	private RectPlotNewMeasurement rnm;
	private SmithChartNewMeasurement snm;
	
	// ================================================================================
	// Constructors
	// ================================================================================
	/**
	 * Create new Dataset with double values
	 * @param x_data x data
	 * @param y_data y data
	 */
	public DataSet(double[] x_data, double[] y_data, RectPlotNewMeasurement nm) {
		// Copy Data
		this.y_data = new double[y_data.length];
		System.arraycopy(y_data, 0, this.y_data, 0, y_data.length);
		this.x_data = new double[x_data.length];
		System.arraycopy(x_data, 0, this.x_data, 0, x_data.length);
		yDataComplex = false;
		rnm = nm;
	}
	
	/**
	 * Create new Dataset with complex y data
	 * @param x_data x data
	 * @param y_data y data
	 */
	public DataSet(double[] x_data, Complex[] y_data, SmithChartNewMeasurement nm) {
		// Copy Data
		this.y_data_cpx = new Complex[y_data.length];
		System.arraycopy(y_data, 0, this.y_data_cpx, 0, y_data.length);
		this.x_data = new double[x_data.length];
		System.arraycopy(x_data, 0, this.x_data, 0, x_data.length);
		yDataComplex = true;
		snm = nm;
	}

	// ================================================================================
	// Public Functions
	// ================================================================================
	/**
	 * Return a copy of the x data
	 * @return double array
	 */
	public double[] getXData() {
		return this.x_data;
	}
	
	/**
	 * Return a copy of the x data
	 * @return double array
	 */
	public double[] getYData() {
		return this.y_data;
	}
	
	/**
	 * Return a copy of the x data
	 * @return complex array
	 */
	public Complex[] getYDataComplex() {
		return this.y_data_cpx;
	}
	
	public boolean isComplex() {
		return this.yDataComplex;
	}
	
	public RectPlotNewMeasurement getRNM() {
		return rnm;
	}
	
	public SmithChartNewMeasurement getSNM() {
		return snm;
	}
	
	public boolean isRectPlotDataSet() {
		if(rnm != null) return true;
		else return false;
	}

}
