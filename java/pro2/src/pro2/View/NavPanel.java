package pro2.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import pro2.MVC.Controller;

public class NavPanel extends JPanel implements ActionListener, Observer {
	
	//================================================================================
    // Local Variables
    //================================================================================
	private Controller controller;
	
	private FileChooser fileChooser;
	private GraphDialog GraphDialog;
	
	private JButton btnLoadFile, btnNewModel, btnNewGraph;
	private JLabel lblInputFile;
	private File file;
	
	
	//================================================================================
    // Constructors
    //================================================================================
	public NavPanel(Controller controller) {
		this.controller = controller;	
		this.fileChooser = new FileChooser(controller);
		this.GraphDialog = new GraphDialog(controller);
	}
		
	
	//================================================================================
    // Public Functions
    //================================================================================
	/**
	 * Builds the navigation Panel
	 */
	public void build() {
		this.setBorder(null);
		this.setMinimumSize(new Dimension(200, 200));
		
		GridBagLayout gbl_navPanel = new GridBagLayout();
		gbl_navPanel.columnWidths = new int[] {0, 0};
		gbl_navPanel.rowHeights = new int[] {0, 0, 0};
		gbl_navPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_navPanel.rowWeights = new double[]{0.0, 0.0, 1.0};
		this.setLayout(gbl_navPanel);
		
		this.buildFilePanel();
		this.buildGraphPanel();
		this.buildModelPanel();
	}
	
	
	//================================================================================
    // Private Functions
    //================================================================================
	/**
	 * build the input file panel
	 */
	private void buildFilePanel() {	
		JPanel pnlInFile = new JPanel();
		pnlInFile.setMaximumSize(new Dimension(0, 0));
		pnlInFile.setPreferredSize(new Dimension(200, 85));
		pnlInFile.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Input File", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlInFile = new GridBagConstraints();
		gbc_pnlInFile.anchor = GridBagConstraints.NORTH;
		gbc_pnlInFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlInFile.insets = new Insets(5, 0, 0, 0);
		gbc_pnlInFile.gridx = 0;
		gbc_pnlInFile.gridy = 0;
		this.add(pnlInFile, gbc_pnlInFile);
		GridBagLayout gbl_pnlInFile = new GridBagLayout();
		gbl_pnlInFile.columnWidths = new int[]{189};
		gbl_pnlInFile.rowHeights = new int[] {29};
		gbl_pnlInFile.columnWeights = new double[]{1.0};
		gbl_pnlInFile.rowWeights = new double[]{0.0};
		pnlInFile.setLayout(gbl_pnlInFile);
		
		//File Label
		file = null;
		lblInputFile = new JLabel("");
		lblInputFile.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_lblInputFile = new GridBagConstraints();
		gbc_lblInputFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblInputFile.insets = new Insets(0, 4, 0, 0);
		gbc_lblInputFile.gridx = 0;
		gbc_lblInputFile.gridy = 0;
		pnlInFile.add(lblInputFile, gbc_lblInputFile);
		
		//LoadFile Button
		btnLoadFile = new JButton("Load File...");
		GridBagConstraints gbc_btnLoadFile = new GridBagConstraints();
		gbc_btnLoadFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLoadFile.insets = new Insets(4, 4, 4, 4);
		gbc_btnLoadFile.gridx = 0;
		gbc_btnLoadFile.gridy = 1;
		pnlInFile.add(btnLoadFile, gbc_btnLoadFile);
		btnLoadFile.addActionListener(this);
		
		fileChooser.fileFilter();
	}
		
	/**
	 * build the Graph panel
	 */
	private void buildGraphPanel() {
		JPanel pnlPanel = new JPanel();
		pnlPanel.setMaximumSize(new Dimension(0, 0));
		pnlPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Graph", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlPanel = new GridBagConstraints();
		gbc_pnlPanel.anchor = GridBagConstraints.NORTH;
		gbc_pnlPanel.insets = new Insets(5, 0, 0, 0);
		gbc_pnlPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlPanel.gridx = 0;
		gbc_pnlPanel.gridy = 1;
		this.add(pnlPanel, gbc_pnlPanel);
		GridBagLayout gbl_pnlPanel = new GridBagLayout();
		gbl_pnlPanel.columnWidths = new int[] {52};
		gbl_pnlPanel.rowHeights = new int[] {29};
		gbl_pnlPanel.columnWeights = new double[]{1.0};
		gbl_pnlPanel.rowWeights = new double[]{0.0};
		pnlPanel.setLayout(gbl_pnlPanel);
		
		//New Graph Button
		btnNewGraph = new JButton("New Graph");
		//btnNewGraph.setPreferredSize(new Dimension(87, 23));
		//btnNewGraph.setMinimumSize(new Dimension(87, 23));
		//btnNewGraph.setMaximumSize(new Dimension(87, 23));
		GridBagConstraints gbc_btnNewGraph = new GridBagConstraints();
		gbc_btnNewGraph.insets = new Insets(4, 4, 4, 4);
		gbc_btnNewGraph.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewGraph.gridx = 0;
		gbc_btnNewGraph.gridy = 0;
		pnlPanel.add(btnNewGraph, gbc_btnNewGraph);
		btnNewGraph.addActionListener(this);
	}
		
	/**
	 * build the model panel
	 */
	private void buildModelPanel() {
		JPanel pnlModel = new JPanel();
		pnlModel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Models", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlModel = new GridBagConstraints();
		gbc_pnlModel.insets = new Insets(5, 0, 5, 0);
		gbc_pnlModel.anchor = GridBagConstraints.NORTH;
		gbc_pnlModel.fill = GridBagConstraints.BOTH;
		gbc_pnlModel.gridx = 0;
		gbc_pnlModel.gridy = 2;
		this.add(pnlModel, gbc_pnlModel);
		GridBagLayout gbl_pnlModel = new GridBagLayout();
		gbl_pnlModel.columnWidths = new int[]{189};
		gbl_pnlModel.rowHeights = new int[] {29};
		gbl_pnlModel.columnWeights = new double[]{1.0};
		gbl_pnlModel.rowWeights = new double[]{1.0};
		pnlModel.setLayout(gbl_pnlModel);
		
		//Model list
		JList lstModles = new JList();
		lstModles.setPreferredSize(new Dimension(200, 300));
		lstModles.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_lstModles = new GridBagConstraints();
		gbc_lstModles.fill = GridBagConstraints.BOTH;
		gbc_lstModles.insets = new Insets(4, 4, 4, 4);
		gbc_lstModles.gridx = 0;
		gbc_lstModles.gridy = 0;
		pnlModel.add(lstModles, gbc_lstModles);
		
		//New Model Button
		btnNewModel = new JButton("New Model");
		//btnNewModel.setPreferredSize(new Dimension(87, 23));
		//btnNewModel.setMinimumSize(new Dimension(87, 23));
		//btnNewModel.setMaximumSize(new Dimension(87, 23));
		GridBagConstraints gbc_btnNewModel = new GridBagConstraints();
		gbc_btnNewModel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewModel.insets = new Insets(4, 4, 4, 4);
		gbc_btnNewModel.gridx = 0;
		gbc_btnNewModel.gridy = 1;
		pnlModel.add(btnNewModel, gbc_btnNewModel);
		btnNewModel.addActionListener(this);
	}
	

	//================================================================================
    // Public Functions
    //================================================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//handle the file chooser
		if(e.getSource() == btnLoadFile) {
			lblInputFile.setText(fileChooser.windowFileChooser());
			File f = fileChooser.getFile();
			if (f != null) {
				controller.loadFile(f);
			}	
		}
		
		//handle new Graphs
		if(e.getSource() == btnNewGraph) {	
			GraphDialog.buildDialog();
		}
		
		//handle new Model
		if(e.getSource() == btnNewModel) {
		}
		
	}


	/**
	 * Sets the file name label
	 * @param name
	 */
	public void setFileName(String name) {
		lblInputFile.setText(name);
	}


	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
}
