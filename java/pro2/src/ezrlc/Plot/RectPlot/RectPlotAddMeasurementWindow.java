package ezrlc.Plot.RectPlot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.MutableComboBoxModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import ezrlc.MVC.Controller;
import ezrlc.MVC.Controller.DataSource;
import ezrlc.Plot.Figure;
import ezrlc.RFData.RFData.ComplexModifier;
import ezrlc.RFData.RFData.MeasurementType;

public class RectPlotAddMeasurementWindow implements ActionListener {

	// ================================================================================
	// Local Variables
	// ================================================================================
	private Controller controller;
	private Figure figure;

	private JDialog dialog;

	private JButton btnCancel, btnOk;

	private RectPlotNewMeasurement newMeas = new RectPlotNewMeasurement();
	private JRadioButton rdbtnFile;
	private JRadioButton rdbtnModel;
	private JComboBox<String> cbModelList;
	private ButtonGroup btngrpSource;
	private JRadioButton rdbtnZ;
	private JRadioButton rdbtnS;
	private JRadioButton rdbtnY;
	private ButtonGroup btngrpMeasType;
	private JRadioButton rdbtnReal;
	private JRadioButton rdbtnImag;
	private JRadioButton rdbtnMag;
	private JRadioButton rdbtnAngle;
	private ButtonGroup btngrpCpxMod;
	private JLabel lblFileName;

	// ================================================================================
	// Constructors
	// ================================================================================

	public RectPlotAddMeasurementWindow(Controller controller, Figure fig) {
		this.controller = controller;
		this.figure = fig;

		builtRectPlotAddMeasurementWindow();
	}

	// ================================================================================
	// Private Functions
	// ================================================================================
	/**
	 * built the whole Window for the reactangular plot to add measurements
	 */
	private void builtRectPlotAddMeasurementWindow() {
		dialog = new JDialog(controller.getMainView());
		dialog.setResizable(false);
		dialog.setTitle("Add Measurement");
		dialog.setModal(true);
		dialog.setLocation(250, 150);
		dialog.setSize(300, 350);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 45, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		dialog.getContentPane().setLayout(gridBagLayout);

		// data source
		JPanel pnlDataSource = new JPanel();
		pnlDataSource.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Data Source", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlDataSource.setToolTipText("");
		pnlDataSource.setName("");
		GridBagConstraints gbc_pnlDataSource = new GridBagConstraints();
		gbc_pnlDataSource.insets = new Insets(5, 5, 5, 5);
		gbc_pnlDataSource.fill = GridBagConstraints.BOTH;
		gbc_pnlDataSource.gridx = 0;
		gbc_pnlDataSource.gridy = 0;
		dialog.getContentPane().add(pnlDataSource, gbc_pnlDataSource);
		GridBagLayout gbl_pnlDataSource = new GridBagLayout();
		gbl_pnlDataSource.columnWidths = new int[] { 0, 0, 0 };
		gbl_pnlDataSource.rowHeights = new int[] { 0, 0, 0 };
		gbl_pnlDataSource.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_pnlDataSource.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		pnlDataSource.setLayout(gbl_pnlDataSource);

		rdbtnFile = new JRadioButton("File:");
		rdbtnFile.setSelected(true);
		GridBagConstraints gbc_rdbtnFile = new GridBagConstraints();
		gbc_rdbtnFile.anchor = GridBagConstraints.WEST;
		gbc_rdbtnFile.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnFile.gridx = 0;
		gbc_rdbtnFile.gridy = 0;
		pnlDataSource.add(rdbtnFile, gbc_rdbtnFile);

		lblFileName = new JLabel("Filename");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		pnlDataSource.add(lblFileName, gbc_lblNewLabel);

		rdbtnModel = new JRadioButton("Model:");
		GridBagConstraints gbc_rdbtnModel = new GridBagConstraints();
		gbc_rdbtnModel.anchor = GridBagConstraints.WEST;
		gbc_rdbtnModel.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnModel.gridx = 0;
		gbc_rdbtnModel.gridy = 1;
		pnlDataSource.add(rdbtnModel, gbc_rdbtnModel);

		cbModelList = new JComboBox<String>();
		cbModelList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnFile.setSelected(false);
				rdbtnModel.setSelected(true);
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		pnlDataSource.add(cbModelList, gbc_comboBox);

		btngrpSource = new ButtonGroup();
		btngrpSource.add(rdbtnModel);
		btngrpSource.add(rdbtnFile);

		// measurement type
		JPanel pnlMeasurementType = new JPanel();
		pnlMeasurementType.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Measurement Type",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pnlMeasurementType = new GridBagConstraints();
		gbc_pnlMeasurementType.insets = new Insets(0, 5, 5, 5);
		gbc_pnlMeasurementType.fill = GridBagConstraints.BOTH;
		gbc_pnlMeasurementType.gridx = 0;
		gbc_pnlMeasurementType.gridy = 1;
		dialog.getContentPane().add(pnlMeasurementType, gbc_pnlMeasurementType);
		GridBagLayout gbl_pnlMeasurementType = new GridBagLayout();
		gbl_pnlMeasurementType.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_pnlMeasurementType.rowHeights = new int[] { 0, 0 };
		gbl_pnlMeasurementType.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_pnlMeasurementType.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		pnlMeasurementType.setLayout(gbl_pnlMeasurementType);

		rdbtnZ = new JRadioButton("Z");
		rdbtnZ.setSelected(true);
		GridBagConstraints gbc_rdbtnZ = new GridBagConstraints();
		gbc_rdbtnZ.anchor = GridBagConstraints.WEST;
		gbc_rdbtnZ.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnZ.gridx = 0;
		gbc_rdbtnZ.gridy = 0;
		pnlMeasurementType.add(rdbtnZ, gbc_rdbtnZ);

		rdbtnS = new JRadioButton("S");
		GridBagConstraints gbc_rdbtnS = new GridBagConstraints();
		gbc_rdbtnS.anchor = GridBagConstraints.WEST;
		gbc_rdbtnS.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnS.gridx = 1;
		gbc_rdbtnS.gridy = 0;
		pnlMeasurementType.add(rdbtnS, gbc_rdbtnS);

		rdbtnY = new JRadioButton("Y");
		GridBagConstraints gbc_rdbtnY = new GridBagConstraints();
		gbc_rdbtnY.anchor = GridBagConstraints.WEST;
		gbc_rdbtnY.gridx = 2;
		gbc_rdbtnY.gridy = 0;
		pnlMeasurementType.add(rdbtnY, gbc_rdbtnY);

		btngrpMeasType = new ButtonGroup();
		btngrpMeasType.add(rdbtnS);
		btngrpMeasType.add(rdbtnY);
		btngrpMeasType.add(rdbtnZ);

		// complex modifier
		JPanel pnlComplexModifier = new JPanel();
		pnlComplexModifier.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Complex Modifier",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pnlComplexModifier = new GridBagConstraints();
		gbc_pnlComplexModifier.insets = new Insets(0, 5, 5, 5);
		gbc_pnlComplexModifier.fill = GridBagConstraints.BOTH;
		gbc_pnlComplexModifier.gridx = 0;
		gbc_pnlComplexModifier.gridy = 2;
		dialog.getContentPane().add(pnlComplexModifier, gbc_pnlComplexModifier);
		GridBagLayout gbl_pnlComplexModifier = new GridBagLayout();
		gbl_pnlComplexModifier.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_pnlComplexModifier.rowHeights = new int[] { 0, 0, 0 };
		gbl_pnlComplexModifier.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_pnlComplexModifier.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		pnlComplexModifier.setLayout(gbl_pnlComplexModifier);

		rdbtnReal = new JRadioButton("Real");
		rdbtnReal.setSelected(true);
		GridBagConstraints gbc_rdbtnReal = new GridBagConstraints();
		gbc_rdbtnReal.anchor = GridBagConstraints.WEST;
		gbc_rdbtnReal.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnReal.gridx = 0;
		gbc_rdbtnReal.gridy = 0;
		pnlComplexModifier.add(rdbtnReal, gbc_rdbtnReal);

		rdbtnImag = new JRadioButton("Imag");
		GridBagConstraints gbc_rdbtnImag = new GridBagConstraints();
		gbc_rdbtnImag.anchor = GridBagConstraints.WEST;
		gbc_rdbtnImag.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnImag.gridx = 1;
		gbc_rdbtnImag.gridy = 0;
		pnlComplexModifier.add(rdbtnImag, gbc_rdbtnImag);

		rdbtnMag = new JRadioButton("Mag");
		GridBagConstraints gbc_rdbtnMag = new GridBagConstraints();
		gbc_rdbtnMag.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnMag.anchor = GridBagConstraints.WEST;
		gbc_rdbtnMag.gridx = 2;
		gbc_rdbtnMag.gridy = 0;
		pnlComplexModifier.add(rdbtnMag, gbc_rdbtnMag);

		rdbtnAngle = new JRadioButton("Angle");
		GridBagConstraints gbc_rdbtnAngle = new GridBagConstraints();
		gbc_rdbtnAngle.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnAngle.anchor = GridBagConstraints.WEST;
		gbc_rdbtnAngle.gridx = 0;
		gbc_rdbtnAngle.gridy = 1;
		pnlComplexModifier.add(rdbtnAngle, gbc_rdbtnAngle);

		btngrpCpxMod = new ButtonGroup();
		btngrpCpxMod.add(rdbtnAngle);
		btngrpCpxMod.add(rdbtnMag);
		btngrpCpxMod.add(rdbtnImag);
		btngrpCpxMod.add(rdbtnReal);

		// buttons
		JPanel pnlButtons = new JPanel();
		GridBagConstraints gbc_pnlButtons = new GridBagConstraints();
		gbc_pnlButtons.insets = new Insets(0, 5, 5, 5);
		gbc_pnlButtons.fill = GridBagConstraints.BOTH;
		gbc_pnlButtons.gridx = 0;
		gbc_pnlButtons.gridy = 3;
		dialog.getContentPane().add(pnlButtons, gbc_pnlButtons);
		GridBagLayout gbl_pnlButtons = new GridBagLayout();
		gbl_pnlButtons.columnWidths = new int[] { 90, 90, 0 };
		gbl_pnlButtons.rowHeights = new int[] { 22, 0 };
		gbl_pnlButtons.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_pnlButtons.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnlButtons.setLayout(gbl_pnlButtons);

		btnCancel = new JButton("Cancel");
		btnCancel.setPreferredSize(new Dimension(0, 0));
		btnCancel.setMinimumSize(new Dimension(0, 0));
		btnCancel.setMaximumSize(new Dimension(0, 0));
		btnCancel.addActionListener(this);
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.fill = GridBagConstraints.BOTH;
		gbc_btnCancel.insets = new Insets(0, 2, 0, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 0;
		pnlButtons.add(btnCancel, gbc_btnCancel);

		btnOk = new JButton("OK");
		btnOk.setMaximumSize(new Dimension(0, 0));
		btnOk.setMinimumSize(new Dimension(0, 0));
		btnOk.setPreferredSize(new Dimension(0, 0));
		btnOk.addActionListener(this);
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 5, 0, 2);
		gbc_btnOk.fill = GridBagConstraints.BOTH;
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 0;
		pnlButtons.add(btnOk, gbc_btnOk);

		dialog.getRootPane().setDefaultButton(btnOk);
	}

	/**
	 * Returns the selected Button in the given ButtonGroup
	 * 
	 * @param buttonGroup
	 *            ButtonGroup
	 * @return Returns the selected Button in the given ButtonGroup
	 */
	private AbstractButton getSelectedButton(ButtonGroup buttonGroup) {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				return button;
			}
		}

		return null;
	}

	/**
	 * Build a RectPlotNewMeasurement Object based upon the user input
	 */
	private void parseInput() {
		RectPlotNewMeasurement nm = new RectPlotNewMeasurement();
		// Source
		if (this.getSelectedButton(this.btngrpSource) == rdbtnFile) {
			nm.src = DataSource.FILE;
			nm.src_name = this.lblFileName.getText();
		}
		if (this.getSelectedButton(this.btngrpSource) == rdbtnModel) {
			nm.src = DataSource.MODEL;
			nm.eqCircuitID = Integer.parseInt(cbModelList.getSelectedItem().toString().split("( )")[1]);
			nm.src_name = cbModelList.getSelectedItem().toString();
		}
		// Measurement Tyoe
		if (this.getSelectedButton(this.btngrpMeasType) == rdbtnS) {
			nm.type = MeasurementType.S;
		}
		if (this.getSelectedButton(this.btngrpMeasType) == rdbtnY) {
			nm.type = MeasurementType.Y;
		}
		if (this.getSelectedButton(this.btngrpMeasType) == rdbtnZ) {
			nm.type = MeasurementType.Z;
		}
		// Complex Modifier
		if (this.getSelectedButton(this.btngrpCpxMod) == rdbtnAngle) {
			nm.cpxMod = ComplexModifier.ANGLE;
		}
		if (this.getSelectedButton(this.btngrpCpxMod) == rdbtnMag) {
			nm.cpxMod = ComplexModifier.MAG;
		}
		if (this.getSelectedButton(this.btngrpCpxMod) == rdbtnReal) {
			nm.cpxMod = ComplexModifier.REAL;
		}
		if (this.getSelectedButton(this.btngrpCpxMod) == rdbtnImag) {
			nm.cpxMod = ComplexModifier.IMAG;
		}
		this.newMeas = nm;
	}

	/**
	 * Sets all buttons and settings to their default
	 */
	private void resetButtons() {
		rdbtnFile.setSelected(true);
		rdbtnModel.setSelected(false);
		rdbtnS.setSelected(false);
		rdbtnY.setSelected(false);
		rdbtnZ.setSelected(true);
		rdbtnReal.setSelected(true);
		rdbtnImag.setSelected(false);
		rdbtnMag.setSelected(false);
		rdbtnAngle.setSelected(false);
	}

	// ================================================================================
	// Public Functions
	// ================================================================================
	public void setFilename(String s) {
		if (s != null) {
			this.lblFileName.setText(s);
			rdbtnFile.setEnabled(true);
			rdbtnFile.setSelected(true);
			rdbtnModel.setSelected(false);
		} else {
			rdbtnFile.setEnabled(false);
			rdbtnModel.setSelected(true);
			this.lblFileName.setText("");
		}
	}

	public void show() {
		newMeas = new RectPlotNewMeasurement();
		dialog.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOk) {
			this.parseInput();
			this.figure.addNewMeasurement();
			dialog.dispose();
		}
		if (e.getSource() == btnCancel) {
			this.resetButtons();
			dialog.dispose();
		}

	}

	public RectPlotNewMeasurement getNewMeasurement() {
		return this.newMeas;
	}

	/**
	 * Sets the drop combo box items of the model list
	 * 
	 * @param modelIDs
	 */
	public void setModels(int[] modelIDs) {
		String[] modelNames = new String[modelIDs.length];
		for (int i = 0; i < modelIDs.length; i++) {
			modelNames[i] = "Model " + modelIDs[i];
			// cbModelList.addItem(modelNames[i]);
		}

		MutableComboBoxModel<String> model = (MutableComboBoxModel<String>) cbModelList.getModel();
		cbModelList.removeAllItems();
		for (int i = 0; i < model.getSize(); i++) {
			model.removeElementAt(i);
		}

		for (int i = 0; i < modelIDs.length; i++) {
			cbModelList.addItem(modelNames[i]);
		}
		resetButtons();
	}
}
