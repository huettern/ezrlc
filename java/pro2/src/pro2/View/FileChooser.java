package pro2.View;

import java.io.File;

import javax.accessibility.Accessible;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser extends JComponent implements Accessible{
	
	//================================================================================
    // Local Variables
    //================================================================================
	private MainView mainView;
	JFileChooser fileChooser = new JFileChooser();
	private File file;
	
	//================================================================================
    // Constructors
    //================================================================================
	public FileChooser(MainView mainView) {
		this.mainView = mainView;
	}
	
	public String windowFileChooser() {
		int returnVal = fileChooser.showDialog(null, "Open File");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
		}
		return file.getName();
 	}
	
	public void fileFilter() {
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(
		        "Touchstone File Format (*.s1p, *.z1p, *.y1p)", "s1p", "z1p", "y1p");
		fileChooser.setFileFilter(fileFilter);
		
	}

}
