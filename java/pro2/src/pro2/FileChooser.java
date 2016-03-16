package pro2;

import java.io.File;

import javax.accessibility.Accessible;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser extends JComponent implements Accessible{
	
	JFileChooser fileChooser = new JFileChooser();
	private File file;
	public String filename;
	
	//================================================================================
    // Constructors
    //================================================================================
	public void FileChooser() {
		
	}
	
	public String windowFileChooser() {
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
		}
		return filename = file.getName();
 	}
	
	public void fileFilter() {
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(
		        "Touchstone File Format", "s1p", "z1p", "y1p");
		fileChooser.setFileFilter(fileFilter);
	}
	

}
