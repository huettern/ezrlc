package pro2.Plot.SmithChart;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import pro2.MVC.Model;
import pro2.Plot.PlotDataSet;
import pro2.Plot.RectPlot.DataSetSettings;
import pro2.Plot.RectPlot.RectPlotNewMeasurement;
import pro2.util.Complex;
import pro2.util.PointD;

public class SmithChart extends JPanel implements Observer, MouseListener {


	//================================================================================
    // Private Data
    //================================================================================
	private SmithChartGrid grid;

//	private List<SmithChartDataSet> data_sets = new ArrayList<SmithChartDataSet>();
	
	private SmithChartSettings settings = new SmithChartSettings();
	
	private List<SmithChartDataSet> dataSets = new ArrayList<SmithChartDataSet>();
	private List<Integer> dataSetIDs = new ArrayList<Integer>();
	private List<DataSetSettings> dataSetSettings = new ArrayList<DataSetSettings>();
	
	
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
    // Constructor
    //================================================================================
	public SmithChart() {
		System.out.println("New SmithChart");
		super.setBackground(Color.WHITE);
		
		settings.referenceResistance = 1.0;
		
		// Build SmithChartGrid
		grid = new SmithChartGrid(this, settings.referenceResistance);
		
		addMouseListener(this);
		
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
	 * Updates the local stored datasets
	 * @param model
	 */
	private void updateDatasets(Model model) {
		int i = 0;
		SmithChartDataSet set;
		for (Integer id : dataSetIDs) {
			set = model.getSmithChartDataSet(id);
			set.setGrid(this.grid);
			set.setDataSetSettings(this.dataSetSettings.get(i));
			this.dataSets.set(i, set);
			i++;
		}
	}

	//================================================================================
    // Public Functions
    //================================================================================
	
	/**
	 * Adds a new Dataset to the plot
	 * @param data Complex List of data
	 * @param freq Double List of freq data
	 */
	public void addDataSet (List<Complex> data, List<Double> freq) {
		SmithChartDataSet set = new SmithChartDataSet(grid, data, freq);
		dataSets.add(set);	
	}
	
	/**
	 * Add dataset in model by ID
	 * @param id id in the model
	 * @param newMeasurement settings
	 */
	public void addDataSet(int id, SmithChartNewMeasurement newMeasurement) {
		DataSetSettings set = new DataSetSettings();
		set.setLineColor(this.getNextColor());
		set.setLabel(newMeasurement.src_name);

		this.dataSetIDs.add(id);
		this.dataSetSettings.add(set);
		this.dataSets.add(null);
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		Model model = (Model) o;
		this.updateDatasets(model);
	}
	

	/**
	 * Paints the panel and its contents
	 */
	@Override
    public void paintComponent(Graphics g)
    {
		// Paint parent
        super.paintComponent(g);
        // Paint grid
        grid.paint(g);
        // Paint datasets
        for (SmithChartDataSet set : dataSets) {
			set.paint(g);
		}
        // Paint reference resistance
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Arial", 0, 18));
        g.drawString(String.format("Z0: %.1f Ω", settings.referenceResistance), 10, this.getHeight()-10);
    }


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("X="+e.getX()+" Y="+e.getY());
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	public SmithChartSettings getSettings() {
		// TODO Auto-generated method stub
		return this.settings;
	}


	public void setSettings(SmithChartSettings s) {
		this.settings = s;
		this.updateSettings();
		repaint();
	}


	private void updateSettings() {
		grid.setReferenceResistance(settings.referenceResistance);
	}



}
