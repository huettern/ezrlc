package pro2.Plot.RectPlot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JPanel;

import pro2.MVC.Controller.DataSource;
import pro2.MVC.Model;
import pro2.Plot.Grid.Orientation;
import pro2.Plot.Axis;
import pro2.Plot.Grid;
import pro2.Plot.PlotDataSet;

public class RectangularPlot extends JPanel implements Observer {

	//================================================================================
    // Private Data
    //================================================================================
	private Axis horAxis;
	private Axis verAxis;

	private Grid horGrid;
	private Grid verGrid;
	
	private List<PlotDataSet> dataSets = new ArrayList<PlotDataSet>();
	private List<Integer> dataSetIDs = new ArrayList<Integer>();
	private List<RectPlotDataSetSettings> dataSetSettings = new ArrayList<RectPlotDataSetSettings>();
	
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
		Point origin = new Point(100,30); 
		
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
		this.horAxis = new Axis(this, Axis.Scale.LINEAR, Axis.Orientation.HORIZONTAL, origin, 40, 
				settings.xAxisMinimum, settings.xAxisMaximum, settings.xAxisSteps, 20); 	// Use for bsp11
		//verAxis = new Axis(this, Axis.Orientation.VERTICAL, origin);
		//this.verAxis = new Axis(this, Axis.Orientation.VERTICAL, origin, 40, 0, 1, 20, -20); // Use for test data
		//this.verAxis = new Axis(this, Axis.Orientation.VERTICAL, origin, 40, 0, 70e3, 20, -20); // Use for r100l10uZRI
		this.verAxis = new Axis(this, Axis.Scale.LINEAR, Axis.Orientation.VERTICAL, origin, 40, 
				settings.yAxisMinimum, settings.yAxisMaximum, settings.xAxisSteps, -20); // Use for bsp11
		
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

        // Paint axis and grid
        this.horAxis.paint(g);
        this.verAxis.paint(g);
        this.verGrid.paint(g);
        this.horGrid.paint(g);
        
        // Paint datasets
        for (PlotDataSet plotDataSet : this.dataSets) {
			if(plotDataSet != null) {
				plotDataSet.paint(g);
			}
		}
    }

	/**
	 * Add a new Dataset to the plot by ID
	 * @param rectPlotNewMeasurement 
	 * @param dataSet
	 */
	public void addDataSet(int id, RectPlotNewMeasurement rectPlotNewMeasurement) {
		RectPlotDataSetSettings set = new RectPlotDataSetSettings();
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


	@Override
	public void update(Observable o, Object arg) {
		Model model = (Model) o;
		this.updateDatasets(model);
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
		}
		
		settings.xAxisMaximum=xmax;
		settings.xAxisMinimum=xmin;
		settings.yAxisMaximum=ymax;
		settings.yAxisMinimum=ymin;
		
		updateSettings();
		
		repaint();
	}
}
