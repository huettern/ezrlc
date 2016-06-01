/**
 * PointD is a class that stores coordinates in double precision
 */

package ezrlc.util;

import java.awt.Point;

public class PointD {

	public double x;
	public double y;
	
	public PointD() {
	}
	public PointD(PointD p) {
		this.x = p.x;
		this.y = p.y;
	}
	public PointD(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX () {
		return this.x;
	}
	public double getY () {
		return this.y;
	}
	public void setX (double x) {
		this.x = x;
	}
	public void setY (double y) {
		this.y = y;
	}
	
	/**
	 * Rounds values to integer and returns a Point object
	 * @return Point
	 */
	public Point point () {
		return new Point ((int)this.x, (int)this.y);
	}
	

}
