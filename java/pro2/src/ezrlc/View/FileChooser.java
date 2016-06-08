package ezrlc.View;

import java.io.File;

import javax.accessibility.Accessible;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import ezrlc.Controller.Controller;

/**
 * File chooser dialug
 * 
 * @author noah
 *
 */
public class FileChooser extends JComponent implements Accessible {
	private static final long serialVersionUID = 1L;
	// ================================================================================
	// Local Variables
	// ================================================================================
	private Controller controller;
	private JFileChooser fileChooser = new JFileChooser();
	private File file;

	// ================================================================================
	// Constructors
	// ================================================================================
	/**
	 * Create new filechooser dialog
	 * 
	 * @param controller
	 *            controller object
	 */
	public FileChooser(Controller controller) {
		this.controller = controller;
	}

	// ================================================================================
	// Public Functions
	// ================================================================================
	/**
	 * open the file chooser and read file name
	 * 
	 * @return string of the choosen filename
	 */
	public String showFileChooser() {
		int returnVal = fileChooser.showDialog(this.controller.getMainView(), "Open File");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			if (file != null)
				return file.getName();
			else
				return null;

		} else {
			return null;
		}
	}

	/**
	 * file filter of the JFileChooser
	 */
	public void fileFilter() {
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Touchstone File Format (*.s1p, *.z1p, *.y1p)",
				"s1p", "z1p", "y1p");
		fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
		fileChooser.setFileFilter(fileFilter);

	}

	/**
	 * get selected file by the JFileChooser
	 * 
	 * @return file
	 */
	public File getFile() {
		return fileChooser.getSelectedFile();
	}

}
