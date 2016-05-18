package pro2.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MathUtil {
	

	//================================================================================
    // Private static data
    //================================================================================
	private final static int PREFIX_OFFSET = 5;
	private final static String[] PREFIX_ARRAY = {"f", "p", "n", "µ", "m", "", "k", "M", "G", "T"};


	public MathUtil() {
		// TODO Auto-generated constructor stub
	}

	//================================================================================
    // Public static methods
    //================================================================================
	/**
	 * Returns the maximum value of an Arraylist with doubles
	 * @param d
	 * @return
	 */
	public static double getMax(List<Double> d) {
		double res = -Double.MAX_VALUE;
		for (Double db : d) {
			if(db > res) res = db;
		}
		return res;
	}
	/**
	 * Returns the maximum value of an Arraylist with doubles
	 * @param d
	 * @return
	 */
	public static double getMax(double[] d) {
		double res = -Double.MAX_VALUE;
		for (Double db : d) {
			if(db > res) res = db;
		}
		return res;
	}

	/**
	 * Returns the maximum value of an Arraylist with doubles
	 * @param d
	 * @return
	 */
	public static double getMin(List<Double> d) {
		double res = Double.MAX_VALUE;
		for (Double db : d) {
			if(db < res) res = db;
		}
		return res;
	}

	/**
	 * Returns the maximum value of an Arraylist with doubles
	 * @param d
	 * @return
	 */
	public static double getMin(double[] d) {
		double res = Double.MAX_VALUE;
		for (Double db : d) {
			if(db < res) res = db;
		}
		return res;
	}
	
	/**
	 * Calculates the hypothenuse
	 * @param a
	 * @param b
	 * @return sqrt(a^2+b^2)
	 */
	public static double pythagoras(double a, double b) {
		return Math.sqrt(Math.pow(a, 2)+Math.pow(b, 2));
	}

	/**
	 * rounds a double to the next higher 'nice' value
	 * @param d double
	 * @return rounded double
	 */
	public static double roundNice (double d){
		return (double)MathUtil.roundNice(new Double(d));
	}
	/**
	 * rounds a double to the next higher 'nice' value
	 * @param d double
	 * @return rounded double
	 */
	public static Double roundNice (Double d) {
		int exp = (int)(Math.floor(Math.log10(Math.abs(d))));	//exponent
		Double ds = d / Math.pow(10, exp);			//scientific base
		int decdig = (int)Math.ceil(((ds*10.0)%10.0)); // first digit after ,
		// round decdig to next 2,5,10
		int onedig;
		if(d >= 0) {
			onedig = (int)Math.floor(ds);	// first digit
			//System.out.println("d="+d+"exp="+exp+"ds="+ds+"decdig="+decdig+"onedig="+onedig);
			if(decdig == 0) decdig = 0;
			else if(decdig <= 2) decdig = 2;
			else if(decdig <= 5) decdig = 5;
			else if(decdig <= 10) decdig = 10;
			//System.out.println("decdig="+decdig);
		} else {
			onedig = (int)Math.ceil(ds);	// first digit
			//System.out.println("d="+d+"exp="+exp+"ds="+ds+"decdig="+decdig+"onedig="+onedig);
			if(decdig == 0) decdig = 0;
			else if(decdig >= -2) decdig = -2;
			else if(decdig >= -5) decdig = -5;
			else if(decdig >= -10) decdig = -10;
			//System.out.println("decdig="+decdig);
		}
		
		// add them up
		Double newnum =  onedig + 0.1*decdig;
		newnum = newnum * Math.pow(10.0, exp); // re-apply exponent

//		System.out.println("d="+d+"exp="+exp+"ds="+ds+"decdig="+decdig+"onedig="+onedig);
//		System.out.println("decdig="+decdig);
		return newnum;
	}
	
	/**
	 * Dumps a List of complex numbers into a file
	 * It can be read with matlab using following code
	 * <code>
	 * rawData = dlmread('../java/pro2/tmp.txt');
	 * complexData = complex(rawData(:, 1), rawData(:, 2));
	 * </code>
	 * @param fname filename
	 * @param clist List of complex numbers
	 */
	public static void dumpListComplex (String fname, List<Complex> clist) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(fname, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		for (Complex complex : clist) {
			writer.write(String.format("%e %e\r\n",complex.re(), complex.im()));
//			writer.write(String.format("%f + i*%f\r\n",complex.re(), complex.im()));
		}
		writer.flush();
		writer.close();
	}
	/**
	 * Dumps a List of complex numbers into a file
	 * It can be read with matlab using following code
	 * <code>
	 * rawData = dlmread('../java/pro2/tmp.txt');
	 * complexData = complex(rawData(:, 1), rawData(:, 2));
	 * </code>
	 * @param fname filename
	 * @param clist List of complex numbers
	 */
	public static void dumpListComplex (String fname, Complex[] clist) {
		List<Complex> l = new ArrayList<Complex>(clist.length);
		for(int i = 0; i < clist.length; i++) {
			l.add(new Complex(clist[i].re(), clist[i].im()));
		}
		MathUtil.dumpListComplex(fname, l);
	}
	
	/**
	 * Dumps a List of double numbers into a file
	 * It can be read with matlab using following code
	 * <code>
	 * Data = dlmread('../java/pro2/tmp.txt');
	 * </code>
	 * @param fname filename
	 * @param clist List of Double numbers
	 */
	public static void dumpListDouble (String fname, List<Double> clist) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(fname, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}
		for (Double num : clist) {
			writer.write(String.format("%e\r\n",num));
//			writer.write(String.format("%f + i*%f\r\n",complex.re(), complex.im()));
		}
		writer.flush();
		writer.close();
	}
	/**
	 * Dumps a List of double numbers into a file
	 * It can be read with matlab using following code
	 * <code>
	 * Data = dlmread('../java/pro2/tmp.txt');
	 * </code>
	 * @param fname filename
	 * @param clist List of double numbers
	 */
	public static void dumpListDouble (String fname, double[] clist) {
		List<Double> l = new ArrayList<Double>(clist.length);
		for(int i = 0; i < clist.length; i++) {
			l.add(clist[i]);
		}
		MathUtil.dumpListDouble(fname, l);
	}
	
	/**
	 * Formats a double to a given number of digits past the decimal point
	 * and cuts the tailing zeroes
	 * @param d number to be formatted
	 * @param n number of digits after decimal point
	 * @return formatted string
	 */
	public static String formatDouble(double d, int n) {
		String out;
		String s = String.format("%."+n+"f", d);
		
		int cut_idx = s.length()-1;
		
		int ctr = s.length()-1; // points to last char
		while(s.charAt(ctr) != '.' && ctr!=0) {
			if(s.charAt(ctr) == '0') cut_idx = ctr-1;
			ctr--;
		}
		// cut everything after the ctr
		if(s.charAt(cut_idx)=='.') out = s.substring(0, cut_idx);
		else out = s.substring(0, cut_idx+1);
		return out;
	}
	
	/**
	 * Checks if array contains number
	 * @param a int array to be searched
	 * @param x number to check
	 * @return true if contains, false if not
	 */
	public static boolean contains(int[] a, int x) {
		for(int i = 0; i<a.length; i++){
			if(a[i] == x) return true;
		}
		return false;
	}
	
	/**
	 * creates a linear vector form start to end with given number of points
	 * @param start start value
	 * @param end end value
	 * @param num number of steps
	 * @return linear vector
	 */
	public static double[] linspace(double start, double end, int num) {
		double[] res = new double[num];
		res[0] = start;
		res[num-1] = end;
		
		double step = (end-start)/(num-1);
		for(int i = 1; i<(num-1); i++){
			res[i] = res[i-1] + step;
		}
		
		return res;
	}
	
	/**
	 * returns index of maximum value of an Array with doubles
	 * @param d
	 * @return
	 */
	public static int getMaxIndex(double[] d) {
		int res=0;
		double db=-Double.MAX_VALUE;
		for (int i=0;i<d.length;i++) {
			if(d[i] > db){
				db=d[i];
				res=i;
			}
		}
		return res;
	}
	
	/**
	 * returns index of minimum value of an Array with doubles
	 * @param d
	 * @return
	 */
	public static int getMinIndex(double[] d) {
		int res=0;
		double db=Double.MAX_VALUE;
		for (int i=0;i<d.length;i++) {
			if(d[i] < db){
				db=d[i];
				res=i;
			}
		}
		return res;
	}
	
	
	/**
	 * returns differences of all elements in a double array
	 * @param d
	 * @return
	 */
	public static double[] diff(double[] d){
		double[] diff = new double[d.length-1];
		for (int i=0;i<(d.length-1);i++){
			diff[i]=d[i+1]-d[i];
		}
		return diff;
	}

	/**
	 * Converts  a double number to the engineering notation
	 * Source: http://www.labbookpages.co.uk/software/java/engNotation.html
	 * @param val value
	 * @param dp number of decimal points
	 * @return formatted string
	 */
	public static String num2eng(double val, int dp)
	{
	   // If the value is zero, then simply return 0 with the correct number of dp
	   if (val == 0) return String.format("%." + dp + "f", 0.0);

	   // If the value is negative, make it positive so the log10 works
	   double posVal = (val<0) ? -val : val;
	   double log10 = Math.log10(posVal);

	   // Determine how many orders of 3 magnitudes the value is
	   int count = (int) Math.floor(log10/3);

	   // Calculate the index of the prefix symbol
	   int index = count + PREFIX_OFFSET;

	   // Scale the value into the range 1<=val<1000
	   val /= Math.pow(10, count * 3);

	   if (index >= 0 && index < PREFIX_ARRAY.length)
	   {
	      // If a prefix exists use it to create the correct string
	      return String.format("%." + dp + "f%s", val, PREFIX_ARRAY[index]);
	   }
	   else
	   {
	      // If no prefix exists just make a string of the form 000e000
	      return String.format("%." + dp + "fe%d", val, count * 3);
	   }
	}
	
	
	/**
	 * returns Magnitudes of an Array of Complex
	 * @param d
	 * @return dabs
	 */
	public static double[] abs(Complex[] d){
		double[] dabs = new double[d.length];
		for (int i=0;i<d.length;i++){
			dabs[i] = d[i].abs();
		}
		return dabs;
	}
}
