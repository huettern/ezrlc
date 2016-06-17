package ezrlc.View;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ezrlc.Controller.Controller;
import ezrlc.Model.Model;
import ezrlc.ModelCalculation.MCEqCircuit;
import ezrlc.ModelCalculation.MCEqCircuit.CircuitType;
import ezrlc.util.UIUtil;

/**
 * Contains GUI elements for the model label panel that displays model
 * parameters
 * 
 * @author noah
 *
 */
public class ModelLabelPanel extends JPanel implements ActionListener, DocumentListener {
	private static final long serialVersionUID = 1L;
	// ================================================================================
	// Local Variables
	// ================================================================================
	private Controller controller;
	private boolean lockUpdate = false;
	private boolean modelPanelBuilt = false;
	private OptimizerSettingsWindow optWindow;
	
	private JEngineerField txtC0;
	private JEngineerField txtR0;
	private JEngineerField txtAlpha;
	private JEngineerField txtC1;
	private JEngineerField txtL0;
	private JEngineerField txtF;
	private JEngineerField txtR1;

	private JCheckBox chbxC0lock;
	private JCheckBox chbxR0lock;
	private JCheckBox chbxFlock;
	private JCheckBox chbxAlphalock;
	private JCheckBox chbxR1lock;
	private JCheckBox chbxL0lock;
	private JCheckBox chbxC1lock;

	private double[] parameters;

	JLabel title;

	private JButton btnDelete;
	private JButton btnOptimize;

	private ImageIcon modelImage;

	private int eqcID;
	private CircuitType circuitType;

	private final boolean[] r0EditableLUT = { true, true, true, true, true, true, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true };
	private final boolean[] f0EditableLUT = { false, false, false, false, false, false, false, false, false, false,
			false, false, false, true, true, true, true, true, true, true, true };
	private final boolean[] alphaEditableLUT = { false, false, false, false, false, false, false, false, false, false,
			false, false, false, true, true, true, true, true, true, true, true };
	private final boolean[] r1EditableLUT = { false, false, false, false, false, false, false, false, true, true, true,
			true, false, false, false, false, true, true, true, true, false };
	private final boolean[] lEditableLUT = { true, true, false, false, true, true, true, true, false, true, true, true,
			true, true, true, true, false, true, true, true, true };
	private final boolean[] c0EditableLUT = { false, false, true, true, true, true, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true };
	private final boolean[] c1EditableLUT = { false, false, false, false, false, false, false, false, false, false,
			false, false, true, false, false, false, false, false, false, false, true };

	// ================================================================================
	// Constructors
	// ================================================================================

	/**
	 * Creates an empty ModelLabelPanel
	 * 
	 * @param c
	 *            controller object
	 */
	public ModelLabelPanel(Controller c) {
		controller = c;
		optWindow = new OptimizerSettingsWindow(c);
		buildEmpty();
		modelPanelBuilt = false;
	}

	// ================================================================================
	// Private Functions
	// ================================================================================
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
		btnOptimize = new JButton("OPT");
		btnOptimize.addActionListener(this);
		pnlBtn.add(btnOptimize, 1);

		btnOptimize.setEnabled(false);
		btnDelete.setEnabled(true);
	}

	/**
	 * Builds a new ModelLabelPanel with the id to the equivalent circuit and
	 * the circuit type
	 * 
	 * @param id
	 *            ID to the EQC in the model
	 * @param t
	 *            CircuitType of the model
	 */
	public void build(CircuitType t, int id) {
		eqcID = id;
		int ordinal = t.ordinal();
		circuitType = t;

		// Title
		title.setText("<html><B>Model " + id + "</B></html>");

		// Load image
		modelImage = UIUtil.loadResourceIcon("model_" + ordinal + ".png", 160, 100);
		JLabel label = new JLabel("", modelImage, JLabel.CENTER);
		label.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(label, gbc);

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
		add(paramPanel, gbc);

		// Create Parameters
		int yctr = 1;
		if (r0EditableLUT[ordinal]) {
			JLabel lblR0 = new JLabel("<html> R<sub>0</sub> </html>");
			paramPanel.add(lblR0, new GridBagConstraints(0, yctr, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 2, 0, 0), 0, 0));
			txtR0 = new JEngineerField(4, 3, "E24");
			txtR0.getDocument().addDocumentListener(this);
			txtR0.addActionListener(this);
			paramPanel.add(txtR0, new GridBagConstraints(1, yctr, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			chbxR0lock = new JCheckBox();
			chbxR0lock.setToolTipText("Lock component value");
			paramPanel.add(chbxR0lock, new GridBagConstraints(2, yctr++, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		}
		if (f0EditableLUT[ordinal]) {
			JLabel lblF = new JLabel("f");
			paramPanel.add(lblF, new GridBagConstraints(0, yctr, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 2, 0, 0), 0, 0));
			txtF = new JEngineerField(4, 3, "E24");
			txtF.getDocument().addDocumentListener(this);
			txtF.addActionListener(this);
			paramPanel.add(txtF, new GridBagConstraints(1, yctr, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			chbxFlock = new JCheckBox();
			chbxFlock.setToolTipText("Lock component value");
			paramPanel.add(chbxFlock, new GridBagConstraints(2, yctr++, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		}
		if (alphaEditableLUT[ordinal]) {
			JLabel lbla = new JLabel("\u03B1");
			paramPanel.add(lbla, new GridBagConstraints(0, yctr, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 2, 0, 0), 0, 0));
			txtAlpha = new JEngineerField(4, 3, "E24");
			txtAlpha.getDocument().addDocumentListener(this);
			txtAlpha.addActionListener(this);
			paramPanel.add(txtAlpha, new GridBagConstraints(1, yctr, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			chbxAlphalock = new JCheckBox();
			chbxAlphalock.setToolTipText("Lock component value");
			paramPanel.add(chbxAlphalock, new GridBagConstraints(2, yctr++, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		}
		if (r1EditableLUT[ordinal]) {
			JLabel lblR1 = new JLabel("<html> R<sub>1</sub> </html>");
			paramPanel.add(lblR1, new GridBagConstraints(0, yctr, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 2, 0, 0), 0, 0));
			txtR1 = new JEngineerField(4, 3, "E24");
			txtR1.getDocument().addDocumentListener(this);
			txtR1.addActionListener(this);
			paramPanel.add(txtR1, new GridBagConstraints(1, yctr, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			chbxR1lock = new JCheckBox();
			chbxR1lock.setToolTipText("Lock component value");
			paramPanel.add(chbxR1lock, new GridBagConstraints(2, yctr++, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		}
		if (lEditableLUT[ordinal]) {
			JLabel lblL = new JLabel("L");
			paramPanel.add(lblL, new GridBagConstraints(0, yctr, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 2, 0, 0), 0, 0));
			txtL0 = new JEngineerField(4, 3, "E24");
			txtL0.getDocument().addDocumentListener(this);
			txtL0.addActionListener(this);
			paramPanel.add(txtL0, new GridBagConstraints(1, yctr, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			chbxL0lock = new JCheckBox();
			chbxL0lock.setToolTipText("Lock component value");
			paramPanel.add(chbxL0lock, new GridBagConstraints(2, yctr++, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		}
		if (c0EditableLUT[ordinal]) {
			JLabel lblc0 = new JLabel("<html> C<sub>0</sub> </html>");
			paramPanel.add(lblc0, new GridBagConstraints(0, yctr, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 2, 0, 0), 0, 0));
			txtC0 = new JEngineerField(4, 3, "E24");
			txtC0.getDocument().addDocumentListener(this);
			txtC0.addActionListener(this);
			paramPanel.add(txtC0, new GridBagConstraints(1, yctr, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			chbxC0lock = new JCheckBox();
			chbxC0lock.setToolTipText("Lock component value");
			paramPanel.add(chbxC0lock, new GridBagConstraints(2, yctr++, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		}
		if (c1EditableLUT[ordinal]) {
			JLabel lblc1 = new JLabel("<html> C<sub>1</sub> </html>");
			paramPanel.add(lblc1, new GridBagConstraints(0, yctr, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 2, 0, 0), 0, 0));
			txtC1 = new JEngineerField(4, 3, "E24");
			txtC1.getDocument().addDocumentListener(this);
			txtC1.addActionListener(this);
			paramPanel.add(txtC1, new GridBagConstraints(1, yctr, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
			chbxC1lock = new JCheckBox();
			chbxC1lock.setToolTipText("Lock component value");
			paramPanel.add(chbxC1lock, new GridBagConstraints(2, yctr++, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
					GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		}
		modelPanelBuilt = true;
	}

	// ================================================================================
	// Paint Functions
	// ================================================================================
	/**
	 * Updates the parameters
	 */
	private void updateParams(MCEqCircuit e) {
		double[] p = e.getParameters();
		int ordinal = e.getCircuitType().ordinal();
		if (r0EditableLUT[ordinal])
			txtR0.setValue(p[0]);
		if (f0EditableLUT[ordinal])
			txtF.setValue(p[1]);
		if (alphaEditableLUT[ordinal])
			txtAlpha.setValue(p[2]);
		if (r1EditableLUT[ordinal])
			txtR1.setValue(p[3]);
		if (lEditableLUT[ordinal])
			txtL0.setValue(p[4]);
		if (c0EditableLUT[ordinal])
			txtC0.setValue(p[5]);
		if (c1EditableLUT[ordinal])
			txtC1.setValue(p[6]);
		btnOptimize.setEnabled(true);
		btnDelete.setEnabled(true);
		title.setText("<html><B>Model " + this.eqcID + "</B></html>");
	}

	/**
	 * Reads all values and stores them in a parameter list
	 */
	private void parseValues() {
		double p[] = new double[] { 0, 0, 0, 0, 0, 0, 0 };
		int ordinal = circuitType.ordinal();
		if (r0EditableLUT[ordinal])
			p[0] = txtR0.getValue();
		if (f0EditableLUT[ordinal])
			p[1] = txtF.getValue();
		if (alphaEditableLUT[ordinal])
			p[2] = txtAlpha.getValue();
		if (r1EditableLUT[ordinal])
			p[3] = txtR1.getValue();
		if (lEditableLUT[ordinal])
			p[4] = txtL0.getValue();
		if (c0EditableLUT[ordinal])
			p[5] = txtC0.getValue();
		if (c1EditableLUT[ordinal])
			p[6] = txtC1.getValue();
		parameters = p;
	}

	/**
	 * parses values and calls controller to do update
	 */
	private void tuner() {
		if (lockUpdate == false) {
			lockUpdate = true;
			this.parseValues();
			controller.updateEqcParams(eqcID, parameters);
			lockUpdate = false;
		}
	}
	
	/**
	 * Reads the lock chechboxes and stores them into an array fitting to the parameter array
	 * @return parameter array with lock flags
	 */
	private boolean[] parseLockBox() {
		boolean[] l = new boolean[7];
		if(chbxR0lock != null) l[0] = chbxR0lock.isSelected(); else l[0] = false;
		if(chbxFlock != null) l[1] = chbxFlock.isSelected(); else l[1] = false;
		if(chbxAlphalock != null) l[2] = chbxAlphalock.isSelected(); else l[2] = false;
		if(chbxR1lock != null) l[3] = chbxR1lock.isSelected(); else l[3] = false;
		if(chbxL0lock != null) l[4] = chbxL0lock.isSelected(); else l[4] = false;
		if(chbxC0lock != null) l[5] = chbxC0lock.isSelected(); else l[5] = false;
		if(chbxC1lock != null) l[6] = chbxC1lock.isSelected(); else l[6] = false;
		return l;
	}

	// ================================================================================
	// Public Functions
	// ================================================================================

	// ================================================================================
	// Interface Functions
	// ================================================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnDelete) {
			if (modelPanelBuilt == true)
				controller.removeEqCircuit(eqcID);
			else
				controller.killWorker(this.eqcID);
		} else if (e.getSource() == btnOptimize) {
			btnOptimize.setEnabled(false);
			btnDelete.setEnabled(false);
			title.setText("Optimizing Model...");
			boolean[] lock = parseLockBox();
			optWindow.setEqcID(eqcID);
			optWindow.setLock(lock);
			optWindow.show();
		} else {
			tuner();
		}
	}

	public void update(Observable o, Object arg) {
		Model m = (Model) o;
		if (lockUpdate == false && modelPanelBuilt) {
			lockUpdate = true;
			updateParams(m.getEquivalentCircuit(this.eqcID));
			updateUI();
			lockUpdate = false;
		}
	}

	public int getID() {
		return this.eqcID;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		tuner();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		tuner();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

}