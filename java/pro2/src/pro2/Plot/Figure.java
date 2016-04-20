package pro2.Plot;

import javax.swing.JPanel;

import pro2.MVC.Controller;
import pro2.MVC.Controller.DataSource;
import pro2.Plot.RectPlot.RectPlotAddMeasurementWindow;
import pro2.Plot.RectPlot.RectPlotSettings;
import pro2.Plot.RectPlot.RectPlotSettingsWindow;
import pro2.Plot.RectPlot.RectangularPlot;
import pro2.Plot.SmithChart.SmithChart;
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

public class Figure extends JPanel implements ActionListener, Observer {

	private RectangularPlot rectPlot;
	private SmithChart smithChart;
	private JButton btnSettings;
	private RectPlotSettingsWindow settingWindow;
	private RectPlotAddMeasurementWindow newMeasurementWindow;
	private Controller controller;
	private JButton btnAddMeasurement;
	
	private List<Integer> dataIDList = new ArrayList<Integer>();
	private JButton btnAutoscale;
	private JPanel panel_1;
	
	public Figure(Controller controller, String title) {
		super.setBackground(new Color(238,238,238));
		
		this.controller = controller;
		
		setBackground(UIManager.getColor("ToggleButton.background"));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{309, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0};
		setLayout(gridBagLayout);

		JLabel lblTitle = new JLabel(title);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTitle.gridwidth = 2;
		gbc_lblTitle.insets = new Insets(10, 10, 10, 10);
		gbc_lblTitle.weightx = 1.0;
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		add(lblTitle, gbc_lblTitle);
		
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
		gbl_panel_1.rowHeights = new int[]{29, 29, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		btnAddMeasurement = new JButton("Add Measurement");
		GridBagConstraints gbc_btnAddMeasurement = new GridBagConstraints();
		gbc_btnAddMeasurement.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddMeasurement.insets = new Insets(5, 5, 0, 5);
		gbc_btnAddMeasurement.gridx = 0;
		gbc_btnAddMeasurement.gridy = 0;
		panel_1.add(btnAddMeasurement, gbc_btnAddMeasurement);
		btnAddMeasurement.addActionListener(this);
		
		btnSettings = new JButton("Settings");
		GridBagConstraints gbc_btnSettings = new GridBagConstraints();
		gbc_btnSettings.insets = new Insets(0, 5, 0, 5);
		gbc_btnSettings.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSettings.gridx = 0;
		gbc_btnSettings.gridy = 1;
		panel_1.add(btnSettings, gbc_btnSettings);
		btnSettings.addActionListener(this);
		
		btnAutoscale = new JButton("Autoscale");
		GridBagConstraints gbc_btnAutoscale = new GridBagConstraints();
		gbc_btnAutoscale.insets = new Insets(0, 5, 0, 5);
		gbc_btnAutoscale.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAutoscale.gridx = 0;
		gbc_btnAutoscale.gridy = 2;
		panel_1.add(btnAutoscale, gbc_btnAutoscale);
		btnAutoscale.addActionListener(this);
	}
	
	/**
	 * Builds a Rectangular Plot inside the figure
	 */
	public void buildRectPlot() {
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
		settingWindow = new RectPlotSettingsWindow(this.controller, this);
		
		// New Measurement dialog
		newMeasurementWindow = new RectPlotAddMeasurementWindow(this.controller, this);
	}
	
	/**
	 * Builds a Smith Chart inside the figure
	 */
	public void buildSmithChart () {
		smithChart = new SmithChart();
		smithChart.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_plot = new GridBagConstraints();
		gbc_plot.weighty = 1.0;
		gbc_plot.insets = new Insets(5, 5, 5, 5);
		gbc_plot.weightx = 1.0;
		gbc_plot.fill = GridBagConstraints.BOTH;
		gbc_plot.gridx = 0;
		gbc_plot.gridy = 1;
		add(smithChart, gbc_plot);
	}

	/**
	 * Updates the plot settings
	 */
	public void updatePlotSettings () {
		rectPlot.setSettings(settingWindow.getSettings());
	}
	
	/**
	 * Adds a new Measurement to the Plot
	 */
	public void addNewMeasurement () {
		// Create Dataset
		int id = controller.createDataset(this.newMeasurementWindow.getNewMeasurement());
		
		// Save the data entry id in the list 
		this.dataIDList.add(id);
		rectPlot.addDataSet(id, this.newMeasurementWindow.getNewMeasurement());
		controller.manualNotify();
		
		rectPlot.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btnSettings) {
			RectPlotSettings s = rectPlot.getSettings();
			settingWindow.setSettings(s);
			settingWindow.show();
		}
		if(e.getSource()==btnAddMeasurement) {
			newMeasurementWindow.setFilename(controller.getFilename());
			newMeasurementWindow.show();
		}
		if(e.getSource()==btnAutoscale) {
			rectPlot.autoScale();
		}
	}

	
	@Override
	public void update(Observable o, Object arg) {
		rectPlot.update(o, arg);
	}

}
