package pro2.Plot.SmithChart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import pro2.Plot.PlotDataSet;
import pro2.util.UIUtil;

public class SmithChartGrid {

	//================================================================================
    // Settings
    //================================================================================
	double majorImagGridValues[] = {0.4,1.0,2.0,10.0};
	double minorImagGridValues[] = {0.1,0.2,0.3,0.6,0.8,1.5};
	double minorImagGridClipThreshold = 2.0;
	

	double majorRealGridValues[] = {0.4,1.0,2.0,10.0};
	double minorRealGridValues[] = {0.1,0.2,0.3,0.6,0.8,1.5,
			5.0, 10.0, 20.0};
	double minorRealGridClipThreshold = 2.0;
	
	
	//================================================================================
    // Private Data
    //================================================================================
	private int border = 20;	// clean border
	
	private List<Point> majorRealAxisPoints = new ArrayList<Point>();
	private List<Point> minorRealAxisPoints = new ArrayList<Point>();
	private List<Point> majorImagAxisPoints = new ArrayList<Point>();
	private List<Point> minorImagAxisPoints = new ArrayList<Point>();
	
	
	// angle circles
	private List<Double> gridAngleValues = new ArrayList<Double>();

	// Values on real axis
	private List<Double> majorGridRealValues = new ArrayList<Double>();
	// normalized to [-1 1] real values
	private List<Double> majorNormalizedGridRealValues = new ArrayList<Double>();
	// minor
	private List<Double> minorGridRealValues = new ArrayList<Double>();
	// normalized to [-1 1] real values
	private List<Double> minorNormalizedGridRealValues = new ArrayList<Double>();
	
	// Values on imag axis
	private List<Double> majorGridImagValues = new ArrayList<Double>();
	// normalized to [-1 1] imag values
	private List<Double> majorNormalizedGridImagValues = new ArrayList<Double>();
	// minor
	private List<Double> minorGridImagValues = new ArrayList<Double>();
	// normalized to [-1 1] imag values
	private List<Double> minorNormalizedGridImagValues = new ArrayList<Double>();
	
	private double zo = 1;	// reference to which the values are normalized to
	
	private SmithChart parent;
	
	private Area imagPlotArea;
	private Area minorRealGridClippingArea;
	private Area minorImagGridClippingArea;
	
	private int parentHeight, parentWidth, height, width;
	private int diameter, radius;
	private Point origin = new Point(); 	// top left origin point
	private Point center = new Point();		// center of the plot
	
	final static float dash1[] = {10.0f};
	final static BasicStroke dashed =
	        new BasicStroke(1.0f,
	                        BasicStroke.CAP_BUTT,
	                        BasicStroke.JOIN_MITER,
	                        10.0f, dash1, 0.0f);
	
	//================================================================================
    // Constructor
    //================================================================================
	public SmithChartGrid(SmithChart parent) {
		this.parent = parent;

		// Major grid lines
		for (int i = 0; i < majorImagGridValues.length; i++) {
			majorGridImagValues.add(majorImagGridValues[i]);
			majorGridImagValues.add(0.0-majorImagGridValues[i]);
		}
		for (int i = 0; i < majorRealGridValues.length; i++) {
			majorGridRealValues.add(majorRealGridValues[i]);
		}
		
		// Minor grid lines
		for (int i = 0; i < minorImagGridValues.length; i++) {
			minorGridImagValues.add(minorImagGridValues[i]);
			minorGridImagValues.add(0.0-minorImagGridValues[i]);
		}
		for (int i = 0; i < minorRealGridValues.length; i++) {
			minorGridRealValues.add(minorRealGridValues[i]);
		}
		
		
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
		BasicStroke origStroke = (BasicStroke) g2.getStroke();
		g2.setColor(Color.LIGHT_GRAY);
		UIUtil.drawCenterCircle(g2, center, radius);
		g2.setColor(Color.LIGHT_GRAY);
        // Paint horizontal line
		g2.drawLine(center.x-radius, center.y, center.x+radius, center.y);
		
		// Draw Real axis circles
		Point p = new Point(0,center.y);
		int index = 0;
		for (Point point : majorRealAxisPoints ) {
			//g2.fillOval(point.x-2, point.y-2, 4, 4);
			g2.drawString(String.format("%2.1f", majorGridRealValues.get(index++)), point.x, point.y);
			rad=((center.x+radius)-point.x)/2;
			p.x=point.x+rad;
			UIUtil.drawCenterCircle(g2, p, rad);
		}
		g2.setColor(Color.LIGHT_GRAY);
		
		// Draw imag circles
		g2.setClip(imagPlotArea);
		for (Point point : majorImagAxisPoints) {
			rad = Math.abs(center.y - point.y);
			UIUtil.drawCenterCircle(g2, point, rad);
		}
		
		// Draw minor real grid
		g2.setClip(minorRealGridClippingArea);
		g2.setStroke(dashed);
		p = new Point(0,center.y);
		for (Point point : minorRealAxisPoints) {
			rad=((center.x+radius)-point.x)/2;
			p.x=point.x+rad;
			UIUtil.drawCenterCircle(g2, p, rad);
		}
		
		// Draw minor imag grid
		g2.setClip(minorImagGridClippingArea);
		g2.setStroke(dashed);
		//g2.setColor(Color.RED);
		for (Point point : minorImagAxisPoints) {
			rad = Math.abs(center.y - point.y);
			UIUtil.drawCenterCircle(g2, point, rad);
		}
		
		
    }
	

	//================================================================================
    // Private Functions
    //================================================================================
	public void evalSize () {
		Point p;
		Double rad;
		int ctr = 0;
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
		
		imagPlotArea = new Area(new  Ellipse2D.Double(center.x-radius, center.y-radius, diameter,diameter));
		
		// Major Real axis grid
		majorRealAxisPoints = new ArrayList<Point>(majorGridRealValues.size());
		for (Double d : majorGridRealValues) {
			majorRealAxisPoints.add(getRealGridCenterPoint(d));
		}
		
		// Major imag circles
		majorImagAxisPoints = new ArrayList<Point>(majorGridImagValues.size());
		for (Double d : majorGridImagValues) {
			majorImagAxisPoints.add(getImagGridCenterPoint (d));
		}
		
		// Minor Real axis grid
		// center points
		minorRealAxisPoints = new ArrayList<Point>(minorGridRealValues.size());
		for (Double d : minorGridRealValues) {
			minorRealAxisPoints.add(getRealGridCenterPoint(d));
		}
		// clipping area
		// upper half circle
		p = new Point(getImagGridCenterPoint(this.minorRealGridClipThreshold));
		rad = (double)Math.abs(center.y - p.y);
		Area a = new Area(new Ellipse2D.Double(p.x-rad, p.y-rad, 2.0*rad, 2.0*rad));

		// lower half circle
		p = new Point(getImagGridCenterPoint(-this.minorRealGridClipThreshold));
		rad = (double)Math.abs(center.y - p.y);
		Area a2  = new Area(new Ellipse2D.Double(p.x-rad, p.y-rad, 2.0*rad, 2.0*rad));

		// add those
		a.add(a2);
		
		// parent area
		Area parentArea = new Area(new Rectangle2D.Double(0, 0, width, height));
		parentArea.subtract(a);
		minorRealGridClippingArea = parentArea;
		
		// Minor imag axis grid
		minorImagAxisPoints = new ArrayList<Point>(minorGridImagValues.size());
		for (Double d : minorGridImagValues) {
			minorImagAxisPoints.add(getRealGridCenterPoint(d));
		}
		
		// Minor imag axis grid
		// center points
		minorImagAxisPoints = new ArrayList<Point>(minorGridImagValues.size());
		for (Double d : minorGridImagValues) {
			minorImagAxisPoints.add(getImagGridCenterPoint (d));
		}
		// clipping area
		p = new Point(getRealGridCenterPoint(this.minorImagGridClipThreshold));
		rad = (double)((center.x+radius)-p.x)/2;
		p.x=(int) (p.x+rad);
		a = new Area(new Ellipse2D.Double(p.x-rad, p.y-rad, 2.0*rad, 2.0*rad));
		a2 = new Area(imagPlotArea);
		a2.subtract(a);
		minorImagGridClippingArea=a2;
		
		// Clipping area for minor imag grid lines
//		Double d = (minorImagGridClipThreshold-zo)/(minorImagGridClipThreshold+zo);
//		p = new Point(0,0);
//		p.y = center.y;
//		p.x = (int)((diameter*(d+1)/2) + (center.x-radius)); //stretch to display area
//		double rad=((center.x+radius)-p.x)/2;
//		// Draw imag circles
//		minorImagGridClippingArea = new Area(new  Ellipse2D.Double(p.x-rad, p.y-rad, 2*rad,2*rad));

		
		
//		angleCircleCenterPoints = new ArrayList<Point>(gridAngleValues.size());
//		for (Double d : gridAngleValues) {
//			p = new Point();
//			p.x = center.x+radius;
//			p.y = (int)(center.y - (radius*Math.tan(d/2)));
//			angleCircleCenterPoints.add(p);
//		}
	}
	
	/**
	 * Returns the center point of a circle of the real axis
	 * @param val The (not normalized) value of the axis point
	 * @return Center point
	 */
	private Point getRealGridCenterPoint(double val){
		Double norm;
		
		norm = Math.abs(val);
		norm = (norm-zo)/(norm+zo);
		
		int y_coordinate = center.y;
		int x_coordinate = (int)((diameter*(norm+1)/2) + (center.x-radius)); //stretch to display area
		return new Point(x_coordinate, y_coordinate);
	}
	
	/**
	 * Calculates the center point of a imag axis circle
	 * @param val The value of the gridline
	 * @return returns the centerpoint of the circle
	 */
	private Point getImagGridCenterPoint(double val) {
		Double norm,mul;
		
		norm = Math.abs(val);
		norm = (norm-zo)/(norm+zo);
		
		if(val < 0) {
			mul = norm - 1.0;
		}
		else {
			mul = 1.0 - norm;
		}
		// calcualte y coordinate
		int y_coordinate = (int)(center.y - (radius*Math.tan(mul*Math.PI/4)));
		// store them
		return new Point(center.x+radius, y_coordinate);
	}
	
}
