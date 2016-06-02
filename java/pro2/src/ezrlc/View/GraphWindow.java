package ezrlc.View;

import java.awt.Color;
import java.awt.Dimension;
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

import ezrlc.MVC.Controller;
import ezrlc.Plot.Figure.ENPlotType;

/**
 * New Graph dialog to choose between rectangular or smith plot
 * @author noah
 *
 */
public class GraphWindow implements ActionListener {

	// ================================================================================
	// Local Variables
	// ================================================================================
	private Controller controller;

	private JDialog graphDialog;
	private ButtonGroup btngrpGraphSelect;
	private JButton btnCreate, btnCancel;

	private JRadioButton rdbtnRectangular;

	private JRadioButton rdbtnSmithChart;

	// ================================================================================
	// Constructors
	// ================================================================================
	/**
	 * New Graph window
	 * @param controller
	 */
	public GraphWindow(Controller controller) {
		this.controller = controller;
	}

	// ================================================================================
	// Public Functions
	// ================================================================================
	/**
	 * Builds the Graph Panel
	 * 
	 */
	public void buildDialog() {
		graphDialog = new JDialog(controller.getMainView());
		graphDialog.getContentPane().setPreferredSize(new Dimension(300, 180));
		graphDialog.setResizable(false);
		graphDialog.setTitle("New Graph");
		graphDialog.setModal(true);
		graphDialog.setLocation(250, 150);
		graphDialog.setSize(300, 200);

		// Main Panel
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		graphDialog.getContentPane().setLayout(gridBagLayout);

		// Graph type
		JPanel pnlSelectType = new JPanel();
		pnlSelectType.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Graph Type", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlSelectType = new GridBagConstraints();
		gbc_pnlSelectType.insets = new Insets(5, 5, 5, 5);
		gbc_pnlSelectType.fill = GridBagConstraints.BOTH;
		gbc_pnlSelectType.gridx = 0;
		gbc_pnlSelectType.gridy = 0;
		graphDialog.getContentPane().add(pnlSelectType, gbc_pnlSelectType);
		GridBagLayout gbl_pnlSelectType = new GridBagLayout();
		gbl_pnlSelectType.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_pnlSelectType.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_pnlSelectType.columnWeights = new double[] { 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_pnlSelectType.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlSelectType.setLayout(gbl_pnlSelectType);

		// Graph Select
		JLabel lblSelectTheDesired = new JLabel("Select the desired type:");
		GridBagConstraints gbc_lblSelectTheDesired = new GridBagConstraints();
		gbc_lblSelectTheDesired.anchor = GridBagConstraints.WEST;
		gbc_lblSelectTheDesired.insets = new Insets(5, 5, 5, 5);
		gbc_lblSelectTheDesired.gridx = 0;
		gbc_lblSelectTheDesired.gridy = 0;
		pnlSelectType.add(lblSelectTheDesired, gbc_lblSelectTheDesired);

		rdbtnRectangular = new JRadioButton("Rectangular");
		rdbtnRectangular.setSelected(true);
		GridBagConstraints gbc_rdbtnRectangular = new GridBagConstraints();
		gbc_rdbtnRectangular.anchor = GridBagConstraints.WEST;
		gbc_rdbtnRectangular.insets = new Insets(0, 5, 5, 5);
		gbc_rdbtnRectangular.gridx = 0;
		gbc_rdbtnRectangular.gridy = 1;
		pnlSelectType.add(rdbtnRectangular, gbc_rdbtnRectangular);
		rdbtnRectangular.addActionListener(this);

		btngrpGraphSelect = new ButtonGroup();
		btngrpGraphSelect.add(rdbtnRectangular);

		rdbtnSmithChart = new JRadioButton("Smith Chart");
		GridBagConstraints gbc_rdbtnSmithChart = new GridBagConstraints();
		gbc_rdbtnSmithChart.insets = new Insets(0, 5, 0, 5);
		gbc_rdbtnSmithChart.anchor = GridBagConstraints.WEST;
		gbc_rdbtnSmithChart.gridx = 0;
		gbc_rdbtnSmithChart.gridy = 2;
		pnlSelectType.add(rdbtnSmithChart, gbc_rdbtnSmithChart);
		btngrpGraphSelect.add(rdbtnSmithChart);
		rdbtnSmithChart.addActionListener(this);

		// Buttons
		JPanel pnlButtons = new JPanel();
		GridBagConstraints gbc_pnlButtons = new GridBagConstraints();
		gbc_pnlButtons.insets = new Insets(5, 5, 0, 0);
		gbc_pnlButtons.fill = GridBagConstraints.BOTH;
		gbc_pnlButtons.gridx = 0;
		gbc_pnlButtons.gridy = 1;
		graphDialog.getContentPane().add(pnlButtons, gbc_pnlButtons);
		GridBagLayout gbl_pnlButtons = new GridBagLayout();
		gbl_pnlButtons.columnWidths = new int[] { 0, 0, 0 };
		gbl_pnlButtons.rowHeights = new int[] { 0, 0 };
		gbl_pnlButtons.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_pnlButtons.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pnlButtons.setLayout(gbl_pnlButtons);

		btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.fill = GridBagConstraints.BOTH;
		gbc_btnCancel.insets = new Insets(0, 2, 5, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 0;
		pnlButtons.add(btnCancel, gbc_btnCancel);
		btnCancel.addActionListener(this);

		btnCreate = new JButton("Create");
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.insets = new Insets(0, 5, 5, 6);
		gbc_btnCreate.fill = GridBagConstraints.BOTH;
		gbc_btnCreate.gridx = 1;
		gbc_btnCreate.gridy = 0;
		pnlButtons.add(btnCreate, gbc_btnCreate);
		btnCreate.addActionListener(this);

		graphDialog.getRootPane().setDefaultButton(btnCreate);
		graphDialog.pack();
		graphDialog.setVisible(true);
	}

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCreate) {
			if (rdbtnRectangular.isSelected() == true) {
				this.controller.getMainView().addGraph(ENPlotType.RECTANGULAR);
			} else if (rdbtnSmithChart.isSelected() == true) {
				this.controller.getMainView().addGraph(ENPlotType.SMITH);
			}
			graphDialog.dispose();
		}

		if (e.getSource() == btnCancel) {
			graphDialog.dispose();
		}

	}

}
