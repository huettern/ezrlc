package pro2.Plot.SmithChart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import pro2.Plot.PlotDataSet;
import pro2.util.UIUtil;

public class SmithChartGrid {

	//================================================================================
    // Private Data
    //================================================================================
	private int border = 20;	// clean border
	
	private int realAxisSteps = 5;
	private List<Point> realAxisPoints = new ArrayList<Point>(realAxisSteps);
	private List<Point> angleCircleCenterPoints = new ArrayList<Point>(realAxisSteps);
	
	// angle circles
	private List<Double> gridAngleValues = new ArrayList<Double>();
	// Values on real axis
	private List<Double> gridRealValues = new ArrayList<Double>();
	// normalized to [-1 1] real values
	private List<Double> normalizedGridRealValues = new ArrayList<Double>();
	
	private double zo = 1;	// reference resistance	
	
	private SmithChart parent;
	
	private int parentHeight, parentWidth, height, width;
	private int diameter, radius;
	private Point origin = new Point(); 	// top left origin point
	private Point center = new Point();		// center of the plot
	
	
	//================================================================================
    // Constructor
    //================================================================================
	public SmithChartGrid(SmithChart parent) {
		this.parent = parent;
		for(int i = 0; i<=realAxisSteps; i++) {
			realAxisPoints.add(new Point());
		}
		// Add some real values
		gridRealValues.add(0.0);
		gridRealValues.add(0.2);
		gridRealValues.add(0.4);
		gridRealValues.add(0.6);
		gridRealValues.add(0.8);
		gridRealValues.add(1.0);
		gridRealValues.add(1.2);
		gridRealValues.add(1.4);
		gridRealValues.add(1.6);
		gridRealValues.add(1.8);
		gridRealValues.add(2.0);
		gridRealValues.add(3.0);
		gridRealValues.add(4.0);
		gridRealValues.add(5.0);
		gridRealValues.add(10.0);
		
		
		// Add some angles to plot
		gridAngleValues.add(Math.PI/2);
		gridAngleValues.add(Math.PI/4);
		gridAngleValues.add(3*Math.PI/4);
		gridAngleValues.add(-Math.PI/2);
		gridAngleValues.add(-Math.PI/4);
		gridAngleValues.add(-3*Math.PI/4);
	}

	//================================================================================
    // Public Functions
    //================================================================================
    public void paint(Graphics g)
    {
    	int dia, rad;
		this.evalSize();
    	// Paint outter border
		Graphics2D g2 = (Graphics2D)g.create();
		g2.setColor(Color.BLACK);
		UIUtil.drawCenterCircle(g2, center, radius);
		g2.setColor(Color.LIGHT_GRAY);
        // Paint horizontal line
		g2.drawLine(center.x-radius, center.y, center.x+radius, center.y);
		// Draw Real axis circles
		Point p = new Point(0,center.y);
		int index = 0;
		for (Point point : realAxisPoints ) {
			g2.fillOval(point.x-2, point.y-2, 4, 4);
			g2.drawString(String.format("%2.1f", gridRealValues.get(index++)), point.x, point.y);
			rad=((center.x+radius)-point.x)/2;
			p.x=point.x+rad;
			UIUtil.drawCenterCircle(g2, p, rad);
		}
		g2.setColor(Color.RED);
		// Draw other circles
		Area plotArea = new Area(new  Ellipse2D.Double(center.x-radius, center.y-radius, diameter,diameter));
		g2.setClip(plotArea);
		for (Point point : angleCircleCenterPoints) {
			rad = Math.abs(center.y - point.y);
			g2.fillOval(point.x-2, point.y-2, 4, 4);
			UIUtil.drawCenterCircle(g2, point, rad);
		}
    }
	

	//================================================================================
    // Private Functions
    //================================================================================
	public void evalSize () {
		Point p;
		this.parentHeight = parent.getHeight();
		this.parentWidth = parent.getWidth();
		this.height = parentHeight - 2*border;
		this.width = parentWidth - 2*border;
		
		if(width >= height) diameter = height;
		else diameter = width;
		radius = (int)(diameter/2);

		this.origin.x = (int)((parentWidth-diameter)/2);
		this.origin.y = border;

		this.center.x = (parentWidth/2);
		this.center.y = (parentHeight/2);
		
		// Normalize real axis values
		normalizedGridRealValues = new ArrayList<Double>(gridRealValues.size());
		for (Double d : gridRealValues) {
			normalizedGridRealValues.add((d-1)/(d+1));
		}
		
		// Real axis points
		realAxisPoints = new ArrayList<Point>(gridRealValues.size());
		for (Double d : normalizedGridRealValues) {
			p = new Point();
			p.y = center.y;
			p.x = (int)((diameter*(d+1)/2) + (center.x-radius)); //stretch to display area
			realAxisPoints.add(p);
		}
		
		// upper circles
		angleCircleCenterPoints = new ArrayList<Point>(gridAngleValues.size());
		for (Double d : gridAngleValues) {
			p = new Point();
			p.x = center.x+radius;
			p.y = (int)(center.y - (radius*Math.tan(d/2)));
			angleCircleCenterPoints.add(p);
		}
	}
	
}
