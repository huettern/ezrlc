package pro2.Plot;

import javax.swing.JDialog;

import pro2.MVC.Controller;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class RectPlotAddMeasurementWindow {

	private JDialog dialog;

	//================================================================================
    // Constructors
    //================================================================================

	/**
	 * @wbp.parser.entryPoint
	 */	
	public RectPlotAddMeasurementWindow(Controller controller) {
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
		
		JRadioButton rdbtnFile = new JRadioButton("File:");
		GridBagConstraints gbc_rdbtnFile = new GridBagConstraints();
		gbc_rdbtnFile.anchor = GridBagConstraints.WEST;
		gbc_rdbtnFile.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnFile.gridx = 0;
		gbc_rdbtnFile.gridy = 0;
		pnlDataSource.add(rdbtnFile, gbc_rdbtnFile);
		
		JLabel lblNewLabel = new JLabel("Filename");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		pnlDataSource.add(lblNewLabel, gbc_lblNewLabel);
		
		JRadioButton rdbtnModel = new JRadioButton("Model:");
		GridBagConstraints gbc_rdbtnModel = new GridBagConstraints();
		gbc_rdbtnModel.anchor = GridBagConstraints.WEST;
		gbc_rdbtnModel.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnModel.gridx = 0;
		gbc_rdbtnModel.gridy = 1;
		pnlDataSource.add(rdbtnModel, gbc_rdbtnModel);
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		pnlDataSource.add(comboBox, gbc_comboBox);
		
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
		
		JRadioButton rdbtnZ = new JRadioButton("Z");
		GridBagConstraints gbc_rdbtnZ = new GridBagConstraints();
		gbc_rdbtnZ.anchor = GridBagConstraints.WEST;
		gbc_rdbtnZ.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnZ.gridx = 0;
		gbc_rdbtnZ.gridy = 0;
		pnlMeasurementType.add(rdbtnZ, gbc_rdbtnZ);
		
		JRadioButton rdbtnS = new JRadioButton("S");
		GridBagConstraints gbc_rdbtnS = new GridBagConstraints();
		gbc_rdbtnS.anchor = GridBagConstraints.WEST;
		gbc_rdbtnS.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnS.gridx = 1;
		gbc_rdbtnS.gridy = 0;
		pnlMeasurementType.add(rdbtnS, gbc_rdbtnS);
		
		JRadioButton rdbtnY = new JRadioButton("Y");
		GridBagConstraints gbc_rdbtnY = new GridBagConstraints();
		gbc_rdbtnY.anchor = GridBagConstraints.WEST;
		gbc_rdbtnY.gridx = 2;
		gbc_rdbtnY.gridy = 0;
		pnlMeasurementType.add(rdbtnY, gbc_rdbtnY);
		
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
		
		JRadioButton rdbtnReal = new JRadioButton("Real");
		GridBagConstraints gbc_rdbtnReal = new GridBagConstraints();
		gbc_rdbtnReal.anchor = GridBagConstraints.WEST;
		gbc_rdbtnReal.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnReal.gridx = 0;
		gbc_rdbtnReal.gridy = 0;
		pnlComplexModifier.add(rdbtnReal, gbc_rdbtnReal);
		
		JRadioButton rdbtnImag = new JRadioButton("Imag");
		GridBagConstraints gbc_rdbtnImag = new GridBagConstraints();
		gbc_rdbtnImag.anchor = GridBagConstraints.WEST;
		gbc_rdbtnImag.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnImag.gridx = 1;
		gbc_rdbtnImag.gridy = 0;
		pnlComplexModifier.add(rdbtnImag, gbc_rdbtnImag);
		
		JRadioButton rdbtnMag = new JRadioButton("Mag");
		GridBagConstraints gbc_rdbtnMag = new GridBagConstraints();
		gbc_rdbtnMag.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnMag.anchor = GridBagConstraints.WEST;
		gbc_rdbtnMag.gridx = 2;
		gbc_rdbtnMag.gridy = 0;
		pnlComplexModifier.add(rdbtnMag, gbc_rdbtnMag);
		
		JRadioButton rdbtnAngle = new JRadioButton("Angle");
		GridBagConstraints gbc_rdbtnAngle = new GridBagConstraints();
		gbc_rdbtnAngle.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnAngle.anchor = GridBagConstraints.WEST;
		gbc_rdbtnAngle.gridx = 0;
		gbc_rdbtnAngle.gridy = 1;
		pnlComplexModifier.add(rdbtnAngle, gbc_rdbtnAngle);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 5, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		dialog.getContentPane().add(panel, gbc_panel);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setBounds(81, 2, 89, 22);
		panel.add(btnCancel);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnOk.setBounds(180, 2, 89, 22);
		panel.add(btnOk);
	}
	
	//================================================================================
    // Public Functions
    //================================================================================
	public void show() {
		// TODO Auto-generated method stub
		dialog.setVisible(true);
	}
}
