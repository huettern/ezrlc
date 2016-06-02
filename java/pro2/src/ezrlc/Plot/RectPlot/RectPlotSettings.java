package ezrlc.Plot.RectPlot;

import ezrlc.Plot.RectPlot.Axis.Scale;

/**
 * Stores the plot settings
 * @author noah
 *
 */
public class RectPlotSettings {

	// ================================================================================
	// Public Data
	// ================================================================================
	public double xAxisMinimum = 0;
	public double xAxisMaximum = 0;
	public int xAxisSteps = 1;
	public Scale xScale = Scale.LINEAR;

	public double yAxisMinimum = 0;
	public double yAxisMaximum = 0;
	public int yAxisSteps = 1;
	public Scale yScale = Scale.LINEAR;

	// ================================================================================
	// Constructors
	// ================================================================================

	public RectPlotSettings() {

	}

}
