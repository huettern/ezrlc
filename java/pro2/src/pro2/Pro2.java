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
		// TODO Auto-generated method stub
		String[] files = {"../../sample_files/bsp1.s1p",
				"../../sample_files/bsp2.s1p",
				"../../sample_files/bsp3.s1p",
				"../../sample_files/bsp4.s1p",
				"../../sample_files/bsp5.s1p",
				"../../sample_files/bsp6.s1p",
				"../../sample_files/bsp11.s1p",
				"../../sample_files/bsp12.s1p",
				"../../sample_files/bsp13.s1p",
				"../../sample_files/bsp14.s1p"
				};
		for (String string : files) {
			RFData data = new RFData(string);
			try {
				data.parse();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("------------------------------------------------");
		}
		
//		System.out.println("------------------------------------------------");
//		System.out.println("Complex Test");
//		System.out.println("------------------------------------------------");
//		Complex a = new Complex(5, 3);
//		Complex b = new Complex(0, -1);
//		Complex c = new Complex(0, 0);
//		Complex d = new Complex(-9, -2);
//
//		Complex mul1 = new Complex(Complex.mul(a, a));
//		Complex mul2 = new Complex(Complex.mul(a, b));
//		Complex mul3 = new Complex(Complex.mul(a, c));
//		Complex mul4 = new Complex(Complex.mul(a, d));
//
//		System.out.println(a.sprintRI() +" * " +a.sprintRI() +" = " +mul1.sprintRI());
//		System.out.println(a.sprintRI() +" * " +b.sprintRI() +" = " +mul2.sprintRI());
//		System.out.println(a.sprintRI() +" * " +c.sprintRI() +" = " +mul3.sprintRI());
//		System.out.println(a.sprintRI() +" * " +d.sprintRI() +" = " +mul4.sprintRI());
//
//		Complex div1 = new Complex(Complex.div(a, a));
//		Complex div2 = new Complex(Complex.div(a, b));
//		Complex div3 = new Complex(Complex.div(a, c));
//		Complex div4 = new Complex(Complex.div(a, d));
//
//		System.out.println(a.sprintRI() +" / " +a.sprintRI() +" = " +div1.sprintRI());
//		System.out.println(a.sprintRI() +" / " +b.sprintRI() +" = " +div2.sprintRI());
//		System.out.println(a.sprintRI() +" / " +c.sprintRI() +" = " +div3.sprintRI());
//		System.out.println(a.sprintRI() +" / " +d.sprintRI() +" = " +div4.sprintRI());
		
		
		
//		EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {                                           
//                Model model = new Model();
//                MainView view = new MainView();
//                view.setVisible(true);
//                Controller controller = new Controller(model,view);
//                view.setController(controller);
//                model.setController(controller);
//                
//                controller.contol();
//            }
//        });  
//		
//		
		
		
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
