/**
 * 
 */
package ezrlc;

import java.awt.EventQueue;

import ezrlc.MVC.Controller;
import ezrlc.MVC.Model;
import ezrlc.View.MainView;

/**
 * @author noah
 *
 */
public class EzRLC {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * MVC stuff
		 * 
		 */
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Model model = new Model();
				MainView view = new MainView();
				Controller controller = new Controller(model, view);

				view.setController(controller);

				view.build();
				view.setVisible(true);

				// Add observers
				model.addObserver(view);

				controller.contol();
			}
		});
	}
}