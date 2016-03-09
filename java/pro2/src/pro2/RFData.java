/**
 * 
 */
package pro2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

/**
 * @author noah
 *
 */
public class RFData {

	/**
	 * Defines the available data types in the datafile
	 *
	 */
	public enum MeasurementType {
	    S, Y, Z
	}
	
	/**
	 * Defines the available data units in the datafile
	 * @author noah
	 *
	 */
	public enum MeasurementUnit {
		 MA, DB, RI
	}
	
	/**
	 * 
	 */
	private UUID uid;
	
	private String fname = "";
	
	private long dataEntrys = 0;
	private long commentEntrys = 0;
	private long instructionEntrys = 0;
	private int freqMultiplier = 1;
	private MeasurementType dataType = MeasurementType.S;
	private MeasurementUnit dataUnit = MeasurementUnit.MA;
	private float r = 0;
	
	
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

	public UUID getUID() {
		return this.uid;
	}
	
	public void parse () throws IOException {
		FileReader file;
		String line;
		int lineno = 0;
		
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
		lineno = 1;
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
		    		String[] data = line.split("\\t| ");
		    		// parse each entry
		    		for(int i=0; i<data.length; i++) {
		    			// first entry is unit of freq
		    			if(i==0){
		    				if(data[i].equalsIgnoreCase("HZ"))
		    					this.freqMultiplier = 1;
		    				if(data[i].equalsIgnoreCase("KHZ"))
		    					this.freqMultiplier = 1000;
		    				if(data[i].equalsIgnoreCase("MHZ"))
		    					this.freqMultiplier = 1000000;
		    				if(data[i].equalsIgnoreCase("GHZ"))
		    					this.freqMultiplier = 1000000000;
		    			}
		    			// next entry is parameter type
		    			else if(i==1) {
		    				if(data[i].equalsIgnoreCase("S"))
		    					this.dataType = MeasurementType.S;
		    				if(data[i].equalsIgnoreCase("Y"))
		    					this.dataType = MeasurementType.Y;
		    				if(data[i].equalsIgnoreCase("Z"))
		    					this.dataType = MeasurementType.Z;
		    			}
		    			// next entry is unit
		    			else if(++i==2) {
		    				if(data[i].equalsIgnoreCase("MA"))
		    					this.dataUnit = MeasurementUnit.MA;
		    				if(data[i].equalsIgnoreCase("DB"))
		    					this.dataUnit = MeasurementUnit.DB;
		    				if(data[i].equalsIgnoreCase("RI"))
		    					this.dataUnit = MeasurementUnit.RI;
		    			}
		    			// next entry is constant 'R'
		    			else if(++i==3) {
		    				// TODO: Check if this can be ignored
		    			}
		    			// next entry is equivelant measurement resistance
		    			else if(++i==4) {
		    				this.r = Float.valueOf(data[i]);
		    			}
		    		}
		    	}
		    	// check if first char is a number which means data
		    	else if (Character.isDigit(line.charAt(0))) {
		    		this.dataEntrys++;
		    		
		    	}
		    	
		    	lineno++;
	    	}
	    }
		
	    System.out.println("lines=" +lineno +" comments=" +this.commentEntrys +" instructions=" +this.instructionEntrys +" data=" +this.dataEntrys);
		System.out.println("Freq multiplier="+this.freqMultiplier);
		System.out.println("Type: "+this.dataType+" Units: "+this.dataUnit+" R: "+this.r);
	}

	public String getFileName() {
		// TODO Auto-generated method stub
		return this.fname;
	}

}
