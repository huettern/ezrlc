/**
 * 
 */
package pro2;

import java.awt.EventQueue;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.util.ArrayUtilities;

/**
 * @author noah
 *
 */
public class Pro2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RFData rfData = null;
		
		
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
			rfData = new RFData(string);
			try {
				rfData.parse();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Writing to file..");
			try {
				FileWriter writer = new FileWriter(string+".tmp");
				for (Complex cpx : rfData.getzData()) {
					writer.write(cpx.sprintRI()+"\r\n");
				}
				writer.close();
				System.out.println(".. Done");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			System.out.println("------------------------------------------------");
		}
		
		

		//================================================================================
	    // XY Test
	    //================================================================================
//		JFrame frame = new JFrame("Charts");
//		frame.setSize(600, 400);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//        
//		DefaultXYDataset ds = new DefaultXYDataset();
//        
//		rfData = new RFData("../../sample_files/bsp2.s1p");
//		try {
//			rfData.parse();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		double[][] tempArray = new double[2][(int)rfData.size()];
//		for (int i = 0; i < rfData.size(); i++) {
//			tempArray[0][i]=rfData.getfData().get(i);
//			tempArray[1][i]=rfData.getzData().get(i).re();
//		}
//		
//        ds.addSeries("series1", tempArray);
//        JFreeChart chart = 
//        		ChartFactory.createXYLineChart("Test Chart",
//        		                "x", "y", ds, PlotOrientation.VERTICAL, true, true,
//        		                false);
//        ChartPanel cp = new ChartPanel(chart);
//        cp.setVisible(true);
//        frame.getContentPane().add(cp);
//        frame.setVisible(true);
		

		//================================================================================
	    // Complex Test
	    //================================================================================
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
//
//		Complex add1 = new Complex(Complex.add(a, a));
//		Complex add2 = new Complex(Complex.add(a, b));
//		Complex add3 = new Complex(Complex.add(a, c));
//		Complex add4 = new Complex(Complex.add(a, d));
//
//		System.out.println(a.sprintRI() +" + " +a.sprintRI() +" = " +add1.sprintRI());
//		System.out.println(a.sprintRI() +" + " +b.sprintRI() +" = " +add2.sprintRI());
//		System.out.println(a.sprintRI() +" + " +c.sprintRI() +" = " +add3.sprintRI());
//		System.out.println(a.sprintRI() +" + " +d.sprintRI() +" = " +add4.sprintRI());
//
//		Complex sub1 = new Complex(Complex.sub(a, a));
//		Complex sub2 = new Complex(Complex.sub(a, b));
//		Complex sub3 = new Complex(Complex.sub(a, c));
//		Complex sub4 = new Complex(Complex.sub(a, d));
//
//		System.out.println(a.sprintRI() +" - " +a.sprintRI() +" = " +sub1.sprintRI());
//		System.out.println(a.sprintRI() +" - " +b.sprintRI() +" = " +sub2.sprintRI());
//		System.out.println(a.sprintRI() +" - " +c.sprintRI() +" = " +sub3.sprintRI());
//		System.out.println(a.sprintRI() +" - " +d.sprintRI() +" = " +sub4.sprintRI());
		
		
		
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
