package pro2.Plot;

import java.awt.Graphics;

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

		horAxis = new Axis(this, Axis.Orientation.HORIZONTAL, 30, 30);
		horAxis.setMinimum(0);
		horAxis.setMaximum(100);
		horAxis.setStep(10);
		horAxis.setLabelOffset(20);
		verAxis = new Axis(this, Axis.Orientation.VERTICAL, 30, 30);
		verAxis.setMinimum(0);
		verAxis.setMaximum(1);
		verAxis.setStep(10);
		verAxis.setLabelOffset(-30);
		
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
