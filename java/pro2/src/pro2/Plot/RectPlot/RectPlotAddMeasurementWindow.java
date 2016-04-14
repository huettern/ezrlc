package pro2.Plot.RectPlot;

import javax.swing.JDialog;

import pro2.MVC.Controller;
import pro2.MVC.Controller.DataSource;
import pro2.Plot.Figure;
import pro2.RFData.RFData.ComplexModifier;
import pro2.RFData.RFData.MeasurementType;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;
import javax.naming.ldap.Rdn;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class RectPlotAddMeasurementWindow implements ActionListener {

	private Controller controller;
	private Figure figure;
	
	private JDialog dialog;

	private JButton btnCancel, btnOk;
	
	private RectPlotNewMeasurement newMeas = new RectPlotNewMeasurement();
	private JRadioButton rdbtnFile;
	private JRadioButton rdbtnModel;
	private JComboBox cbModelList;
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
	
	//================================================================================
    // Constructors
    //================================================================================

	/**
	 * @wbp.parser.entryPoint
	 */	
	public RectPlotAddMeasurementWindow(Controller controller, Figure fig)  {
		this.controller = controller;
		this.figure = fig;
		dialog = new JDialog(controller.getMainView());
		dialog.setResizable(false);
		dialog.setTitle("Add Measurement");		
		dialog.setModal(true);
		dialog.setLocation(250, 150);
		dialog.setSize(300, 350);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 45, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		dialog.getContentPane().setLayout(gridBagLayout);
		
		JPanel pnlDataSource = new JPanel();
		pnlDataSource.setBorder(new TitledBorder(null, "Data Source", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		pnlDataSource.setToolTipText("");
		pnlDataSource.setName("");
		GridBagConstraints gbc_pnlDataSource = new GridBagConstraints();
		gbc_pnlDataSource.insets = new Insets(5, 5, 5, 5);
		gbc_pnlDataSource.fill = GridBagConstraints.BOTH;
		gbc_pnlDataSource.gridx = 0;
		gbc_pnlDataSource.gridy = 0;
		dialog.getContentPane().add(pnlDataSource, gbc_pnlDataSource);
		GridBagLayout gbl_pnlDataSource = new GridBagLayout();
		gbl_pnlDataSource.columnWidths = new int[]{0, 0, 0};
		gbl_pnlDataSource.rowHeights = new int[]{0, 0, 0};
		gbl_pnlDataSource.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pnlDataSource.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
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
		
		cbModelList = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		pnlDataSource.add(cbModelList, gbc_comboBox);
		
		btngrpSource = new ButtonGroup();
		btngrpSource.add(rdbtnModel);
		btngrpSource.add(rdbtnFile);
		
		JPanel pnlMeasurementType = new JPanel();
		pnlMeasurementType.setBorder(new TitledBorder(null, "Measurement Type", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlMeasurementType = new GridBagConstraints();
		gbc_pnlMeasurementType.insets = new Insets(0, 5, 5, 5);
		gbc_pnlMeasurementType.fill = GridBagConstraints.BOTH;
		gbc_pnlMeasurementType.gridx = 0;
		gbc_pnlMeasurementType.gridy = 1;
		dialog.getContentPane().add(pnlMeasurementType, gbc_pnlMeasurementType);
		GridBagLayout gbl_pnlMeasurementType = new GridBagLayout();
		gbl_pnlMeasurementType.columnWidths = new int[]{0, 0, 0, 0};
		gbl_pnlMeasurementType.rowHeights = new int[]{0, 0};
		gbl_pnlMeasurementType.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlMeasurementType.rowWeights = new double[]{1.0, Double.MIN_VALUE};
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
		
		JPanel pnlComplexModifier = new JPanel();
		pnlComplexModifier.setBorder(new TitledBorder(null, "Complex Modifier", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlComplexModifier = new GridBagConstraints();
		gbc_pnlComplexModifier.insets = new Insets(0, 5, 5, 5);
		gbc_pnlComplexModifier.fill = GridBagConstraints.BOTH;
		gbc_pnlComplexModifier.gridx = 0;
		gbc_pnlComplexModifier.gridy = 2;
		dialog.getContentPane().add(pnlComplexModifier, gbc_pnlComplexModifier);
		GridBagLayout gbl_pnlComplexModifier = new GridBagLayout();
		gbl_pnlComplexModifier.columnWidths = new int[]{0, 0, 0, 0};
		gbl_pnlComplexModifier.rowHeights = new int[]{0, 0, 0};
		gbl_pnlComplexModifier.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlComplexModifier.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
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
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 5, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		dialog.getContentPane().add(panel, gbc_panel);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(81, 2, 89, 22);
		panel.add(btnCancel);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(this);
		btnOk.setBounds(180, 2, 89, 22);
		panel.add(btnOk);
	}

	//================================================================================
    // Private Functions
    //================================================================================
	/**
	 * Returns the selected Button in the given ButtonGroup
	 * @param buttonGroup ButtonGroup
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
	private void parseInput () {
		RectPlotNewMeasurement nm = new RectPlotNewMeasurement();
		// Source
		if(this.getSelectedButton(this.btngrpSource) == rdbtnFile){
			nm.src=DataSource.FILE;
		}
		if(this.getSelectedButton(this.btngrpSource)  == rdbtnModel){
			nm.src=DataSource.MODEL;
		}
		// Measurement Tyoe
		if(this.getSelectedButton(this.btngrpMeasType)  == rdbtnS) {
			nm.type=MeasurementType.S;
		}
		if(this.getSelectedButton(this.btngrpMeasType)  == rdbtnY) {
			nm.type=MeasurementType.Y;
		}
		if(this.getSelectedButton(this.btngrpMeasType)  == rdbtnZ) {
			nm.type=MeasurementType.Z;
		}
		// Complex Modifier
		if(this.getSelectedButton(this.btngrpCpxMod)  == rdbtnAngle) {
			nm.cpxMod=ComplexModifier.ANGLE;
		}
		if(this.getSelectedButton(this.btngrpCpxMod) == rdbtnMag) {
			nm.cpxMod=ComplexModifier.MAG;
		}
		if(this.getSelectedButton(this.btngrpCpxMod) == rdbtnReal) {
			nm.cpxMod=ComplexModifier.REAL;
		}
		if(this.getSelectedButton(this.btngrpCpxMod) == rdbtnImag) {
			nm.cpxMod=ComplexModifier.IMAG;
		}
		this.newMeas = nm;
	}
	
	//================================================================================
    // Public Functions
    //================================================================================
	public void setFilename(String s) {
		this.lblFileName.setText(s);
	}
	
	public void show() {
		newMeas = new RectPlotNewMeasurement();
		dialog.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOk) {
			this.parseInput();
			this.figure.addNewMeasurement();
			dialog.dispose();
		}
		if(e.getSource() == btnCancel) {
			dialog.dispose();
		}
		
	}

	public RectPlotNewMeasurement getNewMeasurement() {
		return this.newMeas;
	}
}
