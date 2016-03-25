package pro2.Plot;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;

public class Figure extends JPanel {

	private RectangularPlot rectPlot;
	
	public Figure(String title) {
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
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


		rectPlot = new RectangularPlot();
		GridBagConstraints gbc_plot = new GridBagConstraints();
		gbc_plot.insets = new Insets(0, 40, 40, 40);
		gbc_plot.weightx = 1.0;
		gbc_plot.fill = GridBagConstraints.BOTH;
		gbc_plot.gridx = 0;
		gbc_plot.gridy = 1;
		add(rectPlot, gbc_plot);	
	}

	public void addDataSet(PlotDataSet z_data) {
		// TODO Auto-generated method stub
		rectPlot.addDataSet(z_data);
	}

}
