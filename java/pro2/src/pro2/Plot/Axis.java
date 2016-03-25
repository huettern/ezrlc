package pro2.Plot;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import pro2.Plot.Axis.Orientation;

public class Axis {

	//================================================================================
    // Public Data
    //================================================================================
	public enum Orientation {
		HORIZONTAL,
		VERTICAL
	};

	//================================================================================
    // Private Data
    //================================================================================
	private Orientation or;
	private RectangularPlot parent;
	
	private int start_x, start_y;
	private int end_x, end_y;
	
	private int origin_x, origin_y;
	private int size;
	
	private double min = 0;
	private double max = 0;
	private int step = 1;

	private int[] label_posx = new int[200];
	private int[] label_posy = new int[200];
	private double[] label_val = new double[200];
	private int label_count = 0;
	private int label_offset = 0;	// Position offset from the axis
	
	private Point[] tic_pos = new Point[200];
	private int ticLenght = 10;

	//================================================================================
    // Constructors
    //================================================================================
	public Axis(RectangularPlot parent, Orientation or, Point origin) {
		this.or = or;
		this.parent = parent;
		this.origin_x = origin.x;
		this.origin_y = origin.y;
		
		for(int i=0; i<200; i++) {
			tic_pos[i] = new Point (0,0);
		}
		this.evalSize();
	}
	public Axis(RectangularPlot parent, Orientation or, Point origin, int size, double min, double max, int step, int labelOffset) {
		this.or = or;
		this.parent = parent;
		this.origin_x = origin.x;
		this.origin_y = origin.y;
		this.size = size;
		this.min = min;
		this.max = max;
		this.step = step;
		this.label_offset = labelOffset;

		for(int i=0; i<200; i++) {
			tic_pos[i] = new Point (0,0);
		}
		this.evalSize();
	}
	
	//================================================================================
    // Public Functions
    //================================================================================
	/**
	 * Paints the Axis
	 * @param g
	 */
	public void paint (Graphics g) {
		this.evalSize();
		
		g.drawLine(this.start_x, this.start_y, this.end_x, this.end_y);
		
		for(int i = 0; i <= label_count; i++)
		{
			//g.drawString(this.formatAxisValue(label_val[i]), label_posx[i], label_posy[i]);
			this.drawCenteredString(g,this.formatAxisValue(label_val[i]),new Point(label_posx[i], label_posy[i]), new Font("Arial", Font.PLAIN, 12));
			this.drawTick(g, tic_pos[i]);
		}
	}

	public void setMinimum(double min) {
		this.min=min;
		
	}
	public void setMaximum(double max) {
		this.max=max;	
	}
	public void setStep(int step) {
		this.step=step;	
	}
	public void setLabelOffset(int off) {
		this.label_offset=off;
	}

	//================================================================================
    // Getter Functions
    //================================================================================
	/**
	 * Returns the location of the axis tics
	 * @return
	 */
	public List<Point> getTicPoints() {
		List<Point> points = new ArrayList<Point>();
		for(int i = 0; i <= this.label_count; i++) {
			points.add(tic_pos[i]);
		}
		return points; 
	}
	
	/**
	 * Returns the Origin of the axis
	 * @return
	 */
	public Point getOrigin () {
		return new Point(this.origin_x, this.origin_y);
	}
	
	/**
	 * Retursn the startpoint of the axis
	 * @return
	 */
	public Point getStart () {
		return new Point(this.start_x, this.start_y);
	}
	
	/**
	 * Returns the endpoint of the axis
	 * @return
	 */
	public Point getEnd () {
		return new Point(this.end_x, this.end_y);
	}
	
	/**
	 * Returns the axis minimum value
	 * @return
	 */
	public double getMin () {
		return this.min;
	}
	
	/**
	 * returns the axis maximum value
	 * @return
	 */
	public double getMax () {
		return this.max;
	}
	
	
	//================================================================================
    // Private Functions
    //================================================================================
	private void evalSize() {
		if(or == Orientation.HORIZONTAL) {
			// Calculate origins
			this.start_x = this.origin_x;
			this.start_y = parent.getHeight()-this.origin_y;
			this.end_x = parent.getWidth() - this.size;
			this.end_y = start_y;
			
			// Calculate String and tic positions
			label_count = this.step;
			double spacing = ((this.end_x-this.start_x)/this.step);
			double data_spacing = ((this.max-this.min)/this.step);
			for(int i = 0; i <= label_count; i++)
			{
				label_posx[i] = this.origin_x + (int)(i*spacing);
				label_posy[i] = this.start_y + this.label_offset;
				label_val[i] = this.min + (i*data_spacing);
				tic_pos[i].x = this.origin_x + (int)(i*spacing);
				tic_pos[i].y = this.start_y;
			}
			
			
			
		}
		else if(or == Orientation.VERTICAL) {
			// Calculate origins
			this.start_x = this.origin_x;
			this.start_y = parent.getHeight()-this.origin_y;
			this.end_x = this.start_x;
			this.end_y = this.size;
			
			// Calculate String positions
			label_count = this.step;
			double spacing = ((this.start_y-this.end_y)/this.step);
			double data_spacing = ((this.max-this.min)/this.step);
			for(int i = 0; i <= label_count; i++)
			{
				label_posy[i] = this.start_y - (int)(i*spacing);
				label_posx[i] = this.start_x + this.label_offset;
				label_val[i] = this.min + (i*data_spacing);
				tic_pos[i].x = this.start_x;
				tic_pos[i].y = this.start_y - (int)(i*spacing);
			}
		}
	}
	
	private String formatAxisValue(double d) {
		return String.format("%.1f", d);
	}
	
	/**
	 * Draw a String centered in the middle of a Rectangle.
	 *
	 * @param g The Graphics instance.
	 * @param text The String to draw.
	 * @param rect The Rectangle to center the text in.
	 */
	private void drawCenteredString(Graphics g, String text, Point point, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics();
	    // Determine the X coordinate for the text
	    int x = point.x - (metrics.stringWidth(text) / 2);
	    // Determine the Y coordinate for the text
	    int y = point.y ;//+ (int)(metrics.getHeight() / 2) ;//+ metrics.getAscent();
	    // Set the font
	    //g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
	    // Dispose the Graphics
	    //g.dispose();
	}
	
	/**
	 * Draws an axis tic at the given location
	 * @param g
	 * @param loc
	 */
	private void drawTick (Graphics g, Point loc) {
		int sx = 0, sy = 0, ex = 0, ey = 0;
		if(this.or == Orientation.HORIZONTAL) {
			sx = loc.x;
			sy = loc.y-(int)(this.ticLenght/2);
			ex = loc.x;
			ey = loc.y+(int)(this.ticLenght/2);
		}
		if(this.or == Orientation.VERTICAL) {
			sx = loc.x-(int)(this.ticLenght/2);
			sy = loc.y;
			ex = loc.x+(int)(this.ticLenght/2);
			ey = loc.y;
		}
		g.drawLine(sx, sy, ex, ey);
	}


}
