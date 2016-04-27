package pro2.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class MathUtil {

	public MathUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns the maximum value of an Arraylist with doubles
	 * @param d
	 * @return
	 */
	public static double getMax(List<Double> d) {
		double res = Double.MIN_VALUE;
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
	 * Calculates the hypothenuse
	 * @param a
	 * @param b
	 * @return sqrt(a^2+b^2)
	 */
	public static double pythagoras(double a, double b) {
		return Math.sqrt(Math.pow(a, 2)+Math.pow(b, 2));
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
	
}
