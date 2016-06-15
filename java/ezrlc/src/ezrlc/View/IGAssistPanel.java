package ezrlc.View;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ezrlc.Controller.Controller;
import ezrlc.Model.Model;
import ezrlc.Model.Model.UpdateEvent;
import ezrlc.Model.RectPlotNewMeasurement;
import ezrlc.Plot.RectPlot.Axis.Scale;
import ezrlc.Plot.RectPlot.RectPlotSettings;
import ezrlc.Plot.RectPlot.RectangularPlot;
import ezrlc.RFData.RFData.MeasurementType;
import ezrlc.util.DataSource;
import ezrlc.util.UIUtil;

/**
 * Panel that holds the plots for the initial guess
 * 
 * @author noah
 *
 */
public class IGAssistPanel extends JPanel {

	// ================================================================================
	// Settings
	// ================================================================================
	private final String[] plotTitles = { "R - Series Resistance in Ohm", "L - Series Inductance in H",
			"R - Parallel Resistance in Ohm", "L - Parallel Inductance in H", "R - Series Resistance in Ohm",
			"C - Series Capacitance in F", "R - Parallel Resistance in Ohm", "C - Parallel Capacitance in F" };

	// ================================================================================
	// Private data
	// ================================================================================
	private static final long serialVersionUID = 1L;
	private Controller controller;

	private RectangularPlot[] plots = new RectangularPlot[8];

	private ImageIcon[] topoImgs = new ImageIcon[4];

	private final int[] plotXLoc = { 1, 2, 1, 2, 1, 2, 1, 2 };
	private final int[] plotYLoc = { 0, 0, 1, 1, 2, 2, 3, 3 };
	private final MeasurementType[] plotMeasType = { MeasurementType.Rs, MeasurementType.Ls, MeasurementType.Rp,
			MeasurementType.Lp, MeasurementType.Rs, MeasurementType.Cs, MeasurementType.Rp, MeasurementType.Cp };

	// ================================================================================
	// Constructors
	// ================================================================================
	/**
	 * Create new ig assist panel
	 * 
	 * @param c
	 *            controller object
	 */
	public IGAssistPanel(Controller c) {
		controller = c;
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		this.setBackground(Color.WHITE);
	}

	// ================================================================================
	// Private methods
	// ================================================================================
	/**
	 * Creates the 8 datasets
	 */
	public void createDatasets() {
		int id;
		RectPlotNewMeasurement nm = new RectPlotNewMeasurement();
		nm.src = DataSource.FILE;
		for (int i = 0; i < 8; i++) {
			// Delete old datasets
			plots[i].removeAllDatasets();
			nm.type = plotMeasType[i];
			nm.src_name = plotTitles[i];
			id = controller.createDataset(nm);
			plots[i].addDataSet(id, nm, 0);
		}
	}

	/**
	 * Sets the plot settings of the plots
	 * 
	 * @param o
	 *            model object
	 */
	private void setPlotSettings(Model o) {
		RectPlotSettings s = new RectPlotSettings();
		for (int i = 0; i < 8; i++) {
			plots[i].autoScale(Scale.LOG, Scale.LINEAR);
			s = plots[i].getSettings();
			s.yAxisSteps = 5;
			// if(s.yAxisMinimum < 0) s.yAxisMinimum = 0;
			// s.xAxisMinimum = 1;
			s.xScale = Scale.LOG;
			plots[i].setSettings(s);
			plots[i].repaint();
		}
	}

	// ================================================================================
	// Public methods
	// ================================================================================
	/**
	 * Builds the content
	 */
	public void build() {
		// Load images
		topoImgs[0] = UIUtil.loadResourceIcon("model_0.png");
		topoImgs[1] = UIUtil.loadResourceIcon("model_1.png");
		topoImgs[2] = UIUtil.loadResourceIcon("model_2.png");
		topoImgs[3] = UIUtil.loadResourceIcon("model_3.png");

		// place images
		JLabel label = new JLabel("", topoImgs[0], JLabel.CENTER);
		label.setOpaque(false);
		this.add(label, new GridBagConstraints(0, 0, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
		label = new JLabel("", topoImgs[1], JLabel.CENTER);
		label.setOpaque(false);
		this.add(label, new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
		label = new JLabel("", topoImgs[2], JLabel.CENTER);
		label.setOpaque(false);
		this.add(label, new GridBagConstraints(0, 2, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
		label = new JLabel("", topoImgs[3], JLabel.CENTER);
		label.setOpaque(false);
		this.add(label, new GridBagConstraints(0, 3, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));

		// create plots
		for (int i = 0; i < 8; i++) {
			plots[i] = new RectangularPlot();
			plots[i].setBorder(new LineBorder(new Color(0, 0, 0)));
			plots[i].setTitle(plotTitles[i]);
			this.add(plots[i], new GridBagConstraints(plotXLoc[i], plotYLoc[i], 1, 1, 1.0, 1.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 0, 3, 3), 0, 0));
		}
		this.updateUI();
	}

	/**
	 * Gets called by model if update event happens
	 * 
	 * @param o
	 *            model
	 * @param arg
	 *            arguments
	 */
	public void update(Observable o, Object arg) {
		if (arg == UpdateEvent.MANUAL || arg == UpdateEvent.FILE) {
			// // repaint all plots
			for (int i = 0; i < 8; i++) {
				plots[i].update(o, arg);
			}
			setPlotSettings((Model) o);
		}
	}

}
