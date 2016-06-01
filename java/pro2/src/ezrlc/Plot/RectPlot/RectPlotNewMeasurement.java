package ezrlc.Plot.RectPlot;

import ezrlc.MVC.Controller;
import ezrlc.MVC.Controller.DataSource;
import ezrlc.ModelCalculation.MCEqCircuit;
import ezrlc.RFData.RFData;
import ezrlc.RFData.RFData.ComplexModifier;
import ezrlc.RFData.RFData.MeasurementType;

public class RectPlotNewMeasurement {
	
	public Controller.DataSource src;
	public String src_name;
	public int eqCircuitID;
	public RFData.MeasurementType type;
	public RFData.ComplexModifier cpxMod;

	public RectPlotNewMeasurement() {
		src = DataSource.FILE;
		src_name = "";
		type = MeasurementType.Z;
		cpxMod = ComplexModifier.REAL;
	}

}
