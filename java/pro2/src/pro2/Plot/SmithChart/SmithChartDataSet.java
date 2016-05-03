package pro2.Plot.SmithChart;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import pro2.Plot.RectPlot.DataSetSettings;
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
	
	private DataSetSettings settings = new DataSetSettings();
		
	private List<Point> data_pts = new ArrayList<Point>(); // Datapoints on the chart
	
	private GeneralPath data_path;

	private BasicStroke data_stroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	
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
		Graphics2D g2 = (Graphics2D) g;
		// Draw Data point
		g2.setColor(settings.getLineColor());
//		for (Point p : data_pts) {
//			g2.fillOval(p.x-2, p.y-2, 4, 4);
//		}
		g2.setStroke(data_stroke);
		g2.draw(data_path);
	}
	

	//================================================================================
    // Private Functions
    //================================================================================
	/**
	 * Evaluates the the data points of the Data set
	 */
	private void eval () {
		PointD p;
		
		SmithChartMath sm = new SmithChartMath(grid.getCenter(), grid.getDiameter(), grid.getZ0());
		
		data_pts = new ArrayList<Point>(this.points);
		
		for (int i = 0; i < this.points; i++) {
			p =  sm.getPixelLocation(this.data.get(i));
			data_pts.add(p.point());
		}

		// create path
		int ctr = 0;
		data_path = new GeneralPath(GeneralPath.WIND_EVEN_ODD, data_pts.size());
		data_path.moveTo(data_pts.get(0).x, data_pts.get(0).y);
		for(ctr = 1; ctr < data_pts.size(); ctr++) {
			data_path.lineTo(data_pts.get(ctr).x, data_pts.get(ctr).y);
		}
	}


	public void setGrid(SmithChartGrid g) {
		this.grid = g;
	}


	public void setDataSetSettings(DataSetSettings s) {
		this.settings = s;
	}
	
	
}
