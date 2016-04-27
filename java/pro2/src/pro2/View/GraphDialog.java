package pro2.View;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;

import pro2.MVC.Controller;
import pro2.Plot.Figure.ENPlotType;

import java.awt.Insets;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;

public class GraphDialog implements ActionListener{

	//================================================================================
    // Local Variables
    //================================================================================
	private Controller controller;
	
	private JDialog graphDialog;
	private ButtonGroup btngrpGraphSelect;
	private JButton btnCreate, btnCancel;
	private JTextField txtGraph;

	private JRadioButton rdbtnRectangular;

	private JRadioButton rdbtnSmithChart;
	private JLabel lblZ0;
	private JTextField txtResistance;
	private JLabel lblOhm;
	
	
	//================================================================================
    // Constructors
	public GraphDialog(Controller controller) {
		this.controller = controller;
	}

	
	//================================================================================
    // Public Functions
    //================================================================================
	/**
	 * Builds the Graph Panel
	 * @wbp.parser.entryPoint
	 */
	public void buildDialog() {
		graphDialog = new JDialog(controller.getMainView());
		graphDialog.setResizable(false);
		graphDialog.setTitle("New Graph");		
		graphDialog.setModal(true);
		graphDialog.setLocation(250, 150);
		graphDialog.setSize(300, 350);
		
		//Main Panel
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		graphDialog.getContentPane().setLayout(gridBagLayout);
		
		JPanel pnlGraphName = new JPanel();
		pnlGraphName.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Graph Name", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pnlGraphName = new GridBagConstraints();
		gbc_pnlGraphName.insets = new Insets(5, 5, 0, 5);
		gbc_pnlGraphName.fill = GridBagConstraints.BOTH;
		gbc_pnlGraphName.gridx = 0;
		gbc_pnlGraphName.gridy = 0;
		graphDialog.getContentPane().add(pnlGraphName, gbc_pnlGraphName);
		GridBagLayout gbl_pnlGraphName = new GridBagLayout();
		gbl_pnlGraphName.columnWidths = new int[]{0, 0};
		gbl_pnlGraphName.rowHeights = new int[]{0, 0, 0};
		gbl_pnlGraphName.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnlGraphName.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		pnlGraphName.setLayout(gbl_pnlGraphName);
		
		
		//Graph type
		JPanel pnlSelectType = new JPanel();
		pnlSelectType.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Graph Type", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlSelectType = new GridBagConstraints();
		gbc_pnlSelectType.insets = new Insets(5, 5, 0, 5);
		gbc_pnlSelectType.fill = GridBagConstraints.BOTH;
		gbc_pnlSelectType.gridx = 0;
		gbc_pnlSelectType.gridy = 1;
		graphDialog.getContentPane().add(pnlSelectType, gbc_pnlSelectType);
		GridBagLayout gbl_pnlSelectType = new GridBagLayout();
		gbl_pnlSelectType.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_pnlSelectType.rowHeights = new int[]{0, 0, 0, 0};
		gbl_pnlSelectType.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_pnlSelectType.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlSelectType.setLayout(gbl_pnlSelectType);
		
		JLabel lblEnterAName = new JLabel("Enter a name for the Graph:");
		GridBagConstraints gbc_lblEnterAName = new GridBagConstraints();
		gbc_lblEnterAName.anchor = GridBagConstraints.WEST;
		gbc_lblEnterAName.insets = new Insets(5, 5, 5, 5);
		gbc_lblEnterAName.gridx = 0;
		gbc_lblEnterAName.gridy = 0;
		pnlGraphName.add(lblEnterAName, gbc_lblEnterAName);
		
		txtGraph = new JTextField();
		txtGraph.setText("Graph 1");
		GridBagConstraints gbc_txtGraph = new GridBagConstraints();
		gbc_txtGraph.insets = new Insets(0, 5, 5, 5);
		gbc_txtGraph.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGraph.gridx = 0;
		gbc_txtGraph.gridy = 1;
		pnlGraphName.add(txtGraph, gbc_txtGraph);
		txtGraph.setColumns(10);
		
				
		//Graph Select
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
		
		
		//Buttons
		JPanel pnlButtons = new JPanel();
		GridBagConstraints gbc_pnlButtons = new GridBagConstraints();
		gbc_pnlButtons.insets = new Insets(5, 5, 5, 5);
		gbc_pnlButtons.fill = GridBagConstraints.BOTH;
		gbc_pnlButtons.gridx = 0;
		gbc_pnlButtons.gridy = 2;
		graphDialog.getContentPane().add(pnlButtons, gbc_pnlButtons);
		GridBagLayout gbl_pnlButtons = new GridBagLayout();
		gbl_pnlButtons.columnWidths = new int[]{0, 0, 0};
		gbl_pnlButtons.rowHeights = new int[]{0, 0};
		gbl_pnlButtons.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlButtons.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlButtons.setLayout(gbl_pnlButtons);
		
		btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.fill = GridBagConstraints.BOTH;
		gbc_btnCancel.insets = new Insets(0, 2, 0, 5);
		gbc_btnCancel.gridx = 0;
		gbc_btnCancel.gridy = 0;
		pnlButtons.add(btnCancel, gbc_btnCancel);
		btnCancel.addActionListener(this);
		
		btnCreate = new JButton("Create");
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.insets = new Insets(0, 5, 0, 2);
		gbc_btnCreate.fill = GridBagConstraints.BOTH;
		gbc_btnCreate.gridx = 1;
		gbc_btnCreate.gridy = 0;
		pnlButtons.add(btnCreate, gbc_btnCreate);
		btnCreate.addActionListener(this);
		
		
		graphDialog.setVisible(true);
	}


	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnCreate) {
			if(rdbtnRectangular.isSelected() == true) {
				this.controller.getMainView().addGraph(ENPlotType.RECTANGULAR, txtGraph.getText());
			}
			else if(rdbtnSmithChart.isSelected() == true) {
				this.controller.getMainView().addGraph(ENPlotType.SMITH, txtGraph.getText());
			}
			graphDialog.dispose();
		}
		
		if(e.getSource() == btnCancel) {
			graphDialog.dispose();
		}	
		
	}

}
