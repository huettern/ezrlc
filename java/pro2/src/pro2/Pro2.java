/**
 * 
 */
package pro2;

import java.io.IOException;

/**
 * @author noah
 *
 */
public class Pro2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RFData data = new RFData("../../sample_files/bsp11.s1p");
		try {
			data.parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
