package pro2.Plot.RectPlot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.JPanel;

import pro2.MVC.Model;
import pro2.Plot.Grid.Orientation;
import pro2.util.MathUtil;
import pro2.Plot.Axis;
import pro2.Plot.Axis.Scale;
import pro2.Plot.Grid;
import pro2.Plot.PlotDataSet;

public class RectangularPlot extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;
	//================================================================================
    // Private Data
    //================================================================================
	Point origin = new Point(100,30); // Origin of the coordinate set from bottom left
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
	
	// Data line settings
	private List<Color> plotSetColors = Collections.unmodifiableList(Arrays.asList(
				new Color(0, 113, 188),
				new Color(216, 82, 24),
				new Color(236, 176, 31),
				new Color(125, 46, 141),
				new Color(118, 171, 47),
				new Color(76, 189, 237),
				new Color(161, 19, 46)
			));
	private int PlotSetColorsCtr = 0;	//Holds the index of the next color to be used
	
	//================================================================================
    // Constructors
    //================================================================================
	public RectangularPlot() {
		// TODO Auto-generated constructor stub
		
		System.out.println("New Rect Plot");
		Color col = super.getBackground();
		super.setBackground(Color.WHITE);

		// Origin of the plot
		
		
		// Init settings
		settings.xAxisMinimum = 0;
		settings.xAxisMaximum = 1;
		settings.xAxisSteps = 1;
		settings.yAxisMinimum = 0;
		settings.yAxisMaximum = 1;
		settings.yAxisSteps = 1;
		
		
		// Add Axis
		//horAxis = new Axis(this, Axis.Orientation.HORIZONTAL, origin);
		//this.horAxis = new Axis(this, Axis.Orientation.HORIZONTAL, origin, 40, 0, 100, 10, 20);	// Use for test data
		//this.horAxis = new Axis(this, Axis.Orientation.HORIZONTAL, origin, 40, 1e6, 1e9, 10, 20); 	// Use for r100l10uZRI
		this.horAxis = new Axis(this, Axis.Scale.LINEAR, Axis.Orientation.HORIZONTAL, origin, rightMargin, 
				settings.xAxisMinimum, settings.xAxisMaximum, settings.xAxisSteps, 20); 	// Use for bsp11
		//verAxis = new Axis(this, Axis.Orientation.VERTICAL, origin);
		//this.verAxis = new Axis(this, Axis.Orientation.VERTICAL, origin, 40, 0, 1, 20, -20); // Use for test data
		//this.verAxis = new Axis(this, Axis.Orientation.VERTICAL, origin, 40, 0, 70e3, 20, -20); // Use for r100l10uZRI
		this.verAxis = new Axis(this, Axis.Scale.LINEAR, Axis.Orientation.VERTICAL, origin, topMargin, 
				settings.yAxisMinimum, settings.yAxisMaximum, settings.xAxisSteps, -30); // Use for bsp11
		
		// Add Grid
		this.verGrid = new Grid(this, Orientation.VERTICAL, Color.LIGHT_GRAY, horAxis, 40);
		this.horGrid = new Grid(this, Orientation.HORIZONTAL, Color.LIGHT_GRAY, verAxis, 40);
		
		repaint();
	}

	//================================================================================
    // Private Function
    //================================================================================
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
	 * @param model
	 */
	private void updateDatasets(Model model) {
		int i = 0;
		PlotDataSet dataSet;
		for (Integer integer : dataSetIDs) {
			dataSet = model.getDataSet(integer.intValue());
			dataSet.setAxis(this.horAxis, this.verAxis);
			dataSet.setDataSetSettings(this.dataSetSettings.get(i));
			this.dataSets.set(i, dataSet);
			i++;
		}
	}

	/**
	 * Gets the next color in the color palette and increments counter
	 * @return
	 */
	private Color getNextColor() {
		Color c = this.plotSetColors.get(this.PlotSetColorsCtr);
		this.PlotSetColorsCtr++;
		// If the counter reached the end of the pallete
		if(this.PlotSetColorsCtr >= this.plotSetColors.size()) {
			//start from the beginning
			this.PlotSetColorsCtr = 0;
		}
		return c;
	}

	/**
	 * Builds the labelname of the new plot
	 * @param rpnm RectPlotNewMeasurement
	 * @return string
	 */
	private String createLabelString(RectPlotNewMeasurement rpnm) {
		String s;
		
		s = rpnm.src_name +" " +rpnm.type.name() +" " +rpnm.cpxMod.name();
		
		return s;
	}

	private void evalSize() {
		this.plotWidth = this.getWidth() - this.rightMargin - origin.x;
		this.plotHeight = this.getHeight() - this.topMargin - origin.y;
	}

	
	//================================================================================
    // Public Function
    //================================================================================
	
	/**
	 * Paints the panel and its contents
	 */
	@Override
    public void paintComponent(Graphics g)
    {
		// Paint parent
        super.paintComponent(g);
        
        this.evalSize();
        Area plotArea = new Area(new Rectangle2D.Double(origin.x, this.topMargin, plotWidth, plotHeight));
        
        // if only one dataset, do autoscale
        if(this.dataSets.size() == 1) {
        	this.autoScale();
        }
        
        // Paint axis and grid
        this.horAxis.paint(g);
        this.verAxis.paint(g);
        this.verGrid.paint(g);
        this.horGrid.paint(g);
        
        // Paint datasets
        for (PlotDataSet plotDataSet : this.dataSets) {
			if(plotDataSet != null) {
				plotDataSet.paint(g, plotArea);
			}
		}
    }

	/**
	 * Add a new Dataset to the plot by ID
	 * @param rectPlotNewMeasurement 
	 * @param dataSet
	 */
	public void addDataSet(int id, RectPlotNewMeasurement rectPlotNewMeasurement) {
		DataSetSettings set = new DataSetSettings();
		set.setLineColor(this.getNextColor());
		set.setLabel(this.createLabelString(rectPlotNewMeasurement));
		
		this.dataSetIDs.add(id);
		this.dataSetSettings.add(set);
		this.dataSets.add(null);
	}
	

	public RectPlotSettings getSettings () {
		return settings;
	}
	
	public void setSettings(RectPlotSettings s) {
		this.settings = s;
		this.updateSettings();
		repaint();
	}
	
	/**
	 * Removes dataset from data set id list
	 * @param id  data set id
	 */
	public void removeDataset(int id) {
		int ctr = 0;
		for (Iterator<Integer> iter = dataSetIDs.iterator(); iter.hasNext(); ctr++) {
			Integer i = iter.next();
			if(i == id) {
				dataSets.remove(ctr);
				dataSetSettings.remove(ctr);
				iter.remove();
			}
		}
		this.repaint();
	}


	@Override
	public void update(Observable o, Object arg) {
		Model model = (Model) o;
		this.updateDatasets(model);
	}

	
	/**
	 * Returns datasetsettings of the dataset given by the id
	 * @param id data set id
	 * @return
	 */
	public DataSetSettings getDataSetSettings(int id) {
		// search id
		for(int i = 0; i<dataSetIDs.size(); i++){
			if(dataSetIDs.get(i) == id) {
				return dataSetSettings.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Scales Axis to show all Data
	 */
	public void autoScale() {
		double xmin = 0;
		double xmax = 1;
		double ymin = 0;
		double ymax = 1;
		
		// Crawl all datasets for max / min values
		for (PlotDataSet dataset : this.dataSets) {
			if(dataset.getXMax() > xmax) { xmax = dataset.getXMax(); }
			if(dataset.getXMin() < xmin) { xmin = dataset.getXMin(); }
			if(dataset.getYMax() > ymax) { ymax = dataset.getYMax(); }
			if(dataset.getYMin() < ymin) { ymin = dataset.getYMin(); }
			// if log axis, set minimum at first non-zero value
			if(settings.xScale == Scale.LOG) {
				xmin = dataset.getXData().get(1);
			}
		}
		
		
		
		// round the values
		xmin = MathUtil.roundNice(xmin);
		xmax = MathUtil.roundNice(xmax);
		ymin = MathUtil.roundNice(ymin);
		ymax = MathUtil.roundNice(ymax);
		
		settings.xAxisMaximum=xmax;
		settings.xAxisMinimum=xmin;
		settings.yAxisMaximum=ymax;
		settings.yAxisMinimum=ymin;
		
//		// set step to 10
//		if(settings.xScale == Scale.LINEAR) settings.xAxisSteps = 10;
//		if(settings.yScale == Scale.LINEAR) settings.yAxisSteps = 10;
//		
		updateSettings();
		
		repaint();
	}
}
