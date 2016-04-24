package pro2.Plot.SmithChart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import pro2.Plot.PlotDataSet;
import pro2.util.Complex;

public class SmithChart extends JPanel implements Observer {


	//================================================================================
    // Private Data
    //================================================================================
	private SmithChartGrid grid;

	private List<SmithChartDataSet> data_sets = new ArrayList<SmithChartDataSet>();
	
	
	//================================================================================
    // Constructor
    //================================================================================
	public SmithChart() {
		System.out.println("New SmithChart");
		super.setBackground(Color.WHITE);
		
		// Build SmithChartGrid
		grid = new SmithChartGrid(this);
		
		
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
		data_sets.add(set);	
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
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
        for (SmithChartDataSet set : data_sets) {
			set.paint(g);
		}
    }
	

}
