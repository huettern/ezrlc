package pro2.View;

import java.io.File;

import javax.accessibility.Accessible;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import pro2.MVC.Controller;

public class FileChooser extends JComponent implements Accessible{
	
	//================================================================================
    // Local Variables
    //================================================================================
	private Controller controller;
	JFileChooser fileChooser = new JFileChooser();
	private File file;
	
	//================================================================================
    // Constructors
    //================================================================================
	public FileChooser(Controller controller) {
		this.controller = controller;
	}
	
	public String showFileChooser() {
		int returnVal = fileChooser.showDialog(this.controller.getMainView(), "Open File");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            return file.getName(); 
		}
		else {
			return null;
		}	
 	}
	
	public void fileFilter() {
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(
		        "Touchstone File Format (*.s1p, *.z1p, *.y1p)", "s1p", "z1p", "y1p");
		fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
		fileChooser.setFileFilter(fileFilter);
		
	}

	public File getFile() {
		// TODO Auto-generated method stub
		return fileChooser.getSelectedFile();
	}

}
