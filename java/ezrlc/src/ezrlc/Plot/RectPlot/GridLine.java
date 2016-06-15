package ezrlc.Plot.RectPlot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Single grid line
 * 
 * @author noah
 *
 */
public class GridLine {

	// ================================================================================
	// Public Data
	// ================================================================================
	public enum GridLineStyle {
		LINE,
	};

	// ================================================================================
	// Private Data
	// ================================================================================
	private Color color = new Color(0, 0, 0);
	private Point start = new Point(0, 0);
	private Point end = new Point(0, 0);

	// ================================================================================
	// Constructors
	// ================================================================================
	/**
	 * Create a gridline
	 * 
	 * @param color:
	 *            Color of the line
	 * @param start:
	 *            Start point
	 * @param end:
	 *            End point
	 */
	public GridLine(Color color, Point start, Point end) {
		this.color = color;
		this.start = start;
		this.end = end;
	}

	// ================================================================================
	// Public Functions
	// ================================================================================
	/**
	 * Paints the gridline
	 * 
	 * @param g
	 *            graphics object
	 */
	public void paint(Graphics g) {
		Color col = g.getColor();
		g.setColor(this.color);
		g.drawLine(start.x, start.y, end.x, end.y);
		g.setColor(col);
	}

}
