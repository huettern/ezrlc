package pro2.View;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import pro2.Plot.RectPlot.RectangularPlot;

public class IGAssistPanel extends JPanel {

	//================================================================================
    // Settings
    //================================================================================
	private final int topVGap = 5;
	private final int topHGap = 5;

	private final int subVGap = 2;
	private final int subHGap = 2;
	
	private final String[] topTitles = {"RL Series","RL Parallel","RC Series","RC Parallel"};
	
	//================================================================================
    // Private data
    //================================================================================
	private static final long serialVersionUID = 1L;

	private JPanel[] topoPanels = new JPanel[4];
	
	private RectangularPlot[] plots = new RectangularPlot[8];
	
	
	//================================================================================
    // Constructors
    //================================================================================
	public IGAssistPanel() {
		this.setLayout(new GridLayout(2, 2, topVGap, topHGap));
		this.setBackground(Color.WHITE);
		
	}

	//================================================================================
    // Public methods
    //================================================================================
	/**
	 * Builds the content
	 */
	public void build () {
		// Init 4 top panels
		for(int i = 0; i < 4; i++) {
			topoPanels[i] = new JPanel();
			topoPanels[i].setLayout(new GridLayout(2, 1, subVGap, subHGap));
			plots[i*2] = new RectangularPlot();
			plots[i*2].setBorder(new LineBorder(new Color(0, 0, 0)));
			plots[i*2+1] = new RectangularPlot();
			plots[i*2+1].setBorder(new LineBorder(new Color(0, 0, 0)));
			topoPanels[i].add(plots[i*2], 0);
			topoPanels[i].add(plots[i*2+1], 1);
			
			// set border
//			topoPanels[i].setBorder(new LineBorder(Color.BLACK));
			topoPanels[i].setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), topTitles[i], TitledBorder.CENTER, TitledBorder.TOP, null, null) );
			this.add(topoPanels[i], i);
		}
		
		this.updateUI();
	}

}
