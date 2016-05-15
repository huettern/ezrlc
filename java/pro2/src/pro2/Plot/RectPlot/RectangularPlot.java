package pro2.Plot.RectPlot;

import java.awt.Color;
import java.awt.Font;
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

import javax.swing.JLabel;
import javax.swing.JPanel;

import pro2.MVC.Model;
import pro2.Plot.Grid.Orientation;
import pro2.util.MathUtil;
import pro2.util.UIUtil;
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
	
	private boolean enableAutoAutoscale = true;
	
	private String title = null;
	private int titleSize = 12;
	
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
//		// Expand data set list to size
//		for(; dataSets.size() < dataSetIDs.size(); ){
//			dataSets.add(null);
//		}
		// fill datasets
		for (Integer integer : dataSetIDs) {
			dataSet = model.getDataSet(integer.intValue());
			dataSet.setAxis(this.horAxis, this.verAxis);
			dataSet.setDataSetSettings(this.dataSetSettings.get(i));
			this.dataSets.set(i, dataSet);
			i++;
		}
		// if only one dataset, do autoscale
		if(this.dataSets.size() == 1 && this.enableAutoAutoscale) {
			this.autoScale();
		}
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
        
        // paint title
        if(title != null) {
        	Font f = g.getFont();
        	g.setFont(new Font(f.getFontName(), Font.BOLD, titleSize));
        	UIUtil.drawCenterString(g, new Point(this.getWidth()/2, 12), title );
        }
    }

	/**
	 * Add a new Dataset to the plot by ID
	 * @param rectPlotNewMeasurement 
	 * @param id
	 */
	public void addDataSet(int id, RectPlotNewMeasurement rectPlotNewMeasurement) {
		DataSetSettings set = new DataSetSettings();
		set.setLineColor(UIUtil.getNextColor());
		set.setLabel(this.createLabelString(rectPlotNewMeasurement));
		
		this.dataSetIDs.add(id);
		this.dataSetSettings.add(set);
		this.dataSets.add(null);
	}
	
	/**
	 * Add a new Dataset to the plot by ID
	 * @param rectPlotNewMeasurement 
	 * @param id
	 * @param color Color index
	 */
	public void addDataSet(int id, RectPlotNewMeasurement rectPlotNewMeasurement, int color) {
		DataSetSettings set = new DataSetSettings();
		set.setLineColor(UIUtil.getColor(color));
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
		this.enableAutoAutoscale = false;
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
		if(dataSetIDs.isEmpty()) {
			this.enableAutoAutoscale = true;
		}
		this.repaint();
	}
	
	/**
	 * Removes all stored datasets
	 */
	public void removeAllDatasets () {
		int ctr = 0;
		for (Iterator<Integer> iter = dataSetIDs.iterator(); iter.hasNext(); ctr++) {
			Integer i = iter.next();
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
		autoScale(Scale.LINEAR, Scale.LINEAR);
	}
	/**
	 * Scales Axis to show all Data, with x ang y scale
	 */
	public void autoScale(Scale sx, Scale sy) {
//		double xmin = 0;
//		double xmax = 1;
//		double ymin = 0;
//		double ymax = 1;
		double xmin = Double.MAX_VALUE;
		double xmax = -Double.MAX_VALUE;
		double ymin = Double.MAX_VALUE;
		double ymax = -Double.MAX_VALUE;
		
		// Crawl all datasets for max / min values
		int i = 0;
		for (Integer ctr : this.dataSetIDs) {
			if(dataSets.get(i).getXMax() > xmax) { xmax = dataSets.get(i).getXMax(); }
			if(dataSets.get(i).getXMin() < xmin) { xmin = dataSets.get(i).getXMin(); }
			if(dataSets.get(i).getYMax() > ymax) { ymax = dataSets.get(i).getYMax(); }
			if(dataSets.get(i).getYMin() < ymin) { ymin = dataSets.get(i).getYMin(); }
			// if log X-axis, set minimum at first non-zero value
			if(sx == Scale.LOG) {
				if(dataSets.get(i).getXData()[0] == 0) { xmin = dataSets.get(i).getXData()[1]; }
				else { xmin = dataSets.get(i).getXData()[0]; }
			}
		}

		System.out.println("ORIG: xmin="+xmin+" xmax="+xmax+" ymin="+ymin+" ymax="+ymax);
//		xmax *= 1.1;
		ymax *= 1.1;
		ymin *= 0.9;
//		xmin *= 0.9;
//		System.out.println("LIM: xmin="+xmin+" xmax="+xmax+" ymin="+ymin+" ymax="+ymax);
		
		// if log Y-axis, set minimum at first non-zero or negative value
		if(settings.yScale == Scale.LOG) {
			ymin = Double.MAX_VALUE;
			for (PlotDataSet dataset : this.dataSets) {
				for (Double d : dataset.getYData()) {
					if((d > 0) && (d < ymin)) {ymin = d;}
				}
//				if(dataset.getYMin() <= 0) {ymin = dataset.}
			}
		}

		int yexp = 0;
		// check for delta smaller 1
		if((ymax-ymin) < 1) {
			int expmax = (int)(Math.floor(Math.log10(Math.abs(ymax))));	//exponent
			int expmin = (int)(Math.floor(Math.log10(Math.abs(ymin))));	//exponent
			// get smaller exponent
			if(expmax > expmin) yexp = expmax; else yexp = expmin;
			System.out.println("yexp="+yexp);
			// divide out the exponent
			ymin = ymin / (Math.pow(10, yexp));
			ymax = ymax / (Math.pow(10, yexp));
		}
		verAxis.setExp(yexp);

//		System.out.println("WO EXPd: xmin="+xmin+" xmax="+xmax+" ymin="+ymin+" ymax="+ymax);
		// round the values
		xmin = MathUtil.roundNice(xmin);
		xmax = MathUtil.roundNice(xmax);
		ymin = MathUtil.roundNice(ymin);
		ymax = MathUtil.roundNice(ymax);
//		System.out.println("NICE: xmin="+xmin+" xmax="+xmax+" ymin="+ymin+" ymax="+ymax);
		
		// reapply exponent
//		ymin = ymin * (Math.pow(10, yexp));
//		ymax = ymax * (Math.pow(10, yexp));
//		System.out.println("EXPd: xmin="+xmin+" xmax="+xmax+" ymin="+ymin+" ymax="+ymax);
		
		
		settings.xAxisMaximum=xmax;
		settings.xAxisMinimum=xmin;
		settings.yAxisMaximum=ymax;
		settings.yAxisMinimum=ymin;
		
		// set step to 10
		if(settings.xScale == Scale.LINEAR) settings.xAxisSteps = 10;
		if(settings.yScale == Scale.LINEAR) settings.yAxisSteps = 10;
		
		updateSettings();
		
		repaint();
	}
	
	/**
	 * Sets the title string
	 * @param s
	 */
	public void setTitle (String s) {
		title = s;
	}
}
