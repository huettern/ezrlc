package pro2.View;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JTabbedPane;


public class PlotDialog extends JFrame {

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
		buildDialog();
		
	}

	
	public void buildDialog() {
		plotDialog = new JDialog(this.mainView, "New Plot", true);

		setPlotOptions();			
		
		
		plotDialog.setModal(true);
		plotDialog.setLocation(250, 150);
		plotDialog.setSize(300, 300);	
		plotDialog.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 284, 262);
		plotDialog.getContentPane().add(tabbedPane);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("select Plot", null, tabbedPane_1, null);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Axis", null, tabbedPane_2, null);
		plotDialog.setVisible(true);
	}
	
	private void setPlotOptions() {
		
		//y-Achse Z,
		//x-Achse Freuqenz, real, imaginär

	}
}
