package ezrlc.Plot.SmithChart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

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
import ezrlc.Plot.DataSetSettings;
import ezrlc.Plot.Figure;

/**
 * Window to add a new measurement to the smithchart
 * 
 * @author noah
 *
 */
public class SmithChartAddMeasurementWindow implements ActionListener {

	private Figure figure;

	private JDialog dialog;

	private JButton btnCancel, btnOk;

	private SmithChartNewMeasurement newMeas = new SmithChartNewMeasurement();
	private JRadioButton rdbtnFile;
	private JRadioButton rdbtnModel;
	private JComboBox<String> cbModelList;
	private ButtonGroup btngrpSource;
	private JLabel lblFileName;

	// ================================================================================
	// Constructors
	// ================================================================================

	/**
	 * Build window
	 * 
	 * @param controller
	 *            controller object
	 * @param fig
	 *            figure object
	 */
	public SmithChartAddMeasurementWindow(Controller controller, Figure fig) {
		this.figure = fig;
		dialog = new JDialog(controller.getMainView());
		dialog.setResizable(false);
		dialog.setTitle("Add Measurement");
		dialog.setModal(true);
		dialog.setLocation(250, 150);
		dialog.setSize(300, 350);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		dialog.getContentPane().setLayout(gridBagLayout);

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
		gbl_pnlDataSource.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
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

		JPanel pnlButtons = new JPanel();
		GridBagConstraints gbc_pnlButtons = new GridBagConstraints();
		gbc_pnlButtons.insets = new Insets(0, 5, 5, 5);
		gbc_pnlButtons.fill = GridBagConstraints.BOTH;
		gbc_pnlButtons.gridx = 0;
		gbc_pnlButtons.gridy = 1;
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

	// ================================================================================
	// Private Functions
	// ================================================================================
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
		SmithChartNewMeasurement s = new SmithChartNewMeasurement();
		// Source
		if (this.getSelectedButton(this.btngrpSource) == rdbtnFile) {
			s.src = DataSource.FILE;
			s.src_name = this.lblFileName.getText();
		}
		if (this.getSelectedButton(this.btngrpSource) == rdbtnModel) {
			s.src = DataSource.MODEL;
			s.eqCircuitID = Integer.parseInt(cbModelList.getSelectedItem().toString().split("( )")[1]);
			s.src_name = cbModelList.getSelectedItem().toString();
		}
		this.newMeas = s;
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

	/**
	 * Show the dialog
	 */
	public void show() {
		newMeas = new SmithChartNewMeasurement();
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
			dialog.dispose();
		}

	}

	public SmithChartNewMeasurement getNewMeasurement() {
		return this.newMeas;
	}

	/**
	 * Get list of dataset settings and check if a measurement is already
	 * present and disable this option
	 * 
	 * @param dataSetSettings
	 *            list of dataSetSettings
	 */
	public void setDatasets(List<DataSetSettings> dataSetSettings) {
		for (DataSetSettings set : dataSetSettings) {
			if (this.lblFileName.getText().compareTo(set.getLabelName()) == 0) {
				rdbtnFile.setSelected(false);
				rdbtnFile.setEnabled(false);
				rdbtnModel.setSelected(true);
			}
		}
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

		for (int i = 0; i < model.getSize(); i++) {
			model.removeElementAt(i);
		}

		for (int i = 0; i < modelIDs.length; i++) {
			cbModelList.addItem(modelNames[i]);
		}
		rdbtnFile.setSelected(true);
		rdbtnModel.setSelected(false);
	}
}
