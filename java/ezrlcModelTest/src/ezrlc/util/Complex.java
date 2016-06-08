package ezrlc.util;

/**
 * Complex numbers class
 * 
 * Inspired by: http://introcs.cs.princeton.edu/java/97data/Complex.java.html
 * 
 * @author noah
 */
public class Complex {

	// ================================================================================
	// private data
	// ================================================================================
	private double re; // the real part
	private double im; // the imaginary part

	// ================================================================================
	// constructors
	// ================================================================================
	/**
	 * new complex number
	 * 
	 * @param real
	 *            real part
	 * @param imag
	 *            imaginary part
	 */
	public Complex(double real, double imag) {
		this.re = real;
		this.im = imag;
	}

	/**
	 * new complex number from existing number
	 * 
	 * @param cpx
	 *            existing complex number
	 */
	public Complex(Complex cpx) {
		this.re = cpx.re;
		this.im = cpx.im;
	}

	// ================================================================================
	// STATIC FUNCTIONS
	// ================================================================================
	/**
	 * Returns a new complex number c=a*b
	 * 
	 * @param a
	 *            complex number
	 * @param b
	 *            complex number
	 * @return a*b
	 */
	public static Complex mul(Complex a, Complex b) {
		double real = a.re * b.re - a.im * b.im;
		double imag = a.re * b.im + a.im * b.re;
		return new Complex(real, imag);
	}

	/**
	 * Divides a and b: c=a/b
	 * 
	 * @param a
	 *            complex number
	 * @param b
	 *            complex number
	 * @return a/b
	 */
	public static Complex div(Complex a, Complex b) {
		Complex denominatorRec = new Complex(1.0 / (b.re * b.re + b.im * b.im), 0);
		Complex numerator = new Complex(a.re * b.re + a.im * b.im, a.im * b.re - a.re * b.im);
		return new Complex(Complex.mul(denominatorRec, numerator));
	}

	/**
	 * Adds two complex numbers to a new complex number c=a+b
	 * 
	 * @param a
	 *            complex number
	 * @param b
	 *            complex number
	 * @return a+b
	 */
	public static Complex add(Complex a, Complex b) {
		return new Complex(a.re + b.re, a.im + b.im);
	}

	/**
	 * Substracts two complex numbers c=a-b
	 * 
	 * @param a
	 *            complex number
	 * @param b
	 *            complex number
	 * @return a-b
	 */
	public static Complex sub(Complex a, Complex b) {
		return new Complex(a.re - b.re, a.im - b.im);
	}

	/**
	 * Calculate the angle of a complex number
	 * 
	 * @param c
	 *            complex number
	 * @return angle in rad
	 */
	public static double angle(Complex c) {
		return Math.atan2(c.im, c.re);
	}

	// ================================================================================
	// Public functions
	// ================================================================================

	/**
	 * Return real part
	 * 
	 * @return real part
	 */
	public double re() {
		return re;
	}

	/**
	 * Return imaginary part
	 * 
	 * @return imaginary part
	 */
	public double im() {
		return im;
	}

	/**
	 * Returns abs of the number
	 * 
	 * @return abs of the number
	 */
	public double abs() {
		return Math.hypot(re, im);
	} // Math.sqrt(re*re + im*im)

	/**
	 * Returns the angle between -pi and pi
	 * 
	 * @return angle between -pi and pi
	 */
	public double angle() {
		return Math.atan2(im, re);
	}

	/**
	 * return a new Complex object whose value is (this + b)
	 * 
	 * @param b
	 *            add
	 * @return result
	 */
	public Complex plus(Complex b) {
		Complex a = this; // invoking object
		double real = a.re + b.re;
		double imag = a.im + b.im;
		return new Complex(real, imag);
	}

	/**
	 * return a new Complex object whose value is (this - b)
	 * 
	 * @param b
	 *            subtracotr
	 * @return number
	 */
	public Complex minus(Complex b) {
		Complex a = this;
		double real = a.re - b.re;
		double imag = a.im - b.im;
		return new Complex(real, imag);
	}

	/**
	 * return a new Complex object whose value is (this * b)
	 * 
	 * @param b
	 *            factor
	 * @return number
	 */
	public Complex times(Complex b) {
		Complex a = this;
		double real = a.re * b.re - a.im * b.im;
		double imag = a.re * b.im + a.im * b.re;
		return new Complex(real, imag);
	}

	/**
	 * 
	 * scalar multiplication return a new object whose value is (this * alpha)
	 * 
	 * @param alpha
	 *            factor
	 * @return number
	 */
	public Complex times(double alpha) {
		return new Complex(alpha * re, alpha * im);
	}

	/**
	 * return a new Complex object whose value is the conjugate of this
	 * 
	 * @return conjugate
	 */
	public Complex conjugate() {
		return new Complex(re, -im);
	}

	/**
	 * return a new Complex object whose value is the reciprocal of this
	 * 
	 * @return reciprocal
	 */
	public Complex reciprocal() {
		double scale = re * re + im * im;
		return new Complex(re / scale, -im / scale);
	}

	/**
	 * return a / b
	 * 
	 * @param b divisor
	 * @return result
	 */
	public Complex divides(Complex b) {
		Complex a = this;
		return a.times(b.reciprocal());
	}

	/**
	 * Raises the complex number to the given exponent
	 * 
	 * @param x
	 *            exponent
	 * @return c^x
	 */
	public Complex pow(double x) {
		return new Complex(Math.pow(this.abs(), x) * Math.cos(x * angle(this)),
				Math.pow(this.abs(), x) * Math.sin(x * angle(this)));
	}

	/**
	 * Prints the complex number to syso
	 */
	public void printRI() {
		if (this.im < 0)
			System.out.printf("%f %fi", this.re, this.im);
		else
			System.out.printf("%f +%fi", this.re, this.im);
	}

	/**
	 * Prints the complex number to a string
	 * 
	 * @return string
	 */
	public String sprintRI() {
		if (this.im < 0)
			return String.format("%f %fi", this.re, this.im);
		else
			return String.format("%f +%fi", this.re, this.im);
	}

}
