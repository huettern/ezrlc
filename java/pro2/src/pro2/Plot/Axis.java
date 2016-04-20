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
import pro2.Plot.RectPlot.RectangularPlot;

public class Axis {

	//================================================================================
    // Public Data
    //================================================================================
	public enum Orientation {
		HORIZONTAL,
		VERTICAL
	};

	public enum Scale {
		LINEAR, LOG
	};

	//================================================================================
    // Private Data
    //================================================================================
	private Orientation or;
	private Scale scale;
	private RectangularPlot parent;
	
	private int start_x, start_y;
	private int end_x, end_y;
	
	private int origin_x, origin_y;
	private int size;
	
	private double min = 0;
	private double max = 0;
	private int step = 1;
	
	// Log scale params
	private double logUpperBound = 1;
	private double logLowerBound = 0;
	

	private static int MAX_NUM_POINTS = 400;
	private int[] label_posx = new int[MAX_NUM_POINTS];
	private int[] label_posy = new int[MAX_NUM_POINTS];
	private double[] label_val = new double[MAX_NUM_POINTS];
	private int label_count = 0;
	private int label_offset = 0;	// Position offset from the axis

	private Point[] tic_pos = new Point[MAX_NUM_POINTS];		// major tics
	private List<Point> sub_tic_pos = new ArrayList<Point>();
	private int ticLenght = 10;

	//================================================================================
    // Constructors
    //================================================================================
	/**
	 * Add a new Axis
	 * @param parent: The parent plot
	 * @param or: Orientation either HORIZONTAL or VERTICAL
	 * @param origin: Origin point of the axis
	 * @param size: Length of the axis relative to the parent plot (0 is maximum)
	 * @param min; Minimum Value of the axis
	 * @param max: Maximum Value of the axis
	 * @param step: Number of steps
	 * @param labelOffset: Location of the Value labels, relative to the axis
	 */
	public Axis(RectangularPlot parent, Scale scale, Orientation or, Point origin, int size, double min, double max, int step, int labelOffset) {
		this.or = or;
		this.scale = scale;
		this.parent = parent;
		this.origin_x = origin.x;
		this.origin_y = origin.y;
		this.size = size;
//		this.min = min;
//		this.max = max;
		this.step = step;
		this.label_offset = labelOffset;
		this.setMinimum(min);
		this.setMaximum(max);

		for(int i=0; i<MAX_NUM_POINTS; i++) {
			tic_pos[i] = new Point (0,0);
			//sub_tic_pos[i] = new Point (0,0);
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
		
		// if log scale, draw sub tics
//		for(int i = 0; i <= this.sub_tic_cnt; i++) {
//			this.drawSubTick(g, sub_tic_pos[i]);
//		}
		for (Point p : this.sub_tic_pos) {
			this.drawSubTick(g, p);
		}
	}

	public void setMinimum(double min) {
		if(this.scale == Scale.LOG && min == 0) min = Double.MIN_VALUE;
		this.min=min;
		logLowerBound = Math.log10(min);
	}
	public void setMaximum(double max) {
		this.max=max;	
		logUpperBound = Math.log10(max);
	}
	public void setStep(int step) {
		this.step=step;	
	}
	public void setLabelOffset(int off) {
		this.label_offset=off;
	}
	
	/**
	 * Returns the pixel location of the given value
	 * @param d
	 * @return
	 */
	public int getPixelValue(double d) {
		this.evalSize();
		return this.getPixelValueWithoutEval(d);
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
		for (Point point : sub_tic_pos) {
			points.add(point);
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

	/**
	 * Returns the pixel location of the given value
	 * @param d
	 * @return
	 */
	private int getPixelValueWithoutEval(double d) {
		if(this.or==Orientation.HORIZONTAL) {
			if(this.scale == Scale.LINEAR) {
				double dy = this.end_x - this.start_x;
				double dx = this.max - this.min;
				return (int)((dy/dx)*(d-this.min)+this.start_x);
			}
			else if(this.scale == Scale.LOG) {
				double delta = this.logUpperBound - this.logLowerBound;
				double deltaV = Math.log10(d) - this.logLowerBound;
				double width = this.end_x - this.start_x;
				return (int)(((deltaV/delta) * width ) + this.start_x);
			}
		}
		else if (this.or==Orientation.VERTICAL) {
			double dy = this.end_y - this.start_y;
			double dx = this.max - this.min;
			return (int)((dy/dx)*(d-this.min)+this.start_y);
		}
		return 0;
	}

	
	/**
	 * Caluclates all the necessary pixel values of the Axis to paint it
	 */
	private void evalSize() {
		if(or == Orientation.HORIZONTAL) {
			// Calculate origins
			this.start_x = this.origin_x;
			this.start_y = parent.getHeight()-this.origin_y;
			this.end_x = parent.getWidth() - this.size;
			this.end_y = start_y;
			
			// Calculate String and tic positions
			if(this.scale == Scale.LINEAR) {
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
			else if(this.scale == Scale.LOG) {
				// calculate min and max exponent
				double min_exp = Math.log10(this.min);
				double max_exp = Math.log10(this.max);
				label_count = (int) (Math.ceil(max_exp) - Math.ceil(min_exp)) + 1;
				
				int startExp = (int) Math.ceil(min_exp);
				// Calculate string and major tic positions
				for(int i = 0; i <= label_count; i++) {
					label_val[i] = Math.pow(10, startExp);
					label_posx[i] = this.getPixelValueWithoutEval(label_val[i]);
					label_posy[i] = this.start_y + this.label_offset;
					tic_pos[i].x = label_posx[i];
					tic_pos[i].y = this.start_y;
					startExp++;
				}
				// calculate minor tic positions
				sub_tic_pos = new ArrayList<Point>();
				Point p = new Point();
				for (double i = this.logLowerBound; i <= this.logUpperBound; i++) {
					for (double j = 0.1; j < 1; j += 0.1) {
						double value = j * Math.pow(10, i);
						if( (value >= this.min) && (value <= this.max) ) {
							p.x = this.getPixelValueWithoutEval(value);
							p.y = this.start_y;
							sub_tic_pos.add(new Point(p.x,p.y));
						}
					}
				}
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
	
	/**
	 * Formats the axis Label
	 * @param d
	 * @return
	 */
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
	
	/**
	 * Draws an axis sub tic at the given location
	 * @param g
	 * @param loc
	 */
	private void drawSubTick (Graphics g, Point loc) {
		int sx = 0, sy = 0, ex = 0, ey = 0;
		if(this.or == Orientation.HORIZONTAL) {
			sx = loc.x;
			sy = loc.y-(int)(this.ticLenght/2);
			ex = loc.x;
			ey = loc.y;
		}
		if(this.or == Orientation.VERTICAL) {
			sx = loc.x;
			sy = loc.y;
			ex = loc.x+(int)(this.ticLenght/2);
			ey = loc.y;
		}
		g.drawLine(sx, sy, ex, ey);
	}


}
