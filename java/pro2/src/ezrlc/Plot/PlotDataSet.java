package ezrlc.Plot;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;

import ezrlc.Plot.RectPlot.DataSetSettings;
import ezrlc.Plot.RectPlot.RectPlotNewMeasurement;
import ezrlc.util.MathUtil;

public class PlotDataSet {

	// ================================================================================
	// Private Data
	// ================================================================================
	private double[] x_data;
	private double[] y_data;
	private int points = 0;
	private double x_max;
	private double x_min;
	private double y_max;
	private double y_min;
	private double x_span;
	private double y_span;

	private Axis x_axis;
	private Axis y_axis;

	private Point[] data_pts;
	private GeneralPath data_path;

	private DataSetSettings settings = new DataSetSettings();
	private RectPlotNewMeasurement nm;

	private BasicStroke data_stroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

	// ================================================================================
	// Constructors
	// ================================================================================
	/**
	 * Add new Dataset. Both parameters must be of the same size!!
	 * 
	 * @param x:
	 *            X Data
	 * @param y:
	 *            Y Data
	 */
	public PlotDataSet(double[] x, double[] y, RectPlotNewMeasurement nm) {
		if (x.length != y.length) {
			System.out.println("PlotDataSet: Error! Data not the same size");
			return;
		}
		this.nm = nm;
		x_data = new double[x.length];
		y_data = new double[x.length];
		System.arraycopy(x, 0, x_data, 0, x.length);
		System.arraycopy(y, 0, y_data, 0, x.length);
		this.points = x.length;
		this.data_pts = new Point[this.points];
		for (int i = 0; i < this.points; i++) {
			data_pts[i] = new Point(0, 0);
		}

		// Get the datas max and min
		x_max = MathUtil.getMax(this.x_data);
		x_min = MathUtil.getMin(this.x_data);
		y_max = MathUtil.getMax(this.y_data);
		y_min = MathUtil.getMin(this.y_data);

		this.x_span = this.x_max - this.x_min;
		if (this.x_span == 0)
			this.x_span = Double.MIN_VALUE;
		this.y_span = this.y_max - this.y_min;
		if (this.y_span == 0)
			this.y_span = Double.MIN_VALUE;
	}

	// ================================================================================
	// Private Functions
	// ================================================================================
	/**
	 * Evaluates the pixel positions of the individual datapoints
	 */
	private void eval() {
		int i = 0;
		for (Point point : this.data_pts) {
			point.x = this.x_axis.getPixelValue(x_data[i]);
			point.y = this.y_axis.getPixelValue(y_data[i]);
			i++;
		}

		// create path
		int ctr = 0;
		data_path = new GeneralPath(GeneralPath.WIND_EVEN_ODD, data_pts.length);
		data_path.moveTo(data_pts[0].x, data_pts[0].y);
		for (ctr = 1; ctr < data_pts.length; ctr++) {
			data_path.lineTo(data_pts[ctr].x, data_pts[ctr].y);
		}
	}

	// ================================================================================
	// Public Functions
	// ================================================================================
	/**
	 * Paint the Dataset
	 * 
	 * @param g
	 */
	public void paint(Graphics g, Area clip) {
		// TODO Auto-generated method stub
		this.eval();
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setClip(clip);

		// for (Point point : this.data_pts) {
		// this.drawPoint(g, point);
		// }
		//
		// Color oldCol = g2.getColor();
		// g2.setColor(this.settings.getLineColor());
		// for(int i = 0; i<(this.points-1); i++) {
		// this.drawPoint(g2, this.data_pts.get(i));
		// this.connectPoints(g2, this.data_pts.get(i), this.data_pts.get(i+1));
		// }

		g2.setColor(this.settings.getLineColor());
		g2.setStroke(data_stroke);
		g2.draw(data_path);

		// this.drawPoint(g2, this.data_pts.get(this.points-1));
		// g2.setColor(oldCol);
	}

	/**
	 * Set the parent Axis of the dataset
	 * 
	 * @param x:
	 *            x Axis
	 * @param y:
	 *            y Axis
	 */
	public void setAxis(Axis x, Axis y) {
		this.x_axis = x;
		this.y_axis = y;
	}

	public double[] getXData() {
		double[] res = new double[x_data.length];
		System.arraycopy(x_data, 0, res, 0, x_data.length);
		return res;
	}

	public double[] getYData() {
		double[] res = new double[y_data.length];
		System.arraycopy(y_data, 0, res, 0, y_data.length);
		return res;
	}

	/**
	 * Returns x max value
	 * 
	 * @return x max value
	 */
	public Double getXMax() {
		return this.x_max;
	}

	/**
	 * Returns y max value
	 * 
	 * @return y max value
	 */
	public Double getYMax() {
		return this.y_max;
	}

	/**
	 * Returns x min value
	 * 
	 * @return x min value
	 */
	public Double getXMin() {
		return this.x_min;
	}

	/**
	 * Returns y min value
	 * 
	 * @return y min value
	 */
	public Double getYMin() {
		return this.y_min;
	}

	/**
	 * Stores the RectPlotDataSetSettings
	 * 
	 * @param set
	 *            RectPlotDataSetSettings
	 */
	public void setDataSetSettings(DataSetSettings set) {
		this.settings = set;
	}

	/**
	 * Returns the new measurement object with which the dataset was created
	 * 
	 * @return RectPlotNewMeasurement
	 */
	public RectPlotNewMeasurement getNM() {
		return nm;
	}
}
