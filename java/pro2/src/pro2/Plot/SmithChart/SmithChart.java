package pro2.Plot.SmithChart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import pro2.Plot.PlotDataSet;

public class SmithChart extends JPanel implements Observer {


	//================================================================================
    // Private Data
    //================================================================================
	private SmithChartGrid grid;

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
        
        grid.paint(g);
    }
	

}
