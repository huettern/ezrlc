package ezrlc.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import ezrlc.MVC.Controller;
import ezrlc.Plot.Figure;
import ezrlc.Plot.Figure.ENPlotType;

/**
 * Work panel on the right side of the frame
 * 
 * @author noah
 *
 */
public class WorkPanel extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;

	// ================================================================================
	// Public Variables
	// ================================================================================
	public enum ViewType {
		FIGURE, IGASSIST
	};

	// ================================================================================
	// Local Variables
	// ================================================================================
	private Controller controller;

	private enum WINDOW_ARR {
		NONE, FULL, SPLIT
	}

	private WINDOW_ARR graphArr = WINDOW_ARR.NONE;

	private Figure topFigure, bottomFigure, fullFigure;

	private JPanel pnlFigure;
	private IGAssistPanel pnlIGAssist;

	// ================================================================================
	// Constructors
	// ================================================================================
	/**
	 * Create new work panel
	 * 
	 * @param controller
	 *            controller object
	 */
	public WorkPanel(Controller controller) {
		this.controller = controller;

		this.setBorder(null);

		this.setLayout(new GridLayout(1, 1));
	}

	/**
	 * Builds the work panel
	 */
	public void build() {
		this.setPreferredSize(new Dimension(800, 600));
		this.setBorder(new LineBorder(Color.LIGHT_GRAY));

		pnlFigure = new JPanel();
		pnlFigure.setLayout(new GridLayout(1, 1));
		pnlIGAssist = new IGAssistPanel(controller);
		pnlIGAssist.setBorder(new LineBorder(Color.WHITE, 10));
		pnlIGAssist.build();
		this.setView(ViewType.IGASSIST);
	}

	// ================================================================================
	// Public Functions
	// ================================================================================
	/**
	 * Adds a new Figure to the figure panel
	 * 
	 * @param type plot type
	 */
	public void addGraph(ENPlotType type) {
		Figure f = new Figure(this.controller);

		if (type == ENPlotType.RECTANGULAR) {
			f.buildRectPlot();
		} else if (type == ENPlotType.SMITH) {
			f.buildSmithChart();
		}

		// switch Graph arrangement
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
			break;
		}

		this.setView(ViewType.FIGURE);
	}

	/**
	 * Updates the containing figures
	 * 
	 * @param o
	 *            model
	 * @param arg
	 *            arguments
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
		pnlIGAssist.update(o, arg);
	}

	/**
	 * Delete a figure
	 * 
	 * @param figure figure object
	 */
	public void deleteFigure(Figure figure) {
		this.remove(figure);
		if (figure == fullFigure) {
			fullFigure = null;
			graphArr = WINDOW_ARR.NONE;
			pnlFigure.setLayout(new GridLayout(1, 1));
			pnlFigure.removeAll();
		} else if (figure == topFigure) {
			fullFigure = bottomFigure;
			topFigure = null;
			bottomFigure = null;

			pnlFigure.removeAll();
			pnlFigure.setLayout(new GridLayout(1, 1));
			pnlFigure.add(fullFigure, 0);
			graphArr = WINDOW_ARR.FULL;
		} else if (figure == bottomFigure) {
			fullFigure = topFigure;
			topFigure = null;
			bottomFigure = null;

			pnlFigure.removeAll();
			pnlFigure.setLayout(new GridLayout(1, 1));
			pnlFigure.add(fullFigure, 0);
			graphArr = WINDOW_ARR.FULL;
		}

		this.updateUI();
	}

	/**
	 * Set View Type of the WorkPanel
	 * 
	 * @param t view type
	 */
	public void setView(ViewType t) {
		if (t == ViewType.FIGURE) {
			this.remove(pnlIGAssist);
			this.add(pnlFigure, 0);
		} else if (t == ViewType.IGASSIST) {
			this.remove(pnlFigure);
			this.add(pnlIGAssist, 0);
		}
		this.updateUI();
	}

	/**
	 * Creat the data sets of the IGAssist
	 */
	public void buildIGAssistDataSet() {
		pnlIGAssist.createDatasets();
	}

}
