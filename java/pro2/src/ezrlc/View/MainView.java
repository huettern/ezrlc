package ezrlc.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
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

import ezrlc.MVC.Controller;
import ezrlc.Plot.Figure;
import ezrlc.Plot.Figure.ENPlotType;
import ezrlc.View.WorkPanel.ViewType;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import java.awt.Frame;

public class MainView extends JFrame implements Observer {
	
	//================================================================================
    // Local Variables
    //================================================================================
	private NavPanel navPanel;
	private WorkPanel workPanel;
	
	private Controller controller;
	private DefaultMutableTreeNode fileTreeTop = new DefaultMutableTreeNode("Input Files");
	private JSplitPane splitPane = new JSplitPane();
	
	// Storing the Files in a MenuItem List and a second one containing the UIDs
	private List<UUID> fileTreeItemsUID = new ArrayList<UUID>();   
	
	private static enum LAF {
		METAL, OCEAN, SYSTEM, NIMROD
	}

	private static LAF laf = LAF.SYSTEM;


	//================================================================================
    // Constructors
    //================================================================================
	public MainView() {
	
	}
	
	//================================================================================
    // Public Functions
    //================================================================================
	/**
	 * Create the frame from NanPanel and WorkPanel.
	 * @wbp.parser.entryPoint
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
		navPanel.setPreferredSize(new Dimension(200, 10));
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
		
		// Look and feel
		try {
			switch (laf) {
				case METAL:
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					break;
				case OCEAN:
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					MetalLookAndFeel.setCurrentTheme(new OceanTheme());
					break;
				case SYSTEM:
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					break;
				case NIMROD:
					//UIManager.setLookAndFeel(new MyNimRODLookAndFeel("DarkGray.theme"));
					break;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		if (laf != LAF.SYSTEM) {
			setUndecorated(true);
			getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		}
		
	}
	
	
	/**
	 * Sets the controller object
	 * @param controller
	 */
	public void setController (Controller controller) {
		this.controller = controller;
	}

	/**
	 * added Graph to WorkPanel
	 * @param type
	 */
	public void addGraph(ENPlotType type) {
		// TODO Auto-generated method stub
		workPanel.addGraph(type);
		navPanel.setViewButtonStatus(ViewType.FIGURE);
	}
	
	/**
	 * Set name of the file
	 * @param name
	 */
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
		workPanel.update(o, arg);
		navPanel.update(o, arg);
		
	}
	/**
	 * delete Figure at WorkPanel
	 * @param figure
	 */
	public void deleteFigure(Figure figure) {
		workPanel.deleteFigure(figure);
		
	}

	/**
	 * Sets the status of the new graph button
	 * @param b enabled(true) or disabled(flase)
	 */
	public void setNewGraphButtonEnabled(boolean b) {
		navPanel.setNewGraphButtonEnabled(b);
	}

	/**
	 * Set the work panel view to the given type
	 * @param t ViewType
	 */
	public void setWorkPanelView(ViewType t) {
		workPanel.setView(t);
		navPanel.setViewButtonStatus(t);
	}
	public void buildIGAssistDataSet() {
		workPanel.buildIGAssistDataSet();
	}
	public void setupEqCircuitView() {
		navPanel.setupEqCircuitView();
	}

	
}


