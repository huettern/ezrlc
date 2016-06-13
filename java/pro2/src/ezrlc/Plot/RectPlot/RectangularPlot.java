package ezrlc.Plot.RectPlot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import ezrlc.Model.Model;
import ezrlc.Model.RectPlotNewMeasurement;
import ezrlc.Plot.DataSetSettings;
import ezrlc.Plot.RectPlot.Axis.Scale;
import ezrlc.Plot.RectPlot.Grid.Orientation;
import ezrlc.RFData.RFData.MeasurementType;
import ezrlc.util.DataSource;
import ezrlc.util.MathUtil;
import ezrlc.util.UIUtil;

/**
 * A standard rectangular plot
 * @author noah
 *
 */
public class RectangularPlot extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	// ================================================================================
	// Private Data
	// ================================================================================
	Point origin = new Point(100, 50); // Origin of the coordinate set from
										// bottom left
	private int rightMargin = 40;
	private int topMargin = 40;
	private int plotWidth = 0;
	private int plotHeight = 0;

	private Axis horAxis;
	private Axis verAxis;

	private Grid horGrid;
	private Grid verGrid;

	private List<PlotDataSet> dataSets = new ArrayList<PlotDataSet>();
	private List<Integer> dataSetIDs = new ArrayList<Integer>();
	private List<DataSetSettings> dataSetSettings = new ArrayList<DataSetSettings>();

	private RectPlotSettings settings = new RectPlotSettings();

	private boolean enableAutoAutoscale = true;

	private String title = null;
	private int titleSize = 12;

	// ================================================================================
	// Constructors
	// ================================================================================
	/**
	 * Create a default rectangular with axis linear
	 */
	public RectangularPlot() {
		this(Scale.LINEAR, Scale.LINEAR);
	}

	/**
	 * Builds a new rect plot with given scales
	 * 
	 * @param x
	 *            x scale
	 * @param y
	 *            y scale
	 */
	public RectangularPlot(Scale x, Scale y) {
		super.setBackground(Color.WHITE);

		// Init settings
		settings.xAxisMinimum = 0.1;
		settings.xAxisMaximum = 1;
		settings.xAxisSteps = 1;
		settings.xScale = x;
		settings.yAxisMinimum = 0.1;
		settings.yAxisMaximum = 1;
		settings.yAxisSteps = 1;
		settings.yScale = y;

		// Add Axis
		this.horAxis = new Axis(this, x, Axis.Orientation.HORIZONTAL, origin, rightMargin, settings.xAxisMinimum,
				settings.xAxisMaximum, settings.xAxisSteps, 20);		
		this.verAxis = new Axis(this, y, Axis.Orientation.VERTICAL, origin, topMargin, settings.yAxisMinimum,
				settings.yAxisMaximum, settings.xAxisSteps, -50);
		// Add Grid
		this.verGrid = new Grid(this, Orientation.VERTICAL, Color.LIGHT_GRAY, horAxis, 40);
		this.horGrid = new Grid(this, Orientation.HORIZONTAL, Color.LIGHT_GRAY, verAxis, 40);

		repaint();
	}

	// ================================================================================
	// Private Function
	// ================================================================================
	/**
	 * Updates the plot settings
	 */
	private void updateSettings() {
		// Check if scale changed
		if (settings.xScale != horAxis.getScale()) {
			// Create new grid
			this.verGrid = new Grid(this, Orientation.VERTICAL, Color.LIGHT_GRAY, horAxis, 40);
			horAxis.setScale(settings.xScale);
		}
		if (settings.yScale != verAxis.getScale()) {
			// Create new grid
			this.horGrid = new Grid(this, Orientation.HORIZONTAL, Color.LIGHT_GRAY, verAxis, 40);
			verAxis.setScale(settings.yScale);
		}

		horAxis.setMinimum(settings.xAxisMinimum);
		horAxis.setMaximum(settings.xAxisMaximum);
		horAxis.setStep(settings.xAxisSteps);

		verAxis.setMinimum(settings.yAxisMinimum);
		verAxis.setMaximum(settings.yAxisMaximum);
		verAxis.setStep(settings.yAxisSteps);
	}

	/**
	 * Updates the local stored datasets
	 * 
	 * @param model
	 */
	private void updateDatasets(Model model) {
		int i = 0;
		PlotDataSet dataSet;
		// // Expand data set list to size
		// for(; dataSets.size() < dataSetIDs.size(); ){
		// dataSets.add(null);
		// }
		// fill datasets
		for (Integer integer : dataSetIDs) {
			dataSet = new PlotDataSet(model.getDataSet(integer.intValue()));
			dataSet.setAxis(this.horAxis, this.verAxis);
			dataSet.setDataSetSettings(this.dataSetSettings.get(i));
			this.dataSets.set(i, dataSet);
			i++;
		}
		// if only one dataset, do autoscale
		if (this.dataSets.size() == 1 && this.enableAutoAutoscale) {
			this.autoScale();
		}
	}

	/**
	 * Builds the labelname of the new plot name field
	 * 
	 * @param rpnm
	 *            RectPlotNewMeasurement
	 * @return string
	 */
	private String createLabelStringName(RectPlotNewMeasurement rpnm) {
		String s;

		// s = rpnm.src_name +" " +rpnm.type.name() +" " +rpnm.cpxMod.name();
		s = "<html><B>" + rpnm.src_name + "</B></html>";

		return s;
	}

	/**
	 * Builds the labelname of the new plot type field
	 * 
	 * @param rpnm
	 *            RectPlotNewMeasurement
	 * @return string
	 */
	private String createLabelStringType(RectPlotNewMeasurement rpnm) {
		String s;

		if(rpnm.src == DataSource.COMPARE) 		
			s = "<html>" + rpnm.type.name() + ": " + rpnm.cpxMod.name() + " " + rpnm.unit.name() +"</html>";
		else if (rpnm.type == MeasurementType.S)
			s = "<html>" + rpnm.type.name() + ": " + rpnm.cpxMod.name() + " " + MathUtil.num2eng(rpnm.zRef, 2) +"\u2126</html>";
		else
			s = "<html>" + rpnm.type.name() + ": " + rpnm.cpxMod.name() + "</html>";
		
		return s;
	}

	/**
	 * evaluate the size of the panel
	 */
	private void evalSize() {
		this.plotWidth = this.getWidth() - this.rightMargin - origin.x;
		this.plotHeight = this.getHeight() - this.topMargin - origin.y;
	}

	// ================================================================================
	// Public Function
	// ================================================================================

	/**
	 * Paints the panel and its contents
	 */
	@Override
	public void paintComponent(Graphics g) {
		// Paint parent
		super.paintComponent(g);

		this.evalSize();
		Area plotArea = new Area(new Rectangle2D.Double(origin.x, this.topMargin, plotWidth, plotHeight));

		// Paint axis and grid
		this.horAxis.paint(g);
		this.verAxis.paint(g);
		this.verGrid.paint(g);
		this.horGrid.paint(g);

		// Paint datasets
		for (PlotDataSet plotDataSet : this.dataSets) {
			if (plotDataSet != null) {
				plotDataSet.paint(g, plotArea);
			}
		}

		// paint title
		if (title != null) {
			Font f = g.getFont();
			g.setFont(new Font(f.getFontName(), Font.BOLD, titleSize));
			UIUtil.drawCenterString(g, new Point(this.getWidth() / 2, 12), title);
		}
	}

	/**
	 * Add a new Dataset to the plot by ID
	 * 
	 * @param rectPlotNewMeasurement rectPlotNewMeasurement
	 * @param id id
	 */
	public void addDataSet(int id, RectPlotNewMeasurement rectPlotNewMeasurement) {
		DataSetSettings set = new DataSetSettings();
		set.setLineColor(UIUtil.getNextColor());
		set.setLabelName(this.createLabelStringName(rectPlotNewMeasurement));
		set.setLabelType(this.createLabelStringType(rectPlotNewMeasurement));

		this.dataSetIDs.add(id);
		this.dataSetSettings.add(set);
		this.dataSets.add(null);
	}

	/**
	 * Add a new Dataset to the plot by ID
	 * 
	 * @param rectPlotNewMeasurement rectPlotNewMeasurement
	 * @param id id
	 * @param color
	 *            Color index
	 */
	public void addDataSet(int id, RectPlotNewMeasurement rectPlotNewMeasurement, int color) {
		DataSetSettings set = new DataSetSettings();
		set.setLineColor(UIUtil.getColor(color));
		set.setLabelName(this.createLabelStringName(rectPlotNewMeasurement));
		set.setLabelType(this.createLabelStringType(rectPlotNewMeasurement));

		this.dataSetIDs.add(id);
		this.dataSetSettings.add(set);
		this.dataSets.add(null);
	}

	public RectPlotSettings getSettings() {
		return settings;
	}

	/**
	 * Set settings and update plot
	 * @param s settings
	 */
	public void setSettings(RectPlotSettings s) {
		this.settings = s;
		this.enableAutoAutoscale = false;
		this.updateSettings();
		repaint();
	}

	/**
	 * Removes dataset from data set id list
	 * 
	 * @param id
	 *            data set id
	 */
	public void removeDataset(int id) {
		int ctr = 0;
		for (Iterator<Integer> iter = dataSetIDs.iterator(); iter.hasNext(); ctr++) {
			Integer i = iter.next();
			if (i == id) {
				dataSets.remove(ctr);
				dataSetSettings.remove(ctr);
				iter.remove();
			}
		}
		if (dataSetIDs.isEmpty()) {
			this.enableAutoAutoscale = true;
		}
		this.repaint();
	}

	/**
	 * Removes all stored datasets
	 */
	public void removeAllDatasets() {
		int ctr = 0;
		for (Iterator<Integer> iter = dataSetIDs.iterator(); iter.hasNext(); ctr++) {
			iter.next();
			dataSets.remove(ctr);
			dataSetSettings.remove(ctr);
			iter.remove();
		}
		this.enableAutoAutoscale = true;
		this.repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		Model model = (Model) o;
		this.updateDatasets(model);
		repaint();
	}

	/**
	 * Returns datasetsettings of the dataset given by the id
	 * 
	 * @param id
	 *            data set id
	 * @return data set settings
	 */
	public DataSetSettings getDataSetSettings(int id) {
		// search id
		for (int i = 0; i < dataSetIDs.size(); i++) {
			if (dataSetIDs.get(i) == id) {
				return dataSetSettings.get(i);
			}
		}
		return null;
	}

	/**
	 * Scales Axis to show all Data
	 */
	public void autoScale() {
		autoScale(Scale.LOG, Scale.LINEAR);
	}

	/**
	 * Autoscales the plot using the scales currently set in the axis
	 */
	public void autoScaleKeepScale() {
		autoScale(horAxis.getScale(), verAxis.getScale());
	}

	/**
	 * Scales Axis to show all Data, with x ang y scale
	 * 
	 * @param sx
	 *            Horizontal scale
	 * @param sy
	 *            vertical scale
	 */
	public void autoScale(Scale sx, Scale sy) {
		double xmin = Double.MAX_VALUE;
		double xmax = -Double.MAX_VALUE;
		double ymin = Double.MAX_VALUE;
		double ymax = -Double.MAX_VALUE;

		// dont autoscale if no ids here
		if (this.dataSetIDs.size() == 0)
			return;

		// Crawl all datasets for max / min values
		for (int i = 0; i < this.dataSetIDs.size(); i++){
			if (dataSets.get(i).getXMax() > xmax) {
				xmax = dataSets.get(i).getXMax();
			}
			if (dataSets.get(i).getXMin() < xmin) {
				xmin = dataSets.get(i).getXMin();
			}
			if (dataSets.get(i).getYMax() > ymax) {
				ymax = dataSets.get(i).getYMax();
			}
			if (dataSets.get(i).getYMin() < ymin) {
				ymin = dataSets.get(i).getYMin();
			}
			// if log X-axis, set minimum at first non-zero value
			if (sx == Scale.LOG) {
				if (dataSets.get(i).getXData()[0] == 0) {
					xmin = dataSets.get(i).getXData()[1];
				} else {
					xmin = dataSets.get(i).getXData()[0];
				}
			}
			i++;
		}

		// xmax *= 1.1;
		ymax *= 1.1;
		ymin *= 0.9;
		// xmin *= 0.9;

		// if log Y-axis, set minimum at first non-zero or negative value
		if (sy == Scale.LOG) {
			ymin = Double.MAX_VALUE;
			for (PlotDataSet dataset : this.dataSets) {
				for (Double d : dataset.getYData()) {
					if ((d > 0) && (d < ymin)) {
						ymin = d;
					}
				}
			}
		}

		// round the values
		xmin = MathUtil.roundNice(xmin);
		xmax = MathUtil.roundNice(xmax);
		ymin = MathUtil.roundNice(ymin);
		ymax = MathUtil.roundNice(ymax);
		
		settings.xAxisMaximum = xmax;
		settings.xAxisMinimum = xmin;
		settings.yAxisMaximum = ymax;
		settings.yAxisMinimum = ymin;
		settings.xScale = sx;
		settings.yScale = sy;

		// set step to 10
		if (settings.xScale == Scale.LINEAR)
			settings.xAxisSteps = 10;
		if (settings.yScale == Scale.LINEAR)
			settings.yAxisSteps = 10;

		updateSettings();
		repaint();
	}

	/**
	 * Sets the title string
	 * 
	 * @param s title string
	 */
	public void setTitle(String s) {
		title = s;
	}

	/**
	 * Returns the dataset IDs displayed in the plot
	 * @return
	 */
	public int[] getDataSetIDs() {
		int[] ids = new int[dataSetIDs.size()];
		for(int i = 0; i < dataSetIDs.size(); i++) {
			ids [i] = dataSetIDs.get(i);
		}
		return ids;
	}
}
