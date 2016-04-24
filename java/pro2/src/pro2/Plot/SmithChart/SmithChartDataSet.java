package pro2.Plot.SmithChart;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import pro2.util.Complex;
import pro2.util.PointD;

public class SmithChartDataSet {
	

	//================================================================================
    // Private Data
    //================================================================================
	private SmithChartGrid grid;
	
	private List<Complex> data;
	private List<Double> freq;
	private int points;
	

	private List<Point> data_pts = new ArrayList<Point>(); // Datapoints on the chart
	
	//================================================================================
    // Constructors
    //================================================================================
	/**
	 * Creates a new SmithChart Dataset 
	 * @param data List of complex data
	 * @param freq frequency points
	 */
	public SmithChartDataSet(SmithChartGrid grid, List<Complex> data, List<Double> freq) {
		if(data.size() != freq.size()) {
			System.out.println("FATAL(SmithChartDataSet): data size not equal freq size");
			return;
		}
		// Copy Data
		this.data = new ArrayList<Complex>(data.size());
		for (Complex c : data) {
			this.data.add(c);
		}
		this.freq = new ArrayList<Double>(freq.size());
		for (Double d : freq) {
			this.freq.add(d);
		}
		this.points = data.size();
		
		// Save grid
		this.grid = grid;
		System.out.println("new SmithChartDataSet Added with size="+this.points);
	}


	//================================================================================
    // Public Functions
    //================================================================================
	public void paint(Graphics g) {
		this.eval();
		// Draw Data point
		for (Point p : data_pts) {
			g.fillOval(p.x-2, p.y-2, 4, 4);
		}
	}
	

	//================================================================================
    // Private Functions
    //================================================================================
	/**
	 * Evaluates the the data points of the Data set
	 */
	private void eval () {
		PointD p;
		
		SmithChartMath sm = new SmithChartMath(grid.getCenter(), grid.getDiameter(), grid.getNorm());
		
		data_pts = new ArrayList<Point>(this.points);
		
		for (int i = 0; i < this.points; i++) {
			p =  sm.getPixelLocation(this.data.get(i));
			data_pts.add(p.point());
		}
	}
	
	
}
