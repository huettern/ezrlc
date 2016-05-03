package pro2.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import pro2.MVC.Controller;
import pro2.Plot.Figure;
import pro2.Plot.Figure.ENPlotType;

public class WorkPanel extends JPanel implements Observer {
	
	//================================================================================
    // Local Variables
    //================================================================================
	private Controller controller;
	private enum WINDOW_ARR {NONE, FULL, SPLIT}
	private WINDOW_ARR graphArr = WINDOW_ARR.NONE;
	private Figure topFigure, bottomFigure, fullFigure;

	//================================================================================
    // Constructors
    //================================================================================
	public WorkPanel(Controller controller) {
		this.controller = controller;

		this.setBorder(null);
		
		GridBagLayout gbl_workPanel = new GridBagLayout();
		gbl_workPanel.columnWidths = new int[] {0};
		gbl_workPanel.rowHeights = new int[] {0, 0};
		gbl_workPanel.columnWeights = new double[]{1.0};
		gbl_workPanel.rowWeights = new double[]{1.0, 1.0};
		this.setLayout(gbl_workPanel);
	}
	
	/**
	 * Builds the work panel
	 */
	public void build () {
		this.setPreferredSize(new Dimension(800, 600));
		this.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		//Figure fig = new Figure("Graph 2");
		//this.add(fig);
	}
	
	//================================================================================
    // Public Functions
    //================================================================================
	public void addGraph(ENPlotType type, String text) {
		Figure f = new Figure(this.controller, text);
		
		if(type == ENPlotType.RECTANGULAR) {
			f.buildRectPlot();
		}
		else if (type == ENPlotType.SMITH) {
			f.buildSmithChart();
		}
		
		switch (graphArr) {
		case NONE: 
			fullFigure = f;
			this.add(fullFigure, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0, 
					GridBagConstraints.NORTH, GridBagConstraints.BOTH, 
					new Insets(0, 0, 0, 0), 0, 0));
			graphArr = WINDOW_ARR.FULL;
			break;
		case FULL:
			topFigure = fullFigure;
			fullFigure = null;
			this.add(topFigure, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 
					GridBagConstraints.NORTH, GridBagConstraints.BOTH, 
					new Insets(0, 0, 0, 0), 0, 0));
			
			bottomFigure = f;
			this.add(bottomFigure, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, 
					GridBagConstraints.NORTH, GridBagConstraints.BOTH, 
					new Insets(0, 0, 0, 0), 0, 0));
			graphArr = WINDOW_ARR.SPLIT;
			break;
		case SPLIT:
			System.err.println("Es k�nnen nicht mehr als zwei Plots geopfert werden!");
			break;
		}
		

		this.updateUI();
	}

	@Override
	public void update(Observable o, Object arg) {
		try {
			fullFigure.update(o, arg);
		} catch (Exception e) {
		}
		
		try {
			topFigure.update(o, arg);
		} catch (Exception e) {
		}
		
		try {
			bottomFigure.update(o, arg);
		} catch (Exception e) {
		}		
	}

	public void deleteFigure(Figure figure) {
		this.remove(figure);
		if(figure == fullFigure) {
			fullFigure = null;
			graphArr = WINDOW_ARR.NONE;
			System.out.println("delete Full-Graph");
		}
		else if (figure == topFigure) {
			fullFigure = bottomFigure;
			topFigure = null;
			bottomFigure = null;
			
			this.add(fullFigure, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0, 
					GridBagConstraints.NORTH, GridBagConstraints.BOTH, 
					new Insets(0, 0, 0, 0), 0, 0));
			graphArr = WINDOW_ARR.FULL;
			System.out.println("delete Top-Graph");
		}
		else if (figure == bottomFigure) {
			fullFigure = topFigure;
			topFigure = null;
			bottomFigure = null;
			
			this.add(fullFigure, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0, 
					GridBagConstraints.NORTH, GridBagConstraints.BOTH, 
					new Insets(0, 0, 0, 0), 0, 0));
			graphArr = WINDOW_ARR.FULL;
			System.out.println("delete Bottom-Graph");
		}
		
		this.updateUI();
	}


}
