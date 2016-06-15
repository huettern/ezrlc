package ezrlc.Controller;

import java.io.File;

import ezrlc.Model.Model;
import ezrlc.Model.RectPlotNewMeasurement;
import ezrlc.Model.SmithChartNewMeasurement;
import ezrlc.ModelCalculation.MCOptions;
import ezrlc.Plot.Figure;
import ezrlc.View.MainView;
import ezrlc.View.WorkPanel.ViewType;

/**
 * Implements controller functionality of the MVC framework
 * 
 * @author noah
 *
 */
public class Controller {

	// ================================================================================
	// Public Data
	// ================================================================================

	// ================================================================================
	// Private Data
	// ================================================================================
	private Model model;
	private MainView view;

	// ================================================================================
	// Public Functions
	// ================================================================================
	/**
	 * Create new controller
	 * 
	 * @param model
	 *            model object
	 * @param view
	 *            main view object
	 */
	public Controller(Model model, MainView view) {
		this.model = model;
		this.view = view;

	}

	/**
	 * Control
	 */
	public void contol() {
	}

	/**
	 * Reads the inputfile given by the user
	 * 
	 * @param file
	 *            input file
	 */
	public void loadFile(File file) {
		// Read the file
		this.model.newInputFile(file);
		view.setFileName(file.getName());
	}

	public MainView getMainView() {
		return this.view;
	}

	/**
	 * Forces the model to trigger a notify to the observers
	 */
	public void manualNotify() {
		model.manualNotify();
	}

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

	/**
	 * Returns the currently loaded filename
	 * 
	 * @return filename
	 */
	public String getFilename() {
		return model.getFilename();
	}

	/**
	 * Delete a figure in the view
	 * 
	 * @param figure
	 *            object of the figure
	 */
	public void deleteFigure(Figure figure) {
		view.deleteFigure(figure);
		// delete all datasets in the model
		for (int i = 0; i < figure.getDataSetIDs().length; i++) {
			model.removeDataset(figure.getDataSetIDs()[i]);
		}
		// notify view to enable new graph button
		view.setNewGraphButtonEnabled(true);
	}

	/**
	 * removes a dataset in the model
	 * 
	 * @param plottype
	 *            plottype
	 * @param id
	 *            id
	 */
	public void removeDataset(int id) {
		model.removeDataset(id);
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
	 *            options
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

	/**
	 * Builds datasets for IGAssist panel
	 */
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
	 *            eqcID
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
	 *            eqcID
	 */
	public void optimizeEqCircuit(int eqcID) {
		model.optimizeEqCircuit(eqcID);
	}

	/**
	 * Stops the worker and deletes all eqc related data
	 */
	public void killWorker(int eqcID) {
		model.killWorker();
		removeEqCircuit(eqcID);
	}

}
