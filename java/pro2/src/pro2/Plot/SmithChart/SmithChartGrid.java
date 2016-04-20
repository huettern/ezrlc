package pro2.Plot.SmithChart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import pro2.Plot.PlotDataSet;

public class SmithChartGrid {

	//================================================================================
    // Private Data
    //================================================================================
	private int border = 20;	// clean border
	
	private int realAxisSteps = 5;
	private List<Point> realAxisPoints = new ArrayList<Point>(realAxisSteps);
	
	
	private SmithChart parent;
	
	private int parentHeight, parentWidth, height, width;
	private int diameter;
	private Point origin = new Point(); 	// top left origin point
	private Point center = new Point();		// center of the plot
	
	
	//================================================================================
    // Constructor
    //================================================================================
	public SmithChartGrid(SmithChart parent) {
		this.parent = parent;
		for(int i = 0; i<realAxisSteps; i++) {
			realAxisPoints.add(new Point());
		}
	}

	//================================================================================
    // Public Functions
    //================================================================================
    public void paint(Graphics g)
    {
    	int dia;
		this.evalSize();
    	// Paint outter border
		Graphics2D g2 = (Graphics2D)g.create();
		g2.setColor(Color.LIGHT_GRAY);
		g2.drawOval(origin.x, origin.y, diameter, diameter);
        // Paint horizontal line
		g2.drawLine(origin.x, origin.y + diameter/2, origin.x + diameter, origin.y + diameter/2);
		// Draw Real axis circles
		int rightMargin = border+diameter;
		for (Point point : realAxisPoints) {
			dia = rightMargin-point.x; 
			g2.drawOval(point.x, point.y-(dia/2), dia, dia);
		}
    }
	

	//================================================================================
    // Private Functions
    //================================================================================
	public void evalSize () {
		this.parentHeight = parent.getHeight();
		this.parentWidth = parent.getWidth();
		this.height = parentHeight - 2*border;
		this.width = parentWidth - 2*border;
		
		if(parentWidth >= parentHeight) diameter = height;
		else diameter = width;

		this.origin.x = (int)((parentWidth-diameter)/2);
		this.origin.y = border;

		this.center.x = (parentWidth/2);
		this.center.y = (parentHeight/2);
		
		// Real axis points
		double spacing = width / realAxisSteps;
		for(int i = 0; i<realAxisSteps; i++) {
			realAxisPoints.get(i).x = (int)(width - (i*spacing));
			realAxisPoints.get(i).y = center.y;
		}
		
		
	}
	
}
