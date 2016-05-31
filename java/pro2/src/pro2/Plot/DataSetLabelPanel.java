package pro2.Plot;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import pro2.MVC.Controller;

import java.awt.Color;
import java.awt.Dimension;

public class DataSetLabelPanel extends JPanel implements ActionListener {

	private int id = 0;
	private JLabel lblTraceName;
	private JLabel lblTraceType;
	private JPanel pnlDrawing;
	private JButton btnRemove;
	private Figure figure;
	
	public DataSetLabelPanel(Figure fig, Color c, int id, String name, String type) {
		this.id = id;
		this.figure = fig;
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(80, 100));
		setMaximumSize(new Dimension(32767, 32767));
		setMinimumSize(new Dimension(10, 100));
		setBorder(new LineBorder(Color.BLACK));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{1.0};
//		gridBagLayout.columnWidths = new int[] {80, 0};
//		gridBagLayout.rowHeights = new int[]{30, 30, 0, 0};
//		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
//		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		pnlDrawing = new JPanel();
		pnlDrawing.setBackground(c);
		GridBagConstraints gbc_pnlDrawing = new GridBagConstraints();
		gbc_pnlDrawing.insets = new Insets(0, 5, 5, 5);
		gbc_pnlDrawing.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlDrawing.weightx = 1.0;
		gbc_pnlDrawing.gridx = 0;
		gbc_pnlDrawing.gridy = 0;
		add(pnlDrawing, gbc_pnlDrawing);
		
		lblTraceName = new JLabel(name, SwingConstants.CENTER);
		GridBagConstraints gbc_lblTraceName = new GridBagConstraints();
		gbc_lblTraceName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTraceName.insets = new Insets(0, 5, 5, 5);
		gbc_lblTraceName.gridx = 0;
		gbc_lblTraceName.gridy = 1;
		add(lblTraceName, gbc_lblTraceName);
		
		lblTraceType = new JLabel(type, SwingConstants.CENTER);
		GridBagConstraints gbc_lblTraceType = new GridBagConstraints();
		gbc_lblTraceType.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTraceType.insets = new Insets(0, 5, 5, 5);
		gbc_lblTraceType.gridx = 0;
		gbc_lblTraceType.gridy = 2;
		add(lblTraceType, gbc_lblTraceType);
		
		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(this);
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.insets = new Insets(0, 5, 0, 5);
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.weightx = 1.0;
		gbc_btnRemove.gridx = 0;
		gbc_btnRemove.gridy = 3;
		add(btnRemove, gbc_btnRemove);
	}

	public void setColor (Color c) {
		this.pnlDrawing.setBackground(c);
	}
	public void setID (int id) {
		this.id = id;
	}
	public int getID () {
		return this.id;
	}
	public void setLabel (String s) {
		this.lblTraceName.setText(s);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnRemove) {
			figure.removeDataset(id);
		}
	}
}
