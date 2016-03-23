package pro2.Plot;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class RectangularPlot extends JPanel {

	//================================================================================
    // Private Data
    //================================================================================
	private Axis horAxis;
	private Axis verAxis;

	//================================================================================
    // Constructors
    //================================================================================
	public RectangularPlot() {
		// TODO Auto-generated constructor stub
		
		System.out.println("New Rect Plot");

		// Origin of the plot
		Point origin = new Point(30,30); 
		
		// Add Axis
		horAxis = new Axis(this, Axis.Orientation.HORIZONTAL, origin);
		horAxis.setMinimum(0);
		horAxis.setMaximum(100);
		horAxis.setStep(10);
		horAxis.setLabelOffset(20);
		verAxis = new Axis(this, Axis.Orientation.VERTICAL, origin);
		verAxis.setMinimum(0);
		verAxis.setMaximum(1);
		verAxis.setStep(10);
		verAxis.setLabelOffset(-30);
		
		// Add Grid
		
		
		repaint();
	}
	
	
	@Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        horAxis.paint(g);
        verAxis.paint(g);
    }

}
