package pro2;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class WorkPanel extends JPanel {
	
	//================================================================================
    // Constructors
    //================================================================================
	public void WorkPanel() {

	}
	
	/**
	 * Builds the work panel
	 */
	public void build () {
		this.setPreferredSize(new Dimension(800, 600));
		this.setBorder(new LineBorder(Color.LIGHT_GRAY));	
		
	}


}
