package ezrlc.Plot.RectPlot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import ezrlc.Controller.Controller;
import ezrlc.Plot.Figure;
import ezrlc.Plot.RectPlot.Axis.Scale;
import ezrlc.View.JEngineerField;

/**
 * Settings window for the rectangular plot
 * 
 * @author noah
 *
 */
public class RectPlotSettingsWindow implements ActionListener {

	private Figure figure;

	private JDialog dialog;

	private JButton btnCancel, btnOk;
	private JEngineerField txtYmin, txtYmax, txtYstep;
	private JEngineerField txtXmin, txtXmax, txtXstep;
	private JRadioButton rdbtnLinX, rdbtnLogX, rdbtnLinY, rdbtnLogY;
	private ButtonGroup btngrpLinLogX, btngrpLinLogY;

	private RectPlotSettings settings = new RectPlotSettings();

	// ================================================================================
	// Constructors
	// ================================================================================

	/**
	 * Create new rectangular plot settings window
	 * 
	 * @param controller
	 *            controller object
	 * @param fig
	 *            figure object
	 */
	public RectPlotSettingsWindow(Controller controller, Figure fig) {
		this.figure = fig;

		builtRectPlotSettingsWindow(controller, fig);
	}

	private void builtRectPlotSettingsWindow(Controller controller, Figure fig) {
		dialog = new JDialog(controller.getMainView());
		dialog.setResizable(false);
		dialog.setTitle("Graph Settings");
		dialog.setModal(true);
		dialog.setLocation(250, 150);
		dialog.setSize(300, 422);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 322, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		dialog.getContentPane().setLayout(gridBagLayout);

		JTabbedPane tabbed = new JTabbedPane(JTabbedPane.TOP);
		tabbed.setToolTipText("");
		GridBagConstraints gbc_tabbed = new GridBagConstraints();
		gbc_tabbed.fill = GridBagConstraints.BOTH;
		gbc_tabbed.gridx = 0;
		gbc_tabbed.gridy = 0;
		dialog.getContentPane().add(tabbed, gbc_tabbed);

		// ****Tab Axis****
		JPanel tabAxis = new JPanel();
		tabbed.addTab("Axis", null, tabAxis, null);
		GridBagLayout gbl_tabAxis = new GridBagLayout();
		gbl_tabAxis.columnWidths = new int[] { 0, 0 };
		gbl_tabAxis.rowHeights = new int[] { 0, 0, 30, 0 };
		gbl_tabAxis.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_tabAxis.rowWeights = new double[] { 1.0, 1.0, 0.0, Double.MIN_VALUE };
		tabAxis.setLayout(gbl_tabAxis);

		// X-Panel
		JPanel pnlX = new JPanel();
		pnlX.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "X-Axis", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pnlX = new GridBagConstraints();
		gbc_pnlX.insets = new Insets(5, 5, 5, 5);
		gbc_pnlX.fill = GridBagConstraints.BOTH;
		gbc_pnlX.gridx = 0;
		gbc_pnlX.gridy = 0;
		tabAxis.add(pnlX, gbc_pnlX);
		GridBagLayout gbl_pnlX = new GridBagLayout();
		gbl_pnlX.columnWidths = new int[] { 0, 0, 0 };
		gbl_pnlX.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_pnlX.columnWeights = new double[] { 0.0, 1.0, 1.0 };
		gbl_pnlX.rowWeights = new double[] { 1.0, 1.0, 1.0, 0.0 };
		pnlX.setLayout(gbl_pnlX);

		JLabel lblMinimumX = new JLabel("Minimum:");
		GridBagConstraints gbc_lblMinimumX = new GridBagConstraints();
		gbc_lblMinimumX.anchor = GridBagConstraints.WEST;
		gbc_lblMinimumX.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinimumX.gridx = 0;
		gbc_lblMinimumX.gridy = 0;
		pnlX.add(lblMinimumX, gbc_lblMinimumX);

		txtXmin = new JEngineerField(3, 20, "E24");
		txtXmin.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_spinXmin = new GridBagConstraints();
		gbc_spinXmin.gridwidth = 2;
		gbc_spinXmin.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinXmin.insets = new Insets(0, 0, 5, 0);
		gbc_spinXmin.gridx = 1;
		gbc_spinXmin.gridy = 0;
		pnlX.add(txtXmin, gbc_spinXmin);

		JLabel lblMaximumX = new JLabel("Maximum:");
		GridBagConstraints gbc_lblMaximumX = new GridBagConstraints();
		gbc_lblMaximumX.anchor = GridBagConstraints.WEST;
		gbc_lblMaximumX.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaximumX.gridx = 0;
		gbc_lblMaximumX.gridy = 1;
		pnlX.add(lblMaximumX, gbc_lblMaximumX);

		txtXmax = new JEngineerField(3, 20, "E24");
		txtXmax.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_spinXmax = new GridBagConstraints();
		gbc_spinXmax.gridwidth = 2;
		gbc_spinXmax.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinXmax.insets = new Insets(0, 0, 5, 0);
		gbc_spinXmax.gridx = 1;
		gbc_spinXmax.gridy = 1;
		pnlX.add(txtXmax, gbc_spinXmax);

		txtXmin.setMinValue(0.0);
		txtXmax.setMinValue(0.0);

		JLabel lblStepsX = new JLabel("Steps:");
		GridBagConstraints gbc_lblStepsX = new GridBagConstraints();
		gbc_lblStepsX.anchor = GridBagConstraints.WEST;
		gbc_lblStepsX.insets = new Insets(0, 0, 5, 5);
		gbc_lblStepsX.gridx = 0;
		gbc_lblStepsX.gridy = 2;
		pnlX.add(lblStepsX, gbc_lblStepsX);

		txtXstep = new JEngineerField(new DecimalFormat("###"), 0);
		txtXstep.setMinValue(1);
		txtXstep.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_spinXstep = new GridBagConstraints();
		gbc_spinXstep.gridwidth = 2;
		gbc_spinXstep.insets = new Insets(0, 0, 5, 0);
		gbc_spinXstep.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinXstep.gridx = 1;
		gbc_spinXstep.gridy = 2;
		pnlX.add(txtXstep, gbc_spinXstep);

		rdbtnLinX = new JRadioButton("Lin");
		rdbtnLinX.setSelected(true);
		GridBagConstraints gbc_rdbtnLinX = new GridBagConstraints();
		gbc_rdbtnLinX.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnLinX.gridx = 1;
		gbc_rdbtnLinX.gridy = 3;
		pnlX.add(rdbtnLinX, gbc_rdbtnLinX);
		rdbtnLinX.addActionListener(this);

		rdbtnLogX = new JRadioButton("Log");
		GridBagConstraints gbc_rdbtnLogX = new GridBagConstraints();
		gbc_rdbtnLogX.gridx = 2;
		gbc_rdbtnLogX.gridy = 3;
		pnlX.add(rdbtnLogX, gbc_rdbtnLogX);
		rdbtnLogX.addActionListener(this);

		btngrpLinLogX = new ButtonGroup();
		btngrpLinLogX.add(rdbtnLogX);
		btngrpLinLogX.add(rdbtnLinX);

		// Y-Panel
		JPanel pnlY = new JPanel();
		pnlY.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Y-Axis", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pnlY = new GridBagConstraints();
		gbc_pnlY.insets = new Insets(0, 5, 5, 5);
		gbc_pnlY.fill = GridBagConstraints.BOTH;
		gbc_pnlY.gridx = 0;
		gbc_pnlY.gridy = 1;
		tabAxis.add(pnlY, gbc_pnlY);
		GridBagLayout gbl_pnlY = new GridBagLayout();
		gbl_pnlY.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_pnlY.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_pnlY.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_pnlY.rowWeights = new double[] { 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		pnlY.setLayout(gbl_pnlY);

		JLabel lblMinimumY = new JLabel("Minimum:");
		GridBagConstraints gbc_lblMinimumY = new GridBagConstraints();
		gbc_lblMinimumY.anchor = GridBagConstraints.WEST;
		gbc_lblMinimumY.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinimumY.gridx = 0;
		gbc_lblMinimumY.gridy = 0;
		pnlY.add(lblMinimumY, gbc_lblMinimumY);

		txtYmin = new JEngineerField(3, 20, "E24");
		txtYmin.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_spinYmin = new GridBagConstraints();
		gbc_spinYmin.gridwidth = 2;
		gbc_spinYmin.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinYmin.insets = new Insets(0, 0, 5, 0);
		gbc_spinYmin.gridx = 1;
		gbc_spinYmin.gridy = 0;
		pnlY.add(txtYmin, gbc_spinYmin);

		JLabel lblMaximumY = new JLabel("Maximum:");
		GridBagConstraints gbc_lblMaximumY = new GridBagConstraints();
		gbc_lblMaximumY.anchor = GridBagConstraints.WEST;
		gbc_lblMaximumY.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaximumY.gridx = 0;
		gbc_lblMaximumY.gridy = 1;
		pnlY.add(lblMaximumY, gbc_lblMaximumY);

		txtYmax = new JEngineerField(3, 20, "E24");
		txtYmax.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_spinYmax = new GridBagConstraints();
		gbc_spinYmax.gridwidth = 2;
		gbc_spinYmax.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinYmax.insets = new Insets(0, 0, 5, 0);
		gbc_spinYmax.gridx = 1;
		gbc_spinYmax.gridy = 1;
		pnlY.add(txtYmax, gbc_spinYmax);

		JLabel lblStepsY = new JLabel("Steps:");
		GridBagConstraints gbc_lblStepsY = new GridBagConstraints();
		gbc_lblStepsY.anchor = GridBagConstraints.WEST;
		gbc_lblStepsY.insets = new Insets(0, 0, 5, 5);
		gbc_lblStepsY.gridx = 0;
		gbc_lblStepsY.gridy = 2;
		pnlY.add(lblStepsY, gbc_lblStepsY);

		txtYstep = new JEngineerField(new DecimalFormat("###"), 0);
		txtYstep.setMinValue(1);
		txtYstep.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_spinYstep = new GridBagConstraints();
		gbc_spinYstep.gridwidth = 2;
		gbc_spinYstep.insets = new Insets(0, 0, 5, 0);
		gbc_spinYstep.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinYstep.gridx = 1;
		gbc_spinYstep.gridy = 2;
		pnlY.add(txtYstep, gbc_spinYstep);

		rdbtnLinY = new JRadioButton("Lin");
		rdbtnLinY.setSelected(true);
		GridBagConstraints gbc_rdbtnLinY = new GridBagConstraints();
		gbc_rdbtnLinY.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnLinY.gridx = 1;
		gbc_rdbtnLinY.gridy = 3;
		pnlY.add(rdbtnLinY, gbc_rdbtnLinY);
		rdbtnLinY.addActionListener(this);

		rdbtnLogY = new JRadioButton("Log");
		GridBagConstraints gbc_rdbtnLogY = new GridBagConstraints();
		gbc_rdbtnLogY.gridx = 2;
		gbc_rdbtnLogY.gridy = 3;
		pnlY.add(rdbtnLogY, gbc_rdbtnLogY);
		rdbtnLogY.addActionListener(this);

		btngrpLinLogY = new ButtonGroup();
		btngrpLinLogY.add(rdbtnLogY);
		btngrpLinLogY.add(rdbtnLinY);

		// Buttons
		JPanel pnlButton = new JPanel();
		GridBagConstraints gbc_pnlButton = new GridBagConstraints();
		gbc_pnlButton.insets = new Insets(0, 5, 0, 5);
		gbc_pnlButton.fill = GridBagConstraints.BOTH;
		gbc_pnlButton.gridx = 0;
		gbc_pnlButton.gridy = 2;
		tabAxis.add(pnlButton, gbc_pnlButton);
		GridBagLayout gbl_pnlButton = new GridBagLayout();
		gbl_pnlButton.columnWidths = new int[] { 0, 0, 0 };
		gbl_pnlButton.rowHeights = new int[] { 22, 0 };
		gbl_pnlButton.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_pnlButton.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnlButton.setLayout(gbl_pnlButton);

		btnCancel = new JButton("Cancel");
		btnCancel.setMinimumSize(new Dimension(0, 0));
		btnCancel.setMaximumSize(new Dimension(0, 0));
		btnCancel.setPreferredSize(new Dimension(0, 0));
		btnCancel.addActionListener(this);
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.fill = GridBagConstraints.BOTH;
		gbc_btnCancel.insets = new Insets(0, 2, 0, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 0;
		pnlButton.add(btnCancel, gbc_btnCancel);

		btnOk = new JButton("OK");
		btnOk.setMinimumSize(new Dimension(0, 0));
		btnOk.setMaximumSize(new Dimension(0, 0));
		btnOk.setPreferredSize(new Dimension(0, 0));
		btnOk.addActionListener(this);
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 5, 0, 2);
		gbc_btnOk.fill = GridBagConstraints.BOTH;
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 0;
		pnlButton.add(btnOk, gbc_btnOk);

		dialog.getRootPane().setDefaultButton(btnOk);
	}

	// ================================================================================
	// Public Functions
	// ================================================================================
	/**
	 * show the dialog
	 */
	public void show() {
		dialog.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOk) {
			// Build Settings Object
			settings.xAxisMaximum = txtXmax.getValue();
			settings.xAxisMinimum = txtXmin.getValue();
			settings.xAxisSteps = (int) txtXstep.getValue();

			if (rdbtnLinX.isSelected())
				settings.xScale = Scale.LINEAR;
			else
				settings.xScale = Scale.LOG;
			if (rdbtnLinY.isSelected())
				settings.yScale = Scale.LINEAR;
			else
				settings.yScale = Scale.LOG;

			settings.yAxisMaximum = txtYmax.getValue();
			settings.yAxisMinimum = txtYmin.getValue();
			settings.yAxisSteps = (int) txtYstep.getValue();

			this.figure.updatePlotSettings();

			dialog.dispose();
		}
		if (e.getSource() == btnCancel) {
			dialog.dispose();
		}

		// Step Spinners
		if (e.getSource() == rdbtnLogX) {
			txtXstep.setEnabled(false);
			txtXmin.setMinValue(0.0);
			txtXmax.setMinValue(0.0);
			if (txtXmin.getValue() < 0.0)
				txtXmin.setValue(1);
			if (txtXmax.getValue() < 0.0)
				txtXmax.setValue(1);
			if (txtXmin.getValue() == 0) {
				txtXmin.setValue(1);
				txtXmax.setValue(1000);
			}
		}

		if (e.getSource() == rdbtnLinX) {
			txtXmin.setMinValue(-Double.MAX_VALUE);
			txtXmax.setMinValue(-Double.MAX_VALUE);
			txtXstep.setEnabled(true);
		}

		if (e.getSource() == rdbtnLogY) {
			txtYstep.setEnabled(false);
			txtYmin.setMinValue(0.0);
			txtYmax.setMinValue(0.0);
			if (txtYmin.getValue() < 0.0)
				txtYmin.setValue(1);
			if (txtYmax.getValue() < 0.0)
				txtYmax.setValue(1);
			if (txtYmin.getValue() == 0) {
				txtYmin.setValue(1);
				txtYmax.setValue(1000);
			}
		}

		if (e.getSource() == rdbtnLinY) {
			txtYmin.setMinValue(-Double.MAX_VALUE);
			txtYmax.setMinValue(-Double.MAX_VALUE);
			txtYstep.setEnabled(true);
		}

	}

	/**
	 * Sets the RectPlotSettings object
	 * 
	 * @param s
	 *            RectPlotSettings
	 */
	public void setSettings(RectPlotSettings s) {
		this.settings = s;

		txtXmax.setValue(settings.xAxisMaximum);
		txtXmin.setValue(settings.xAxisMinimum);
		txtXstep.setValue(settings.xAxisSteps);
		txtYmax.setValue(settings.yAxisMaximum);
		txtYmin.setValue(settings.yAxisMinimum);
		txtYstep.setValue(settings.yAxisSteps);

		rdbtnLinX.setSelected(false);
		rdbtnLinY.setSelected(false);
		rdbtnLogX.setSelected(false);
		rdbtnLogY.setSelected(false);
		if (s.xScale == Scale.LINEAR)
			rdbtnLinX.setSelected(true);
		else {
			rdbtnLogX.setSelected(true);
			txtXstep.setEnabled(false);
		}
		if (s.yScale == Scale.LINEAR)
			rdbtnLinY.setSelected(true);
		else {
			rdbtnLogY.setSelected(true);
			txtYstep.setEnabled(false);
		}

	}

	public RectPlotSettings getSettings() {
		return this.settings;
	}
}
