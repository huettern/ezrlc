package pro2.Plot;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

public class DataSetLabelPanel extends JPanel {

	public DataSetLabelPanel() {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(159, 100));
		setMaximumSize(new Dimension(32767, 100));
		setMinimumSize(new Dimension(10, 100));
		setBorder(new LineBorder(Color.BLACK));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {80, 0};
		gridBagLayout.rowHeights = new int[]{30, 30, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel pnlDrawing = new JPanel();
		GridBagConstraints gbc_pnlDrawing = new GridBagConstraints();
		gbc_pnlDrawing.insets = new Insets(0, 0, 5, 0);
		gbc_pnlDrawing.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlDrawing.gridx = 0;
		gbc_pnlDrawing.gridy = 0;
		add(pnlDrawing, gbc_pnlDrawing);
		
		JLabel lblTraceName = new JLabel("trace name");
		GridBagConstraints gbc_lblTraceName = new GridBagConstraints();
		gbc_lblTraceName.insets = new Insets(0, 0, 5, 0);
		gbc_lblTraceName.gridx = 0;
		gbc_lblTraceName.gridy = 1;
		add(lblTraceName, gbc_lblTraceName);
		
		JButton btnRemove = new JButton("Remove");
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.gridx = 0;
		gbc_btnRemove.gridy = 2;
		add(btnRemove, gbc_btnRemove);
		// TODO Auto-generated constructor stub
	}

}
