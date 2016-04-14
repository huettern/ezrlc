package pro2.Plot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ArrayList;

import javax.swing.JPanel;

import pro2.MVC.Model;
import pro2.Plot.Grid.Orientation;
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
		
		// Add Axis
		//horAxis = new Axis(this, Axis.Orientation.HORIZONTAL, origin);
		//this.horAxis = new Axis(this, Axis.Orientation.HORIZONTAL, origin, 40, 0, 100, 10, 20);	// Use for test data
		//this.horAxis = new Axis(this, Axis.Orientation.HORIZONTAL, origin, 40, 1e6, 1e9, 10, 20); 	// Use for r100l10uZRI
		this.horAxis = new Axis(this, Axis.Orientation.HORIZONTAL, origin, 40, 0, 1000000.0, 2, 20); 	// Use for bsp11
		//verAxis = new Axis(this, Axis.Orientation.VERTICAL, origin);
		//this.verAxis = new Axis(this, Axis.Orientation.VERTICAL, origin, 40, 0, 1, 20, -20); // Use for test data
		//this.verAxis = new Axis(this, Axis.Orientation.VERTICAL, origin, 40, 0, 70e3, 20, -20); // Use for r100l10uZRI
		this.verAxis = new Axis(this, Axis.Orientation.VERTICAL, origin, 40, 0, 2000, 2, -20); // Use for bsp11
		
		// Add Grid
		this.verGrid = new Grid(this, Orientation.VERTICAL, Color.LIGHT_GRAY, horAxis, 40);
		this.horGrid = new Grid(this, Orientation.HORIZONTAL, Color.LIGHT_GRAY, verAxis, 40);
		
		repaint();
	}
	
	
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
				System.out.println("plotting dataset");
				plotDataSet.paint(g);
			}
		}
    }

	/**
	 * Add a new Dataset to the plot
	 * @param dataSet
	 */
	public void addDataSet(PlotDataSet dataSet) {
		// TODO Auto-generated method stub
		dataSet.setAxis(horAxis, verAxis);
		this.dataSets.add(dataSet);
		
	}

	/**
	 * Add a new Dataset to the plot by ID
	 * @param dataSet
	 */
	public void addDataSet(int id) {
		this.dataSetIDs.add(id);
		this.dataSets.add(null);
	}


	@Override
	public void update(Observable o, Object arg) {
		int i = 0;
		PlotDataSet dataSet;
		
		System.out.println("RectPlor update");
		
		Model model = (Model) o;
		
		for (Integer integer : dataSetIDs) {
//			dataSet = new PlotDataSet( model.getDataSet(integer.intValue()).getXData(),
//					 	model.getDataSet(integer.intValue()).getYData() );
			dataSet = model.getDataSet(integer.intValue());
			dataSet.setAxis(this.horAxis, this.verAxis);
			this.dataSets.set(i, dataSet);
			i++;
		}
	}

}
