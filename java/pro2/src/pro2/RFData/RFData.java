/**
 * 
 */
package pro2.RFData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import pro2.util.Complex;

/**
 * @author noah
 *
 */
public class RFData {
	//================================================================================
    // Datatypes
    //================================================================================

	/**
	 * Defines the available data types in the datafile
	 *
	 */
	public enum MeasurementType {
	    S, Y, Z
	}
	
	/**
	 * Defines the available data units in the datafile
	 *
	 */
	public enum MeasurementUnit {
		 MA, DB, RI
	}
	
	public enum ComplexModifier {
		REAL, IMAG, MAG, ANGLE
	}
	
	
	//================================================================================
    // Private Data
    //================================================================================

	/**
	 * 
	 */
	private UUID uid;
	
	private String fname = "";
	
	private long dataEntries = 0;
	private long commentEntrys = 0;
	private long instructionEntrys = 0;
	private int freqMultiplier = 1;
	private MeasurementType dataType = MeasurementType.S;
	private MeasurementUnit dataUnit = MeasurementUnit.MA;
	private float r = 0;
	private List<DataEntry> rawData = new ArrayList<DataEntry>();

	// Normalized data, independant of input MeasurementUnit 
	private List<Complex> normalizedData = new ArrayList<Complex>();
	
	// Scattering data
	private List<Complex> sData = new ArrayList<Complex>();
	
	// Impedance data
	private List<Complex> zData = new ArrayList<Complex>();
	
	// Admittance data
	private List<Complex> yData = new ArrayList<Complex>();
	
	// Frequency points
	private List<Double> fData = new ArrayList<Double>();
	
	
	
	//================================================================================
    // Constructors
    //================================================================================

	
	public RFData(String fname) {
		// TODO Auto-generated constructor stub
		this.fname = fname;
		System.out.println("Filename " +this.fname);
		this.uid = UUID.randomUUID();
	}
	
	public RFData(File file) {
		// TODO Auto-generated constructor stub
		this.fname = file.getAbsolutePath();
		System.out.println("Filename " +this.fname);
		this.uid = UUID.randomUUID();
	}
	
	//================================================================================
    // Non static Functions
    //================================================================================


	public UUID getUID() {
		return this.uid;
	}
	
	public void parse () throws IOException {
		FileReader file;
		String line;
		double freq, data1, data2;
		String[] linedata;
		
		// Try opening the input file
		try {
			file = new FileReader(this.fname);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Input File not found!");
			return;
		}
		
		// Add file to a buffer to read line by line
		BufferedReader br = new BufferedReader(file);
	    while ((line = br.readLine()) != null) {
	    	if(line.length() != 0) {
		    	// check if first char is '!', which means a comment
		    	if(line.charAt(0) == '!') {
		    		this.commentEntrys++;
		    	}
		    	// check if first char is a '#' which means instruction
		    	else if (line.charAt(0) == '#') {
		    		this.instructionEntrys++;
		    		// split line into array
		    		// delimiter is space or tab
		    		linedata = line.split("\\t| ");
		    		// parse each entry
		    		for(int i=0; i<linedata.length; i++) {
		    			// first entry is unit of freq
		    			if(i==1){
		    				if(linedata[i].equalsIgnoreCase("HZ"))
		    					this.freqMultiplier = 1;
		    				if(linedata[i].equalsIgnoreCase("KHZ"))
		    					this.freqMultiplier = 1000;
		    				if(linedata[i].equalsIgnoreCase("MHZ"))
		    					this.freqMultiplier = 1000000;
		    				if(linedata[i].equalsIgnoreCase("GHZ"))
		    					this.freqMultiplier = 1000000000;
		    			}
		    			// next entry is parameter type
		    			else if(i==2) {
		    				if(linedata[i].equalsIgnoreCase("S"))
		    					this.dataType = MeasurementType.S;
		    				if(linedata[i].equalsIgnoreCase("Y"))
		    					this.dataType = MeasurementType.Y;
		    				if(linedata[i].equalsIgnoreCase("Z"))
		    					this.dataType = MeasurementType.Z;
		    			}
		    			// next entry is unit
		    			else if(i==3) {
		    				if(linedata[i].equalsIgnoreCase("MA"))
		    					this.dataUnit = MeasurementUnit.MA;
		    				if(linedata[i].equalsIgnoreCase("DB"))
		    					this.dataUnit = MeasurementUnit.DB;
		    				if(linedata[i].equalsIgnoreCase("RI"))
		    					this.dataUnit = MeasurementUnit.RI;
		    			}
		    			// next entry is constant 'R'
		    			else if(i==4) {
		    				// TODO: Check if this can be ignored
		    			}
		    			// next entry is equivelant measurement resistance
		    			else if(i==5) {
		    				this.r = Float.valueOf(linedata[i]);
		    			}
		    		}
		    	}
		    	// check if first char is a number which means data
		    	else if (Character.isDigit(line.charAt(0))) {
		    		this.dataEntries++;
		    		// split line into array
		    		// delimiter is space or tab
		    		linedata = line.split("\\t| ");
		    		freq = this.freqMultiplier*Double.valueOf(linedata[0]);
		    		data1 = Double.valueOf(linedata[1]);
		    		data2 = Double.valueOf(linedata[2]);
		    		rawData.add(new DataEntry(this.dataType, this.dataUnit, freq, data1, data2));	
		    	}
	    	}
	    }
	    // close file stream
	    br.close();
	    
	    this.normalizeRawData();
	    this.compensateMeasurementResistance();
	    this.adjustNormalizedData();
	    
	    // Copy f-Information
	    for (DataEntry entry : rawData) {
			fData.add(entry.getFreq());
		}
		
	    System.out.println("comments=" +this.commentEntrys +" instructions=" +this.instructionEntrys +" data=" +this.dataEntries);
		System.out.println("Freq multiplier="+this.freqMultiplier);
		System.out.println("Type: "+this.dataType+" Units: "+this.dataUnit+" R: "+this.r);
	}

	//================================================================================
    // Private Functions
    //================================================================================
	/**
	 * Normalizes the input data according to the input unit
	 * Normalized output is in complex numbers
	 */
	private void normalizeRawData() {
		double angle = 0;
		double mag = 0;
		switch (this.dataUnit)
		{
		case RI:
			// convert raw data to complex number
			for (DataEntry rawEntry : this.rawData) {
				normalizedData.add(new Complex(rawEntry.getData1(), rawEntry.getData2()));
			}
			break;
		case MA:
			// convert raw data from absolute and angle to complex
			// angle = (d2*pi)/180
			// real = d1*cos(angle)
			// imag = d1*sin(angle)
			for (DataEntry rawEntry : this.rawData) {
				angle = (rawEntry.getData2()*Math.PI)/180.0;
				mag = rawEntry.getData1();
				normalizedData.add(new Complex(mag*Math.cos(angle), mag*Math.sin(angle)));
			}
			break;
		case DB:
			// Convert raw data from DB absolute and angle to complex
			// angle = (d2*pi)/180
			// mag = 10^(d1/20)
			// real = mag*cos(angle)
			// imag = mag*sin(angle)
			for (DataEntry rawEntry : this.rawData) {
				angle = (rawEntry.getData2()*Math.PI)/180.0;
				mag = Math.pow(10, rawEntry.getData1()/20.0 );
				normalizedData.add(new Complex(mag*Math.cos(angle), mag*Math.sin(angle)));
			}
			break;
			default:
				break;
		}
		// Check if output size is equal to input size
		if(this.rawData.size() != this.normalizedData.size()) {
			System.out.println("FATAL: normalized entries not equal raw entries");
		}
	}
	
	/**
	 * Compensates the measurement resistance
	 */
	private void compensateMeasurementResistance() {
		double factor = 1;
		
		// Switch by datatype
		switch (this.dataType) {
		case S:
			// Do nothing with the data
			break;
			
		case Y:
			// Multiply real and imaginary part by the resistance
			factor=1.0/this.r;
			break;
		case Z:
			// Multiply real and imaginary part by the resistance
			factor=this.r;
			break;

		default:
			break;
		}
		
		// Change every item in the list
		for (final ListIterator<Complex> c = normalizedData.listIterator(); c.hasNext();) {
		  Complex element = c.next();
		  element = element.times(factor);
		  c.set(element);
		}
	}
	
	/**
	 * Adjusts the normalized values to S-Parameters including Z0-Compensation
	 */
	private void adjustNormalizedData() {
		Complex complextmp1, complextmp2, complextmp3;
		switch (this.dataType) {
		case S:
			// If the data is already scattering, then copy the scattering data...
			//
			// S=S
			//
			for (Complex entry : normalizedData) {
				sData.add(new Complex(entry));
			}
			// ...And calculate z data
			//
			// Z=Ro*((1+S)/(1-S))
			//
			complextmp2 = new Complex(1.0,0);			// constant 1 as complex number
			complextmp3 = new Complex(this.r,0);	// resistance as complex number
			for (Complex complex : normalizedData) {
				// TODO: Calculations in the next line aren't correct!
				complextmp1 = new Complex(Complex.mul(complextmp3, Complex.div(Complex.add(complextmp2, complex), Complex.sub(complextmp2, complex))));
				zData.add(complextmp1);
			}
			//
			// Y=1/Z
			//
			complextmp1 = new Complex(1,0);
			for (Complex entry : zData) {
				yData.add(Complex.div(complextmp1, entry));
			}
			break;
		case Y:
			// If the data is already scattering, then copy the scattering data...
			//
			// Y=Y
			//
			for (Complex entry : normalizedData) {
				yData.add(new Complex(entry));
			}
			//
			// Z=1/Y
			//
			complextmp1 = new Complex(1,0);
			for (Complex entry : normalizedData) {
				zData.add(Complex.div(complextmp1, entry));
			}
			//
			// S=(Z-R0)/(Z+Ro)
			//
			complextmp3 = new Complex(this.r,0);	// resistance as complex number
			for (Complex entry : zData) {
				complextmp1 = new Complex(Complex.div(Complex.sub(entry, complextmp3), Complex.add(entry, complextmp3)));
				sData.add(complextmp1);
			}
			break;
		case Z:
			// if the data is already impedance, conpy impedance...
			//
			// Z=Z
			//
			for (Complex entry : normalizedData) {
				zData.add(new Complex(entry));
			}
			// ..And calculate S data
			//
			// S=(Z-R0)/(Z+Ro)
			//
			complextmp3 = new Complex(this.r,0);	// resistance as complex number
			for (Complex entry : normalizedData) {
				complextmp1 = new Complex(Complex.div(Complex.sub(entry, complextmp3), Complex.add(entry, complextmp3)));
				sData.add(complextmp1);
			}
			//
			// Y=1/Z
			//
			complextmp1 = new Complex(1,0);
			for (Complex entry : zData) {
				yData.add(Complex.div(complextmp1, entry));
			}
			break;
		default:
			break;
		}
		// Check if output size is equal to input size
		if(this.normalizedData.size() != this.zData.size() ||
				this.normalizedData.size() != this.sData.size()) {
			System.out.println("FATAL: sData or zData entries not equal normalized entries");
		}
	}
	
	//================================================================================
    // Getters
    //================================================================================
	/**
	 * Returns the filename of the dataset source
	 * @return
	 */
	public String getFileName() {
		// TODO Auto-generated method stub
		return this.fname;
	}
	
	/**
	 * Returns the number of datapoints in the RFData set
	 * @return
	 */
	public long size() {
		return this.dataEntries;
	}
	
	/**
	 * Returns the Frequency list of the datapoints
	 * @return
	 */
	public ArrayList<Double> getfData() {
		return new ArrayList<Double>(fData);
	}
	
	/**
	 * Returns a list of Z Data
	 * @return
	 */
	public ArrayList<Complex> getzData() {
		return new ArrayList<Complex>(zData);
	}
	
	/** 
	 * Returns a list of S Data
	 * @return
	 */
	public ArrayList<Complex> getsData() {
		return new ArrayList<Complex>(sData);
	}

	
	/** 
	 * Returns a list of Y Data
	 * @return
	 */
	public ArrayList<Complex> getyData() {
		return new ArrayList<Complex>(yData);
	}

}
