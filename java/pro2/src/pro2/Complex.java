/**
 * Complex numbers class
 * 
 * Source: http://introcs.cs.princeton.edu/java/97data/Complex.java.html
 * 
 * @author noah
 */

package pro2;

public class Complex {
    private double re;   // the real part
    private double im;   // the imaginary part

	public Complex(double real, double imag) {
		this.re = real;
		this.im = imag;
	}
	
	public Complex(Complex cpx) {
		this.re = cpx.re;
		this.im = cpx.im;
	}
	
	//********************
	// STATIC FUNCTIONS
	//********************
	/**
	 * Returns a new complex number c=a*b
	 * @param a
	 * @param b
	 * @return a*b
	 */
	public static Complex mul (Complex a, Complex b) {
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new Complex(real, imag);
	}
	
	/**
	 * Divides a and b: c=a/b
	 * @param a
	 * @param b
	 * @return a/b
	 */
	public static Complex div  (Complex a, Complex b) {
		Complex denominatorRec = new Complex(1.0 / (b.re*b.re + b.im*b.im), 0);
		Complex numerator = new Complex(a.re*b.re+a.im*b.im, a.im*b.re-a.re*b.im);
		return new Complex(Complex.mul(denominatorRec, numerator));
	}
	
	/**
	 * Adds two complex numbers to a new complex number
	 * c=a+b
	 * @param a
	 * @param b
	 * @return a+b
	 */
	public static Complex add (Complex a, Complex b) {
		return new Complex(a.re + b.re, a.im + b.im);
	}
	
	/**
	 * Substracts two complex numbers
	 * c=a-b
	 * @param a
	 * @param b
	 * @return a-b
	 */
	public static Complex sub(Complex a, Complex b) {
		return new Complex(a.re - b.re, a.im - b.im);
	}

	//********************
	// NON STATIC FUNCTIONS
	//********************

    /**
     * Return real part
     * @return
     */
    public double re() {
    	return re; 	
    }
    
    /**
     * Return imaginary part
     * @return
     */
    public double im() {
    	return im; 
    }
	
	/**
	 * Returns abs of the number
	 * @return
	 */
    public double abs() {
    	return Math.hypot(re, im); 
    }  // Math.sqrt(re*re + im*im)
    
    /**
     * Returns the angle between -pi and pi
     * @return
     */
    public double angle() {
    	return Math.atan2(im, re);
    }  

    /**
     * return a new Complex object whose value is (this + b)
     * @param b
     * @return
     */
    public Complex plus(Complex b) {
        Complex a = this;             // invoking object
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new Complex(real, imag);
    }

    /**
     * return a new Complex object whose value is (this - b)
     * @param b
     * @return
     */
    public Complex minus(Complex b) {
        Complex a = this;
        double real = a.re - b.re;
        double imag = a.im - b.im;
        return new Complex(real, imag);
    }

    /**
     * return a new Complex object whose value is (this * b)
     * @param b
     * @return
     */
    public Complex times(Complex b) {
        Complex a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new Complex(real, imag);
    }

    /**
     * 
     * scalar multiplication
     * return a new object whose value is (this * alpha)
     * @param alpha
     * @return
     */
    public Complex times(double alpha) {
        return new Complex(alpha * re, alpha * im);
    }

    /**
     * return a new Complex object whose value is the conjugate of this
     * @return
     */
    public Complex conjugate() {  
    	return new Complex(re, -im); 
    	}

    /**
     * return a new Complex object whose value is the reciprocal of this
     * @return
     */
    public Complex reciprocal() {
        double scale = re*re + im*im;
        return new Complex(re / scale, -im / scale);
    }

    /**
     * return a / b
     * @param b
     * @return
     */
    public Complex divides(Complex b) {
        Complex a = this;
        return a.times(b.reciprocal());
    }

    /**
     * return a new Complex object whose value is the complex exponential of this
     * @return
     */
    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    /**
     * return a new Complex object whose value is the complex sine of this
     * @return
     */
    public Complex sin() {
        return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    /**
     * return a new Complex object whose value is the complex cosine of this
     * @return
     */
    public Complex cos() {
        return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    /**
     * return a new Complex object whose value is the complex tangent of this
     * @return
     */
    public Complex tan() {
        return sin().divides(cos());
    }
    
    /**
     * Prints the complex number to syso
     * @param cpx
     */
    public void printRI () {
    	System.out.println(this.re +" + " +this.im +"i");
    }
    
    /**
     * Prints the complex number to a string
     * @param cpx
     */
    public String sprintRI () {
    	return String.format("%f + %f i", this.re, this.im);
    }
    

}
