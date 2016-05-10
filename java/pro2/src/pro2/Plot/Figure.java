package pro2.Plot;

import javax.swing.JPanel;

import pro2.MVC.Controller;
import pro2.MVC.Controller.DataSource;
import pro2.MVC.Model;
import pro2.Plot.RectPlot.RectPlotAddMeasurementWindow;
import pro2.Plot.RectPlot.RectPlotSettings;
import pro2.Plot.RectPlot.RectPlotSettingsWindow;
import pro2.Plot.RectPlot.RectangularPlot;
import pro2.Plot.SmithChart.SmithChart;
import pro2.Plot.SmithChart.SmithChartAddMeasurementWindow;
import pro2.Plot.SmithChart.SmithChartSettings;
import pro2.Plot.SmithChart.SmithChartSettingsWindow;
import pro2.RFData.RFData.ComplexModifier;
import pro2.RFData.RFData.MeasurementType;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ScrollPaneConstants;

public class Figure extends JPanel implements ActionListener, Observer {

	//================================================================================
    // Private Data
    //================================================================================
	public enum ENPlotType { RECTANGULAR, SMITH};
	ENPlotType plotType = null;
	
	private RectangularPlot rectPlot;
	private SmithChart smithChart;
	private JButton btnSettings;
	private RectPlotSettingsWindow rectPlotSettingWindow;
	private RectPlotAddMeasurementWindow newRectMeasurementWindow;
	private SmithChartAddMeasurementWindow newSmithMeasurementWindow;
	private Controller controller;
	private JButton btnAddMeasurement;
	
	private List<Integer> dataIDList = new ArrayList<Integer>();
	private JButton btnAutoscale;
	private JPanel panel_1;
	
	private SmithChartSettingsWindow smithChartSettingWindow;

	private JButton btnDeleteGraph;
	private JPanel pnlDataSetsBorder;
	private JScrollPane spDataSets;
	private JPanel pnlDataSets;
	
	private int dataSetPnlRowCnt = 0;
	private List<DataSetLabelPanel> dataSetLabelPanels = new ArrayList<DataSetLabelPanel>();

	private GridBagLayout gbl_pnlDataSets;
	private Component verticalGlue;

	//================================================================================
    // Constructors
    //================================================================================
	public Figure(Controller controller) {
		super.setBackground(new Color(238,238,238));
		
		this.controller = controller;
		
		setBackground(UIManager.getColor("ToggleButton.background"));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0};
		setLayout(gridBagLayout);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(5, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.VERTICAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 1;
		add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{95, 0};
		gbl_panel_1.rowHeights = new int[]{29, 29, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		btnAddMeasurement = new JButton("Add Measurement");
		GridBagConstraints gbc_btnAddMeasurement = new GridBagConstraints();
		gbc_btnAddMeasurement.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddMeasurement.insets = new Insets(5, 5, 5, 5);
		gbc_btnAddMeasurement.gridx = 0;
		gbc_btnAddMeasurement.gridy = 0;
		panel_1.add(btnAddMeasurement, gbc_btnAddMeasurement);
		btnAddMeasurement.addActionListener(this);
		
		btnSettings = new JButton("Settings");
		GridBagConstraints gbc_btnSettings = new GridBagConstraints();
		gbc_btnSettings.insets = new Insets(0, 5, 5, 5);
		gbc_btnSettings.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSettings.gridx = 0;
		gbc_btnSettings.gridy = 1;
		panel_1.add(btnSettings, gbc_btnSettings);
		btnSettings.addActionListener(this);
		
		btnAutoscale = new JButton("Autoscale");
		GridBagConstraints gbc_btnAutoscale = new GridBagConstraints();
		gbc_btnAutoscale.insets = new Insets(0, 5, 5, 5);
		gbc_btnAutoscale.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAutoscale.gridx = 0;
		gbc_btnAutoscale.gridy = 2;
		panel_1.add(btnAutoscale, gbc_btnAutoscale);
		
		pnlDataSetsBorder = new JPanel();
		pnlDataSetsBorder.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Data Sets", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlDataSetsBorder = new GridBagConstraints();
		gbc_pnlDataSetsBorder.insets = new Insets(0, 0, 5, 0);
		gbc_pnlDataSetsBorder.anchor = GridBagConstraints.NORTH;
		gbc_pnlDataSetsBorder.fill = GridBagConstraints.BOTH;
		gbc_pnlDataSetsBorder.gridx = 0;
		gbc_pnlDataSetsBorder.gridy = 3;
		panel_1.add(pnlDataSetsBorder, gbc_pnlDataSetsBorder);
		GridBagLayout gbl_pnlDataSetsBorder = new GridBagLayout();
		gbl_pnlDataSetsBorder.columnWidths = new int[] {0};
		gbl_pnlDataSetsBorder.rowHeights = new int[] {0};
		gbl_pnlDataSetsBorder.columnWeights = new double[]{1.0};
		gbl_pnlDataSetsBorder.rowWeights = new double[]{1.0};
		pnlDataSetsBorder.setLayout(gbl_pnlDataSetsBorder);
		
		spDataSets = new JScrollPane();
		spDataSets.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spDataSets.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_spDataSets = new GridBagConstraints();
		gbc_spDataSets.insets = new Insets(0, 0, 0, 0);
		gbc_spDataSets.fill = GridBagConstraints.BOTH;
		gbc_spDataSets.gridx = 0;
		gbc_spDataSets.gridy = 0;
		pnlDataSetsBorder.add(spDataSets, gbc_spDataSets);
		spDataSets.getVerticalScrollBar().setUnitIncrement(25);
		
		pnlDataSets = new JPanel();
		spDataSets.setColumnHeaderView(pnlDataSets);
		spDataSets.setViewportView(pnlDataSets);
		gbl_pnlDataSets = new GridBagLayout();
		gbl_pnlDataSets.columnWidths = new int[]{0};
		gbl_pnlDataSets.rowHeights = new int[] {0};
		gbl_pnlDataSets.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_pnlDataSets.rowWeights = new double[]{Double.MIN_VALUE};
		pnlDataSets.setLayout(gbl_pnlDataSets);
		
		verticalGlue = Box.createVerticalGlue();
		verticalGlue.setMaximumSize(new Dimension(0, 0));
		GridBagConstraints gbc_verticalGlue = new GridBagConstraints();
		gbc_verticalGlue.weighty = 0.0;
		gbc_verticalGlue.weightx = 1.0;
		gbc_verticalGlue.fill = GridBagConstraints.BOTH;
		gbc_verticalGlue.gridx = 0;
		gbc_verticalGlue.gridy = 99;
		pnlDataSets.add(verticalGlue, gbc_verticalGlue);
		
		btnDeleteGraph = new JButton("Delete Graph");
		GridBagConstraints gbc_btnDeleteGraph = new GridBagConstraints();
		gbc_btnDeleteGraph.anchor = GridBagConstraints.SOUTH;
		gbc_btnDeleteGraph.insets = new Insets(0, 5, 5, 5);
		gbc_btnDeleteGraph.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeleteGraph.gridx = 0;
		gbc_btnDeleteGraph.gridy = 4;
		panel_1.add(btnDeleteGraph, gbc_btnDeleteGraph);
		btnDeleteGraph.addActionListener(this);
		btnAutoscale.addActionListener(this);
	}

	//================================================================================
    // Getters
    //================================================================================
	public SmithChart getSmithChart () {
		return this.smithChart;
	}
	
	//================================================================================
    // Public Functions
    //================================================================================
	/**
	 * Builds a Rectangular Plot inside the figure
	 */
	public void buildRectPlot() {
		plotType = ENPlotType.RECTANGULAR;
		rectPlot = new RectangularPlot();
		rectPlot.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_plot = new GridBagConstraints();
		gbc_plot.weighty = 1.0;
		gbc_plot.insets = new Insets(5, 5, 5, 5);
		gbc_plot.weightx = 1.0;
		gbc_plot.fill = GridBagConstraints.BOTH;
		gbc_plot.gridx = 0;
		gbc_plot.gridy = 1;
		add(rectPlot, gbc_plot);

		// Settings Dialog
		rectPlotSettingWindow = new RectPlotSettingsWindow(this.controller, this);
		
		// New Measurement dialog
		newRectMeasurementWindow = new RectPlotAddMeasurementWindow(this.controller, this);
	}
	
	/**
	 * Builds a Smith Chart inside the figure
	 */
	public void buildSmithChart () {
		plotType = ENPlotType.SMITH;
		smithChart = new SmithChart();
		smithChart.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_plot = new GridBagConstraints();
		gbc_plot.weighty = 1.0;
		gbc_plot.insets = new Insets(5, 5, 5, 5);
		gbc_plot.weightx = 1.0;
		gbc_plot.fill = GridBagConstraints.BOTH;
		gbc_plot.gridx = 0;
		gbc_plot.gridy = 1;
		panel_1.remove(btnAutoscale);
		add(smithChart, gbc_plot);
		
		// Settings dialog
		smithChartSettingWindow = new SmithChartSettingsWindow(this.controller, this);
		
		// New Measurement window
		newSmithMeasurementWindow = new SmithChartAddMeasurementWindow(this.controller, this);
	}

	/**
	 * Updates the plot settings
	 */
	public void updatePlotSettings () {
		if(plotType == ENPlotType.RECTANGULAR) {
			rectPlot.setSettings(rectPlotSettingWindow.getSettings());
		} else if (plotType == ENPlotType.SMITH) {
			smithChart.setSettings(smithChartSettingWindow.getSettings());
		}
	}
	
	/**
	 * Adds a new Measurement to the Plot
	 */
	public void addNewMeasurement () {
		int id;
		DataSetLabelPanel p = null;
		if(plotType == ENPlotType.RECTANGULAR) {
			// Create Dataset
			id = controller.createDataset(this.newRectMeasurementWindow.getNewMeasurement());
			// Save the data entry id in the list 
			this.dataIDList.add(id);
			rectPlot.addDataSet(id, this.newRectMeasurementWindow.getNewMeasurement());
			controller.manualNotify();
			rectPlot.repaint();		
			p = new DataSetLabelPanel(this, rectPlot.getDataSetSettings(id).getLineColor(), id, rectPlot.getDataSetSettings(id).getLabel());
		} else if (plotType == ENPlotType.SMITH) {
			id = controller.createDataset(this.newSmithMeasurementWindow.getNewMeasurement());
			// Save the data entry in the list
			this.dataIDList.add(id);
			smithChart.addDataSet(id, this.newSmithMeasurementWindow.getNewMeasurement());
			controller.manualNotify();
			smithChart.repaint();
			p = new DataSetLabelPanel(this, smithChart.getDataSetSettings(id).getLineColor(), id, smithChart.getDataSetSettings(id).getLabel());
		}

		// Dataset list entry
		dataSetLabelPanels.add(p);
		pnlDataSets.add(p, new GridBagConstraints(0, dataSetPnlRowCnt++, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0,0,5,0), 0, 0));
		// adjust pnlDataSets gridbaglayout so that row weights are zero except last one
		double[] d = new double[dataSetPnlRowCnt+1];
		for (int i = 0; i<d.length; i++) { d[i] = 0.0; }
		d[dataSetPnlRowCnt] = 1.0;
		gbl_pnlDataSets.rowWeights = d;
		pnlDataSets.setLayout(gbl_pnlDataSets);
		super.updateUI();
		

	}
	
	/**
	 * Removes a dataset by id
	 * @param id dataset id
	 */
	public void removeDataset(int id) {
		if(this.plotType == ENPlotType.RECTANGULAR) rectPlot.removeDataset(id);
		if(this.plotType == ENPlotType.SMITH) smithChart.removeDataset(id);
		controller.removeDataset(this.plotType, id);
	}

	//================================================================================
    // Interfaces
    //================================================================================
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btnSettings) {
			if(plotType == ENPlotType.RECTANGULAR) {
				RectPlotSettings s = rectPlot.getSettings();
				rectPlotSettingWindow.setSettings(s);
				rectPlotSettingWindow.show();
			} else if (plotType == ENPlotType.SMITH) {
				SmithChartSettings s = smithChart.getSettings();
				smithChartSettingWindow.setSettings(s);
				smithChartSettingWindow.show();
			}
		}
		if(e.getSource()==btnAddMeasurement) {
			if(plotType == ENPlotType.RECTANGULAR) {
				newRectMeasurementWindow.setFilename(controller.getFilename());
				newRectMeasurementWindow.show();
			} else if (plotType == ENPlotType.SMITH) {
				newSmithMeasurementWindow.setFilename(controller.getFilename());
				newSmithMeasurementWindow.setDatasets(smithChart.getDataSetSettings());
				newSmithMeasurementWindow.show();
			}
		}
		if(e.getSource()==btnAutoscale) {
			if(plotType == ENPlotType.RECTANGULAR) rectPlot.autoScale();
		}
		if(e.getSource()==btnDeleteGraph){
			controller.deleteFigure(this);
		}
			
	}

	
	@Override
	public void update(Observable o, Object arg) {
		Model model = (Model)o;
		// update plots
		if(plotType == ENPlotType.RECTANGULAR) rectPlot.update(o, arg);
		else if(plotType == ENPlotType.SMITH) smithChart.update(o, arg);
		// update data set labels
		for (DataSetLabelPanel lbl : dataSetLabelPanels) {
			// if data set doesnt exist remove panel
			if(model.isDataset(this.plotType, lbl.getID()) == false) {
				pnlDataSets.remove(lbl);
				lbl = null;
			}
		}
		pnlDataSets.revalidate();
		pnlDataSets.repaint();
	}

}
