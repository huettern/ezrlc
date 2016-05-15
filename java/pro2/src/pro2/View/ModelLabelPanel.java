package pro2.View;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Observable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import pro2.MVC.Controller;
import pro2.MVC.Model;
import pro2.ModelCalculation.MCEqCircuit;
import pro2.ModelCalculation.MCEqCircuit.CircuitType;
import pro2.Plot.Figure;
import pro2.util.MathUtil;
import pro2.util.UIUtil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JTextField;
import java.awt.Rectangle;

public class ModelLabelPanel extends JPanel implements ActionListener {

	private JEngineerField txtC0;
	private JEngineerField txtR0;
	private JEngineerField txtAlpha;
	private JEngineerField txtC1;
	private JEngineerField txtL0;
	private JEngineerField txtF;
	private JEngineerField txtR1;

	private JButton btnDelete;
	private JButton btnOptimize;
	
	private ImageIcon modelImage;
	
	private int eqcID;
	
	private final boolean[] r0EditableLUT = {true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,};
	private final boolean[] f0EditableLUT = {false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true};
	private final boolean[] alphaEditableLUT = {false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true,true};
	private final boolean[] r1EditableLUT = {false,false,false,false,false,false,false,false,true,true,true,true,false,false,false,false,true,true,true,true,false};
	private final boolean[] lEditableLUT = {true,true,false,false,true,true,true,true,false,true,true,true,true,true,true,true,false,true,true,true,true};
	private final boolean[] c0EditableLUT = {false,false,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,};
	private final boolean[] c1EditableLUT = {false,false,false,false,false,false,false,false,false,false,false,false,true,false,false,false,false,false,false,false,true};
	
	//================================================================================
    // Constructors
    //================================================================================
	/**
	 * Builds a new ModelLabelPanel with the id to the equivalent circuit and the circuit type
	 * @param id ID to the EQC in the model
	 * @param t CircuitType of the model
	 * @param c Color of the model
	 */
	public ModelLabelPanel(int id, CircuitType t) {
		eqcID = id;
		build(t,id);
	}
	
	
	//================================================================================
    // Private Functions
    //================================================================================
	/**
	 * Builds the panel according to the circuit type
	 * @param t
	 */
	private void build (CircuitType t, int id){
		int ordinal = t.ordinal();
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		setBackground(Color.WHITE);
		setBorder(new LineBorder(Color.BLACK));
		
		// Title
		JPanel pnlTitle = new JPanel();
		pnlTitle.setLayout(new BorderLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(pnlTitle, gbc);
		JLabel title = new JLabel("Model "+id);
		title.setHorizontalAlignment(JLabel.CENTER);
		pnlTitle.add(title, BorderLayout.CENTER);
		
		// Load image
		modelImage = UIUtil.loadResourceIcon("model_" +  ordinal + ".png", 160, 100);
		JLabel label = new JLabel("", modelImage, JLabel.CENTER);
		label.setOpaque(false);
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		add( label, gbc );
		
		// Create parameter panel
		JPanel paramPanel = new JPanel();
		paramPanel.setBackground(Color.WHITE);
		paramPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;
		add( paramPanel, gbc );
		
		// Create Parameters
		int yctr = 1;
		if(r0EditableLUT[ordinal]) {
			JLabel lblR0 = new JLabel("<html> R<sub>0</sub> </html>");
			paramPanel.add(lblR0, new GridBagConstraints(0, yctr, 1	, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,2,0,0), 0, 0));
			txtR0 = new JEngineerField();
			paramPanel.add(txtR0, new GridBagConstraints(1, yctr++, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
		}
		if(f0EditableLUT[ordinal]) {
			JLabel lblF = new JLabel("f");
			paramPanel.add(lblF, new GridBagConstraints(0, yctr, 1	, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,2,0,0), 0, 0));
			txtF = new JEngineerField();
			paramPanel.add(txtF, new GridBagConstraints(1, yctr++, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
		}
		if(alphaEditableLUT[ordinal]) {
			JLabel lbla = new JLabel("\u03B1");
			paramPanel.add(lbla, new GridBagConstraints(0, yctr, 1	, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,2,0,0), 0, 0));
			txtAlpha = new JEngineerField();
			paramPanel.add(txtAlpha, new GridBagConstraints(1, yctr++, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
		}
		if(r1EditableLUT[ordinal]) {
			JLabel lblR1 = new JLabel("<html> R<sub>1</sub> </html>");
			paramPanel.add(lblR1, new GridBagConstraints(0, yctr, 1	, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,2,0,0), 0, 0));
			txtR1 = new JEngineerField();
			paramPanel.add(txtR1, new GridBagConstraints(1, yctr++, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
		}
		if(lEditableLUT[ordinal]) {
			JLabel lblL = new JLabel("L");
			paramPanel.add(lblL, new GridBagConstraints(0, yctr, 1	, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,2,0,0), 0, 0));
			txtL0 = new JEngineerField();
			paramPanel.add(txtL0, new GridBagConstraints(1, yctr++, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
		}
		if(c0EditableLUT[ordinal]) {
			JLabel lblc0 = new JLabel("<html> C<sub>0</sub> </html>");
			paramPanel.add(lblc0, new GridBagConstraints(0, yctr, 1	, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,2,0,0), 0, 0));
			txtC0 = new JEngineerField();
			paramPanel.add(txtC0, new GridBagConstraints(1, yctr++, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
		}
		if(c1EditableLUT[ordinal]) {
			JLabel lblc1 = new JLabel("<html> C<sub>1</sub> </html>");
			paramPanel.add(lblc1, new GridBagConstraints(0, yctr, 1	, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,2,0,0), 0, 0));
			txtC1 = new JEngineerField();
			paramPanel.add(txtC1, new GridBagConstraints(1, yctr++, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
		}
		
		// buttons
		JPanel pnlBtn = new JPanel();
		pnlBtn.setLayout(new GridLayout(1, 2, 0, 0));
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(pnlBtn, gbc);
		
		btnDelete = new JButton("DEL");
		pnlBtn.add(btnDelete, 0);
		btnOptimize= new JButton("OPT");
		pnlBtn.add(btnOptimize, 1);
	}
	
	
	

	//================================================================================
    // Interface Functions
    //================================================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	//================================================================================
    // Paint Functions
    //================================================================================
	/**
	 * Updates the parameters
	 */
	private void updateParams(MCEqCircuit e) {
		double[] p = e.getParameters();
		int ordinal = e.getCircuitType().ordinal();
		if(r0EditableLUT[ordinal]) txtR0.setText(MathUtil.num2eng(p[0], 2));
		if(f0EditableLUT[ordinal]) txtF.setText(MathUtil.num2eng(p[1], 2));
		if(alphaEditableLUT[ordinal]) txtAlpha.setText(MathUtil.num2eng(p[2], 2));
		if(r1EditableLUT[ordinal]) txtR1.setText(MathUtil.num2eng(p[3], 2));
		if(lEditableLUT[ordinal]) txtL0.setText(MathUtil.num2eng(p[4], 2));
		if(c0EditableLUT[ordinal]) txtC0.setText(MathUtil.num2eng(p[5], 2));
		if(c1EditableLUT[ordinal]) txtC1.setText(MathUtil.num2eng(p[6], 2));
	}
	
	//================================================================================
    // Public Functions
    //================================================================================
	public void update(Observable o, Object arg) {
		Model m = (Model)o;
		updateParams(m.getEquivalentCircuit(this.eqcID));
		updateUI();
	}
	
}
