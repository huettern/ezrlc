package pro2.Plot.RectPlot;

import pro2.MVC.Controller;
import pro2.MVC.Controller.DataSource;
import pro2.ModelCalculation.MCEqCircuit;
import pro2.RFData.RFData;
import pro2.RFData.RFData.ComplexModifier;
import pro2.RFData.RFData.MeasurementType;

public class RectPlotNewMeasurement {
	
	public Controller.DataSource src;
	public String src_name;
	public MCEqCircuit eqCircuit;
	public RFData.MeasurementType type;
	public RFData.ComplexModifier cpxMod;

	public RectPlotNewMeasurement() {
		src = DataSource.FILE;
		src_name = "";
		eqCircuit = null;
		type = MeasurementType.Z;
		cpxMod = ComplexModifier.REAL;
	}

}
