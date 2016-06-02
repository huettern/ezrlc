package ezrlc.Plot;

import java.awt.Color;

/**
 * Data set settings
 * @author noah
 *
 */
public class DataSetSettings {

	// ================================================================================
	// Private Data
	// ================================================================================
	private Color lineColor = new Color(0, 0, 0);
	private String labelName = "";
	private String labelType = "";

	// ================================================================================
	// Constructors
	// ================================================================================
	public DataSetSettings() {
	}

	// ================================================================================
	// Public Functions
	// ================================================================================
	public void setLineColor(Color c) {
		this.lineColor = c;
	}

	public Color getLineColor() {
		return this.lineColor;
	}

	public String getLabelName() {
		return labelName;
	}

	public String getLabelType() {
		return labelType;
	}

	public void setLabelName(String name) {
		this.labelName = name;
	}

	public void setLabelType(String type) {
		this.labelType = type;
	}

}
