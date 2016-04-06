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
		dialog.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setToolTipText("");
		tabbedPane.setBounds(0, 0, 294, 322);
		dialog.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(0, 0));
		tabbedPane.addTab("Axis", null, panel, null);
		tabbedPane.setEnabledAt(0, true);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.rowHeights = new int[] {20, 20, 20, 20, 20, 20};
		gbl_panel.columnWidths = new int[] {130, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panel.setLayout(gbl_panel);
		
		JLabel lblXMin = new JLabel("X Min");
		GridBagConstraints gbc_lblXMin = new GridBagConstraints();
		gbc_lblXMin.insets = new Insets(0, 0, 5, 5);
		gbc_lblXMin.gridx = 0;
		gbc_lblXMin.gridy = 0;
		panel.add(lblXMin, gbc_lblXMin);
		
		JSpinner spinner = new JSpinner();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 0;
		panel.add(spinner, gbc_spinner);
		
		JLabel lblXMax = new JLabel("X Max");
		GridBagConstraints gbc_lblXMax = new GridBagConstraints();
		gbc_lblXMax.insets = new Insets(0, 0, 5, 5);
		gbc_lblXMax.gridx = 0;
		gbc_lblXMax.gridy = 1;
		panel.add(lblXMax, gbc_lblXMax);
		
		JSpinner spinner_1 = new JSpinner();
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_1.insets = new Insets(0, 0, 5, 0);
		gbc_spinner_1.gridx = 1;
		gbc_spinner_1.gridy = 1;
		panel.add(spinner_1, gbc_spinner_1);
		
		JLabel lblXSteps = new JLabel("X Steps");
		GridBagConstraints gbc_lblXSteps = new GridBagConstraints();
		gbc_lblXSteps.insets = new Insets(0, 0, 5, 5);
		gbc_lblXSteps.gridx = 0;
		gbc_lblXSteps.gridy = 2;
		panel.add(lblXSteps, gbc_lblXSteps);
		
		JSpinner spinner_2 = new JSpinner();
		GridBagConstraints gbc_spinner_2 = new GridBagConstraints();
		gbc_spinner_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_2.insets = new Insets(0, 0, 5, 0);
		gbc_spinner_2.gridx = 1;
		gbc_spinner_2.gridy = 2;
		panel.add(spinner_2, gbc_spinner_2);
		
		JLabel lblYMin = new JLabel("Y Min");
		GridBagConstraints gbc_lblYMin = new GridBagConstraints();
		gbc_lblYMin.insets = new Insets(0, 0, 5, 5);
		gbc_lblYMin.gridx = 0;
		gbc_lblYMin.gridy = 3;
		panel.add(lblYMin, gbc_lblYMin);
		
		JSpinner spinner_3 = new JSpinner();
		GridBagConstraints gbc_spinner_3 = new GridBagConstraints();
		gbc_spinner_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_3.insets = new Insets(0, 0, 5, 0);
		gbc_spinner_3.gridx = 1;
		gbc_spinner_3.gridy = 3;
		panel.add(spinner_3, gbc_spinner_3);
		
		JLabel lblYMax = new JLabel("Y Max");
		GridBagConstraints gbc_lblYMax = new GridBagConstraints();
		gbc_lblYMax.insets = new Insets(0, 0, 5, 5);
		gbc_lblYMax.gridx = 0;
		gbc_lblYMax.gridy = 4;
		panel.add(lblYMax, gbc_lblYMax);
		
		JSpinner spinner_4 = new JSpinner();
		GridBagConstraints gbc_spinner_4 = new GridBagConstraints();
		gbc_spinner_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_4.insets = new Insets(0, 0, 5, 0);
		gbc_spinner_4.gridx = 1;
		gbc_spinner_4.gridy = 4;
		panel.add(spinner_4, gbc_spinner_4);
		
		JLabel lblYSteps = new JLabel("Y Steps");
		GridBagConstraints gbc_lblYSteps = new GridBagConstraints();
		gbc_lblYSteps.insets = new Insets(0, 0, 0, 5);
		gbc_lblYSteps.gridx = 0;
		gbc_lblYSteps.gridy = 5;
		panel.add(lblYSteps, gbc_lblYSteps);
		
		JSpinner spinner_5 = new JSpinner();
		GridBagConstraints gbc_spinner_5 = new GridBagConstraints();
		gbc_spinner_5.weightx = 1.0;
		gbc_spinner_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner_5.gridx = 1;
		gbc_spinner_5.gridy = 5;
		panel.add(spinner_5, gbc_spinner_5);
	}

	//================================================================================
    // Public Functions
    //================================================================================
	public void show() {
		// TODO Auto-generated method stub
		dialog.setVisible(true);
	}

}
