package pro2.MVC;

import java.io.File;
import java.util.*;

import pro2.RFData.RFData;

public class Model {
	
	private Controller controller;
	
	private List<RFData> rfDataList = new ArrayList<RFData>(); 

	public Model() {
		// TODO Auto-generated constructor stub
		
	}

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
			RFData rf = new RFData(file);
			rf.parse();
			this.rfDataList.add(rf);
			return this.rfDataList.get(this.rfDataList.size() - 1).getUID();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	/**
	 * Returns the full qualified File name of an input file by uid
	 * @param uid
	 * @return
	 */
	public String getFileByUID(UUID uid) {
		// TODO Auto-generated method stub
		for (RFData rfData : this.rfDataList) {
			if(rfData.getUID()==uid) {
				return rfData.getFileName();
			}
		}
		return null;
	}
	
	


}
