package pro2.Plot;

import javax.swing.JPanel;

import pro2.MVC.Controller;
import pro2.MVC.Controller.DataSource;
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

public class Figure extends JPanel implements ActionListener, Observer {

	private RectangularPlot rectPlot;
	private JButton btnSettings;
	private RectPlotSettingsWindow settingWindow;
	private Controller controller;
	private JButton btnAddMeasurement;
	private JPanel panel;
	
	private List<Integer> dataIDList = new ArrayList<Integer>();
	
	public Figure(Controller controller, String title) {
		super.setBackground(new Color(238,238,238));
		
		this.controller = controller;
		
		setBackground(UIManager.getColor("ToggleButton.background"));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{309, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		setLayout(gridBagLayout);

		JLabel lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(10, 10, 10, 10);
		gbc_lblTitle.weightx = 1.0;
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		add(lblTitle, gbc_lblTitle);
		
		btnSettings = new JButton("Settings");
		btnSettings.addActionListener(this);
		
		btnAddMeasurement = new JButton("Add Measurement");
		btnAddMeasurement.addActionListener(this);
		GridBagConstraints gbc_btnAddMeasurement = new GridBagConstraints();
		gbc_btnAddMeasurement.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddMeasurement.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddMeasurement.gridx = 1;
		gbc_btnAddMeasurement.gridy = 1;
		add(btnAddMeasurement, gbc_btnAddMeasurement);
		GridBagConstraints gbc_btnSettings = new GridBagConstraints();
		gbc_btnSettings.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSettings.insets = new Insets(0, 0, 5, 0);
		gbc_btnSettings.gridx = 1;
		gbc_btnSettings.gridy = 2;
		add(btnSettings, gbc_btnSettings);


		rectPlot = new RectangularPlot();
		GridBagConstraints gbc_plot = new GridBagConstraints();
		gbc_plot.weighty = 2.0;
		gbc_plot.gridheight = 4;
		gbc_plot.insets = new Insets(5, 5, 5, 5);
		gbc_plot.weightx = 1.0;
		gbc_plot.fill = GridBagConstraints.BOTH;
		gbc_plot.gridx = 0;
		gbc_plot.gridy = 1;
		add(rectPlot, gbc_plot);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		add(panel, gbc_panel);
		
		// Settings Dialog
		settingWindow = new RectPlotSettingsWindow(this.controller);
	}

	public void addDataSet(PlotDataSet z_data) {
		// TODO Auto-generated method stub
		rectPlot.addDataSet(z_data);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btnSettings) {
			settingWindow.show();
		}
		if(e.getSource()==btnAddMeasurement) {
			// TODO Put Dialog window here
			
			// DEBUG
			int id = controller.createDataset(DataSource.FILE, 0, MeasurementType.Z, ComplexModifier.REAL);
			// END DEBUG
			
			// Save the data entry id in the list 
			this.dataIDList.add(id);
			rectPlot.addDataSet(id);
			controller.manualNotify();
			
			rectPlot.repaint();
		}
	}

	
	@Override
	public void update(Observable o, Object arg) {
		rectPlot.update(o, arg);
	}

}
