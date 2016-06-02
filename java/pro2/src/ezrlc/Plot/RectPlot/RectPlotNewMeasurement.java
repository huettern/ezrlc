package ezrlc.Plot.RectPlot;

import ezrlc.MVC.Controller;
import ezrlc.MVC.Controller.DataSource;
import ezrlc.RFData.RFData;
import ezrlc.RFData.RFData.ComplexModifier;
import ezrlc.RFData.RFData.MeasurementType;

public class RectPlotNewMeasurement {

	public enum Unit {Linear, dB};
	
	public Controller.DataSource src;
	public String src_name;
	public int eqCircuitID;
	public RFData.MeasurementType type;
	public RFData.ComplexModifier cpxMod;
	public Unit unit;
	public double zRef;
	

	public RectPlotNewMeasurement() {
		src = DataSource.FILE;
		src_name = "";
		type = MeasurementType.Z;
		cpxMod = ComplexModifier.REAL;
		unit = Unit.dB;
		zRef = 50.0;
	}

}