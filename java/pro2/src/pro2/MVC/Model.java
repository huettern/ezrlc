package pro2.MVC;

import java.io.File;
import java.util.*;

import pro2.MVC.Controller.DataSource;
import pro2.ModelCalculation.MCEqCircuit;
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
	
	private Controller controller;
	
	private RFData rfDataFile;

	private List<PlotDataSet> plotDataSetList = new ArrayList<PlotDataSet>();
	private List<SmithChartDataSet> smithPlotDataSetList = new ArrayList<SmithChartDataSet>();

	public Model() {
		// TODO Auto-generated constructor stub
		
	}

	//================================================================================
    // Private Functions
    //================================================================================
	private int buildDataSet(RectPlotNewMeasurement nm) {
		Complex[] data = null;
		double[] outdata = null;
		// Get Data
		if(nm.src == DataSource.FILE) {
			outdata = new double[rfDataFile.size()];
			switch (nm.type) {
			case S:
				data = this.rfDataFile.getsData();
				break;
			case Z:
				data = this.rfDataFile.getzData();
				break;
			case Y:
				data = this.rfDataFile.getyData();
				break;
			default:
				break;
			}
		} else if (nm.src == DataSource.MODEL) {
			outdata = new double[nm.eqCircuit.getWSize()];
			switch (nm.type) {
			case S:
				data = nm.eqCircuit.getS();
				break;
			case Y:
				data = nm.eqCircuit.getY();
				break;
			case Z:
				data = nm.eqCircuit.getS();
				break;
			default:
				break;
			}
		}
		
		// Convert to Complex Modifier
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
		}
		
		
		// Now create the dataset and add it to the dataset list
		PlotDataSet dataSet = new PlotDataSet(rfDataFile.getfData(), outdata);
		this.plotDataSetList.add(dataSet);
		// mark as value changed
//		setChanged();
//		notifyObservers();
		
		// Return the number in the list
		return this.plotDataSetList.size() - 1;
		
		

//	      List<Double> xtest2 = new ArrayList<Double>();
//	      List<Double> ytest2 = new ArrayList<Double>();
//	      xtest2.add(50.0);
//	      xtest2.add(60.0);
//	      ytest2.add(0.10);
//	      ytest2.add(0.20);
//	      PlotDataSet testset2 = new PlotDataSet(xtest2, ytest2);
//	      this.plotDataSetList.add(testset2);
//	      return (this.plotDataSetList.size() - 1);
	}
	
	/**
	 * Builds a new Smithchart dataset and returns its unique ID
	 * @param nm SmithChartNewMeasurement
	 * @return ID in the list of smith chart datasets
	 */
	private int buildSmithChartDataSet(SmithChartNewMeasurement nm) {
		Complex[] data = null;

		// Get Data
		if(nm.src == DataSource.FILE) {
			data = rfDataFile.getzData();
		}
		
		// Create set
		SmithChartDataSet set  = new SmithChartDataSet(null, data, rfDataFile.getfData());
		this.smithPlotDataSetList.add(set);
		return this.smithPlotDataSetList.size()-1;
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
			return null;
		}
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
	 * @param id Model ID, if File then not used
	 * @param measType RFData.MeasurementType
	 * @param cpxMod RFData.ComplexModifier
	 * @return	unique data identifier of the plotdataset
	 */
	public int createDataset(Controller.DataSource src, MCEqCircuit ec, RFData.MeasurementType measType, RFData.ComplexModifier cpxMod) {
		RectPlotNewMeasurement nm = new RectPlotNewMeasurement();
		nm.src = src;
		nm.eqCircuit = ec;
		nm.type=measType;
		nm.cpxMod=cpxMod;
		return this.buildDataSet(nm);
	}
	
	
	public void manualNotify() {
		// mark as value changed
		setChanged();
		notifyObservers();	
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
			System.out.println("removed dataset id="+id);
		} else if(plottype == ENPlotType.SMITH) {
			this.smithPlotDataSetList.set(id, null);
			System.out.println("removed dataset id="+id);
		}
		this.setChanged();
		this.notifyObservers();
	}

	

	
	


}
