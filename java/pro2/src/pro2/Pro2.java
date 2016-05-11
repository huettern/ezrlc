/**
 * 
 */
package pro2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;


import pro2.MVC.Controller;
import pro2.MVC.Controller.DataSource;
import pro2.MVC.Model;
import pro2.ModelCalculation.MCUtil;
import pro2.ModelCalculation.Polynomial;
import pro2.ModelCalculation.MCEqCircuit;
import pro2.ModelCalculation.MCEqCircuit.CIRCUIT_TYPE;
import pro2.ModelCalculation.MCOptions;
import pro2.ModelCalculation.MCRank;
import pro2.Plot.Figure;
import pro2.Plot.RectPlot.RectPlotNewMeasurement;
import pro2.RFData.RFData;
import pro2.RFData.RFData.ComplexModifier;
import pro2.RFData.RFData.MeasurementType;
import pro2.View.MainView;
import pro2.util.Complex;
import pro2.util.MathUtil;

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
//		EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {                                           
//                Model model = new Model();
//                MainView view = new MainView();
//                Controller controller = new Controller(model,view);
//                
//                view.setController(controller);
//                model.setController(controller);
//                
//                view.build();
//                view.setVisible(true);
//                
//                // Add observers
//                model.addObserver(view);
//                
//                controller.contol();
//            }
//        });  

		

		//================================================================================
	    // MC Test
	    //================================================================================
		
		/**
		 * Apply options first
		 */
		MCOptions opt = new MCOptions();
		
		opt.fMin = 25;
		opt.fMax = 55;
		opt.nElementsMin = 2;
		opt.nElementsMax = 3;
		opt.skinEffectEnabled = true;
		
		// fake measurement
		double[] f = {10,20,30,40,50,60,70,80,90,100};
		double[] ys = {1,2,3,4,5,6,7,8,9,10};
		double[] yz = {1,2,3,4,5,6,7,8,9,10};
		

		double[] w = MCUtil.applyMCOpsToF(opt, f, MCUtil.DATA_FORMAT.OMEGA);
		ys = MCUtil.applyMCOpsToData(opt, f, ys);
		yz = MCUtil.applyMCOpsToData(opt, f, yz);
		double[] fn = MCUtil.applyMCOpsToF(opt, f, MCUtil.DATA_FORMAT.HZ);

		CIRCUIT_TYPE[] circuitList;
		circuitList = MCUtil.createModelList(opt);
		
		System.out.println("ES=" +Arrays.toString(circuitList));
		System.out.println("w="+Arrays.toString(w));
		System.out.println("fn="+Arrays.toString(fn));
		System.out.println("yz="+Arrays.toString(yz));
		System.out.println("ys="+Arrays.toString(ys));
		
		/**
		 * Create list of models
		 */
		List<MCEqCircuit> circuits = new ArrayList<MCEqCircuit>(circuitList.length);
		for (CIRCUIT_TYPE type : circuitList) {
			circuits.add(new MCEqCircuit(type));
			circuits.get(circuits.size()-1).setWVector(w);
		}
		
		/**
		 * Do analytic initial guess generation
		 */
		//double[][] paramArray = new double[ES.length][7];
		//paramArray[0] = {}
		double[] params = {100,0,0,0,10e-9,0,0};
		circuits.get(0).setParameters(params);
		
		/**
		 * Generate rank of equivalent circuits
		 */
		ArrayList<MCEqCircuit> sortedList = MCRank.sortByError(ys, circuits);

		//================================================================================
	    // sort test
	    //================================================================================
//		double[][] errortest = {
//				{2,0},
//				{5,1},
//				{9.0,2},
//				{-1,3},
//				{-99,4},
//				{0,5},
//				{2,6},
//				{42,7},
//				{3,8},
//				{0.1,9},
//				};
//		System.out.println(Arrays.toString(errortest[0]));
//		System.out.println(Arrays.toString(errortest[1]));
//		System.out.println(Arrays.toString(errortest[2]));
//		System.out.println(Arrays.toString(errortest[3]));
//		System.out.println(Arrays.toString(errortest[4]));
//		System.out.println(Arrays.toString(errortest[5]));
//		System.out.println(Arrays.toString(errortest[6]));
//		System.out.println(Arrays.toString(errortest[7]));
//		System.out.println(Arrays.toString(errortest[8]));
//		System.out.println(Arrays.toString(errortest[9]));
//		// Sort error array by first col[0], so second col is listed indexes
//		java.util.Arrays.sort(errortest, new java.util.Comparator<double[]>() {
//		    public int compare(double[] a, double[] b) {
//		        return Double.compare(a[0], b[0]);
//		    }
//		});
//		System.out.println(Arrays.toString(errortest[0]));
//		System.out.println(Arrays.toString(errortest[1]));
//		System.out.println(Arrays.toString(errortest[2]));
//		System.out.println(Arrays.toString(errortest[3]));
//		System.out.println(Arrays.toString(errortest[4]));
//		System.out.println(Arrays.toString(errortest[5]));
//		System.out.println(Arrays.toString(errortest[6]));
//		System.out.println(Arrays.toString(errortest[7]));
//		System.out.println(Arrays.toString(errortest[8]));
//		System.out.println(Arrays.toString(errortest[9]));
//		while(true);
		
		//================================================================================
	    // Polyval test
	    //================================================================================
//		Polynomial p = new Polynomial(0,0,0,0,0);
//		
//		System.out.println("equ=" +p.polyval(new Complex(0,0)).sprintRI() );
//		System.out.println("equ=" +p.polyval(new Complex(1,0)).sprintRI() );
//		System.out.println("equ=" +p.polyval(new Complex(10,0)).sprintRI() );
//		System.out.println("equ=" +p.polyval(new Complex(-1,0)).sprintRI() );
//		System.out.println("equ=" +p.polyval(new Complex(-10,0)).sprintRI() );
//		System.out.println("equ=" +p.polyval(new Complex(0.5,0)).sprintRI() );
//		System.out.println("equ=" +p.polyval(new Complex(0,1)).sprintRI() );
		

//		Polynomial n = new Polynomial(3,2,15,8,23);
//		Polynomial d = new Polynomial(42,-3.5,1,0,Math.PI, 69.8);
//		
//		Complex x = new Complex(5,5);
//		
//		System.out.println("equ=" +n.polydiv(d, x).sprintRI() );
		

		
		// Skin
//		double l=1e-6; double c0=1e-9;
//		double r0=100.0; double f0=1000.0; double alpha=0.8;
//
//		// model no 15matlab(14)
//		Polynomial n=new Polynomial(0,0,l,1);
//		Polynomial d=new Polynomial(0,c0*l,c0,1);
//		int[] i = {3};
//		n.setVarRes(r0, f0, alpha, i);
//		i[0] = 2;
//		d.setVarRes(r0, f0, alpha, i);
//
//		
//		
//		double[] w = {2.0*Math.PI*2000, 2.0*Math.PI*3000};
//		System.out.println("nequ1=" +n.polyval(w).get(0).sprintRI() );
//		System.out.println("nequ2=" +n.polyval(w).get(1).sprintRI() );
//		System.out.println("pequ1=" +d.polyval(w).get(0).sprintRI() );
//		System.out.println("pequ2=" +d.polyval(w).get(1).sprintRI() );
//
//		System.out.println("div1=" +n.polydiv(d, w).get(0).sprintRI());
//		System.out.println("div2=" +n.polydiv(d, w).get(1).sprintRI());

		//================================================================================
	    // Model Test
	    //================================================================================
		
		// Plot test
//		double[] params = {20,0,0,0,10e-9,10e-9,0}; 
//		double[] w = MathUtil.linspace(2.0*Math.PI*(1e6), 2.0*Math.PI*(100e6), 100);
//		for (double d : w) {
//			System.out.println(d);
//		}
		
//		MCEqCircuit ec = new MCEqCircuit(CIRCUIT_TYPE.MODEL4, params);
//		ec.setWVector(w);
//		
//		RectPlotNewMeasurement nm = new RectPlotNewMeasurement();
//		nm.type = MeasurementType.Z;
//		nm.cpxMod = ComplexModifier.REAL;
//		nm.eqCircuit = ec;
//		nm.src = DataSource.MODEL;
//		nm.src_name = "Model 0";
//		
//		int id = controller.createDataset(nm);
//		MathUtil.dumpListComplex("tmp.txt", ec.getZ());
//		System.out.println("done");
		
		
		
		
		
		
		
		//================================================================================
	    // Math number format Test
	    //================================================================================
//        double n;
//        n = 10.0*Math.random();
//		System.out.println("n="+n+" format="+MathUtil.formatDouble(n, 2));
//        n = 10.0*Math.random();
//		System.out.println("n="+n+" format="+MathUtil.formatDouble(n, 2));
//        n = 10.0*Math.random();
//		System.out.println("n="+n+" format="+MathUtil.formatDouble(n, 2));
//        n = 10.0*Math.random();
//		System.out.println("n="+n+" format="+MathUtil.formatDouble(n, 2));
//        n = 10.0*Math.random();
//		System.out.println("n="+n+" format="+MathUtil.formatDouble(n, 2));
//        n = 10.0*Math.random();
//		System.out.println("n="+n+" format="+MathUtil.formatDouble(n, 2));
//        n = 10.0;
//		System.out.println("n="+n+" format="+MathUtil.formatDouble(n, 2));
//        n = 1.0;
//		System.out.println("n="+n+" format="+MathUtil.formatDouble(n, 2));
//        n = 1000.0;
//		System.out.println("n="+n+" format="+MathUtil.formatDouble(n, 2));
//        n = 1.01;
//		System.out.println("n="+n+" format="+MathUtil.formatDouble(n, 2));
//		
		
		//================================================================================
	    // Smith Test
	    //================================================================================
                
//         Model model = new Model();
//         MainView view = new MainView();
//         Controller controller = new Controller(model,view);
//                
//                
//		/* Create a little frame containing the testplot */
//		JFrame frame = new JFrame("Smith Test");
//        frame.setSize(500, 400);
//        frame.setLocationRelativeTo(null);
//        
//		/* Read a Datafile and extract the necessary infos */
//		// Read datafile
//		RFData rfData = new RFData("../../sample_files/bsp1.s1p");;
//		try {
//			// Parse datafile
//			rfData.parse();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//        /* Create Plot */
//        Figure fig = new Figure(controller,"Graph 1");
//        fig.buildSmithChart();
//        
//        frame.getContentPane().add(fig);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//        
        /* Create Test Data set */
//		List<Complex> cpxtext = new ArrayList<Complex>();
//		cpxtext.add(new Complex(10.0, 20.0));
//		cpxtext.add(new Complex(1.0, 0.2));
//		cpxtext.add(new Complex(1.0, 0.4));
//		cpxtext.add(new Complex(1.0, 0.8));
//		cpxtext.add(new Complex(1.0, 1.4));
//		cpxtext.add(new Complex(1.0, 2.0));
//		cpxtext.add(new Complex(1.0, 3.0));
//		cpxtext.add(new Complex(1.0, 10.0));
//		cpxtext.add(new Complex(1.0, 20.0));
//		cpxtext.add(new Complex(1.0, 9999999.0));
//		cpxtext.add(new Complex(1.0, -0.0));
//		cpxtext.add(new Complex(1.0, -0.2));
//		cpxtext.add(new Complex(1.0, -0.4));
//		cpxtext.add(new Complex(1.0, -0.8));
//		cpxtext.add(new Complex(1.0, -1.4));
//		cpxtext.add(new Complex(1.0, -2.0));
//		cpxtext.add(new Complex(1.0, -3.0));
//		cpxtext.add(new Complex(1.0, -10.0));
//		cpxtext.add(new Complex(1.0, -20.0));
//		cpxtext.add(new Complex(1.0, -9999999.0));
		//cpxtext=rfData.getsData();

//		MathUtil.dumpListComplex("tmp.txt", cpxtext);
//		
//		List<Double> freq = new ArrayList<Double>();
//		freq = rfData.getfData();
		//MathUtil.dumpListDouble("tmp.txt", freq);
		//freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
//		freq.add(100.0);
		
//		fig.getSmithChart().addDataSet(cpxtext, freq);
//		fig.repaint();
		
		
		//================================================================================
	    // Math Test
	    //================================================================================
//		Double n = 0.0;
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = 1.2;
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = 0.04;
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = 0.08;
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = 3.8;
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = -3.8;
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = 500.0;
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = 750.0;
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = 1990.0;
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = -1990.0;
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = -0.001;
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = 0.9;
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = 0.09;
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = -0.09;
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = Math.random();
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = 10.0*Math.random();
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = -10.0*Math.random();
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = -1.0*Math.random();
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
//		n = 100.0*Math.random();
//		System.out.println("n="+n+" round="+MathUtil.roundNice(n));
		
		//================================================================================
	    // Plot Test
	    //================================================================================
		
		/* Create a little frame containing the testplot */
//		JFrame frame = new JFrame("Test");
//        frame.setSize(500, 400);
//        frame.setLocationRelativeTo(null);
//        
//		/* Read a Datafile and extract the necessary infos */
//		// Read datafile
//		RFData rfData = new RFData("../../sample_files/bsp11.s1p");;
//		try {
//			// Parse datafile
//			rfData.parse();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// Get Z Data
//		List<Double> z_imag = new ArrayList<Double>(rfData.getzData().size());
//        // Extract imaginary part
//        int i = 0;
//        for (Complex in : rfData.getzData()) {
//			z_imag.add(in.im());
//			i++;
//		}
//        // Create a new Dataset using the z Data (y Axis) and f Data (x Axis)
//        PlotDataSet z_data = new PlotDataSet(rfData.getfData(), z_imag);
//
//        /* Create test data set one and two */
//        List<Double> xtest = new ArrayList<Double>();
//        List<Double> ytest = new ArrayList<Double>();
//        
//        for( i = 0; i<100; i++) {
//        	xtest.add(Double.valueOf(i));
//        	ytest.add(Double.valueOf(i/100.0));
//        }
//        PlotDataSet testset = new PlotDataSet(xtest, ytest);
//
//        List<Double> xtest2 = new ArrayList<Double>();
//        List<Double> ytest2 = new ArrayList<Double>();
//        xtest2.add(50.0);
//        xtest2.add(60.0);
//        ytest2.add(0.10);
//        ytest2.add(0.20);
//        PlotDataSet testset2 = new PlotDataSet(xtest2, ytest2);
//
//        /* Create Plot */
//        Figure fig = new Figure("Graph 1");
//        //fig.addDataSet(z_data);		// Real data from s1p files
//        //fig.addDataSet(testset);	// Testset 1, linear from x=0 to 99, y=x/100
//        //fig.addDataSet(testset2);	// Testset 2, two single datapoints
//        
//        frame.getContentPane().add(fig);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
        
        
        
//		JFrame frame = new JFrame();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setBounds(100, 100, 1000, 800);
//		frame.setVisible(true);
////		GridBagLayout gbl = new GridBagLayout();
////		frame.setLayout(gbl);
////		
//		
//		JPanel pnl = new JPanel();
//		//frame.add(pnl);
//		frame.getContentPane().add(pnl);
//		
//		
//		pnl.setVisible(true);
//		pnl.setBackground(Color.WHITE);
		
		
		
		
		//================================================================================
	    // RF Data Test
	    //================================================================================

//		RFData rfData = null;
//		// TODO Auto-generated method stub
//		String[] files = {"../../sample_files/bsp1.s1p",
//				"../../sample_files/bsp2.s1p",
//				"../../sample_files/bsp3.s1p",
//				"../../sample_files/bsp4.s1p",
//				"../../sample_files/bsp5.s1p",
//				"../../sample_files/bsp6.s1p",
//				"../../sample_files/bsp11.s1p",
//				"../../sample_files/bsp12.s1p",
//				"../../sample_files/bsp13.s1p",
//				"../../sample_files/bsp14.s1p",
//				"../../sample_files/r100zRI.s1p", 
//				"../../sample_files/r100l10uZRI.s1p",
//				"../../sample_files/r100l10uZMA.s1p",
//				"../../sample_files/r100l10uZDB.s1p",
//				"../../sample_files/r100l10SZRI.s1p",
//				"../../sample_files/r100l10SZMA.s1p",
//				"../../sample_files/r100l10SZDB.s1p",
//				"../../sample_files/r100l10YZRI.s1p",
//				"../../sample_files/r100l10YZMA.s1p",
//				"../../sample_files/r100l10YZDB.s1p"
//				};
//		for (String string : files) {
//			rfData = new RFData(string);
//			try {
//				rfData.parse();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println("Writing to file..");
//			try {
//				FileWriter writer = new FileWriter(string+".tmp");
//				for (Complex cpx : rfData.getsData()) {
//					writer.write(cpx.sprintRI()+"\r\n");
//				}
//				writer.close();
//				System.out.println(".. Done");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//			System.out.println("------------------------------------------------");
//		}
//
//		Complex complex = new Complex(1,-3);
//		Complex complextmp1;
//		Complex complextmp2 = new Complex(1.0,0);			// constant 1 as complex number
//		Complex complextmp3 = new Complex(50,0);	// resistance as complex number
//
//		// Z=Ro*((1+S)/(1-S))
//		complextmp1 = new Complex(Complex.mul(complextmp3, Complex.div(Complex.add(complextmp2, complex), Complex.sub(complextmp2, complex))));
//		complextmp1.printRI();

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