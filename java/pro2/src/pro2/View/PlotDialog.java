package pro2.View;

import javax.swing.JDialog;

public class PlotDialog {

	//================================================================================
    // Local Variables
    //================================================================================
	private MainView mainView;
	JDialog plotDialog;
	
	
	//================================================================================
    // Constructors
    //================================================================================
	public PlotDialog(MainView mainView) {
		this.mainView = mainView;
		
	}

	
	public void buildDialog() {
		plotDialog = new JDialog(this.mainView, "New Plot");
		plotDialog.setLocation(250, 150);
		plotDialog.setSize(300, 300);
		plotDialog.setVisible(true);
		
	}
	
	private void setPlotOptions() {
		//JRadioButton birdButton = new JRadioButton(birdString);
	}

}
