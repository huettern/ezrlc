package pro2.Plot;

import javax.swing.JPanel;

import pro2.MVC.Controller;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.JButton;

public class Figure extends JPanel implements ActionListener {

	private RectangularPlot rectPlot;
	private JButton btnSettings;
	private RectPlotSettingsWindow settingWindow;
	private Controller controller;
	
	public Figure(Controller controller, String title) {
		this.controller = controller;
		
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
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
		GridBagConstraints gbc_btnSettings = new GridBagConstraints();
		gbc_btnSettings.insets = new Insets(0, 0, 5, 0);
		gbc_btnSettings.gridx = 1;
		gbc_btnSettings.gridy = 0;
		add(btnSettings, gbc_btnSettings);


		rectPlot = new RectangularPlot();
		GridBagConstraints gbc_plot = new GridBagConstraints();
		gbc_plot.insets = new Insets(0, 40, 40, 40);
		gbc_plot.weightx = 1.0;
		gbc_plot.fill = GridBagConstraints.BOTH;
		gbc_plot.gridx = 0;
		gbc_plot.gridy = 1;
		add(rectPlot, gbc_plot);
		
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
	}

}
