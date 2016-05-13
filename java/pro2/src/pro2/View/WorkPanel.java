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
    // Public Variables
    //================================================================================
	public enum ViewType { FIGURE, IGASSIST };
	
	//================================================================================
    // Local Variables
    //================================================================================
	private Controller controller;
	private enum WINDOW_ARR {NONE, FULL, SPLIT}
	private WINDOW_ARR graphArr = WINDOW_ARR.NONE;
	
	private Figure topFigure, bottomFigure, fullFigure;

	private JPanel pnlFigure, pnlIGAssist;
	
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
		
		pnlFigure = new JPanel();
		pnlFigure.setLayout(new GridLayout(1, 1));
		pnlIGAssist = new JPanel();
		pnlIGAssist.setBackground(Color.GREEN);
	}
	
	//================================================================================
    // Public Functions
    //================================================================================
	/**
	 * Adds a new Figure to the figure panel
	 * @param type
	 */
	public void addGraph(ENPlotType type) {
		Figure f = new Figure(this.controller);
		
		if(type == ENPlotType.RECTANGULAR) {
			f.buildRectPlot();
		}
		else if (type == ENPlotType.SMITH) {
			f.buildSmithChart();
		}
		
		switch (graphArr) {
			case NONE:
				pnlFigure.setLayout(new GridLayout(1, 1));
				fullFigure = f;
				pnlFigure.add(fullFigure, 0);
				graphArr = WINDOW_ARR.FULL;
				break;
			case FULL:
				pnlFigure.setLayout(new GridLayout(2, 1));
				topFigure = fullFigure;
				fullFigure = null;
				pnlFigure.add(topFigure, 0);
				
				bottomFigure = f;
				pnlFigure.add(bottomFigure, 1);
				graphArr = WINDOW_ARR.SPLIT;
				
				// notify controller to disable new graph button
				controller.setNewGraphButtonEnabled(false);
				break;
			case SPLIT:
				System.err.println("Es k�nnen nicht mehr als zwei Plots geopfert werden!");
				break;
		}
		

		this.updateUI();
	}

	/**
	 * Updates the containing figures
	 * @param o model
	 * @param arg arguments
	 */
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

	/**
	 * Delete a figure
	 * @param figure
	 */
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
	
	public void setView (ViewType t) {
		if(t == ViewType.FIGURE) {
			this.remove(pnlIGAssist);
			this.add(pnlFigure,0);
		} else if(t == ViewType.IGASSIST) {
			this.remove(pnlFigure);
			this.add(pnlIGAssist,0);
		}
	}


}
