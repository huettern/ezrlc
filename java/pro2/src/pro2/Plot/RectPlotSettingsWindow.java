package pro2.Plot;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import pro2.MVC.Controller;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;

public class RectPlotSettingsWindow {
	
	private JDialog dialog;

	//================================================================================
    // Constructors
    //================================================================================

	/**
	 * @wbp.parser.entryPoint
	 */
	public RectPlotSettingsWindow(Controller controller) {
		dialog = new JDialog(controller.getMainView());
		dialog.setResizable(false);
		dialog.setTitle("Graph Settings");		
		dialog.setModal(true);
		dialog.setLocation(250, 150);
		dialog.setSize(300, 350);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{294, 0};
		gridBagLayout.rowHeights = new int[]{322, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		dialog.getContentPane().setLayout(gridBagLayout);
		
		JTabbedPane tabAxis = new JTabbedPane(JTabbedPane.TOP);
		tabAxis.setToolTipText("");
		GridBagConstraints gbc_tabAxis = new GridBagConstraints();
		gbc_tabAxis.fill = GridBagConstraints.BOTH;
		gbc_tabAxis.gridx = 0;
		gbc_tabAxis.gridy = 0;
		dialog.getContentPane().add(tabAxis, gbc_tabAxis);
		
		JPanel panel = new JPanel();
		tabAxis.addTab("Axis", null, panel, null);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 30, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JPanel pnlX = new JPanel();
		pnlX.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "X-Axis", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pnlX = new GridBagConstraints();
		gbc_pnlX.insets = new Insets(5, 5, 0, 5);
		gbc_pnlX.fill = GridBagConstraints.BOTH;
		gbc_pnlX.gridx = 0;
		gbc_pnlX.gridy = 0;
		panel.add(pnlX, gbc_pnlX);
		GridBagLayout gbl_pnlX = new GridBagLayout();
		gbl_pnlX.columnWidths = new int[]{0, 0, 0};
		gbl_pnlX.rowHeights = new int[]{0, 0, 0, 0};
		gbl_pnlX.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pnlX.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		pnlX.setLayout(gbl_pnlX);
		
		JLabel lblMinimum = new JLabel("Minimum:");
		GridBagConstraints gbc_lblMinimum = new GridBagConstraints();
		gbc_lblMinimum.anchor = GridBagConstraints.WEST;
		gbc_lblMinimum.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinimum.gridx = 0;
		gbc_lblMinimum.gridy = 0;
		pnlX.add(lblMinimum, gbc_lblMinimum);
		
		JSpinner spinner = new JSpinner();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 0;
		pnlX.add(spinner, gbc_spinner);
		
		JLabel lblMaximum = new JLabel("Maximum:");
		GridBagConstraints gbc_lblMaximum = new GridBagConstraints();
		gbc_lblMaximum.anchor = GridBagConstraints.WEST;
		gbc_lblMaximum.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaximum.gridx = 0;
		gbc_lblMaximum.gridy = 1;
		pnlX.add(lblMaximum, gbc_lblMaximum);
		
		JSpinner spinner_1 = new JSpinner();
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_1.insets = new Insets(0, 0, 5, 0);
		gbc_spinner_1.gridx = 1;
		gbc_spinner_1.gridy = 1;
		pnlX.add(spinner_1, gbc_spinner_1);
		
		JLabel lblSteps = new JLabel("Steps:");
		GridBagConstraints gbc_lblSteps = new GridBagConstraints();
		gbc_lblSteps.anchor = GridBagConstraints.WEST;
		gbc_lblSteps.insets = new Insets(0, 0, 0, 5);
		gbc_lblSteps.gridx = 0;
		gbc_lblSteps.gridy = 2;
		pnlX.add(lblSteps, gbc_lblSteps);
		
		JSpinner spinner_2 = new JSpinner();
		GridBagConstraints gbc_spinner_2 = new GridBagConstraints();
		gbc_spinner_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_2.gridx = 1;
		gbc_spinner_2.gridy = 2;
		pnlX.add(spinner_2, gbc_spinner_2);
		
		JPanel pnlY = new JPanel();
		pnlY.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Y-Axis", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pnlY = new GridBagConstraints();
		gbc_pnlY.insets = new Insets(5, 5, 5, 5);
		gbc_pnlY.fill = GridBagConstraints.BOTH;
		gbc_pnlY.gridx = 0;
		gbc_pnlY.gridy = 1;
		panel.add(pnlY, gbc_pnlY);
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
		
		JSpinner spinner_3 = new JSpinner();
		GridBagConstraints gbc_spinner_3 = new GridBagConstraints();
		gbc_spinner_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_3.insets = new Insets(0, 0, 5, 0);
		gbc_spinner_3.gridx = 1;
		gbc_spinner_3.gridy = 0;
		pnlY.add(spinner_3, gbc_spinner_3);
		
		JLabel lblMaximum_1 = new JLabel("Maximum:");
		GridBagConstraints gbc_lblMaximum_1 = new GridBagConstraints();
		gbc_lblMaximum_1.anchor = GridBagConstraints.WEST;
		gbc_lblMaximum_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaximum_1.gridx = 0;
		gbc_lblMaximum_1.gridy = 1;
		pnlY.add(lblMaximum_1, gbc_lblMaximum_1);
		
		JSpinner spinner_4 = new JSpinner();
		GridBagConstraints gbc_spinner_4 = new GridBagConstraints();
		gbc_spinner_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_4.insets = new Insets(0, 0, 5, 0);
		gbc_spinner_4.gridx = 1;
		gbc_spinner_4.gridy = 1;
		pnlY.add(spinner_4, gbc_spinner_4);
		
		JLabel lblSteps_1 = new JLabel("Steps:");
		GridBagConstraints gbc_lblSteps_1 = new GridBagConstraints();
		gbc_lblSteps_1.anchor = GridBagConstraints.WEST;
		gbc_lblSteps_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblSteps_1.gridx = 0;
		gbc_lblSteps_1.gridy = 2;
		pnlY.add(lblSteps_1, gbc_lblSteps_1);
		
		JSpinner spinner_5 = new JSpinner();
		GridBagConstraints gbc_spinner_5 = new GridBagConstraints();
		gbc_spinner_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_5.gridx = 1;
		gbc_spinner_5.gridy = 2;
		pnlY.add(spinner_5, gbc_spinner_5);
		
		JPanel pnlButton = new JPanel();
		pnlButton.setLayout(null);
		GridBagConstraints gbc_pnlButton = new GridBagConstraints();
		gbc_pnlButton.insets = new Insets(0, 5, 0, 0);
		gbc_pnlButton.fill = GridBagConstraints.BOTH;
		gbc_pnlButton.gridx = 0;
		gbc_pnlButton.gridy = 2;
		panel.add(pnlButton, gbc_pnlButton);
		
		JButton btnOk = new JButton("Ok");
		btnOk.setBounds(183, 4, 89, 22);
		pnlButton.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(83, 4, 89, 22);
		pnlButton.add(btnCancel);
	}

	//================================================================================
    // Public Functions
    //================================================================================
	public void show() {
		// TODO Auto-generated method stub
		dialog.setVisible(true);
	}
}
