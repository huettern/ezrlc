package pro2.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
		
		this.setLayout(new GridLayout(1, 1));
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
				this.setLayout(new GridLayout(1, 1));
				fullFigure = f;
				this.add(fullFigure, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0, 
						GridBagConstraints.NORTH, GridBagConstraints.BOTH, 
						new Insets(0, 0, 0, 0), 0, 0));
				graphArr = WINDOW_ARR.FULL;
				break;
			case FULL:
				this.setLayout(new GridLayout(2, 1));
				topFigure = fullFigure;
				fullFigure = null;
				this.add(topFigure, 0);
				
				bottomFigure = f;
				this.add(bottomFigure, 1);
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
			this.setLayout(new GridLayout(1, 1));
			System.out.println("delete Full-Graph");
		}
		else if (figure == topFigure) {
			fullFigure = bottomFigure;
			topFigure = null;
			bottomFigure = null;
			
			this.setLayout(new GridLayout(1, 1));
			this.add(fullFigure, 0);
			graphArr = WINDOW_ARR.FULL;
			System.out.println("delete Top-Graph");
		}
		else if (figure == bottomFigure) {
			fullFigure = topFigure;
			topFigure = null;
			bottomFigure = null;
			
			this.setLayout(new GridLayout(1, 1));
			this.add(fullFigure, 0);
			graphArr = WINDOW_ARR.FULL;
			System.out.println("delete Bottom-Graph");
		}
		
		this.updateUI();
	}


}
