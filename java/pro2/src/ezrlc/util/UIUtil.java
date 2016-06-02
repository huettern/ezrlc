package ezrlc.util;

import java.awt.Color;
import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;

/**
 * GUI utility funtcions
 * 
 * @author noah
 *
 */
public class UIUtil {

	// ================================================================================
	// Private Data
	// ================================================================================
	private static Container p = new Container();

	// Data line settings
	private static List<Color> plotSetColors = Collections
			.unmodifiableList(Arrays.asList(new Color(0, 113, 188), new Color(216, 82, 24), new Color(236, 176, 31),
					new Color(125, 46, 141), new Color(118, 171, 47), new Color(76, 189, 237), new Color(161, 19, 46)));
	private static int PlotSetColorsCtr = 0; // Holds the index of the next
												// color to be used

	// ================================================================================
	// Contructor
	// ================================================================================
	public UIUtil() {
	}

	// ================================================================================
	// Private Functions
	// ================================================================================

	// ================================================================================
	// Public static Functions
	// ================================================================================
	/**
	 * Evaluates the numeric double value of a spinner object
	 * 
	 * @param spin
	 *            JSpinner
	 * @return double value of the spinner value
	 */
	public static Double getSpinnerDoubleValue(JSpinner spin) {
		Double d = 0.0;
		int i = 0;
		if (spin.getValue().getClass() == Double.class || spin.getValue().getClass() == double.class) {
			d = (double) spin.getValue();
		}
		if (spin.getValue().getClass() == Integer.class || spin.getValue().getClass() == int.class) {
			i = (Integer) spin.getValue();
			d = (double) i;
		}
		return d;
	}

	/**
	 * Evaluates the numeric int value of a spinner object
	 * 
	 * @param spin
	 *            JSpinner
	 * @return int value of the spinner value
	 */
	public static int getSpinnerIntValue(JSpinner spin) {
		return getSpinnerDoubleValue(spin).intValue();
	}

	/**
	 * Reads the double value of a textfield and returns it
	 * 
	 * @param tf
	 *            textfield
	 * @return double value
	 */
	public static double getTextFieldDoubleValue(JFormattedTextField tf) {
		Double d = 0.0;
		int i = 0;
		if (tf.getValue().getClass() == Double.class || tf.getValue().getClass() == double.class) {
			d = (double) tf.getValue();
		} else if (tf.getValue().getClass() == Integer.class || tf.getValue().getClass() == int.class) {
			i = (Integer) tf.getValue();
			d = (double) i;
		} else if (tf.getValue().getClass() == long.class || tf.getValue().getClass() == Long.class) {
			d = ((Long) tf.getValue()).doubleValue();
		}
		if (tf.getValue().getClass() == String.class) {
			try {
				String s = (String) tf.getValue();
				d = Double.valueOf(s);
			} catch (Exception e) {
			}
		}
		return d;
	}

	/**
	 * normal number to scientific notation
	 * 
	 * @param d
	 *            double number
	 * @return string
	 */
	public static String num2Scientific(double d) {
		NumberFormat formatter = new DecimalFormat();
		formatter = new DecimalFormat("0.###E0");
		return formatter.format(d); // 1.2345E-1
	}

	/**
	 * Draws a circle arround the given midpoint and radius
	 * 
	 * @param g
	 *            Graphics object
	 * @param p
	 *            Midpoint of the circle
	 * @param radius
	 *            radius of the circle
	 */
	public static void drawCenterCircle(Graphics2D g, Point p, int radius) {
		g.drawOval(p.x - radius, p.y - radius, 2 * radius, 2 * radius);
	}

	/**
	 * Draws a centered string
	 * 
	 * @param g
	 *            Grpahics object
	 * @param p
	 *            Location of string
	 * @param text
	 *            Text to draw
	 */
	public static void drawCenterString(Graphics g, Point p, String text) {
		Graphics2D g2d = (Graphics2D) g;

		// Get the FontMetrics
		FontMetrics metrics = g2d.getFontMetrics();
		int x = p.x - (metrics.stringWidth(text) / 2);
		// Determine the Y coordinate for the text
		int y = p.y + (int) (metrics.getHeight() / 3);

		g2d.drawString(text, x, y);
	}

	// ================================================================================
	// Public static Functions: Images
	// ================================================================================
	/**
	 * Returns an image given from the path in the project img folder
	 * 
	 * @param strBild
	 *            string to image
	 * @return Image
	 */
	public static Image loadResourceImage(String strBild) {
		MediaTracker tracker = new MediaTracker(p);
		Image img = null;
		try {
			InputStream input = UIUtil.class.getResourceAsStream("/img/" + strBild);
			img = ImageIO.read(input);
			tracker.addImage(img, 0);
			tracker.waitForID(0);
		} catch (Exception e) {
			System.out.println("Can not load image: " + strBild);
		}
		return img;
	}

	/**
	 * Returns an image given from the path in the project img folder,
	 * transformed to width and height
	 * 
	 * @param strBild
	 *            string to image
	 * @param width
	 *            image width
	 * @param height
	 *            image height
	 * @return Image
	 */
	public static Image loadResourceImage(String strBild, int width, int height) {
		MediaTracker tracker = new MediaTracker(p);
		Image img = null;
		try {
			InputStream input = UIUtil.class.getResourceAsStream("/img/" + strBild);
			img = ImageIO.read(input);
			img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			tracker.addImage(img, 0);
			tracker.waitForID(0);
		} catch (InterruptedException ex) {
			System.out.println("Can not load image: " + strBild);
		} catch (IOException ex) {

		}
		return img;
	}

	/**
	 * Returns an icon given from the path in the project img folder,
	 * transformed to width and height
	 * 
	 * @param strBild
	 *            string to image
	 * @param width
	 *            image width
	 * @param height
	 *            image height
	 * @return Icon
	 */
	public static ImageIcon loadResourceIcon(String strBild, int width, int height) {
		InputStream input = UIUtil.class.getResourceAsStream("/img/" + strBild);
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(ImageIO.read(input).getScaledInstance(width, height, Image.SCALE_SMOOTH));
		} catch (IOException e) {
			System.out.println("Can not load image: " + strBild);
		}
		return icon;
	}

	/**
	 * Returns an icon given from the path in the project img folder
	 * 
	 * @param strBild string to file
	 * @return Icon
	 */
	public static ImageIcon loadResourceIcon(String strBild) {
		InputStream input = UIUtil.class.getResourceAsStream("/img/" + strBild);
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(ImageIO.read(input));
		} catch (IOException e) {
			System.out.println("Can not load image: " + strBild);
		}
		return icon;
	}

	/**
	 * Gets the next color in the color palette and increments counter
	 * 
	 * @return color
	 */
	public static Color getNextColor() {
		Color c = plotSetColors.get(PlotSetColorsCtr);
		PlotSetColorsCtr++;
		// If the counter reached the end of the pallete
		if (PlotSetColorsCtr >= plotSetColors.size()) {
			// start from the beginning
			PlotSetColorsCtr = 0;
		}
		return c;
	}

	/**
	 * Gets the color in the color palette
	 * 
	 * @param i
	 *            color index
	 * @return Color
	 */
	public static Color getColor(int i) {
		return plotSetColors.get(i % plotSetColors.size());
	}
}
