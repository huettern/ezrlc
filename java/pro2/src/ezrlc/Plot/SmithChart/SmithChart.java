package ezrlc.Plot.SmithChart;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import ezrlc.Model.Model;
import ezrlc.Model.SmithChartNewMeasurement;
import ezrlc.Plot.DataSetSettings;
import ezrlc.util.MathUtil;
import ezrlc.util.UIUtil;

/**
 * A simple smith chart
 * @author noah
 *
 */
public class SmithChart extends JPanel implements Observer {
	private static final long serialVersionUID = 1L;

	// ================================================================================
	// Private Data
	// ================================================================================
	private SmithChartGrid grid;

	private SmithChartSettings settings = new SmithChartSettings();

	private List<SmithChartDataSet> dataSets = new ArrayList<SmithChartDataSet>();
	private List<Integer> dataSetIDs = new ArrayList<Integer>();
	private List<DataSetSettings> dataSetSettings = new ArrayList<DataSetSettings>();

	// ================================================================================
	// Constructor
	// ================================================================================
	/**
	 * Create new smithchart
	 */
	public SmithChart() {
		super.setBackground(Color.WHITE);

		settings.referenceResistance = 50.0;

		// Build SmithChartGrid
		grid = new SmithChartGrid(this, settings.referenceResistance);
	}

	// ================================================================================
	// Private Functions
	// ================================================================================
	/**
	 * Updates the local stored datasets
	 * 
	 * @param model
	 */
	private void updateDatasets(Model model) {
		int i = 0;
		SmithChartDataSet set;
		for (Integer id : dataSetIDs) {
			set = new SmithChartDataSet(model.getDataSet(id));
			set.setGrid(this.grid);
			set.setDataSetSettings(this.dataSetSettings.get(i));
			this.dataSets.set(i, set);
			i++;
		}
	}

	// ================================================================================
	// Public Functions
	// ================================================================================
	/**
	 * Add dataset in model by ID
	 * 
	 * @param id
	 *            id in the model
	 * @param newMeasurement
	 *            settings
	 */
	public void addDataSet(int id, SmithChartNewMeasurement newMeasurement) {
		DataSetSettings set = new DataSetSettings();
		set.setLineColor(UIUtil.getNextColor());
		set.setLabelName("<html><B>" + newMeasurement.src_name + "</html></B>");

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
	public void paintComponent(Graphics g) {
		// Paint parent
		super.paintComponent(g);
		// Paint grid
		grid.paint(g);
		// Paint datasets
		for (SmithChartDataSet set : dataSets) {
			if (set != null) {
				set.paint(g);
			}
		}
		// Paint reference resistance
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Arial", 0, 18));
		g.drawString(String.format("Z0: %s\u2126", MathUtil.num2eng(settings.referenceResistance, 2)), 10,
				this.getHeight() - 10);
	}

	public SmithChartSettings getSettings() {
		return this.settings;
	}

	public void setSettings(SmithChartSettings s) {
		this.settings = s;
		this.updateSettings();
		repaint();
	}

	/**
	 * Returns datasetsettings of the dataset given by the id
	 * 
	 * @param id
	 *            data set id
	 * @return settings
	 */
	public DataSetSettings getDataSetSettings(int id) {
		// search id
		for (int i = 0; i < dataSetIDs.size(); i++) {
			if (dataSetIDs.get(i) == id) {
				return dataSetSettings.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns a copy list of data set settings
	 * 
	 * @return list
	 */
	public List<DataSetSettings> getDataSetSettings() {
		return new ArrayList<DataSetSettings>(dataSetSettings);
	}

	private void updateSettings() {
		grid.setReferenceResistance(settings.referenceResistance);
	}

	/**
	 * Removes dataset from data set id list
	 * 
	 * @param id
	 *            data set id
	 */
	public void removeDataset(int id) {
		int ctr = 0;
		for (Iterator<Integer> iter = dataSetIDs.iterator(); iter.hasNext(); ctr++) {
			Integer i = iter.next();
			if (i == id) {
				dataSets.remove(ctr);
				dataSetSettings.remove(ctr);
				iter.remove();
			}
		}
		this.repaint();
	}

	/**
	 * Returns the dataset IDs displayed in the plot
	 * @return
	 */
	public int[] getDataSetIDs() {
		int[] ids = new int[dataSetIDs.size()];
		for(int i = 0; i < dataSetIDs.size(); i++) {
			ids [i] = dataSetIDs.get(i);
		}
		return ids;
	}
}
