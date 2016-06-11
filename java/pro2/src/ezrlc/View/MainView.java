package ezrlc.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import com.apple.eawt.Application;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

import ezrlc.Controller.Controller;
import ezrlc.Plot.Figure;
import ezrlc.Plot.Figure.ENPlotType;
import ezrlc.View.WorkPanel.ViewType;
import ezrlc.util.UIUtil;

/**
 * Implements the view functionality of the MVC framework
 * 
 * @author noah
 *
 */
public class MainView extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	// ================================================================================
	// Local Variables
	// ================================================================================
	private NavPanel navPanel;
	private WorkPanel workPanel;

	private Controller controller;
	private JSplitPane splitPane = new JSplitPane();

	private static enum LAF {
		METAL, OCEAN, SYSTEM, NIMROD
	}

	private static LAF laf = LAF.SYSTEM;

	// ================================================================================
	// Constructors
	// ================================================================================
	public MainView() {
	}

	// ================================================================================
	// Public Functions
	// ================================================================================
	/**
	 * Create the frame from NanPanel and WorkPanel.
	 */
	public void build() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 402);

		// MainPanel
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

		// Add navPanel & workPanel
		navPanel = new NavPanel(this.controller);
		navPanel.setPreferredSize(new Dimension(200, 10));
		splitPane.setLeftComponent(navPanel);
		navPanel.build();
		workPanel = new WorkPanel(this.controller);
		splitPane.setRightComponent(workPanel);
		workPanel.build();

		// Window properties
		setTitle("EZRLC");
		ImageIcon icon = UIUtil.loadResourceIcon("pro2LogoTransparent.png");
		this.setIconImage(icon.getImage());
		
		// For Apple
		Application.getApplication().setDockIconImage(UIUtil.loadResourceImage("pro2LogoTransparent.png"));
		System.setProperty("apple.laf.useScreenMenuBar", "true");
    	System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Test");
    	
		pack();
		setMinimumSize(getPreferredSize());

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
				// UIManager.setLookAndFeel(new
				// MyNimRODLookAndFeel("DarkGray.theme"));
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
	 * 
	 * @param controller controller object
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * added Graph to WorkPanel
	 * 
	 * @param type plot type
	 */
	public void addGraph(ENPlotType type) {
		workPanel.addGraph(type);
		navPanel.setViewButtonStatus(ViewType.FIGURE);
	}

	/**
	 * Set name of the file
	 * 
	 * @param name name
	 */
	public void setFileName(String name) {
		navPanel.setFileName(name);

	}

	/**
	 * Gets called if model notifies Observers
	 * 
	 * @param o model object
	 * @param arg arguments
	 */
	@Override
	public void update(Observable o, Object arg) {
		workPanel.update(o, arg);
		navPanel.update(o, arg);

	}

	/**
	 * delete Figure at WorkPanel
	 * 
	 * @param figure figure object
	 */
	public void deleteFigure(Figure figure) {
		workPanel.deleteFigure(figure);

	}

	/**
	 * Sets the status of the new graph button
	 * 
	 * @param b
	 *            enabled(true) or disabled(flase)
	 */
	public void setNewGraphButtonEnabled(boolean b) {
		navPanel.setNewGraphButtonEnabled(b);
	}

	/**
	 * Set the work panel view to the given type
	 * 
	 * @param t
	 *            ViewType
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
