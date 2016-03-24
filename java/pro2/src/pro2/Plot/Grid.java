package pro2.Plot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import pro2.Plot.GridLine.GridLineStyle;

public class Grid {
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
	private RectangularPlot parent;
	private Orientation or;
	private Axis ax;
	private Color color;
	
	private int numLines = 0;
	
	private int size = 0;	// Size of the grid (height or width)
	
	List<GridLine> lines = new ArrayList<GridLine>();
	

	//================================================================================
    // Constructors
    //================================================================================
	public Grid(RectangularPlot parent, Orientation or, Color color, Axis ax, int size) {
		this.parent = parent;
		this.or = or;
		this.ax = ax;
		this.size = size;
		this.color = color;
	}
	//================================================================================
    // Public Functions
    //================================================================================
	/**
	 * Paints the Grid
	 * @param g
	 */
	public void paint (Graphics g) {
		this.evalSize();
		for (GridLine gridLine : this.lines) {
			gridLine.paint(g);
		}
	}

	//================================================================================
    // Private Functions
    //================================================================================
	private void evalSize() {
		List<Point> points = this.ax.getTicPoints();
		points.remove(0);	// Remove first entry
		this.numLines = points.size();
		int length = 0;
		
		lines.clear();

		if(this.or == Orientation.VERTICAL) {
			length = this.size;
			for (Point point : points) {
				lines.add(new GridLine(GridLineStyle.LINE, this.color, point, new Point(point.x, length)));
			}
		}
		if(this.or == Orientation.HORIZONTAL) {
			length = parent.getWidth() - this.size;
			for (Point point : points) {
				lines.add(new GridLine(GridLineStyle.LINE, this.color, point, new Point(length, point.y)));
			}
		}
	}


}
