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
import javax.swing.JList;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class MainView extends JFrame implements ActionListener {

	private Controller controller;
	private JButton btnLoadDataFile;
	private JFileChooser openFileChooser;
	
	private JTree fileTree;
	private DefaultMutableTreeNode fileTreeTop = new DefaultMutableTreeNode("Input Files");
	
	// Storing the Files in a MenuItem List and a second one containing the UIDs
	private List<UUID> fileTreeItemsUID = new ArrayList<UUID>();   
	
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
		getContentPane().add(mainPanel);
		mainPanel.setBackground(new Color(100, 0, 0));
		mainPanel.setLayout(new BorderLayout(0, 5));
		
		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(Color.PINK);
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		contentPanel.add(splitPane);
		
		JPanel navPanel = new JPanel();
		navPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		navPanel.setMinimumSize(new Dimension(200, 200));
		splitPane.setLeftComponent(navPanel);
		GridBagLayout gbl_navPanel = new GridBagLayout();
		gbl_navPanel.columnWidths = new int[]{79, 0};
		gbl_navPanel.rowHeights = new int[]{0, 76, 0, 0, 0, 0};
		gbl_navPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_navPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		navPanel.setLayout(gbl_navPanel);
		
		JLabel lblInputFiles = new JLabel("Input Files");
		GridBagConstraints gbc_lblInputFiles = new GridBagConstraints();
		gbc_lblInputFiles.insets = new Insets(10, 5, 5, 5);
		gbc_lblInputFiles.gridx = 0;
		gbc_lblInputFiles.gridy = 0;
		navPanel.add(lblInputFiles, gbc_lblInputFiles);
		
		fileTree = new JTree(fileTreeTop);
		fileTree.setPreferredSize(new Dimension(79, 200));
		fileTree.setMaximumSize(new Dimension(79, 79));
		fileTree.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_fileTree = new GridBagConstraints();
		gbc_fileTree.fill = GridBagConstraints.HORIZONTAL;
		gbc_fileTree.insets = new Insets(5, 5, 5, 0);
		gbc_fileTree.weightx = 1.0;
		gbc_fileTree.anchor = GridBagConstraints.NORTHWEST;
		gbc_fileTree.gridx = 0;
		gbc_fileTree.gridy = 1;
		navPanel.add(fileTree, gbc_fileTree);
		fileTree.setRootVisible(false); 
		
		btnLoadDataFile = new JButton("Load Data File");
		btnLoadDataFile.addActionListener(this);
		GridBagConstraints gbc_btnLoadDataFile = new GridBagConstraints();
		gbc_btnLoadDataFile.insets = new Insets(0, 5, 5, 0);
		gbc_btnLoadDataFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLoadDataFile.weightx = 1.0;
		gbc_btnLoadDataFile.gridx = 0;
		gbc_btnLoadDataFile.gridy = 2;
		navPanel.add(btnLoadDataFile, gbc_btnLoadDataFile);
		
		JLabel lblModels = new JLabel("Models");
		GridBagConstraints gbc_lblModels = new GridBagConstraints();
		gbc_lblModels.insets = new Insets(10, 0, 5, 0);
		gbc_lblModels.gridx = 0;
		gbc_lblModels.gridy = 3;
		navPanel.add(lblModels, gbc_lblModels);
		
		JList modelList = new JList();
		modelList.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_modelList = new GridBagConstraints();
		gbc_modelList.insets = new Insets(0, 5, 0, 0);
		gbc_modelList.fill = GridBagConstraints.BOTH;
		gbc_modelList.gridx = 0;
		gbc_modelList.gridy = 4;
		navPanel.add(modelList, gbc_modelList);
		
		JPanel workPanel = new JPanel();
		workPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		workPanel.setMinimumSize(new Dimension(200, 200));
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
		DefaultTreeModel model = (DefaultTreeModel)fileTree.getModel();
		model.reload();

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
		// check source
		if(e.getSource() == btnLoadDataFile) {
			System.out.println("btnLoadDataFile");
			openFileChooser = new JFileChooser();
			int returnVal = openFileChooser.showOpenDialog(this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = openFileChooser.getSelectedFile();
	            this.controller.loadFile(file);
	        } else {
	            System.out.println("Open command cancelled by user.");
	        }	
		}
	}
}
