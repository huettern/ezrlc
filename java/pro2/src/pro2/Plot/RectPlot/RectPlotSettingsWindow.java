package pro2.Plot.RectPlot;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import pro2.MVC.Controller;
import pro2.Plot.Figure;
import pro2.util.UIUtil;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.border.LineBorder;

public class RectPlotSettingsWindow implements ActionListener {
	
	private Controller controller;
	private Figure figure;
	
	private JDialog dialog;

	private JButton btnCancel, btnOk;
	private JSpinner spinYmin, spinYmax, spinYstep;
	private JSpinner spinXmin, spinXmax, spinXstep;
	
	private RectPlotSettings settings = new RectPlotSettings();
	
	//================================================================================
    // Constructors
    //================================================================================

	/**
	 * @wbp.parser.entryPoint
	 */
	public RectPlotSettingsWindow(Controller controller, Figure fig) {
		this.controller = controller;
		this.figure = fig;
		dialog = new JDialog(controller.getMainView());
		dialog.setResizable(false);
		dialog.setTitle("Graph Settings");		
		dialog.setModal(true);
		dialog.setLocation(250, 150);
		dialog.setSize(300, 350);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{322, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		dialog.getContentPane().setLayout(gridBagLayout);
		
		JTabbedPane tabbed = new JTabbedPane(JTabbedPane.TOP);
		tabbed.setToolTipText("");
		GridBagConstraints gbc_tabbed = new GridBagConstraints();
		gbc_tabbed.insets = new Insets(0, 0, 5, 0);
		gbc_tabbed.fill = GridBagConstraints.BOTH;
		gbc_tabbed.gridx = 0;
		gbc_tabbed.gridy = 0;
		dialog.getContentPane().add(tabbed, gbc_tabbed);
		
		JPanel tabAxis = new JPanel();
		tabbed.addTab("Axis", null, tabAxis, null);
		GridBagLayout gbl_tabAxis = new GridBagLayout();
		gbl_tabAxis.columnWidths = new int[]{0, 0};
		gbl_tabAxis.rowHeights = new int[]{0, 0, 30, 0};
		gbl_tabAxis.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_tabAxis.rowWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		tabAxis.setLayout(gbl_tabAxis);
		
		JPanel pnlX = new JPanel();
		pnlX.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "X-Axis", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pnlX = new GridBagConstraints();
		gbc_pnlX.insets = new Insets(5, 5, 5, 0);
		gbc_pnlX.fill = GridBagConstraints.BOTH;
		gbc_pnlX.gridx = 0;
		gbc_pnlX.gridy = 0;
		tabAxis.add(pnlX, gbc_pnlX);
		GridBagLayout gbl_pnlX = new GridBagLayout();
		gbl_pnlX.columnWidths = new int[] {0, 0};
		gbl_pnlX.rowHeights = new int[] {0, 0, 0};
		gbl_pnlX.columnWeights = new double[]{0.0, 1.0};
		gbl_pnlX.rowWeights = new double[]{1.0, 1.0, 1.0};
		pnlX.setLayout(gbl_pnlX);
		
		JLabel lblMinimum = new JLabel("Minimum:");
		GridBagConstraints gbc_lblMinimum = new GridBagConstraints();
		gbc_lblMinimum.anchor = GridBagConstraints.WEST;
		gbc_lblMinimum.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinimum.gridx = 0;
		gbc_lblMinimum.gridy = 0;
		pnlX.add(lblMinimum, gbc_lblMinimum);
		
		// Spinner Model for Axis min and max
		//	SpinnerNumberModel smdlAxisSize = new SpinnerNumberModel(0, Double.MIN_VALUE, Double.MAX_VALUE, Double.MIN_VALUE);
//		Double current = new Double(0);
//	    Double min = new Double(Double.MIN_VALUE);
//	    Double max = new Double(Double.MAX_VALUE);
//	    Double step = new Double(Double.MIN_VALUE);

		// Spinner settings for Axis min and max
	    double acurrent = 0;
	    double amin = -(1e50);
	    double amax = 1e50;
	    double astep = 1e-9;
	    
	    // Spinner settings for Axis step
	    double scurrent = 2;
	    double smin = 1;
	    double smax = 100;
	    double sstep = 1;
	    
	    
	  //  System.out.println("cur"+current+"min"+min+"max"+max+"stp"+step);
		
		
		spinXmin = new JSpinner();
		spinXmin.setModel(new SpinnerNumberModel(acurrent, amin, amax, astep));
		GridBagConstraints gbc_spinXmin = new GridBagConstraints();
		gbc_spinXmin.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinXmin.insets = new Insets(0, 0, 5, 0);
		gbc_spinXmin.gridx = 1;
		gbc_spinXmin.gridy = 0;
		pnlX.add(spinXmin, gbc_spinXmin);
		
		JLabel lblMaximum = new JLabel("Maximum:");
		GridBagConstraints gbc_lblMaximum = new GridBagConstraints();
		gbc_lblMaximum.anchor = GridBagConstraints.WEST;
		gbc_lblMaximum.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaximum.gridx = 0;
		gbc_lblMaximum.gridy = 1;
		pnlX.add(lblMaximum, gbc_lblMaximum);
		
		spinXmax = new JSpinner();
		spinXmax.setModel(new SpinnerNumberModel(acurrent, amin, amax, astep));
		GridBagConstraints gbc_spinXmax = new GridBagConstraints();
		gbc_spinXmax.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinXmax.insets = new Insets(0, 0, 5, 0);
		gbc_spinXmax.gridx = 1;
		gbc_spinXmax.gridy = 1;
		pnlX.add(spinXmax, gbc_spinXmax);
		
		JLabel lblSteps = new JLabel("Steps:");
		GridBagConstraints gbc_lblSteps = new GridBagConstraints();
		gbc_lblSteps.anchor = GridBagConstraints.WEST;
		gbc_lblSteps.insets = new Insets(0, 0, 0, 5);
		gbc_lblSteps.gridx = 0;
		gbc_lblSteps.gridy = 2;
		pnlX.add(lblSteps, gbc_lblSteps);
		
		spinXstep = new JSpinner();
		spinXstep.setModel(new SpinnerNumberModel(scurrent, smin, smax, sstep));
		GridBagConstraints gbc_spinXstep = new GridBagConstraints();
		gbc_spinXstep.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinXstep.gridx = 1;
		gbc_spinXstep.gridy = 2;
		pnlX.add(spinXstep, gbc_spinXstep);
		
		JPanel pnlY = new JPanel();
		pnlY.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Y-Axis", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pnlY = new GridBagConstraints();
		gbc_pnlY.insets = new Insets(5, 5, 5, 0);
		gbc_pnlY.fill = GridBagConstraints.BOTH;
		gbc_pnlY.gridx = 0;
		gbc_pnlY.gridy = 1;
		tabAxis.add(pnlY, gbc_pnlY);
		GridBagLayout gbl_pnlY = new GridBagLayout();
		gbl_pnlY.columnWidths = new int[]{0, 0, 0};
		gbl_pnlY.rowHeights = new int[]{0, 0, 0, 0};
		gbl_pnlY.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pnlY.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		pnlY.setLayout(gbl_pnlY);
		
		JLabel lblMinimum_1 = new JLabel("Minimum:");
		GridBagConstraints gbc_lblMinimum_1 = new GridBagConstraints();
		gbc_lblMinimum_1.anchor = GridBagConstraints.WEST;
		gbc_lblMinimum_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinimum_1.gridx = 0;
		gbc_lblMinimum_1.gridy = 0;
		pnlY.add(lblMinimum_1, gbc_lblMinimum_1);
		
		spinYmin = new JSpinner();
		spinYmin.setModel(new SpinnerNumberModel(acurrent, amin, amax, astep));
		GridBagConstraints gbc_spinYmin = new GridBagConstraints();
		gbc_spinYmin.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinYmin.insets = new Insets(0, 0, 5, 0);
		gbc_spinYmin.gridx = 1;
		gbc_spinYmin.gridy = 0;
		pnlY.add(spinYmin, gbc_spinYmin);
		
		JLabel lblMaximum_1 = new JLabel("Maximum:");
		GridBagConstraints gbc_lblMaximum_1 = new GridBagConstraints();
		gbc_lblMaximum_1.anchor = GridBagConstraints.WEST;
		gbc_lblMaximum_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaximum_1.gridx = 0;
		gbc_lblMaximum_1.gridy = 1;
		pnlY.add(lblMaximum_1, gbc_lblMaximum_1);
		
		spinYmax = new JSpinner();
		spinYmax.setModel(new SpinnerNumberModel(acurrent, amin, amax, astep));
		GridBagConstraints gbc_spinYmax = new GridBagConstraints();
		gbc_spinYmax.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinYmax.insets = new Insets(0, 0, 5, 0);
		gbc_spinYmax.gridx = 1;
		gbc_spinYmax.gridy = 1;
		pnlY.add(spinYmax, gbc_spinYmax);
		
		JLabel lblSteps_1 = new JLabel("Steps:");
		GridBagConstraints gbc_lblSteps_1 = new GridBagConstraints();
		gbc_lblSteps_1.anchor = GridBagConstraints.WEST;
		gbc_lblSteps_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblSteps_1.gridx = 0;
		gbc_lblSteps_1.gridy = 2;
		pnlY.add(lblSteps_1, gbc_lblSteps_1);
		
		spinYstep = new JSpinner();
		spinYstep.setModel(new SpinnerNumberModel(scurrent, smin, smax, sstep));
		GridBagConstraints gbc_spinYstep = new GridBagConstraints();
		gbc_spinYstep.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinYstep.gridx = 1;
		gbc_spinYstep.gridy = 2;
		pnlY.add(spinYstep, gbc_spinYstep);
		
		JPanel pnlButton = new JPanel();
		GridBagConstraints gbc_pnlButton = new GridBagConstraints();
		gbc_pnlButton.insets = new Insets(0, 5, 0, 0);
		gbc_pnlButton.fill = GridBagConstraints.BOTH;
		gbc_pnlButton.gridx = 0;
		gbc_pnlButton.gridy = 2;
		tabAxis.add(pnlButton, gbc_pnlButton);
		GridBagLayout gbl_pnlButton = new GridBagLayout();
		gbl_pnlButton.columnWidths = new int[]{0, 90, 90, 0};
		gbl_pnlButton.rowHeights = new int[]{22, 0};
		gbl_pnlButton.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlButton.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlButton.setLayout(gbl_pnlButton);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.fill = GridBagConstraints.BOTH;
		gbc_btnCancel.insets = new Insets(0, 5, 0, 5);
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 0;
		pnlButton.add(btnCancel, gbc_btnCancel);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(this);
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.insets = new Insets(0, 5, 0, 5);
		gbc_btnOk.fill = GridBagConstraints.BOTH;
		gbc_btnOk.gridx = 2;
		gbc_btnOk.gridy = 0;
		pnlButton.add(btnOk, gbc_btnOk);
		
		JPanel tabColor = new JPanel();
		tabbed.addTab("Color", null, tabColor, null);
		GridBagLayout gbl_tabColor = new GridBagLayout();
		gbl_tabColor.columnWidths = new int[]{0, 0};
		gbl_tabColor.rowHeights = new int[]{0, 0};
		gbl_tabColor.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_tabColor.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		tabColor.setLayout(gbl_tabColor);
	}

	//================================================================================
    // Public Functions
    //================================================================================
	public void show() {
		// TODO Auto-generated method stub
		dialog.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOk) {
			Double d;
			
			// Build Settings Object
			settings.xAxisMaximum = UIUtil.getSpinnerDoubleValue(spinXmax);
			settings.xAxisMinimum = UIUtil.getSpinnerDoubleValue(spinXmin);
			settings.xAxisSteps = UIUtil.getSpinnerIntValue(spinXstep);
			settings.yAxisMaximum = UIUtil.getSpinnerDoubleValue(spinYmax);
			settings.yAxisMinimum = UIUtil.getSpinnerDoubleValue(spinYmin);
			settings.yAxisSteps = UIUtil.getSpinnerIntValue(spinYstep);
			
			this.figure.updatePlotSettings();
			
			dialog.dispose();
		}
		if(e.getSource() == btnCancel) {
			dialog.dispose();
		}
		
	}

	/**
	 * Sets the RectPlotSettings object
	 * @param s RectPlotSettings
	 */
	public void setSettings(RectPlotSettings s) {
		this.settings = s;

		spinXmax.setValue(settings.xAxisMaximum);
		spinXmin.setValue(settings.xAxisMinimum);
		spinXstep.setValue(settings.xAxisSteps);
		spinYmax.setValue(settings.yAxisMaximum);
		spinYmin.setValue(settings.yAxisMinimum);
		spinYstep.setValue(settings.yAxisSteps);
	}

	public RectPlotSettings getSettings() {
		return this.settings;
	}
}
