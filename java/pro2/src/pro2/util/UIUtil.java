package pro2.util;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;

public class UIUtil {

	public UIUtil() {
		// TODO Auto-generated constructor stub
	}

	//================================================================================
    // Private Functions
    //================================================================================


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
	
	/**
	 * Reads the double value of a textfield and returns it
	 * @param tf textfield
	 * @return double value
	 */
	public static double getTextFieldDoubleValue (JFormattedTextField tf) {
		Double d = 0.0;
		int i = 0;
		if(tf.getValue().getClass() == Double.class ||
			tf.getValue().getClass() == double.class) {
			d = (double) tf.getValue();
		} else if (tf.getValue().getClass() == Integer.class || 
				tf.getValue().getClass() == int.class) {
			i = (Integer)tf.getValue();
			d = (double) i;
		} else if (tf.getValue().getClass() == long.class || 
				tf.getValue().getClass() == Long.class) {
			d = ((Long)tf.getValue()).doubleValue();
		}
		if(tf.getValue().getClass() == String.class) {
			try {
				String s =  (String)tf.getValue();
				d = Double.valueOf(s);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return d;
	}
	
	public static String num2Scientific(double d) {
		String s;
		NumberFormat formatter = new DecimalFormat();
		formatter = new DecimalFormat("0.###E0");
	    return formatter.format(d); // 1.2345E-1
	}
	/**
	 * Draws a circle arround the given midpoint and radius
	 * @param g Graphics object
	 * @param p Midpoint of the circle
	 * @param radius radius of the circle
	 */
	public static void drawCenterCircle (Graphics2D g, Point p, int radius) {
		g.drawOval(p.x-radius, p.y-radius, 2*radius, 2*radius);
	}
	
	
	/**
	 * Draws a centered string
	 * @param g Grpahics object
	 * @param p Location of string
	 * @param text Text to draw
	 */
	public static void drawCenterString (Graphics g, Point p, String text) {
		Graphics2D g2d = (Graphics2D)g;

	    // Get the FontMetrics
	    FontMetrics metrics = g2d.getFontMetrics();
	    int x = p.x - (metrics.stringWidth(text) / 2);
	    // Determine the Y coordinate for the text
	    int y = p.y + (int)(metrics.getHeight() / 3);
	    
	    g2d.drawString(text, x, y);
	}
}
