package ezrlc.MVC;

import java.io.File;

import ezrlc.ModelCalculation.MCOptions;
import ezrlc.Plot.Figure;
import ezrlc.Plot.Figure.ENPlotType;
import ezrlc.Plot.RectPlot.RectPlotNewMeasurement;
import ezrlc.Plot.SmithChart.SmithChartNewMeasurement;
import ezrlc.View.MainView;
import ezrlc.View.WorkPanel.ViewType;

public class Controller {

	// ================================================================================
	// Public Data
	// ================================================================================
	public enum DataSource {
		FILE, MODEL
	};

	// ================================================================================
	// Private Data
	// ================================================================================
	private Model model;
	private MainView view;

	// ================================================================================
	// Public Functions
	// ================================================================================

	public Controller(Model model, MainView view) {
		this.model = model;
		this.view = view;

	}

	public void contol() {
	}

	/**
	 * Reads the inputfile given by the user
	 * 
	 * @param file
	 */
	public void loadFile(File file) {
		// Read the file
		this.model.newInputFile(file);
		view.setFileName(file.getName());
	}

	public MainView getMainView() {
		return this.view;
	}

	public void manualNotify() {
		model.manualNotify();
	}

	// /**
	// * Adds a new Dataset in the model
	// * @param src Datasource (File or Model)
	// * @param ec MCEqCircuit
	// * @param measType RFData.MeasurementType
	// * @param cpxMod RFData.ComplexModifier
	// * @return unique data identifier of the plotdataset
	// */
	// public int createDataset(DataSource src, MCEqCircuit ec,
	// RFData.MeasurementType measType, RFData.ComplexModifier cpxMod) {
	// return model.createDataset(src,ec,measType,cpxMod);
	// }
	/**
	 * Adds a new Dataset in the model
	 * 
	 * @param nm
	 *            RectPlotNewMeasurement
	 * @return unique data identifier of the plotdataset
	 */
	public int createDataset(RectPlotNewMeasurement nm) {
		return model.createDataset(nm);
	}

	/**
	 * Adds a new Dataset in the model
	 * 
	 * @param nm
	 *            SmithChartNewMeasurement
	 * @return unique data identifier of the plotdataset
	 */
	public int createDataset(SmithChartNewMeasurement nm) {
		return model.createDataset(nm);
	}

	public String getFilename() {
		return model.getFilename();
	}

	public void deleteFigure(Figure figure) {
		view.deleteFigure(figure);
		// notify view to enable new graph button
		view.setNewGraphButtonEnabled(true);
	}

	/**
	 * removes a dataset in the model
	 * 
	 * @param id
	 */
	public void removeDataset(ENPlotType plottype, int id) {
		model.removeDataset(plottype, id);
	}

	/**
	 * Sets the status of the new graph button
	 * 
	 * @param b
	 *            enabled(true) or disabled(flase)
	 */
	public void setNewGraphButtonEnabled(boolean b) {
		view.setNewGraphButtonEnabled(b);
	}

	/**
	 * Creates a new equivalent circuit based on the given options
	 * 
	 * @param ops
	 */
	public void createEqCircuit(MCOptions ops) {
		view.setupEqCircuitView();
		model.createEqCircuit(ops);
	}

	/**
	 * Set the work panel view to the given type
	 * 
	 * @param t
	 *            ViewType
	 */
	public void setWorkPanelView(ViewType t) {
		view.setWorkPanelView(t);
	}

	public void buildIGAssistDataSet() {
		view.buildIGAssistDataSet();
	}

	/**
	 * Returns an int array with the currently available equivalent circuit
	 * models
	 * 
	 * @return int array with eqc IDs
	 */
	public int[] getModelIDs() {
		return model.getModelIDs();
	}

	/**
	 * Removes an equivalent circuit from the model
	 * 
	 * @param eqcID
	 */
	public void removeEqCircuit(int eqcID) {
		model.removeEqCircuit(eqcID);
	}

	/**
	 * Updates a equivalent circuits parameters in the model
	 * 
	 * @param eqcID
	 *            id of the model
	 * @param parameters
	 *            parameter list
	 */
	public void updateEqcParams(int eqcID, double[] parameters) {
		model.updateEqcParams(eqcID, parameters);
	}

	/**
	 * Starts the optimizer of the eqcircuit
	 * 
	 * @param eqcID
	 */
	public void optimizeEqCircuit(int eqcID) {
		model.optimizeEqCircuit(eqcID);
	}

}
