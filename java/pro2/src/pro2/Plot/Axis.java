package pro2.Plot;

import java.awt.Graphics;

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
	
	private double min = 0;
	private double max = 0;
	private int step = 1;

	private int[] label_posx = new int[200];
	private int[] label_posy = new int[200];
	private double[] label_val = new double[200];
	private int label_count = 0;
	private int label_offset = 0;	// Position offset from the axis

	//================================================================================
    // Constructors
    //================================================================================
	public Axis(RectangularPlot parent, Orientation or, int x, int y) {
		this.or = or;
		this.parent = parent;
		this.origin_x = x;
		this.origin_y = y;
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
			g.drawString(this.formatAxisValue(label_val[i]), label_posx[i], label_posy[i]);
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
    // Private Functions
    //================================================================================
	private void evalSize() {
		if(or == Orientation.HORIZONTAL) {
			// Calculate origins
			this.start_x = this.origin_x;
			this.start_y = parent.getHeight()-this.origin_y;
			this.end_x = parent.getWidth();
			this.end_y = start_y;
			
			// Calculate String positions
			label_count = this.step;
			double spacing = ((this.end_x-this.start_x)/this.step);
			double data_spacing = ((this.max-this.min)/this.step);
			for(int i = 0; i <= label_count; i++)
			{
				label_posx[i] = this.origin_x + (int)(i*spacing);
				label_posy[i] = this.start_y + this.label_offset;
				label_val[i] = this.min + (i*data_spacing);
			}
			
		}
		else if(or == Orientation.VERTICAL) {
			// Calculate origins
			this.start_x = this.origin_x;
			this.start_y = parent.getHeight()-this.origin_y;
			this.end_x = this.start_x;
			this.end_y = 0;
			
			// Calculate String positions
			label_count = this.step;
			double spacing = ((this.end_y-this.start_y)/this.step);
			double data_spacing = ((this.max-this.min)/this.step);
			for(int i = 0; i <= label_count; i++)
			{
				label_posy[i] = this.origin_y - (int)(i*spacing);
				label_posx[i] = this.start_x + this.label_offset;
				label_val[i] = this.min + (i*data_spacing);
			}
		}
	}
	
	private String formatAxisValue(double d) {
		return String.format("%.1f", d);
	}


}
