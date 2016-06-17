package ezrlc.View;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import ezrlc.Controller.Controller;
import ezrlc.Model.Model.UpdateEvent;
import ezrlc.ModelCalculation.MCOptSettings;
import ezrlc.RFData.RFData.ComplexModifier;
import ezrlc.RFData.RFData.MeasurementType;

/**
 * Dialog for optimizer settings
 * @author noah
 *
 */
public class OptimizerSettingsWindow implements ActionListener {


	// ================================================================================
	// Private Attributes
	// ================================================================================
	private Controller controller;
	
	private JDialog dialog;
	
	private MCOptSettings ops;
	private int eqcID;
	private boolean[] lock;
	
	// UI Elements
	private JEngineerField tfStep, tfRelTh, tfAbsTh, tfMaxEval;
	private JRadioButton rbZ, rbY, rbS, rbReal, rbImag, rbMag, rbAngle;
	private JButton btOk, btCancel, btDefault;
	
	// ================================================================================
	// Public Methods
	// ================================================================================
	/**
	 * Create new optimizer settings dialog
	 * @param parent
	 */
	public OptimizerSettingsWindow(Controller parent) {
		this.controller = parent;
		build();
	}
	
	/**
	 * Set the eqc ID
	 * @param id
	 */
	public void setEqcID(int id) {
		eqcID = id;
	}

	/**
	 * Set the parameter lock array
	 * @param lck
	 */
	public void setLock(boolean[] lck) {
		lock = lck;
	}
	
	/**
	 * Displays the dialog
	 */
	public void show() {
		dialog.setVisible(true);
	}
	
	
	// ================================================================================
	// Private Methods
	// ================================================================================
	private void build() {
		dialog = new JDialog(controller.getMainView(), "Optimizer Settings");
		dialog.setResizable(false);
		dialog.setModal(true);
		dialog.setLocation(250, 150);
		dialog.setSize(300, 422);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 45, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		dialog.getContentPane().setLayout(gridBagLayout);
		
		// First group: text fields
		JPanel tf = new JPanel(new GridBagLayout());

		tf.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Simplex optimizer settings", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		dialog.getContentPane().add(tf, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		
		JLabel lbStep = new JLabel("Step Size");
		tf.add(lbStep, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5,5,5,5), 0, 0));
		tfStep = new JEngineerField(3, 20, "E24");
		tf.add(tfStep, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		
		JLabel lbAbsTh = new JLabel("Absolute Threshold");
		tf.add(lbAbsTh, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5,5,5,5), 0, 0));
		tfAbsTh = new JEngineerField(3, 20, "E24");
		tf.add(tfAbsTh, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		
		JLabel lbRelTh = new JLabel("Relative Threshold");
		tf.add(lbRelTh, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5,5,5,5), 0, 0));
		tfRelTh = new JEngineerField(3, 20, "E24");
		tf.add(tfRelTh, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		
		JLabel lbMaxEval = new JLabel("Max Eval");
		tf.add(lbMaxEval, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5,5,5,5), 0, 0));
		tfMaxEval = new JEngineerField(3, 20, "E24");
		tf.add(tfMaxEval, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		
		// Error sum settings
		JPanel pe = new JPanel(new GridBagLayout());
		pe.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Error Calculation", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		dialog.getContentPane().add(pe, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		
		JPanel pm = new JPanel(new GridBagLayout());
		pm.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Measurement Type", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		pe.add(pm, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));

		rbS = new JRadioButton("S");
		pm.add(rbS, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		rbY = new JRadioButton("Y");
		pm.add(rbY, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		rbZ = new JRadioButton("Z");
		pm.add(rbZ, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		
		JPanel pc = new JPanel(new GridBagLayout());
		pc.setBorder(new TitledBorder(new LineBorder(Color.LIGHT_GRAY), "Complex Modifier", TitledBorder.CENTER,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		pe.add(pc, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));

		rbReal = new JRadioButton("REAL");
		pc.add(rbReal, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		rbImag = new JRadioButton("IMAG");
		pc.add(rbImag, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		rbMag = new JRadioButton("MAG");
		pc.add(rbMag, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		rbAngle = new JRadioButton("ANGLE");
		pc.add(rbAngle, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0, 0));
		
		// Buttons
		JPanel pb = new JPanel();
		dialog.getContentPane().add(pb, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(5,5,5,5), 0, 0));

		btCancel = new JButton("Cancel");
		btCancel.addActionListener(this);
		pb.add(btCancel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,0,5,0), 0, 0));
		btDefault = new JButton("Default");
		btDefault.addActionListener(this);
		pb.add(btDefault, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,0,5,0), 0, 0));
		btOk = new JButton("OK");
		btOk.addActionListener(this);
		pb.add(btOk, new GridBagConstraints(2, 0, 0, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5,0,5,0), 0, 0));
		
		// Button groups
		ButtonGroup bgMeas = new ButtonGroup();
		bgMeas.add(rbS);
		bgMeas.add(rbY);
		bgMeas.add(rbZ);
		ButtonGroup bgCpx = new ButtonGroup();
		bgCpx.add(rbReal);
		bgCpx.add(rbImag);
		bgCpx.add(rbMag);
		bgCpx.add(rbAngle);

		ops = new MCOptSettings();
		updateFields();
	}
	
	/**
	 * Reads the current settings and updates the ui correspondingly
	 */
	private void updateFields() {
		tfStep.setValue(ops.step);
		tfAbsTh.setValue(ops.absTh);
		tfRelTh.setValue(ops.relTh);
		tfMaxEval.setValue(ops.maxEval);
		if(ops.measType == MeasurementType.S) rbS.setSelected(true);
		if(ops.measType == MeasurementType.Y) rbY.setSelected(true);
		if(ops.measType == MeasurementType.Z) rbZ.setSelected(true);
		if(ops.cpxMod == ComplexModifier.REAL) rbReal.setSelected(true);
		if(ops.cpxMod == ComplexModifier.IMAG) rbImag.setSelected(true);
		if(ops.cpxMod == ComplexModifier.MAG) rbMag.setSelected(true);
		if(ops.cpxMod == ComplexModifier.ANGLE) rbAngle.setSelected(true);
	}
	
	/**
	 * Parses the GUI elements
	 */
	private void parse() {
		ops.step = tfStep.getValue();
		ops.relTh = tfRelTh.getValue();
		ops.absTh = tfAbsTh.getValue();
		ops.maxEval = (int)tfMaxEval.getValue();
		if(rbS.isSelected()) ops.measType = MeasurementType.S;
		else if(rbY.isSelected()) ops.measType = MeasurementType.Y;
		else if(rbZ.isSelected()) ops.measType = MeasurementType.Z;
		if(rbReal.isSelected()) ops.cpxMod = ComplexModifier.REAL;
		else if(rbImag.isSelected()) ops.cpxMod = ComplexModifier.IMAG;
		else if(rbMag.isSelected()) ops.cpxMod = ComplexModifier.MAG;
		else if(rbAngle.isSelected()) ops.cpxMod = ComplexModifier.ANGLE;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btCancel) {
			controller.manualNotify(UpdateEvent.CHANGE_EQC);
			dialog.dispose();
		} else if(e.getSource() == btDefault) {
			ops = new MCOptSettings();
			updateFields();
		} else if(e.getSource() == btOk) {
			parse();
			controller.optimizeEqCircuit(eqcID, lock, ops);
			dialog.dispose();
		}
	}
	
	

}
