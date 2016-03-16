package pro2;

import java.io.File;

import javax.accessibility.Accessible;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser extends JComponent implements Accessible{
	
	//================================================================================
    // local variables
    //================================================================================
	JFileChooser fileChooser = new JFileChooser();
	private File file;
	
	//================================================================================
    // Constructors
    //================================================================================
	public void FileChooser() {
		
	}
	
	public String windowFileChooser() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception exception) { 
			exception.printStackTrace(); 
		}
		
		int returnVal = fileChooser.showDialog(null, "Open File");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
		}
		return file.getName();
 	}
	
	public void fileFilter() {
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(
		        "Touchstone File Format", "s1p", "z1p", "y1p");
		fileChooser.setFileFilter(fileFilter);
		
	}

}
