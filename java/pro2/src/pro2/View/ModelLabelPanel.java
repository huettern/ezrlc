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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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

public class ModelLabelPanel extends JPanel implements ActionListener, DocumentListener {

	//================================================================================
    // Local Variables
    //================================================================================
	private Controller controller;
	private boolean lockUpdate;
	
	private JEngineerField txtC0;
	private JEngineerField txtR0;
	private JEngineerField txtAlpha;
	private JEngineerField txtC1;
	private JEngineerField txtL0;
	private JEngineerField txtF;
	private JEngineerField txtR1;
	
	private double[] parameters;
	
	JLabel title;

	private JButton btnDelete;
	private JButton btnOptimize;
	
	private ImageIcon modelImage;
	
	private int eqcID;
	private CircuitType circuitType;
	
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
	 */
	public ModelLabelPanel(int id, CircuitType t) {
		eqcID = id;
		circuitType = t;
		build(t,id);
	}
	
	/**
	 * Creates an empty ModelLabelPanel
	 * @param c controller object
	 */
	public ModelLabelPanel(Controller c) {
		controller = c;
		buildEmpty();
	}
	//================================================================================
    // Private Functions
    //================================================================================
	/**
	 * Builds an empty model label panel
	 */
	private void buildEmpty() {
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
		title = new JLabel("Creating new Model..");
		title.setHorizontalAlignment(JLabel.CENTER);
		pnlTitle.add(title, BorderLayout.CENTER);
		

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
		btnDelete.addActionListener(this);
		pnlBtn.add(btnDelete, 0);
		btnOptimize= new JButton("OPT");
		btnOptimize.addActionListener(this);
		pnlBtn.add(btnOptimize, 1);
	}
	
	/**
	 * Builds a new ModelLabelPanel with the id to the equivalent circuit and the circuit type
	 * @param id ID to the EQC in the model
	 * @param t CircuitType of the model
	 */
	public void build (CircuitType t, int id){
		eqcID = id;
		int ordinal = t.ordinal();
		circuitType = t;
		
		// Title
		title.setText("<html><B>Model "+id +"</B></html>");
		
		// Load image
		modelImage = UIUtil.loadResourceIcon("model_" +  ordinal + ".png", 160, 100);
		JLabel label = new JLabel("", modelImage, JLabel.CENTER);
		label.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
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
			txtR0 = new JEngineerField(4,3,"E24");
			txtR0.getDocument().addDocumentListener(this);
			paramPanel.add(txtR0, new GridBagConstraints(1, yctr++, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
		}
		if(f0EditableLUT[ordinal]) {
			JLabel lblF = new JLabel("f");
			paramPanel.add(lblF, new GridBagConstraints(0, yctr, 1	, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,2,0,0), 0, 0));
			txtF = new JEngineerField(4,3,"E24");
			txtF.getDocument().addDocumentListener(this);
			paramPanel.add(txtF, new GridBagConstraints(1, yctr++, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
		}
		if(alphaEditableLUT[ordinal]) {
			JLabel lbla = new JLabel("\u03B1");
			paramPanel.add(lbla, new GridBagConstraints(0, yctr, 1	, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,2,0,0), 0, 0));
			txtAlpha = new JEngineerField(4,3,"E24");
			txtAlpha.getDocument().addDocumentListener(this);
			paramPanel.add(txtAlpha, new GridBagConstraints(1, yctr++, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
		}
		if(r1EditableLUT[ordinal]) {
			JLabel lblR1 = new JLabel("<html> R<sub>1</sub> </html>");
			paramPanel.add(lblR1, new GridBagConstraints(0, yctr, 1	, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,2,0,0), 0, 0));
			txtR1 = new JEngineerField(4,3,"E24");
			txtR1.getDocument().addDocumentListener(this);
			paramPanel.add(txtR1, new GridBagConstraints(1, yctr++, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
		}
		if(lEditableLUT[ordinal]) {
			JLabel lblL = new JLabel("L");
			paramPanel.add(lblL, new GridBagConstraints(0, yctr, 1	, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,2,0,0), 0, 0));
			txtL0 = new JEngineerField(4,3,"E24");
			txtL0.getDocument().addDocumentListener(this);
			paramPanel.add(txtL0, new GridBagConstraints(1, yctr++, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
		}
		if(c0EditableLUT[ordinal]) {
			JLabel lblc0 = new JLabel("<html> C<sub>0</sub> </html>");
			paramPanel.add(lblc0, new GridBagConstraints(0, yctr, 1	, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,2,0,0), 0, 0));
			txtC0 = new JEngineerField(4,3,"E24");
			txtC0.getDocument().addDocumentListener(this);
			paramPanel.add(txtC0, new GridBagConstraints(1, yctr++, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
		}
		if(c1EditableLUT[ordinal]) {
			JLabel lblc1 = new JLabel("<html> C<sub>1</sub> </html>");
			paramPanel.add(lblc1, new GridBagConstraints(0, yctr, 1	, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,2,0,0), 0, 0));
			txtC1 = new JEngineerField(4,3,"E24");
			txtC1.getDocument().addDocumentListener(this);
			paramPanel.add(txtC1, new GridBagConstraints(1, yctr++, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0));
		}
		
	}
	
	
	

	//================================================================================
    // Interface Functions
    //================================================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnDelete) {
			controller.removeEqCircuit(eqcID);
		}
		if(e.getSource() == btnOptimize) {
			btnOptimize.setEnabled(false);
			controller.optimizeEqCircuit(eqcID);
		}
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
		if(r0EditableLUT[ordinal]) txtR0.setValue(p[0]);
		if(f0EditableLUT[ordinal]) txtF.setValue(p[1]);
		if(alphaEditableLUT[ordinal]) txtAlpha.setValue(p[2]);
		if(r1EditableLUT[ordinal]) txtR1.setValue(p[3]);
		if(lEditableLUT[ordinal]) txtL0.setValue(p[4]);
		if(c0EditableLUT[ordinal]) txtC0.setValue(p[5]);
		if(c1EditableLUT[ordinal]) txtC1.setValue(p[6]);
		btnOptimize.setEnabled(true);
	}
	
	/**
	 * Reads all values and stores them in a parameter list
	 */
	private void parseValues() {
		double p[] = new double[] {0,0,0,0,0,0,0};
		int ordinal = circuitType.ordinal();
		if(r0EditableLUT[ordinal]) p[0] = txtR0.getValue();
		if(f0EditableLUT[ordinal]) p[1] = txtF.getValue();
		if(alphaEditableLUT[ordinal]) p[2] = txtAlpha.getValue();
		if(r1EditableLUT[ordinal]) p[3] = txtR1.getValue();
		if(lEditableLUT[ordinal]) p[4] = txtL0.getValue();
		if(c0EditableLUT[ordinal]) p[5] = txtC0.getValue();
		if(c1EditableLUT[ordinal]) p[6] = txtC0.getValue();
		parameters = p;
	}
	
	//================================================================================
    // Public Functions
    //================================================================================
	public void update(Observable o, Object arg) {
		Model m = (Model)o;
		if(lockUpdate == false) {
			lockUpdate = true;
			updateParams(m.getEquivalentCircuit(this.eqcID));
			updateUI();
			lockUpdate = false;
		}
	}

	public int getID() {
		// TODO Auto-generated method stub
		return this.eqcID;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		if(lockUpdate == false) {
			lockUpdate = true;
			this.parseValues();
			controller.updateEqcParams(eqcID, parameters);
			lockUpdate = false;
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if(lockUpdate == false) {
			lockUpdate = true;
			this.parseValues();
			controller.updateEqcParams(eqcID, parameters);
			lockUpdate = false;
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}
	
}
