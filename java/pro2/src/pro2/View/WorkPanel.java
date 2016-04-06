package pro2.View;

import java.awt.Color;
import java.awt.Dimension;
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
		this.figures.add(new Figure(text));
		this.add(figures.get(figures.size()-1));
		this.updateUI();
	}


}
