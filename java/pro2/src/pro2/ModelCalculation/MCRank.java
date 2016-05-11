package pro2.ModelCalculation;

import java.util.ArrayList;
import java.util.List;

import pro2.util.Complex;

/**
 * Handles rank generation of model list
 * @author noah
 *
 */
public class MCRank {

	//================================================================================
    // Constructor
    //================================================================================
	public MCRank() {
		// TODO Auto-generated constructor stub
	}
	

	//================================================================================
    // Public static methods
    //================================================================================
	/**
	 * Sorts the incomming list of equivalent circuits by their error, best first 
	 * @param ys measured data
	 * @param in list of circuit models
	 * @return sorted models, best first
	 */
	public static final ArrayList<MCEqCircuit> sortByError (Complex[] ys, List<MCEqCircuit> in) {
		ArrayList<MCEqCircuit> res = new ArrayList<MCEqCircuit>(in.size());
		ArrayList<Complex> data;
		double[] magmod = new double[in.get(0).getWSize()];
		double[] magmeas = new double[in.get(0).getWSize()];
		double[][] error = new double[in.size()][2];
		
		// Fill error with indexes
		for(int i = 0; i<in.size(); i++) {
			error[i][1] = i;
		}
		
		// Get error of all incomming modles
		for (int j = 0; j < in.size(); j++) {
			data = in.get(j).getS();
			// get magnitude of S
			for (int i = 0; i < data.size(); i++) {
				magmod[i] = data.get(i).abs();
				magmeas[i] = ys[i].abs();
			}
			// calculate error
			error[j][0] = MCErrorSum.getError(magmeas, magmod);
		}
		
		// Sort error array by first col[0], so second col is listed indexes
		java.util.Arrays.sort(error, new java.util.Comparator<double[]>() {
		    public int compare(double[] a, double[] b) {
		        return Double.compare(a[0], b[0]);
		    }
		});
		
		// Sort input list by error array
		for(int i = 0; i < in.size(); i++){
			res.add(in.get((int)error[i][1]));
		}
		
		return res;
	}
	
	
}
