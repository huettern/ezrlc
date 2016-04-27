package pro2.Plot.SmithChart;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;

import pro2.MVC.Controller;
import pro2.Plot.Figure;
import pro2.util.UIUtil;

import javax.swing.JFormattedTextField;

public class SmithChartSettingsWindow implements ActionListener {


	//================================================================================
    // Private Data
    //================================================================================
	private Controller controller;
	private Figure figure;
	
	private JDialog dialog;

	SmithChartSettings settings;

	private JButton btnCancel, btnOk;
	private ButtonGroup btngrpLinLogX, btngrpLinLogY;
	private JFormattedTextField tfRefResistance;
	private NumberFormat numFormat;
	
	//================================================================================
    // Constructors
    //================================================================================
	public SmithChartSettingsWindow(Controller controller, Figure fig)  {
		this.controller = controller;
		this.figure = fig;
		dialog = new JDialog(controller.getMainView());
		dialog.setResizable(false);
		dialog.setTitle("Smith Chart Settings");		
		dialog.setModal(true);
		dialog.setLocation(250, 150);
		dialog.setSize(300, 422);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{322, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		dialog.getContentPane().setLayout(gridBagLayout);
		
		JTabbedPane tabbed = new JTabbedPane(JTabbedPane.TOP);
		tabbed.setToolTipText("");
		GridBagConstraints gbc_tabbed = new GridBagConstraints();
		gbc_tabbed.fill = GridBagConstraints.BOTH;
		gbc_tabbed.gridx = 0;
		gbc_tabbed.gridy = 0;
		dialog.getContentPane().add(tabbed, gbc_tabbed);
		
		//****Axis****
		JPanel tabAxis = new JPanel();
		tabbed.addTab("General", null, tabAxis, null);
		GridBagLayout gbl_tabAxis = new GridBagLayout();
		gbl_tabAxis.columnWidths = new int[]{0, 0};
		gbl_tabAxis.rowHeights = new int[]{0, 30, 0};
		gbl_tabAxis.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_tabAxis.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		tabAxis.setLayout(gbl_tabAxis);
		
		//X-Panel
		JPanel pnlX = new JPanel();
		//pnlX.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "X-Axis", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pnlX = new GridBagConstraints();
		gbc_pnlX.insets = new Insets(5, 5, 5, 5);
		gbc_pnlX.fill = GridBagConstraints.BOTH;
		gbc_pnlX.gridx = 0;
		gbc_pnlX.gridy = 0;
		tabAxis.add(pnlX, gbc_pnlX);
		GridBagLayout gbl_pnlX = new GridBagLayout();
		gbl_pnlX.columnWidths = new int[] {0, 0, 0};
		gbl_pnlX.rowHeights = new int[] {0, 0};
		gbl_pnlX.columnWeights = new double[]{0.0, 1.0, 0.0};
		gbl_pnlX.rowWeights = new double[]{0.0, 1.0};
		pnlX.setLayout(gbl_pnlX);
		
		JLabel lblMinimumX = new JLabel("Reference Resistance:");
		GridBagConstraints gbc_lblMinimumX = new GridBagConstraints();
		gbc_lblMinimumX.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinimumX.gridx = 0;
		gbc_lblMinimumX.gridy = 0;
		pnlX.add(lblMinimumX, gbc_lblMinimumX);
		
		// Number format
		numFormat = NumberFormat.getNumberInstance();
		tfRefResistance = new JFormattedTextField(numFormat);
		GridBagConstraints gbc_formattedTextField = new GridBagConstraints();
		gbc_formattedTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextField.insets = new Insets(0, 0, 5, 5);
		gbc_formattedTextField.gridx = 1;
		gbc_formattedTextField.gridy = 0;
		pnlX.add(tfRefResistance, gbc_formattedTextField);
		
		JLabel lblNewLabel = new JLabel("Ω");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 0;
		pnlX.add(lblNewLabel, gbc_lblNewLabel);
		
		
		//Buttons
		JPanel pnlButton = new JPanel();
		GridBagConstraints gbc_pnlButton = new GridBagConstraints();
		gbc_pnlButton.insets = new Insets(0, 5, 0, 0);
		gbc_pnlButton.fill = GridBagConstraints.BOTH;
		gbc_pnlButton.gridx = 0;
		gbc_pnlButton.gridy = 1;
		tabAxis.add(pnlButton, gbc_pnlButton);
		GridBagLayout gbl_pnlButton = new GridBagLayout();
		gbl_pnlButton.columnWidths = new int[]{0, 0, 0};
		gbl_pnlButton.rowHeights = new int[]{22, 0};
		gbl_pnlButton.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlButton.rowWeights = new double[]{0.0, Double.MIN_VALUE};
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
	}

	//================================================================================
    // Public Functions
    //================================================================================
	public void show () {
		dialog.setVisible(true);
	}
	
	public void setSettings (SmithChartSettings s) {
		this.settings = s;
		tfRefResistance.setText(String.format("%.1f", this.settings.referenceResistance));
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOk) {
			settings.referenceResistance = UIUtil.getTextFieldDoubleValue(tfRefResistance);
			System.out.println("RES="+settings.referenceResistance);
			this.figure.updatePlotSettings();
			dialog.dispose();
		} else if (e.getSource() == btnCancel) {
			dialog.dispose();
		}
	}
	
	public SmithChartSettings getSettings () {
		return this.settings;
	}

}
