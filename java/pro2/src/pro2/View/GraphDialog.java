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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;

import pro2.MVC.Controller;

import java.awt.Insets;

public class GraphDialog implements ActionListener{
	//================================================================================
    // Local Variables
    //================================================================================
	private Controller controller;
	
	JDialog graphDialog;
	private JTextField txtGrahpName;
	private JButton btnCreate, btnCancel;
	
	
	//================================================================================
    // Constructors
    //================================================================================
	public GraphDialog(Controller controller) {
		this.controller = controller;
	}

	
	//================================================================================
    // Public Functions
    //================================================================================
	/**
	 * Builds the Graph Panel
	 */
	public void buildDialog() {
		graphDialog = new JDialog(controller.getMainView());
		graphDialog.setResizable(false);
		graphDialog.setTitle("New Graph");		
		graphDialog.setModal(true);
		graphDialog.setLocation(250, 150);
		graphDialog.setSize(300, 350);
		graphDialog.getContentPane().setLayout(null);
		
		setGraphOptions();
		
		graphDialog.setVisible(true);
	}
	
	
	//================================================================================
    // Private Functions
    //================================================================================
	/**
	 * Set all Buttons, Labels, Texfields
	 */
	private void setGraphOptions() {
		// Tab
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setToolTipText("");
		tabbedPane.setBounds(0, 0, 294, 322);
		graphDialog.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(0, 0));
		tabbedPane.addTab("New Graph", null, panel, null);
		tabbedPane.setEnabledAt(0, true);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {27, 130, 130, 27};
		gbl_panel.rowHeights = new int[] {30, 0, 30, 0, 165};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		panel.setLayout(gbl_panel);
		
		// Label "Enter a name for the Graph:"
		JLabel lblEnterAName = new JLabel("Enter a name for the Graph:");
		GridBagConstraints gbc_lblEnterAName = new GridBagConstraints();
		gbc_lblEnterAName.gridwidth = 2;
		gbc_lblEnterAName.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblEnterAName.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterAName.gridx = 1;
		gbc_lblEnterAName.gridy = 0;
		panel.add(lblEnterAName, gbc_lblEnterAName);
		
		// TextField of the name from the Graph
		txtGrahpName = new JTextField();
		txtGrahpName.setText("Graph 1");
		GridBagConstraints gbc_txtGrahpName = new GridBagConstraints();
		gbc_txtGrahpName.gridwidth = 2;
		gbc_txtGrahpName.insets = new Insets(0, 0, 5, 5);
		gbc_txtGrahpName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtGrahpName.gridx = 1;
		gbc_txtGrahpName.gridy = 1;
		panel.add(txtGrahpName, gbc_txtGrahpName);
		txtGrahpName.setColumns(10);
		
		// Label "Select the desired type:"
		JLabel lblSelectTheDesired = new JLabel("Select the desired type:");
		GridBagConstraints gbc_lblSelectTheDesired = new GridBagConstraints();
		gbc_lblSelectTheDesired.gridwidth = 2;
		gbc_lblSelectTheDesired.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblSelectTheDesired.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectTheDesired.gridx = 1;
		gbc_lblSelectTheDesired.gridy = 2;
		panel.add(lblSelectTheDesired, gbc_lblSelectTheDesired);
		
		// RadioButton "Rectangular"
		JRadioButton rdbtnRectangular = new JRadioButton("Rectangular");
		rdbtnRectangular.setSelected(true);
		GridBagConstraints gbc_rdbtnRectangular = new GridBagConstraints();
		gbc_rdbtnRectangular.anchor = GridBagConstraints.WEST;
		gbc_rdbtnRectangular.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnRectangular.gridx = 1;
		gbc_rdbtnRectangular.gridy = 3;
		panel.add(rdbtnRectangular, gbc_rdbtnRectangular);
		
		// Buttons Create and Cancel
		btnCreate = new JButton("Create");
		GridBagConstraints gbc_btnCreate = new GridBagConstraints();
		gbc_btnCreate.anchor = GridBagConstraints.SOUTH;
		gbc_btnCreate.insets = new Insets(0, 0, 0, 5);
		gbc_btnCreate.gridx = 1;
		gbc_btnCreate.gridy = 4;
		panel.add(btnCreate, gbc_btnCreate);
		btnCreate.addActionListener(this);
		
		btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.anchor = GridBagConstraints.SOUTH;
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 2;
		gbc_btnCancel.gridy = 4;
		panel.add(btnCancel, gbc_btnCancel);
		btnCancel.addActionListener(this);
		
		//y-Achse Z,
		//x-Achse Freuqenz, real, imagin�r
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnCreate) {
			this.controller.getMainView().addGraph(txtGrahpName.getText());
			graphDialog.dispose();
		}
		if(e.getSource() == btnCancel) {
			graphDialog.dispose();
		}
	}

}
