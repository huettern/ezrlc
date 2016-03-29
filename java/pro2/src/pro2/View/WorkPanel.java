package pro2.View;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class WorkPanel extends JPanel {
	
	//================================================================================
    // Local Variables
    //================================================================================
	private MainView mainView;
	

	//================================================================================
    // Constructors
    //================================================================================
	public WorkPanel(MainView mainView) {
		this.mainView = mainView;
	}
	
	/**
	 * Builds the work panel
	 */
	public void build () {
		this.setPreferredSize(new Dimension(800, 600));
		this.setBorder(new LineBorder(Color.LIGHT_GRAY));	
		
	}


}
