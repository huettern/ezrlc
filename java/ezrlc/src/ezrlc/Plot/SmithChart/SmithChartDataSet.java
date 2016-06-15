package ezrlc.Plot.SmithChart;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import ezrlc.Model.DataSet;
import ezrlc.Model.SmithChartNewMeasurement;
import ezrlc.Plot.DataSetSettings;
import ezrlc.util.Complex;
import ezrlc.util.PointD;

/**
 * Data set that contains the data to be painted on the smithchart
 * 
 * @author noah
 *
 */
public class SmithChartDataSet {

	// ================================================================================
	// Private Data
	// ================================================================================
	private SmithChartGrid grid;

	private Complex[] data;
	private double[] freq;
	private int points;

	private DataSetSettings settings = new DataSetSettings();
	private SmithChartNewMeasurement nm;

	private List<Point> data_pts = new ArrayList<Point>(); // Datapoints on the
															// chart

	private GeneralPath data_path;

	private BasicStroke data_stroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

	// ================================================================================
	// Constructors
	// ================================================================================
	/**
	 * * Creates a new SmithChart Dataset
	 * 
	 * @param data
	 *            List of complex data
	 * @param freq
	 *            frequency points
	 * @param nm
	 *            new measurement
	 */
	public SmithChartDataSet(double[] freq, Complex[] data, SmithChartNewMeasurement nm) {
		if (data.length != freq.length) {
			System.err.println("FATAL(SmithChartDataSet): data size not equal freq size");
			return;
		}
		this.nm = nm;
		// Copy Data
		this.data = new Complex[data.length];
		System.arraycopy(data, 0, this.data, 0, data.length);
		this.freq = new double[freq.length];
		System.arraycopy(freq, 0, this.freq, 0, freq.length);
		this.points = data.length;
	}

	public SmithChartDataSet(DataSet dataSet) {
		this(dataSet.getXData(), dataSet.getYDataComplex(), dataSet.getSNM());
	}

	// ================================================================================
	// Public Functions
	// ================================================================================
	public void paint(Graphics g) {
		this.eval();
		Graphics2D g2 = (Graphics2D) g;
		// Draw Data point
		g2.setColor(settings.getLineColor());
		// for (Point p : data_pts) {
		// g2.fillOval(p.x-2, p.y-2, 4, 4);
		// }
		g2.setStroke(data_stroke);
		g2.draw(data_path);
	}

	// ================================================================================
	// Private Functions
	// ================================================================================
	/**
	 * Evaluates the the data points of the Data set
	 */
	private void eval() {
		PointD p;

		SmithChartMath sm = new SmithChartMath(grid.getCenter(), grid.getDiameter(), grid.getZ0());

		data_pts = new ArrayList<Point>(this.points);

		for (int i = 0; i < this.points; i++) {
			p = sm.getPixelLocation(this.data[i]);
			data_pts.add(p.point());
		}

		// create path
		int ctr = 0;
		data_path = new GeneralPath(GeneralPath.WIND_EVEN_ODD, data_pts.size());
		data_path.moveTo(data_pts.get(0).x, data_pts.get(0).y);
		for (ctr = 1; ctr < data_pts.size(); ctr++) {
			data_path.lineTo(data_pts.get(ctr).x, data_pts.get(ctr).y);
		}
	}

	public void setGrid(SmithChartGrid g) {
		this.grid = g;
	}

	public void setDataSetSettings(DataSetSettings s) {
		this.settings = s;
	}

	/**
	 * Returns the new measurement object with which the dataset was created
	 * 
	 * @return SmithChartNewMeasurement
	 */
	public SmithChartNewMeasurement getNM() {
		return nm;
	}

}
