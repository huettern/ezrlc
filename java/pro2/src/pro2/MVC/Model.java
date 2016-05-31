package pro2.MVC;

import java.io.File;
import java.lang.Thread.State;
import java.util.*;

import pro2.MVC.Controller.DataSource;
import pro2.ModelCalculation.MCEqCircuit;
import pro2.ModelCalculation.MCOptions;
import pro2.ModelCalculation.MCWorker;
import pro2.ModelCalculation.MCWorker.WorkerMode;
import pro2.Plot.PlotDataSet;
import pro2.Plot.Figure.ENPlotType;
import pro2.Plot.RectPlot.RectPlotNewMeasurement;
import pro2.Plot.SmithChart.SmithChartDataSet;
import pro2.Plot.SmithChart.SmithChartNewMeasurement;
import pro2.RFData.RFData;
import pro2.RFData.RFData.ComplexModifier;
import pro2.RFData.RFData.MeasurementType;
import pro2.util.Complex;

public class Model extends Observable {

	//================================================================================
    // Public Data
    //================================================================================
	public enum UpdateEvent {MANUAL, FILE, NEW_EQC, REMOVE_EQC, CHANGE_EQC};
	
	
	//================================================================================
    // Private Data
    //================================================================================
	private Controller controller;
	
	private RFData rfDataFile;

	private List<PlotDataSet> plotDataSetList = new ArrayList<PlotDataSet>();
	private List<SmithChartDataSet> smithPlotDataSetList = new ArrayList<SmithChartDataSet>();
	private List<MCEqCircuit> eqCircuits = new ArrayList<MCEqCircuit>();
	
	// Stores the plotDataSetList ids to the corresponding model id
	private List<Integer[]> eqCircuitRectPlotIDs = new ArrayList<Integer[]>();
	private List<Integer[]> eqCircuitSmithPlotIDs = new ArrayList<Integer[]>();
	
	MCWorker worker;
	
	public Model() {
		// TODO Auto-generated constructor stub
		
	}

	//================================================================================
    // Private Functions
    //================================================================================
	/**
	 * Builds a new dataset with the given settings
	 * @param nm RectPlotNewMeasurement
	 * @return PlotDataSet
	 */
	private PlotDataSet buildDataSetRaw(RectPlotNewMeasurement nm) {
		Complex[] data = null;
		double[] outdata = null;
		// Get Data
		
		if(nm.src == DataSource.FILE) {
			outdata = new double[rfDataFile.size()];
			switch (nm.type) {
			case S: data = this.rfDataFile.getsData(); break;
			case Z: data = this.rfDataFile.getzData(); break;
			case Y: data = this.rfDataFile.getyData(); break;
			case Rs: outdata = RFData.z2Rs(this.rfDataFile.getzData()); break;
			case Rp: outdata = RFData.y2Rp(this.rfDataFile.getyData()); break;
			case Ls: outdata = RFData.z2Ls(this.rfDataFile.getzData(), rfDataFile.getfData()); break;
			case Lp: outdata = RFData.y2Lp(this.rfDataFile.getyData(), rfDataFile.getfData()); break;
			case Cs: outdata = RFData.z2Cs(this.rfDataFile.getzData(), rfDataFile.getfData()); break;
			case Cp: outdata = RFData.y2Cp(this.rfDataFile.getyData(), rfDataFile.getfData()); break;
			default:
				break;
			}
		} else if (nm.src == DataSource.MODEL) {
			outdata = new double[eqCircuits.get(nm.eqCircuitID).getWSize()];
			switch (nm.type) {
			case S:
				data = eqCircuits.get(nm.eqCircuitID).getS();
				break;
			case Y:
				data = eqCircuits.get(nm.eqCircuitID).getY();
				break;
			case Z:
				data = eqCircuits.get(nm.eqCircuitID).getZ();
				break;
			default:
				break;
			}
		} 
		
		// Convert to Complex Modifier
		if(nm.type == MeasurementType.S || nm.type == MeasurementType.Y || nm.type == MeasurementType.Z) {
			outdata = new double[data.length];
			switch(nm.cpxMod) {
			case REAL:
		        // Extract real part
				for(int i = 0; i < data.length; i++){
					outdata[i] = data[i].re();
				}
				break;
			case IMAG:
		        // Extract imaginary part
				for(int i = 0; i < data.length; i++){
					outdata[i] = data[i].im();
				}
				break;
			case MAG:
		        // Extract magnitude
				for(int i = 0; i < data.length; i++){
					outdata[i] = data[i].abs();
				}
				break;
			case ANGLE:
		        // Extract angle
				for(int i = 0; i < data.length; i++){
					outdata[i] = data[i].angle();
				}
				break;
				default: break;
			}
		}
		
		double[] xdata = null;
		if(nm.src == DataSource.FILE) xdata = rfDataFile.getfData();
		if(nm.src == DataSource.MODEL) xdata = eqCircuits.get(nm.eqCircuitID).getF();
		
		// Now create the dataset and add it to the dataset list
		PlotDataSet dataSet = new PlotDataSet(xdata, outdata, nm);
		return dataSet;
	}
	
	private int buildDataSet(RectPlotNewMeasurement nm) {
		PlotDataSet dataSet = buildDataSetRaw(nm);
		this.plotDataSetList.add(dataSet);
		
		// Return the number in the list
		int id = this.plotDataSetList.size() - 1;
		
		// save id in list to associate it to its source
		if(nm.src == DataSource.MODEL) {
			Integer[] lst = eqCircuitRectPlotIDs.get(nm.eqCircuitID);
			Integer[] newlst = new Integer[lst.length+1];
			System.arraycopy(lst, 0, newlst, 0, lst.length);
			newlst[lst.length] = id;
			eqCircuitRectPlotIDs.set(nm.eqCircuitID, newlst);
		}
		
		return id;
	}

	/**
	 * Builds a new dataset with the given settings
	 * @param nm SmithChartNewMeasurement
	 * @return SmithChartDataSet
	 */
	private SmithChartDataSet buildSmithChartDataSetRaw (SmithChartNewMeasurement nm) {
		Complex[] data = null;
		SmithChartDataSet set = null;
		// Get Data
		if(nm.src == DataSource.FILE) {
			data = rfDataFile.getzData();
			 set  = new SmithChartDataSet(null, data, rfDataFile.getfData(), nm);
		} else if(nm.src == DataSource.MODEL) {
			data = eqCircuits.get(nm.eqCircuitID).getZ();
			set  = new SmithChartDataSet(null, data, eqCircuits.get(nm.eqCircuitID).getF(), nm);
		}
		return set;
	}
	
	/**
	 * Builds a new Smithchart dataset and returns its unique ID
	 * @param nm SmithChartNewMeasurement
	 * @return ID in the list of smith chart datasets
	 */
	private int buildSmithChartDataSet(SmithChartNewMeasurement nm) {
		SmithChartDataSet set = buildSmithChartDataSetRaw(nm);
		
		// Create set
		this.smithPlotDataSetList.add(set);

		int id = this.smithPlotDataSetList.size()-1;
		
		if(nm.src == DataSource.MODEL) {
			// save id in list to associate it to its source
			Integer[] lst = eqCircuitSmithPlotIDs.get(nm.eqCircuitID);
			Integer[] newlst = new Integer[lst.length+1];
			System.arraycopy(lst, 0, newlst, 0, lst.length);
			newlst[lst.length] = id;
			eqCircuitSmithPlotIDs.set(nm.eqCircuitID, newlst);
		}
		
		
		return id;
	}
	//================================================================================
    // Public Functions
    //================================================================================

	
	/**
	 * Sets the controller object
	 * @param controller
	 */
	public void setController (Controller controller) {
		this.controller = controller;
	}

	/**
	 * Parses the given Inputfile and adds a new RFData object
	 * @param file
	 */
	public UUID newInputFile(File file) {
		try {
			rfDataFile = new RFData(file);
			rfDataFile.parse();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("FATAL: Error in file parsing");
		}
//		setChanged();
//		notifyObservers(UpdateEvent.FILE);
		return null;
	}

	/**
	 * Adds a new Dataset in the model
	 * @param nm RectPlotNewMeasurement
	 * @return unique data identifier of the plotdataset
	 */
	public int createDataset(RectPlotNewMeasurement nm) {
		return this.buildDataSet(nm);
	}
	
	/**
	 * Adds a new Smithchart dataset in the model
	 * @param nm SmithChartNewMeasurement
	 * @return unique data identifier of the plotdataset
	 */
	public int createDataset(SmithChartNewMeasurement nm) {
		return this.buildSmithChartDataSet(nm);
	}
	

	/**
	 * Adds a new Dataset in the model
	 * @param src Datasource (File or Model)
	 * @param ec MCEqCircuit
	 * @param measType RFData.MeasurementType
	 * @param cpxMod RFData.ComplexModifier
	 * @return	unique data identifier of the plotdataset
	 */
	public int createDataset(Controller.DataSource src, int ecID, RFData.MeasurementType measType, RFData.ComplexModifier cpxMod) {
		RectPlotNewMeasurement nm = new RectPlotNewMeasurement();
		nm.src = src;
		nm.eqCircuitID = ecID;
		nm.type=measType;
		nm.cpxMod=cpxMod;
		return this.buildDataSet(nm);
	}
	
	
	public void manualNotify() {
		// mark as value changed
		setChanged();
		notifyObservers(UpdateEvent.MANUAL);	
	}

	public PlotDataSet getDataSet(int intValue) {
		// TODO Auto-generated method stub
		return this.plotDataSetList.get(intValue);
	}
	
	public SmithChartDataSet getSmithChartDataSet(int intValue) {
		// TODO Auto-generated method stub
		return this.smithPlotDataSetList.get(intValue);
	}

	public String getFilename() {
		// TODO Auto-generated method stub
		if(this.rfDataFile != null) {
			return this.rfDataFile.getFileName();
		}
		else return null;
	}
	
	/**
	 * Checks if the given id is a dataset
	 * @param plottype ploy type
	 * @param id plot data set id
	 * @return true, if id exists, false if not
	 */
	public Boolean isDataset(ENPlotType plottype, int id) {
		if(plottype == ENPlotType.RECTANGULAR) {
			try {
				if(this.plotDataSetList.get(id) != null) return true;
				else return false;
			} catch (Exception e) {
				return false;
			}
		} else if(plottype == ENPlotType.SMITH) {
			try {
				if(this.smithPlotDataSetList.get(id) != null) return true;
				else return false;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Removes a dataset by ID
	 * @param id
	 */
	public void removeDataset(ENPlotType plottype, int id) {
		if(plottype == ENPlotType.RECTANGULAR) {
			// remove set from list
			this.plotDataSetList.set(id, null);
		} else if(plottype == ENPlotType.SMITH) {
			this.smithPlotDataSetList.set(id, null);
		}
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Creates a new equivalent circuit based on the given options
	 * @param ops
	 */
	public void createEqCircuit(MCOptions ops) {
		// Create worker, set data and start it
		worker = new MCWorker(this, "MCWorker-1");
		worker.setRFDataSet(rfDataFile);
		worker.setMCOptions(ops);
		worker.start();
	}
	
	/**
	 * Returns the ID of the last created Model
	 * @return
	 */
	public int getEQCID () {
		return eqCircuits.size()-1;
	}

	/**
	 * Returns the current state of the worker
	 * @return
	 */
	public State getWorkerStatus() {
		return worker.getState();
	}
	
	/**
	 * Returns the calculation worker
	 * @return
	 */
	public MCWorker getWorker() {
		return worker;
	}

	/**
	 * Gets called if the MCWorker succeeds
	 * @param eqc Equivalent circuit that was generated
	 */
	public void mcWorkerSuccess(MCEqCircuit eqc,  MCWorker.WorkerMode mode) {
		if(mode == WorkerMode.NORMAL) {
			eqCircuits.add(eqc);
			eqCircuitRectPlotIDs.add(new Integer[] {});
			eqCircuitSmithPlotIDs.add(new Integer[] {});
			setChanged();
			notifyObservers(UpdateEvent.NEW_EQC);
		} else if (mode == WorkerMode.OPT_ONLY) {
			// update plots
			for (int i = 0; i < plotDataSetList.size(); i++) {
				if(plotDataSetList.get(i) != null)
					updateRectPlotDataset(i);
			}
			for (int i = 0; i < smithPlotDataSetList.size(); i++) {
				if(smithPlotDataSetList.get(i) != null)
					updateSmithPlotDataset(i);
			}
			setChanged();
			notifyObservers(UpdateEvent.CHANGE_EQC);
		}
	}

	/**
	 * Returns the MCEqCircuit by ID
	 * @param eqcID ID of MCEqCircuit
	 * @return MCEqCircuit
	 */
	public MCEqCircuit getEquivalentCircuit(int eqcID) {
		return eqCircuits.get(eqcID);
	}

	/**
	 * Returns an int array with the currently available equivalent circuit models
	 * @return int array with eqc IDs
	 */
	public int[] getModelIDs() {
		int[] tmp = new int[eqCircuits.size()];
		int j = 0;
		for(int i = 0; i < eqCircuits.size(); i++){
			if(eqCircuits.get(i) != null) {
				tmp[j++] = i;
			}
		}
		int[] res = new int[j];
		System.arraycopy(tmp, 0, res, 0, j);
		return res;
	}

	/**
	 * Removes an equivalent circuit
	 * @param eqcID ID to the circuit
	 */
	public void removeEqCircuit(int eqcID) {
		this.eqCircuits.set(eqcID, null);
		setChanged();
		notifyObservers(UpdateEvent.REMOVE_EQC);
	}

	/**
	 * Checks if the eqcircuit given by the id exists
	 * @param id ID to the eqcircuit
	 * @return true if existing, flase if not
	 */
	public boolean isEqCircuit(int id) {
		try {
			if(this.eqCircuits.get(id) != null) return true;
			else return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Updates a equivalent circuits parameters in the model
	 * @param eqcID id of the model
	 * @param parameters parameter list
	 */
	public void updateEqcParams(int eqcID, double[] parameters) {
		eqCircuits.get(eqcID).setParameters(parameters);
		// update the associated plots
		for (Integer i : eqCircuitRectPlotIDs.get(eqcID) ) {
			updateRectPlotDataset(i);
		}
		for (Integer i : eqCircuitSmithPlotIDs.get(eqcID) ) {
			updateSmithPlotDataset(i);
		}
		setChanged();
		notifyObservers(UpdateEvent.CHANGE_EQC);
	}

	private void updateRectPlotDataset(Integer i) {
		RectPlotNewMeasurement nm = plotDataSetList.get(i).getNM();
	 	plotDataSetList.set(i, buildDataSetRaw(nm));
	}

	private void updateSmithPlotDataset(Integer i) {
		SmithChartNewMeasurement nm = smithPlotDataSetList.get(i).getNM();
	 	smithPlotDataSetList.set(i, buildSmithChartDataSetRaw(nm));
	}

	/**
	 * Starts the optimizer of the eqcircuit
	 * @param eqcID
	 */
	public void optimizeEqCircuit(int eqcID) {
		// Create worker, set data and start it
		worker = new MCWorker(this, "MCWorker-EQCOptimizer-"+eqcID);
		worker.setRFDataSet(rfDataFile);
		worker.setEQCircuit(eqCircuits.get(eqcID));
		worker.start();
	}
}
