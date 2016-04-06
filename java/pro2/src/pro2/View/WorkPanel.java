package pro2.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import pro2.MVC.Controller;
import pro2.Plot.Figure;

public class WorkPanel extends JPanel {
	
	//================================================================================
    // Local Variables
    //================================================================================
	private Controller controller;
	
	private List<Figure> figures = new ArrayList<Figure>();

	//================================================================================
    // Constructors
    //================================================================================
	public WorkPanel(Controller controller) {
		this.controller = controller;

		this.setBorder(null);
		
		GridBagLayout gbl_workPanel = new GridBagLayout();
		gbl_workPanel.columnWidths = new int[] {0};
		gbl_workPanel.rowHeights = new int[] {0};
		gbl_workPanel.columnWeights = new double[]{1.0};
		gbl_workPanel.rowWeights = new double[]{0.0};
		this.setLayout(gbl_workPanel);
	}
	
	/**
	 * Builds the work panel
	 */
	public void build () {
		this.setPreferredSize(new Dimension(800, 600));
		this.setBorder(new LineBorder(Color.LIGHT_GRAY));	
		
	}
	
	//================================================================================
    // Public Functions
    //================================================================================
	public void addGraph(String text) {
		Figure f = new Figure(this.controller, text);
		
		this.figures.add(f);
		this.add(figures.get(figures.size()-1), new GridBagConstraints(0, 0, 1, 1, 1, 1, 
				GridBagConstraints.NORTH, GridBagConstraints.BOTH, 
				new Insets(0, 0, 0, 0), 0, 0));
		this.updateUI();
	}


}
