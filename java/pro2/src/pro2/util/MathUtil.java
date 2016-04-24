package pro2.util;

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
	
}
