package pro2.View;

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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
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
import java.awt.Image;

import javax.swing.border.LineBorder;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JList;
import javax.swing.tree.DefaultTreeModel;

import pro2.MVC.Controller;
import pro2.Plot.Figure;
import pro2.Plot.Figure.ENPlotType;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Frame;

public class MainView extends JFrame implements Observer {
	
	private NavPanel navPanel;
	private WorkPanel workPanel;
	
	private Controller controller;
	private DefaultMutableTreeNode fileTreeTop = new DefaultMutableTreeNode("Input Files");
	private JSplitPane splitPane = new JSplitPane();
	
	// Storing the Files in a MenuItem List and a second one containing the UIDs
	private List<UUID> fileTreeItemsUID = new ArrayList<UUID>();   
	


	//================================================================================
    // Constructors
    //================================================================================
	public MainView() {
	
	}
	//================================================================================
    // Public Functions
    //================================================================================
	/**
	 * Create the frame.
	 */
	public void build () {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 402);		
		
		//MainPanel
		JPanel mainPanel = new JPanel();
		mainPanel.setMinimumSize(new Dimension(400, 200));
		getContentPane().add(mainPanel);
		mainPanel.setBackground(new Color(100, 0, 0));
		mainPanel.setLayout(new BorderLayout(0, 5));
		
		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(Color.PINK);
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		splitPane.setEnabled(false);
		contentPanel.add(splitPane);
		
		//Add navPanel & workPanel
		navPanel = new NavPanel(this.controller);
		splitPane.setLeftComponent(navPanel);
		navPanel.build();
		workPanel = new WorkPanel(this.controller);
		splitPane.setRightComponent(workPanel);
		workPanel.build();
		
		// Window properties
		setTitle("EZRLC");
		//ImageIcon icon = new ImageIcon(MainView.class.getResource("pro2LogoTransparent.png"));
		//setIconImage(icon.getImage());
		
		pack();
		setMinimumSize(getPreferredSize());
	//	setExtendedState(JFrame.MAXIMIZED_BOTH);
		
	}
	
	
	/**
	 * Sets the controller object
	 * @param controller
	 */
	public void setController (Controller controller) {
		this.controller = controller;
	}

	
	public void addGraph(ENPlotType type, String text) {
		// TODO Auto-generated method stub
		workPanel.addGraph(type, text);
	}
	
	public void addNewFile(File f) {
		// TODO Auto-generated method stub
		this.controller.loadFile(f);
		
	}
	public void setFileName(String name) {
		navPanel.setFileName(name);
		
	}
	
	/**
	 * Gets called if model notifies Observers
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Main View update!");
		workPanel.update(o, arg);
		navPanel.update(o, arg);
		
	}
	public void deleteFigure(Figure figure) {
		workPanel.deleteFigure(figure);
		
	}

	
}


