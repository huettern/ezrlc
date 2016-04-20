package pro2.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JSpinner;

public class UIUtil {

	public UIUtil() {
		// TODO Auto-generated constructor stub
	}


	//================================================================================
    // Public static Functions
    //================================================================================
	/**
	 * Evaluates the numeric double value of a spinner object
	 * @param spin JSpinner
	 * @return double value of the spinner value
	 */
	public static Double getSpinnerDoubleValue (JSpinner spin) {
		Double d = 0.0;
		int i = 0;
		if(spin.getValue().getClass() == Double.class || 
				spin.getValue().getClass() == double.class) {
			d = (double) spin.getValue();
		}
		if(spin.getValue().getClass() == Integer.class ||
				spin.getValue().getClass() == int.class) {
			i = (Integer)spin.getValue();
			d = (double) i;
		}
		return d;
	}
	/**
	 * Evaluates the numeric int value of a spinner object
	 * @param spin JSpinner
	 * @return int value of the spinner value
	 */
	public static int getSpinnerIntValue (JSpinner spin) {
		return getSpinnerDoubleValue(spin).intValue();
	}
	
	public static String num2Scientific(double d) {
		String s;
		NumberFormat formatter = new DecimalFormat();
		formatter = new DecimalFormat("0.###E0");
	    return formatter.format(d); // 1.2345E-1
	}
}
