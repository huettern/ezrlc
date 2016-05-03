package pro2.Plot.SmithChart;

import java.awt.Point;
import java.awt.geom.Point2D;

import pro2.util.Complex;
import pro2.util.MathUtil;
import pro2.util.PointD;

public class SmithChartMath {

	//================================================================================
    // Private Data
    //================================================================================
	private Point center;
	private int diameter;

	private double zo;	// reference to which the values are normalized to
	

	//================================================================================
    // Constructors
    //================================================================================
	/**
	 * Create new SmithChartMath object 
	 * @param center centerpoint of the plot
	 * @param diameter diameter of the smithchart
	 * @param norm normalization value
	 */
	public SmithChartMath(Point center, int diameter, double norm) {
		this.center = center;
		this.diameter = diameter;
		this.zo = norm;
	}
	

	//================================================================================
    // Getters and Setters
    //================================================================================
	public Point getCenter () {
		return new Point(this.center);
	}
	/**
	 * Set new Centerpoint
	 * @param center centerpoint of the smithchart
	 */
	public void setCenter (Point center) {
		this.center = new Point(center);
	}
	/**
	 * Set new Diameter
	 * @param dia Diameter of the smith chart
	 */
	public void setDiameter (int dia) {
		this.diameter = dia;
	}
	public int getDiameter () {
		return this.diameter;
	}
	//================================================================================
    // Public Static Functions
    //================================================================================
	/**
	 * Normalizes a given value to a given referenve
	 * @param val value to be normalized
	 * @param ref reference value
	 * @return normalized value
	 */
	public static double normalizeValue (double val, double ref) {
		return (val-ref)/(val+ref);
	}
	/**
	 * Normalizes a given complex value to a given referenve
	 * @param val complex value to be normalized
	 * @param ref reference value
	 * @return normalized value
	 */
	public static Complex normalizeValue (Complex val, double ref) {
		Complex c = new Complex(ref, 0.0);
		return Complex.div(Complex.sub(val, c), Complex.add(val, c));
	}	
	//================================================================================
    // Public Functions
    //================================================================================
	/**
	 * Returns the center point of the imaginary circle of a value 
	 * @param val imaginary value
	 * @return center point
	 */
	public PointD getImagGridCenterPoint(double val) {
		double mul;
		double normalized;
		
		normalized = SmithChartMath.normalizeValue(Math.abs(val), this.zo);
		
		if(val < 0) {
			mul = normalized - 1.0;
		}
		else {
			mul = 1.0 - normalized;
		}

		// calcualte y coordinate
		double y_coordinate = (this.center.y - ((this.diameter/2)*Math.tan(mul*Math.PI/4)));
		// store them
		return new PointD(center.x+(this.diameter/2), y_coordinate);
		
	}
	
	/**
	 * Returns the center point of a circle of the real axis
	 * @param val The (not normalized) value of the axis point
	 * @return Center point
	 */
	public PointD getRealGridCenterPoint(double val){
		double normalized;
		
		normalized = SmithChartMath.normalizeValue(Math.abs(val), this.zo);
		
		double y_coordinate = center.y;
		double x_coordinate = ((diameter*(normalized+1)/2) + (center.x-(this.diameter/2))); //stretch to display area
		double rad=((center.x+(this.diameter/2))-x_coordinate)/2;
		x_coordinate = x_coordinate + rad;
		
		return new PointD(x_coordinate, y_coordinate);
	}
	
	/**
	 * Calculates the point location of a complex value in the chart
	 * @param val complex value
	 * @return PointD object containing the coordinates of the point
	 */
	public PointD getPixelLocation (Complex val) {
		PointD p;
		
		// V2 using polar coordinates
		// faster and more accurate
		p = new PointD(0,0);
		double rad = this.diameter/2.0;
		// normalze value
		// r = (val-zo)/(val+zo)
		Complex r = SmithChartMath.normalizeValue(val, zo);
		// get real and imag part
		double re = r.re();
		double im = r.im();
		// scale re and im from -1 to +1 on both axis
		p.x = (this.center.x-rad)+(diameter*(re+1.0)/2.0);
		p.y = (this.center.y-rad)+(diameter*(-im+1.0)/2.0);
	
//		// real axis radius
//		double rr = center.x + (diameter/2) - this.getRealGridCenterPoint(val.re()).x;
//		// imag axis radius
//		double ri = center.y - this.getImagGridCenterPoint(val.im()).y;
//		// hypotenuse of rr and ri
//		double s = MathUtil.pythagoras(rr, ri);
//		// angle s - rr
//		double alpha = Math.atan(ri/rr);
//		// angle ri - s
//		//double epsilon = Math.acos( (Math.pow(s, 2) - Math.pow(rr, 2) - Math.pow(ri, 2)) / (-2.0*rr*ri) );
//		double epsilon = alpha;
//		// y length 
//		double ys = rr* Math.sin(Math.PI - (alpha + epsilon));
//		// x length
//		double xs = rr* Math.cos(Math.PI - (alpha + epsilon));
//		
//		p = new PointD(0,0);
//		p.x = ((center.x + (diameter/2)))-xs-rr;
//		p.y = center.y - ys;
//		
//		//System.out.println("rr="+rr+"ri="+ri+"s="+s+"alpha="+alpha+"epsilon="+epsilon+"ys="+ys+"xs"+xs);
		
		return p;
	}
	
	
	

		
	
}
