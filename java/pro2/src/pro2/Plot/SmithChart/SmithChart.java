package pro2.Plot.SmithChart;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import pro2.MVC.Model;
import pro2.Plot.PlotDataSet;
import pro2.Plot.RectPlot.DataSetSettings;
import pro2.Plot.RectPlot.RectPlotNewMeasurement;
import pro2.util.Complex;
import pro2.util.MathUtil;
import pro2.util.PointD;
import pro2.util.UIUtil;

public class SmithChart extends JPanel implements Observer, MouseListener {


	//================================================================================
    // Private Data
    //================================================================================
	private SmithChartGrid grid;

//	private List<SmithChartDataSet> data_sets = new ArrayList<SmithChartDataSet>();
	
	private SmithChartSettings settings = new SmithChartSettings();
	
	private List<SmithChartDataSet> dataSets = new ArrayList<SmithChartDataSet>();
	private List<Integer> dataSetIDs = new ArrayList<Integer>();
	private List<DataSetSettings> dataSetSettings = new ArrayList<DataSetSettings>();
		
	//================================================================================
    // Constructor
    //================================================================================
	public SmithChart() {
		super.setBackground(Color.WHITE);
		
		settings.referenceResistance = 50.0;
		
		// Build SmithChartGrid
		grid = new SmithChartGrid(this, settings.referenceResistance);
		
		addMouseListener(this);
		
	}

	//================================================================================
    // Private Functions
    //================================================================================
	/**
	 * Updates the local stored datasets
	 * @param model
	 */
	private void updateDatasets(Model model) {
		int i = 0;
		SmithChartDataSet set;
		for (Integer id : dataSetIDs) {
			set = model.getSmithChartDataSet(id);
			set.setGrid(this.grid);
			set.setDataSetSettings(this.dataSetSettings.get(i));
			this.dataSets.set(i, set);
			i++;
		}
	}

	//================================================================================
    // Public Functions
    //================================================================================
	
//	/**
//	 * Adds a new Dataset to the plot
//	 * @param data Complex List of data
//	 * @param freq Double List of freq data
//	 */
//	public void addDataSet (Complex[] data, double[] freq) {
//		SmithChartDataSet set = new SmithChartDataSet(grid, data, freq, null);
//		dataSets.add(set);	
//	}
	
	/**
	 * Add dataset in model by ID
	 * @param id id in the model
	 * @param newMeasurement settings
	 */
	public void addDataSet(int id, SmithChartNewMeasurement newMeasurement) {
		DataSetSettings set = new DataSetSettings();
		set.setLineColor(UIUtil.getNextColor());
		set.setLabel("<html><B>"+newMeasurement.src_name+"</html></B>");

		this.dataSetIDs.add(id);
		this.dataSetSettings.add(set);
		this.dataSets.add(null);
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		Model model = (Model) o;
		this.updateDatasets(model);
		repaint();
	}
	

	/**
	 * Paints the panel and its contents
	 */
	@Override
    public void paintComponent(Graphics g)
    {
		// Paint parent
        super.paintComponent(g);
        // Paint grid
        grid.paint(g);
        // Paint datasets
        for (SmithChartDataSet set : dataSets) {
        	if(set != null) {
        		set.paint(g);
        	}
		}
        // Paint reference resistance
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Arial", 0, 18));
        g.drawString(String.format("Z0: %s\u2126", MathUtil.num2eng(settings.referenceResistance, 2)), 10, this.getHeight()-10);
    }


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	public SmithChartSettings getSettings() {
		// TODO Auto-generated method stub
		return this.settings;
	}


	public void setSettings(SmithChartSettings s) {
		this.settings = s;
		this.updateSettings();
		repaint();
	}
	
	/**
	 * Returns datasetsettings of the dataset given by the id
	 * @param id data set id
	 * @return
	 */
	public DataSetSettings getDataSetSettings(int id) {
		// search id
		for(int i = 0; i<dataSetIDs.size(); i++){
			if(dataSetIDs.get(i) == id) {
				return dataSetSettings.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns a copy list of data set settings
	 * @return
	 */
	public List<DataSetSettings> getDataSetSettings() {
		return new ArrayList<DataSetSettings>(dataSetSettings);
	}



	private void updateSettings() {
		grid.setReferenceResistance(settings.referenceResistance);
	}
	
	/**
	 * Removes dataset from data set id list
	 * @param id  data set id
	 */
	public void removeDataset(int id) {
		int ctr = 0;
		for (Iterator<Integer> iter = dataSetIDs.iterator(); iter.hasNext(); ctr++) {
			Integer i = iter.next();
			if(i == id) {
				dataSets.remove(ctr);
				dataSetSettings.remove(ctr);
				iter.remove();
			}
		}
		this.repaint();
	}




}
