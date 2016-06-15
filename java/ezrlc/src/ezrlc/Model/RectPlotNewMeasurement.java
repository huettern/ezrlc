package ezrlc.Model;

import ezrlc.RFData.RFData;
import ezrlc.RFData.RFData.ComplexModifier;
import ezrlc.RFData.RFData.MeasurementType;
import ezrlc.util.DataSource;

/**
 * Stores the new measurement options
 * 
 * @author noah
 *
 */
public class RectPlotNewMeasurement {

	public enum Unit {
		Linear, dB
	};

	public DataSource src;
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
