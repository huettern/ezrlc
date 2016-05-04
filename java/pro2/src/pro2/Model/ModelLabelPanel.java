package pro2.Model;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import pro2.MVC.Controller;
import pro2.Plot.Figure;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.Rectangle;

public class ModelLabelPanel extends JPanel {

	private JTextField txtC0;
	private JTextField txtR0;
	private JTextField txtAlpha;
	private JTextField txtC1;
	private JTextField txtL0;
	private JTextField txtF;
	private JTextField txtR1;
	
	public ModelLabelPanel() {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(150, 100));
		setMaximumSize(new Dimension(32767, 100));
		setMinimumSize(new Dimension(10, 100));
		setBorder(new LineBorder(Color.BLACK));
		setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel pnlModelImage = new JPanel();
		add(pnlModelImage);
		
		JPanel pnlModelLabel = new JPanel();
		add(pnlModelLabel);
		pnlModelLabel.setLayout(new GridLayout(4, 4, 4, 1));
		
		JLabel lblL = new JLabel("L0");
		pnlModelLabel.add(lblL);
		
		txtL0 = new JTextField();
		pnlModelLabel.add(txtL0);
		txtL0.setColumns(10);
		
		JLabel lblR0 = new JLabel("R0");
		pnlModelLabel.add(lblR0);
		
		txtR0 = new JTextField();
		pnlModelLabel.add(txtR0);
		txtR0.setColumns(10);
		
		JLabel lblC = new JLabel("C0");
		pnlModelLabel.add(lblC);
		
		txtC0 = new JTextField();
		pnlModelLabel.add(txtC0);
		txtC0.setColumns(10);
		
		JLabel lblAlpha = new JLabel("\\u03B1");
		pnlModelLabel.add(lblAlpha);
		
		txtAlpha = new JTextField();
		pnlModelLabel.add(txtAlpha);
		txtAlpha.setColumns(10);
		
		JLabel lblR1 = new JLabel("R1");
		pnlModelLabel.add(lblR1);
		
		txtR1 = new JTextField();
		pnlModelLabel.add(txtR1);
		txtR1.setColumns(10);
		
		JLabel f = new JLabel("R1");
		pnlModelLabel.add(f);
		
		txtF = new JTextField();
		pnlModelLabel.add(txtF);
		txtF.setColumns(10);
		
		JLabel lblC1 = new JLabel("C1");
		pnlModelLabel.add(lblC1);
		
		txtC1 = new JTextField();
		pnlModelLabel.add(txtC1);
		txtC1.setColumns(10);
		// TODO Auto-generated constructor stub
	}
}
