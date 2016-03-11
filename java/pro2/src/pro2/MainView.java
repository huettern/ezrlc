package pro2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JList;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Frame;

public class MainView extends JFrame implements ActionListener {

	private Controller controller;
	private JFileChooser openFileChooser = new JFileChooser();
	private DefaultMutableTreeNode fileTreeTop = new DefaultMutableTreeNode("Input Files");
	
	// Storing the Files in a MenuItem List and a second one containing the UIDs
	private List<UUID> fileTreeItemsUID = new ArrayList<UUID>();   
	
	//local Variable
	private JButton btnLoadFile, btnNewModel, btnNewPlot;
	private JLabel lblInputFile;
	private File file;
	
	/**
	 * Create the frame.
	 */
	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1261, 774);
		
		
		// First, setup the menu bar
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem fileNew = new JMenuItem("New File");
		JMenuItem fileOpen = new JMenuItem("Open File");
		JMenuItem fileSave = new JMenuItem("Save File");
		JMenuItem fileExit = new JMenuItem("Exit");
		fileMenu.add(fileNew);
		fileMenu.add(fileOpen);
		fileMenu.add(fileSave);
		fileMenu.add(fileExit);
		
		// Now the main panel
		JPanel mainPanel = new JPanel();
		mainPanel.setMinimumSize(new Dimension(400, 200));
		getContentPane().add(mainPanel);
		mainPanel.setBackground(new Color(100, 0, 0));
		mainPanel.setLayout(new BorderLayout(0, 5));
		
		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(Color.PINK);
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		contentPanel.add(splitPane);
		
		JPanel navPanel = new JPanel();
		navPanel.setBorder(null);
		navPanel.setMinimumSize(new Dimension(200, 200));
		splitPane.setLeftComponent(navPanel);
		GridBagLayout gbl_navPanel = new GridBagLayout();
		gbl_navPanel.columnWidths = new int[] {0, 0};
		gbl_navPanel.rowHeights = new int[] {0, 0, 0};
		gbl_navPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_navPanel.rowWeights = new double[]{0.0, 0.0, 1.0};
		navPanel.setLayout(gbl_navPanel);
		
		JPanel pnlInFile = new JPanel();
		pnlInFile.setMaximumSize(new Dimension(0, 0));
		pnlInFile.setPreferredSize(new Dimension(200, 85));
		pnlInFile.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Input File", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pnlInFile = new GridBagConstraints();
		gbc_pnlInFile.anchor = GridBagConstraints.NORTH;
		gbc_pnlInFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlInFile.insets = new Insets(5, 0, 5, 0);
		gbc_pnlInFile.gridx = 0;
		gbc_pnlInFile.gridy = 0;
		navPanel.add(pnlInFile, gbc_pnlInFile);
		GridBagLayout gbl_pnlInFile = new GridBagLayout();
		gbl_pnlInFile.columnWidths = new int[]{189, 0};
		gbl_pnlInFile.rowHeights = new int[] {29, 29, 0};
		gbl_pnlInFile.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_pnlInFile.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		pnlInFile.setLayout(gbl_pnlInFile);
		
		file = null;
		lblInputFile = new JLabel("");
		lblInputFile.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_lblInputFile = new GridBagConstraints();
		gbc_lblInputFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblInputFile.insets = new Insets(0, 0, 5, 0);
		gbc_lblInputFile.gridx = 0;
		gbc_lblInputFile.gridy = 0;
		pnlInFile.add(lblInputFile, gbc_lblInputFile);
		
		btnLoadFile = new JButton("Load File...");
		GridBagConstraints gbc_btnLoadFile = new GridBagConstraints();
		gbc_btnLoadFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLoadFile.gridx = 0;
		gbc_btnLoadFile.gridy = 1;
		pnlInFile.add(btnLoadFile, gbc_btnLoadFile);
		btnLoadFile.addActionListener(this);
		
		JPanel pnlPanel = new JPanel();
		pnlPanel.setMaximumSize(new Dimension(0, 0));
		pnlPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Plot", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlPanel = new GridBagConstraints();
		gbc_pnlPanel.anchor = GridBagConstraints.NORTH;
		gbc_pnlPanel.insets = new Insets(0, 0, 5, 0);
		gbc_pnlPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlPanel.gridx = 0;
		gbc_pnlPanel.gridy = 1;
		navPanel.add(pnlPanel, gbc_pnlPanel);
		GridBagLayout gbl_pnlPanel = new GridBagLayout();
		gbl_pnlPanel.columnWidths = new int[] {52};
		gbl_pnlPanel.rowHeights = new int[] {29};
		gbl_pnlPanel.columnWeights = new double[]{1.0};
		gbl_pnlPanel.rowWeights = new double[]{0.0};
		pnlPanel.setLayout(gbl_pnlPanel);
		
		btnNewPlot = new JButton("New Plot");
		GridBagConstraints gbc_btnNewPlot = new GridBagConstraints();
		gbc_btnNewPlot.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewPlot.gridx = 0;
		gbc_btnNewPlot.gridy = 0;
		pnlPanel.add(btnNewPlot, gbc_btnNewPlot);
		btnNewPlot.addActionListener(this);
		
		
		JPanel pnlModel = new JPanel();
		pnlModel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Models", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlModel = new GridBagConstraints();
		gbc_pnlModel.insets = new Insets(0, 0, 5, 0);
		gbc_pnlModel.anchor = GridBagConstraints.NORTH;
		gbc_pnlModel.fill = GridBagConstraints.BOTH;
		gbc_pnlModel.gridx = 0;
		gbc_pnlModel.gridy = 2;
		navPanel.add(pnlModel, gbc_pnlModel);
		GridBagLayout gbl_pnlModel = new GridBagLayout();
		gbl_pnlModel.columnWidths = new int[]{189, 0};
		gbl_pnlModel.rowHeights = new int[] {0, 29, 0};
		gbl_pnlModel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_pnlModel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		pnlModel.setLayout(gbl_pnlModel);
		
		JList lstModles = new JList();
		lstModles.setPreferredSize(new Dimension(200, 300));
		lstModles.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_lstModles = new GridBagConstraints();
		gbc_lstModles.fill = GridBagConstraints.BOTH;
		gbc_lstModles.insets = new Insets(0, 0, 5, 0);
		gbc_lstModles.gridx = 0;
		gbc_lstModles.gridy = 0;
		pnlModel.add(lstModles, gbc_lstModles);
		
		btnNewModel = new JButton("New Model");
		GridBagConstraints gbc_btnNewModel = new GridBagConstraints();
		gbc_btnNewModel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewModel.gridx = 0;
		gbc_btnNewModel.gridy = 1;
		pnlModel.add(btnNewModel, gbc_btnNewModel);
		btnNewModel.addActionListener(this);
		
		JPanel workPanel = new JPanel();
		workPanel.setPreferredSize(new Dimension(800, 600));
		workPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		splitPane.setRightComponent(workPanel);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		mainPanel.add(toolBar, BorderLayout.NORTH);
		
		JButton btnSave = new JButton("Save");
		toolBar.add(btnSave);
		
		JPanel statusPanel = new JPanel();
		statusPanel.setBackground(Color.GREEN);
		mainPanel.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel statusLabel = new JLabel("Status Here....");
		statusPanel.add(statusLabel);
		
		// Window size
		pack();
		setMinimumSize(getPreferredSize());
		//setExtendedState(JFrame.MAXIMIZED_BOTH);

		
		
//		JMenuBar menuBar = new JMenuBar();
//		GridBagConstraints gbc = new GridBagConstraints();
//		mainPanel.add(menuBar, gbc);
		
	}
	
	/**
	 * Adds a new entry to the input file list
	 * @param name
	 * @param uid
	 */
	public void addFileListItem(String name, UUID uid) {
		
		DefaultMutableTreeNode item = new DefaultMutableTreeNode(name);
		this.fileTreeTop.add(item);
	//	DefaultTreeModel model = (DefaultTreeModel)fileTree.getModel();
	//	model.reload();

		this.fileTreeItemsUID.add(uid);
	}
	
	/**
	 * Sets the controller object
	 * @param controller
	 */
	public void setController (Controller controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//handle the file chooser
		if(e.getSource() == btnLoadFile) {
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "Touchstone File Format", "s1p", "z1p", "y1p");
			openFileChooser.setFileFilter(filter);
			int returnVal = openFileChooser.showDialog(null, "Open File");
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
	            file = openFileChooser.getSelectedFile();
				lblInputFile.setText(file.getName());
			}
		}
		
		//handle new Plots
		if(e.getSource() == btnNewPlot) {	
		}
		
		//handle new Model
		if(e.getSource() == btnNewModel) {
		}
		
	}
}


