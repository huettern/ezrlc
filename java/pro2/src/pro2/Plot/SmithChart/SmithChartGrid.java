package pro2.Plot.SmithChart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import pro2.Plot.PlotDataSet;
import pro2.util.Complex;
import pro2.util.UIUtil;

public class SmithChartGrid {

	//================================================================================
    // Settings
    //================================================================================
	double majorImagGridValues[] = {0.4,1.0,2.0,5.0,10.0};
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

	private List<Point> imagAxisLabelPointsPos = new ArrayList<Point>();
	private List<Point> imagAxisLabelPointsNeg = new ArrayList<Point>();
	
	
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
	
	private double zo;	// reference to which the values are normalized to
	
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
	private final Font labelFont = new Font("Arial", Font.BOLD, 12);
	
	
	//================================================================================
    // Constructor
    //================================================================================
	public SmithChartGrid(SmithChart parent, double zo) {
		this.parent = parent;
		this.zo = zo;

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
    // Getters Functions
    //================================================================================
	public Point getCenter () {
		return new Point(this.center);
	}
	public int getDiameter() {
		return this.diameter;
	}
	public double getZ0 () {
		return this.zo;
	}
	public Area getDrawingArea () {
		return new Area(imagPlotArea);
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
		Rectangle whole = g2.getClipBounds();//g2 is my Graphics2D object
		g2.setColor(Color.LIGHT_GRAY);
		UIUtil.drawCenterCircle(g2, center, radius);
		g2.setColor(Color.LIGHT_GRAY);
        // Paint horizontal line
		g2.drawLine(center.x-radius, center.y, center.x+radius, center.y);
		
		// Draw Real axis circles
		Point p = new Point(0,center.y);
		int index = 0;
		for (Point point : majorRealAxisPoints ) {
			rad=(center.x+radius)-point.x;
			g2.setColor(Color.LIGHT_GRAY);
			UIUtil.drawCenterCircle(g2, point, rad);
			g2.setColor(Color.BLACK);
			g2.drawString(String.format("%2.1f", majorGridRealValues.get(index++)), point.x-rad, point.y);
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
			rad=(center.x+radius)-point.x;
			UIUtil.drawCenterCircle(g2, point, rad);
		}
		
		// Draw minor imag grid
		g2.setClip(minorImagGridClippingArea);
		g2.setStroke(dashed);
		//g2.setColor(Color.RED);
		for (Point point : minorImagAxisPoints) {
			rad = Math.abs(center.y - point.y);
			UIUtil.drawCenterCircle(g2, point, rad);
		}
		
		// Draw imag axis labels
		g2.setClip(whole);
		g2.setStroke(origStroke);
		Font a = g2.getFont();
		//g2.setFont(labelFont);
		g2.setColor(Color.BLACK);
		int i = 0;
		for (Point point : imagAxisLabelPointsPos) {
			UIUtil.drawCenterString(g2, point, String.format("%.1f", this.majorImagGridValues[i++]));
		}
		i = 0;
		for (Point point : imagAxisLabelPointsNeg) {
			UIUtil.drawCenterString(g2, point, String.format("%.1f", -this.majorImagGridValues[i++]));
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
		
		// smith chart math with reference 1
		SmithChartMath sm = new SmithChartMath(center, diameter, 1);
		
		imagPlotArea = new Area(new  Ellipse2D.Double(center.x-radius, center.y-radius, diameter,diameter));
		
		// Major Real axis grid
		majorRealAxisPoints = new ArrayList<Point>(majorGridRealValues.size());
		for (Double d : majorGridRealValues) {
			majorRealAxisPoints.add(sm.getRealGridCenterPoint(d).point());
		}
		
		// Major imag circles
		majorImagAxisPoints = new ArrayList<Point>(majorGridImagValues.size());
		for (Double d : majorGridImagValues) {
			majorImagAxisPoints.add(sm.getImagGridCenterPoint(d).point());
		}
		
		// Minor Real axis grid
		// center points
		minorRealAxisPoints = new ArrayList<Point>(minorGridRealValues.size());
		for (Double d : minorGridRealValues) {
			minorRealAxisPoints.add(sm.getRealGridCenterPoint(d).point());
		}
		// clipping area
		// upper half circle
		p = new Point(sm.getImagGridCenterPoint(this.minorRealGridClipThreshold).point());
		rad = (double)Math.abs(center.y - p.y);
		Area a = new Area(new Ellipse2D.Double(p.x-rad, p.y-rad, 2.0*rad, 2.0*rad));

		// lower half circle
		p = new Point(sm.getImagGridCenterPoint(-this.minorRealGridClipThreshold).point());
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
			minorImagAxisPoints.add(sm.getRealGridCenterPoint(d).point());
		}
		
		// Minor imag axis grid
		// center points
		minorImagAxisPoints = new ArrayList<Point>(minorGridImagValues.size());
		for (Double d : minorGridImagValues) {
			minorImagAxisPoints.add(sm.getImagGridCenterPoint(d).point());
		}
		// clipping area
		p = new Point(sm.getRealGridCenterPoint(this.minorImagGridClipThreshold).point());
		rad = (double)((center.x+radius)-p.x)/2;
		p.x=(int) (p.x+rad);
		a = new Area(new Ellipse2D.Double(p.x-rad, p.y-rad, 2.0*rad, 2.0*rad));
		a2 = new Area(imagPlotArea);
		a2.subtract(a);
		minorImagGridClippingArea=a2;
		
		// Imag axis labels
		imagAxisLabelPointsPos = new ArrayList<Point>(this.majorImagGridValues.length);
		imagAxisLabelPointsNeg = new ArrayList<Point>(this.majorImagGridValues.length);
		for (int i = 0; i < (this.majorImagGridValues.length); i++) {
			imagAxisLabelPointsPos.add(sm.getPixelLocation(new Complex(0, majorImagGridValues[i])).point());
			imagAxisLabelPointsNeg.add(sm.getPixelLocation(new Complex(0, -majorImagGridValues[i])).point());
		}
	}

	public void setReferenceResistance(double referenceResistance) {
		this.zo = referenceResistance;
	}
}
