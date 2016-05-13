package pro2.View;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import pro2.Plot.RectPlot.RectangularPlot;
import pro2.util.UIUtil;

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
	
	private RectangularPlot[] plots = new RectangularPlot[8];
	
	private ImageIcon[] topoImgs = new ImageIcon[4];

	private final int[] plotXLoc = {1,2,1,2,1,2,1,2};
	private final int[] plotYLoc = {0,0,1,1,2,2,3,3};
	
	//================================================================================
    // Constructors
    //================================================================================
	public IGAssistPanel() {
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		this.setBackground(Color.WHITE);
	}

	//================================================================================
    // Public methods
    //================================================================================
	/**
	 * Builds the content
	 */
	public void build () {
		// Load images
		topoImgs[0] = UIUtil.loadResourceIcon("model_0.png");
		topoImgs[1] = UIUtil.loadResourceIcon("model_1.png");
		topoImgs[2] = UIUtil.loadResourceIcon("model_2.png");
		topoImgs[3] = UIUtil.loadResourceIcon("model_3.png");
		
		// place images
		JLabel label = new JLabel("", topoImgs[0], JLabel.CENTER);
		label.setOpaque(false);
		this.add(label, new GridBagConstraints(0, 0, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0,0,0,0), 0, 0));
		label = new JLabel("", topoImgs[1], JLabel.CENTER);
		label.setOpaque(false);
		this.add(label, new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0,0,0,0), 0, 0));
		label = new JLabel("", topoImgs[2], JLabel.CENTER);
		label.setOpaque(false);
		this.add(label, new GridBagConstraints(0, 2, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0,0,0,0), 0, 0));
		label = new JLabel("", topoImgs[3], JLabel.CENTER);
		label.setOpaque(false);
		this.add(label, new GridBagConstraints(0, 3, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0,0,0,0), 0, 0));
		
		// create plots
		for(int i = 0; i < 8; i++) {
			plots[i] = new RectangularPlot();
			plots[i].setBorder(new LineBorder(new Color(0, 0, 0)));
			this.add(plots[i], new GridBagConstraints(plotXLoc[i], plotYLoc[i], 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3,0,3,3), 0, 0));
		}
		
//		// Init 4 top panels
//		for(int i = 0; i < 4; i++) {
//			topoPanels[i] = new JPanel();
//			topoPanels[i].setLayout(new GridLayout(2, 1, subVGap, subHGap));
//			plots[i*2] = new RectangularPlot();
//			plots[i*2].setBorder(new LineBorder(new Color(0, 0, 0)));
//			plots[i*2+1] = new RectangularPlot();
//			plots[i*2+1].setBorder(new LineBorder(new Color(0, 0, 0)));
//			topoPanels[i].add(plots[i*2], 0);
//			topoPanels[i].add(plots[i*2+1], 1);
//			
//			// set border
////			topoPanels[i].setBorder(new LineBorder(Color.BLACK));
//			topoPanels[i].setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), topTitles[i], TitledBorder.CENTER, TitledBorder.TOP, null, null) );
//			this.add(topoPanels[i], i);
//		}
//		
		this.updateUI();
	}

}
