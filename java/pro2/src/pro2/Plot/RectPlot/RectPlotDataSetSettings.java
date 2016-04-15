package pro2.Plot.RectPlot;

import java.awt.Color;
import java.awt.color.ColorSpace;

public class RectPlotDataSetSettings {

	//================================================================================
    // Private Data
    //================================================================================
	private Color lineColor = new Color(0,0,0);
	private String label = "";
	
	//================================================================================
    // Constructors
    //================================================================================
	public RectPlotDataSetSettings() {
		// TODO Auto-generated constructor stub
	}

	//================================================================================
    // Public Functions
    //================================================================================
	public void setLineColor(Color c) {
		this.lineColor=c;
	}
	public Color getLineColor () {
		return this.lineColor;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	

	
}
