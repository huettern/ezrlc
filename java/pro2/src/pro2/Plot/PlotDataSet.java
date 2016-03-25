package pro2.Plot;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import pro2.util.MathUtil;

public class PlotDataSet {

	//================================================================================
    // Private Data
    //================================================================================
	private List<Double> x_data;
	private List<Double> y_data;
	private int points = 0;
	private double x_max;
	private double x_min;
	private double y_max;
	private double y_min;
	private double x_span;
	private double y_span;
	
	private Axis x_axis;
	private Axis y_axis;
	
	private List<Point> data_pts;
	

	//================================================================================
    // Constructors
    //================================================================================
	public PlotDataSet(List<Double> x,List<Double> y) {
		this.x_data = x;
		this.y_data = y;
		this.points = x.size();
		this.data_pts = new ArrayList<Point>(this.points);
		
		// Get the datas max and min
		x_max = MathUtil.getMax(this.x_data);
		x_min = MathUtil.getMin(this.x_data);
		y_max = MathUtil.getMax(this.y_data);
		y_min = MathUtil.getMin(this.y_data);

		this.x_span = this.x_max - this.x_min;
		this.y_span = this.y_max - this.y_min;
	}

	//================================================================================
    // Private Functions
    //================================================================================
	private void eval() {
		// Get Axis points
		Point xstart = this.x_axis.getStart();
		Point xend = this.x_axis.getEnd();
		Point ystart = this.y_axis.getStart();
		Point yend = this.y_axis.getEnd();
		
		// Calculate X and Y lenght in pixels
		int pdx = xend.x - xstart.x;
		int pdy = yend.y - ystart.y; 
		
		// Get Axis start and end values
		double xmin = this.x_axis.getMin();
		double xmax = this.x_axis.getMax();
		double ymin = this.y_axis.getMin();
		double ymax = this.y_axis.getMax();
		
		// Calculate X and Y laenght in values
		double vdx = xmax - xmin;
		double vdy = ymax - ymin;
		
		int i = 0;
		Double tmp = 0.0;
		for (Point point : this.data_pts) {
			// get x pixel values
			tmp = ( (x_data.get(i) * vdx) / this.x_span);
			tmp = ( (tmp * pdx) / this.x_span);
			point.x = tmp.intValue() + xstart.x;
			// get y pixel values
			tmp = ( (y_data.get(i) * vdy) / this.y_span);
			tmp = ( (tmp * pdy) / this.y_span);
			point.y = tmp.intValue() + ystart.y;
			i++;
		}
	}
	
	private void drawPoint (Graphics g, Point pt) {
		g.drawOval(pt.x-1, pt.y-1, 2, 2);
	}
	
	//================================================================================
    // Public Functions
    //================================================================================
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		this.eval();
		
		for (Point point : this.data_pts) {
			this.drawPoint(g, point);
		}
		
	}
	
	public void setAxis(Axis x, Axis y) {
		this.x_axis=x;
		this.y_axis=y;
	}
	
	

}
