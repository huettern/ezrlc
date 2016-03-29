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
	
}
