package pro2.MVC;

import pro2.MVC.Model;
import pro2.View.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.UUID;

public class Controller {
	private Model model;
    private MainView view;
    private ActionListener actionListener;

	public Controller(Model model, MainView view) {
		// TODO Auto-generated constructor stub
		this.model = model;
        this.view = view;
		
	}

	public void contol() {
		// TODO Auto-generated method stub
		System.out.println("control");
		
	}
	/**
	 * Reads the inputfile given by the user
	 * @param file
	 */
	public void loadFile (File file) {
        System.out.println("Opening: " + file.getName());
        // Read the file
        UUID uid = this.model.newInputFile(file);
        if(uid != null) {
        	// Put the file on the view
        	this.view.addFileListItem(file.getName(), uid);
        }
        
        
        
		
	}
	
	

}
