/**
 * 
 */
package pro2;

import java.awt.EventQueue;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * @author noah
 *
 */
public class Pro2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* MVC stuff
		 * 
		 */
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                                           
                Model model = new Model();
                MainView view = new MainView();
                view.setVisible(true);
                Controller controller = new Controller(model,view);
                view.setController(controller);
                model.setController(controller);
                
                controller.contol();
            }
        });  
		
		
		
		
//		
//		// TODO Auto-generated method stub
//		RFData data = new RFData("../../sample_files/bsp11.s1p");
//		try {
//			data.parse();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainView frame = new MainView();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		
//		
		
//		
//		/* JFreeChart test
//		 * 
//		 */
//		// create a dataset...
//		DefaultPieDataset datachart = new DefaultPieDataset();
//		datachart.setValue("Category 1", 43.2);
//		datachart.setValue("Category 2", 27.9);
//		datachart.setValue("Category 3", 79.5);
//		// create a chart...
//		JFreeChart chart = ChartFactory.createPieChart(
//				"Sample Pie Chart",
//				datachart,
//				true, // legend?
//				true, // tooltips?
//				false // URLs?
//				);
//		// create and display a frame...
//		ChartFrame frame = new ChartFrame("First", chart);
//		frame.pack();
//		frame.setVisible(true);
		

	}

}
