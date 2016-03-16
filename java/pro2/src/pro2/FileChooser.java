package pro2;

import javax.accessibility.Accessible;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser extends JComponent implements Accessible{
	
	//================================================================================
    // Constructors
    //================================================================================
	public void FileChooser() {
		
	}
	
	public int windowFileChooser() {
		JFileChooser openFileChooser = new JFileChooser();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Touchstone File Format", "s1p", "z1p", "y1p");
		openFileChooser.setFileFilter(filter);
		int returnVal = openFileChooser.showDialog(null, "Open File");
		return 
	}
}
