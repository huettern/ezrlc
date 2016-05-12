package pro2.View;

import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import pro2.MVC.Controller;
import pro2.ModelCalculation.MCOptions;
import pro2.Plot.Axis;
import pro2.util.UIUtil;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JComponent;

public class NewModelWindow implements ActionListener{
	
	//================================================================================
    // Local Variables
    //================================================================================
	private Controller controller;
	private JDialog dialog; 
	
	private JTextField txtFmin;
	private JTextField txtFmax;
	private JTextField txtCompMin;
	private JTextField txtCompMax;
	private JTextField txtR0;
	private JTextField txtAlpha;
	private JTextField txtF;
	private JTextField txtL;
	private JTextField txtC0;
	private JTextField txtR1;
	private JTextField txtC1;
	
	private JButton btnCancel;
	private JButton btnAllAuto;
	private JButton btnGenerate;
	
	private JPanel pnlParameter;
	
	private JComboBox list;
	private ImageComboBox comBoxModelList;
	
	String[] imagesName = {"auto.png", "model_0.png", "model_1.png", "model_2.png", "model_3.png",
						"model_4.png", "model_5.png", "model_6.png", "model_7.png",
						"model_8.png", "model_9.png", "model_10.png", "model_11.png",
						"model_12.png", "model_13.png", "model_14.png", "model_15.png",
						"model_16.png", "model_17.png", "model_18.png", "model_19.png",
						"model_20.png"};
	String[] imagesText = {"", "", "", "", "",
						"", "", "", "",
						"", "", "", "",
						"", "", "", "",
						"", "", "", "",
						""};
	
	private JLabel lblMh;
	private JLabel lblUf;
	private JLabel lblOhm0;
	private JLabel lblUf_1;
	private JLabel lblHz_2;
	private JLabel lblOhm1;
	private JLabel lblL;
	private JLabel lblR0;
	private JLabel lblC0;
	private JLabel lblAlpha;
	private JLabel lblR1;
	private JLabel lblF;
	private JLabel lblC1;
	private JLabel lblCompMin;
	private JLabel lblCompMax;
	
	//================================================================================
    // Constructors
    //================================================================================
	public NewModelWindow(Controller controller) {
		this.controller = controller;
	}
	
	//================================================================================
    // Public Functions
    //================================================================================
	/**
	 * Builds the Graph Panel
	 * @wbp.parser.entryPoint
	 */
	public void buildNewModelWindow() {
		dialog = new JDialog(controller.getMainView());
		dialog.setTitle("New Model");
		dialog.setResizable(false);
		dialog.setModal(true);
		dialog.setLocation(300, 300);
		dialog.setSize(433, 665);
		
		
		//Main Panel
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		dialog.getContentPane().setLayout(gridBagLayout);
		
		//Model list
		JPanel pnlModelSelection = new JPanel();
		pnlModelSelection.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Model List", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlModelSelection = new GridBagConstraints();
		gbc_pnlModelSelection.insets = new Insets(5, 5, 5, 5);
		gbc_pnlModelSelection.fill = GridBagConstraints.BOTH;
		gbc_pnlModelSelection.gridx = 0;
		gbc_pnlModelSelection.gridy = 0;
		dialog.getContentPane().add(pnlModelSelection, gbc_pnlModelSelection);
		GridBagLayout gbl_pnlModelSelection = new GridBagLayout();
		gbl_pnlModelSelection.columnWidths = new int[]{0, 0, 0};
		gbl_pnlModelSelection.rowHeights = new int[]{0, 0, 0};
		gbl_pnlModelSelection.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_pnlModelSelection.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		pnlModelSelection.setLayout(gbl_pnlModelSelection);
		
		comBoxModelList = new ImageComboBox(this, imagesName, imagesText);
		comBoxModelList.setOpaque(true);
		GridBagConstraints gbc_comBoxModelList = new GridBagConstraints();
		gbc_comBoxModelList.gridwidth = 2;
		gbc_comBoxModelList.fill = GridBagConstraints.HORIZONTAL;
		gbc_comBoxModelList.insets = new Insets(0, 0, 5, 0);
		gbc_comBoxModelList.gridx = 0;
		gbc_comBoxModelList.gridy = 0;
		pnlModelSelection.add(comBoxModelList, gbc_comBoxModelList);
		
		//Components
		JPanel pnlComponents = new JPanel();
		pnlComponents.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Components", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlComponents = new GridBagConstraints();
		gbc_pnlComponents.insets = new Insets(0, 5, 5, 5);
		gbc_pnlComponents.fill = GridBagConstraints.BOTH;
		gbc_pnlComponents.gridx = 0;
		gbc_pnlComponents.gridy = 1;
		dialog.getContentPane().add(pnlComponents, gbc_pnlComponents);
		GridBagLayout gbl_pnlComponents = new GridBagLayout();
		gbl_pnlComponents.columnWidths = new int[]{80, 100, 23, 0};
		gbl_pnlComponents.rowHeights = new int[]{0, 0, 0};
		gbl_pnlComponents.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_pnlComponents.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		pnlComponents.setLayout(gbl_pnlComponents);
		
		lblCompMin = new JLabel("<html> Comp<sub>min</sub> </html>");
		GridBagConstraints gbc_lblCompMin = new GridBagConstraints();
		gbc_lblCompMin.anchor = GridBagConstraints.WEST;
		gbc_lblCompMin.insets = new Insets(0, 5, 5, 5);
		gbc_lblCompMin.gridx = 0;
		gbc_lblCompMin.gridy = 0;
		pnlComponents.add(lblCompMin, gbc_lblCompMin);
		
		txtCompMin = new JTextField();
		txtCompMin.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_txtCompMin = new GridBagConstraints();
		gbc_txtCompMin.insets = new Insets(0, 0, 5, 5);
		gbc_txtCompMin.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCompMin.gridx = 1;
		gbc_txtCompMin.gridy = 0;
		pnlComponents.add(txtCompMin, gbc_txtCompMin);
		txtCompMin.setColumns(10);
		
		JLabel label_1 = new JLabel("");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 0);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 0;
		pnlComponents.add(label_1, gbc_label_1);
		
		lblCompMax = new JLabel("<html> Comp<sub>max</sub> </html>");
		GridBagConstraints gbc_lblCompMax = new GridBagConstraints();
		gbc_lblCompMax.anchor = GridBagConstraints.WEST;
		gbc_lblCompMax.insets = new Insets(0, 5, 0, 5);
		gbc_lblCompMax.gridx = 0;
		gbc_lblCompMax.gridy = 1;
		pnlComponents.add(lblCompMax, gbc_lblCompMax);
		
		txtCompMax = new JTextField();
		txtCompMax.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_txtCompMax = new GridBagConstraints();
		gbc_txtCompMax.insets = new Insets(0, 0, 0, 5);
		gbc_txtCompMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCompMax.gridx = 1;
		gbc_txtCompMax.gridy = 1;
		pnlComponents.add(txtCompMax, gbc_txtCompMax);
		txtCompMax.setColumns(10);
		
		//Frequency
		JPanel pnlFrequency = new JPanel();
		pnlFrequency.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Frequency", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pnlFrequency = new GridBagConstraints();
		gbc_pnlFrequency.insets = new Insets(0, 5, 5, 5);
		gbc_pnlFrequency.fill = GridBagConstraints.BOTH;
		gbc_pnlFrequency.gridx = 0;
		gbc_pnlFrequency.gridy = 2;
		dialog.getContentPane().add(pnlFrequency, gbc_pnlFrequency);
		GridBagLayout gbl_pnlFrequency = new GridBagLayout();
		gbl_pnlFrequency.columnWidths = new int[]{80, 100, 23, 0};
		gbl_pnlFrequency.rowHeights = new int[]{0, 0, 0, 0};
		gbl_pnlFrequency.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_pnlFrequency.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlFrequency.setLayout(gbl_pnlFrequency);
		
		JLabel lblFmin = new JLabel("<html> f<sub>min</sub> </html>");
		GridBagConstraints gbc_lblFmin = new GridBagConstraints();
		gbc_lblFmin.anchor = GridBagConstraints.WEST;
		gbc_lblFmin.insets = new Insets(0, 5, 5, 5);
		gbc_lblFmin.gridx = 0;
		gbc_lblFmin.gridy = 1;
		pnlFrequency.add(lblFmin, gbc_lblFmin);
		
		
		txtFmin = new JTextField();
		txtFmin.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_txtFmin = new GridBagConstraints();
		gbc_txtFmin.insets = new Insets(0, 0, 5, 5);
		gbc_txtFmin.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFmin.gridx = 1;
		gbc_txtFmin.gridy = 1;
		pnlFrequency.add(txtFmin, gbc_txtFmin);
		txtFmin.setColumns(10);
		
		JLabel lblHz = new JLabel("Hz");
		GridBagConstraints gbc_lblHz = new GridBagConstraints();
		gbc_lblHz.insets = new Insets(0, 0, 5, 0);
		gbc_lblHz.gridx = 2;
		gbc_lblHz.gridy = 1;
		pnlFrequency.add(lblHz, gbc_lblHz);
		
		JLabel lblFmax = new JLabel("<html> f<sub>max</sub> </html>");
		GridBagConstraints gbc_lblFmax = new GridBagConstraints();
		gbc_lblFmax.anchor = GridBagConstraints.WEST;
		gbc_lblFmax.insets = new Insets(0, 5, 0, 5);
		gbc_lblFmax.gridx = 0;
		gbc_lblFmax.gridy = 2;
		pnlFrequency.add(lblFmax, gbc_lblFmax);
		
		txtFmax = new JTextField();
		txtFmax.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_txtFmax = new GridBagConstraints();
		gbc_txtFmax.insets = new Insets(0, 0, 0, 5);
		gbc_txtFmax.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFmax.gridx = 1;
		gbc_txtFmax.gridy = 2;
		pnlFrequency.add(txtFmax, gbc_txtFmax);
		txtFmax.setColumns(10);
		
		JLabel lblHz_1 = new JLabel("Hz");
		GridBagConstraints gbc_lblHz_1 = new GridBagConstraints();
		gbc_lblHz_1.gridx = 2;
		gbc_lblHz_1.gridy = 2;
		pnlFrequency.add(lblHz_1, gbc_lblHz_1);
		
		//Parameter 
		pnlParameter = new JPanel();
		pnlParameter.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Start Value Parameters", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pnlParameter = new GridBagConstraints();
		gbc_pnlParameter.insets = new Insets(0, 5, 5, 5);
		gbc_pnlParameter.fill = GridBagConstraints.BOTH;
		gbc_pnlParameter.gridx = 0;
		gbc_pnlParameter.gridy = 3;
		dialog.getContentPane().add(pnlParameter, gbc_pnlParameter);
		GridBagLayout gbl_pnlParameter = new GridBagLayout();
		gbl_pnlParameter.columnWidths = new int[]{0, 0, 0, 23, 0, 0, 0, 0};
		gbl_pnlParameter.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_pnlParameter.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_pnlParameter.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlParameter.setLayout(gbl_pnlParameter);
		
		lblL = new JLabel("L");
		GridBagConstraints gbc_lblL = new GridBagConstraints();
		gbc_lblL.anchor = GridBagConstraints.WEST;
		gbc_lblL.insets = new Insets(0, 5, 5, 5);
		gbc_lblL.gridx = 0;
		gbc_lblL.gridy = 0;
		pnlParameter.add(lblL, gbc_lblL);
		
		txtL = new JTextField();
		txtL.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_txtL = new GridBagConstraints();
		gbc_txtL.insets = new Insets(0, 0, 5, 5);
		gbc_txtL.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtL.gridx = 1;
		gbc_txtL.gridy = 0;
		pnlParameter.add(txtL, gbc_txtL);
		txtL.setColumns(10);
		
		lblMh = new JLabel("mH");
		GridBagConstraints gbc_lblMh = new GridBagConstraints();
		gbc_lblMh.insets = new Insets(0, 0, 5, 5);
		gbc_lblMh.gridx = 2;
		gbc_lblMh.gridy = 0;
		pnlParameter.add(lblMh, gbc_lblMh);
		
		lblR0 = new JLabel("<html> R<sub>0</sub> </html>");
		GridBagConstraints gbc_lblR0 = new GridBagConstraints();
		gbc_lblR0.insets = new Insets(0, 5, 5, 5);
		gbc_lblR0.anchor = GridBagConstraints.WEST;
		gbc_lblR0.gridx = 4;
		gbc_lblR0.gridy = 0;
		pnlParameter.add(lblR0, gbc_lblR0);
		
		txtR0 = new JTextField();
		txtR0.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_txtR0 = new GridBagConstraints();
		gbc_txtR0.insets = new Insets(0, 0, 5, 5);
		gbc_txtR0.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtR0.gridx = 5;
		gbc_txtR0.gridy = 0;
		pnlParameter.add(txtR0, gbc_txtR0);
		txtR0.setColumns(10);
		
		lblOhm0 = new JLabel("\u2126");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 6;
		gbc_label.gridy = 0;
		pnlParameter.add(lblOhm0, gbc_label);
		
		lblC0 = new JLabel("<html> C<sub>0</sub> </html>");
		GridBagConstraints gbc_lblC0 = new GridBagConstraints();
		gbc_lblC0.anchor = GridBagConstraints.WEST;
		gbc_lblC0.insets = new Insets(0, 5, 5, 5);
		gbc_lblC0.gridx = 0;
		gbc_lblC0.gridy = 1;
		pnlParameter.add(lblC0, gbc_lblC0);
		
		txtC0 = new JTextField();
		txtC0.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_txtC0 = new GridBagConstraints();
		gbc_txtC0.insets = new Insets(0, 0, 5, 5);
		gbc_txtC0.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtC0.gridx = 1;
		gbc_txtC0.gridy = 1;
		pnlParameter.add(txtC0, gbc_txtC0);
		txtC0.setColumns(10);
		
		lblUf = new JLabel("\u00B5"+"F");
		GridBagConstraints gbc_lblUf = new GridBagConstraints();
		gbc_lblUf.insets = new Insets(0, 0, 5, 5);
		gbc_lblUf.gridx = 2;
		gbc_lblUf.gridy = 1;
		pnlParameter.add(lblUf, gbc_lblUf);
		
		lblAlpha = new JLabel("\u03B1");
		GridBagConstraints gbc_lblAlpha = new GridBagConstraints();
		gbc_lblAlpha.anchor = GridBagConstraints.WEST;
		gbc_lblAlpha.insets = new Insets(0, 5, 5, 5);
		gbc_lblAlpha.gridx = 4;
		gbc_lblAlpha.gridy = 1;
		pnlParameter.add(lblAlpha, gbc_lblAlpha);
		
		txtAlpha = new JTextField();
		txtAlpha.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_txtAlpha = new GridBagConstraints();
		gbc_txtAlpha.insets = new Insets(0, 0, 5, 5);
		gbc_txtAlpha.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAlpha.gridx = 5;
		gbc_txtAlpha.gridy = 1;
		pnlParameter.add(txtAlpha, gbc_txtAlpha);
		txtAlpha.setColumns(10);
		
		lblR1 = new JLabel("<html> R<sub>1</sub> </html>");
		GridBagConstraints gbc_lblR1 = new GridBagConstraints();
		gbc_lblR1.anchor = GridBagConstraints.WEST;
		gbc_lblR1.insets = new Insets(0, 5, 5, 5);
		gbc_lblR1.gridx = 0;
		gbc_lblR1.gridy = 2;
		pnlParameter.add(lblR1, gbc_lblR1);
		
		txtR1 = new JTextField();
		txtR1.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_txtR1 = new GridBagConstraints();
		gbc_txtR1.insets = new Insets(0, 0, 5, 5);
		gbc_txtR1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtR1.gridx = 1;
		gbc_txtR1.gridy = 2;
		pnlParameter.add(txtR1, gbc_txtR1);
		txtR1.setColumns(10);
		
		lblOhm1 = new JLabel("\u2126");
		GridBagConstraints gbc_lblu = new GridBagConstraints();
		gbc_lblu.insets = new Insets(0, 0, 5, 5);
		gbc_lblu.gridx = 2;
		gbc_lblu.gridy = 2;
		pnlParameter.add(lblOhm1, gbc_lblu);
		
		lblF = new JLabel("f");
		GridBagConstraints gbc_lblF = new GridBagConstraints();
		gbc_lblF.anchor = GridBagConstraints.WEST;
		gbc_lblF.insets = new Insets(0, 5, 5, 5);
		gbc_lblF.gridx = 4;
		gbc_lblF.gridy = 2;
		pnlParameter.add(lblF, gbc_lblF);
		
		txtF = new JTextField();
		txtF.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_txtF = new GridBagConstraints();
		gbc_txtF.insets = new Insets(0, 0, 5, 5);
		gbc_txtF.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtF.gridx = 5;
		gbc_txtF.gridy = 2;
		pnlParameter.add(txtF, gbc_txtF);
		txtF.setColumns(10);
		
		lblHz_2 = new JLabel("Hz");
		GridBagConstraints gbc_lblHz_2 = new GridBagConstraints();
		gbc_lblHz_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblHz_2.gridx = 6;
		gbc_lblHz_2.gridy = 2;
		pnlParameter.add(lblHz_2, gbc_lblHz_2);
		
		lblC1 = new JLabel("<html> C<sub>1</sub> </html>");
		GridBagConstraints gbc_lblC1 = new GridBagConstraints();
		gbc_lblC1.anchor = GridBagConstraints.WEST;
		gbc_lblC1.insets = new Insets(0, 5, 0, 5);
		gbc_lblC1.gridx = 0;
		gbc_lblC1.gridy = 3;
		pnlParameter.add(lblC1, gbc_lblC1);
		
		txtC1 = new JTextField();
		txtC1.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_txtC1 = new GridBagConstraints();
		gbc_txtC1.insets = new Insets(0, 0, 0, 5);
		gbc_txtC1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtC1.gridx = 1;
		gbc_txtC1.gridy = 3;
		pnlParameter.add(txtC1, gbc_txtC1);
		txtC1.setColumns(10);
		
		lblUf_1 = new JLabel("\u00B5"+"F");
		GridBagConstraints gbc_lblUf_1 = new GridBagConstraints();
		gbc_lblUf_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblUf_1.gridx = 2;
		gbc_lblUf_1.gridy = 3;
		pnlParameter.add(lblUf_1, gbc_lblUf_1);
		
		//Buttons
		JPanel pnlButtons = new JPanel();
		GridBagConstraints gbc_pnlButtons = new GridBagConstraints();
		gbc_pnlButtons.fill = GridBagConstraints.BOTH;
		gbc_pnlButtons.gridx = 0;
		gbc_pnlButtons.gridy = 4;
		dialog.getContentPane().add(pnlButtons, gbc_pnlButtons);
		GridBagLayout gbl_pnlButtons = new GridBagLayout();
		gbl_pnlButtons.columnWidths = new int[]{125, 0, 120, 125, 0};
		gbl_pnlButtons.rowHeights = new int[]{0, 0};
		gbl_pnlButtons.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlButtons.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		pnlButtons.setLayout(gbl_pnlButtons);
		
		btnAllAuto = new JButton("All Auto");
		GridBagConstraints gbc_btnAllAuto = new GridBagConstraints();
		gbc_btnAllAuto.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAllAuto.insets = new Insets(0, 5, 5, 5);
		gbc_btnAllAuto.gridx = 0;
		gbc_btnAllAuto.gridy = 0;
		pnlButtons.add(btnAllAuto, gbc_btnAllAuto);
		btnAllAuto.addActionListener(this);
		
		btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancel.insets = new Insets(0, 5, 5, 5);
		gbc_btnCancel.gridx = 2;
		gbc_btnCancel.gridy = 0;
		pnlButtons.add(btnCancel, gbc_btnCancel);
		btnCancel.addActionListener(this);
		
		btnGenerate = new JButton("Generate");
		GridBagConstraints gbc_btnGenerate = new GridBagConstraints();
		gbc_btnGenerate.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGenerate.insets = new Insets(0, 0, 5, 5);
		gbc_btnGenerate.gridx = 3;
		gbc_btnGenerate.gridy = 0;
		pnlButtons.add(btnGenerate, gbc_btnGenerate);
		btnGenerate.addActionListener(this);		
		
		dialog.setVisible(true);
	}
	


	//================================================================================
    // Private Methods
    //================================================================================
	/**
	 * Parses input fileds an creates a MCOptions object
	 * @return
	 */
	private MCOptions parseInput() {
		MCOptions ops = new MCOptions();
		
		// Selected model
		int index = comBoxModelList.getList().getSelectedIndex();
		if(index == 0) {
			ops.modelAutoSelect = true;
			ops.modelID = 0;
		} else {
			ops.modelID = index - 1;
			ops.modelAutoSelect = false;
		}
		
		// f range
//		ops.fMinAuto = chBoxFmin.isSelected();
//		ops.fMaxAuto = chBoxFmax.isSelected();
		if(ops.fMinAuto == false) ops.fMin = Double.parseDouble(txtFmin.getText());
		if(ops.fMaxAuto == false) ops.fMax = Double.parseDouble(txtFmax.getText());
		
		// elements range
//		ops.nElementsMinAuto = chBoxCompMin.isSelected();
//		ops.nElementsMaxAuto = chBoxCompMax.isSelected();
		if(ops.nElementsMinAuto == false && txtCompMin.getSelectedText() != null) ops.nElementsMin = (int)Double.parseDouble(txtCompMin.getText());
		if(ops.nElementsMaxAuto == false && txtCompMax.getSelectedText() != null) ops.nElementsMax = (int)Double.parseDouble(txtCompMax.getText());
		
		// element auto
//		ops.paramsAuto[0] = chBoxR0.isSelected();
//		ops.paramsAuto[1] = chBoxF.isSelected();
//		ops.paramsAuto[2] = chBoxAlpha.isSelected();
//		ops.paramsAuto[3] = chBoxR1.isSelected();
//		ops.paramsAuto[4] = chBoxL.isSelected();
//		ops.paramsAuto[5] = chBoxC0.isSelected();
//		ops.paramsAuto[6] = chBoxC1.isSelected();
		
		// element values
		if(ops.paramsAuto[0] == false && txtR0.getSelectedText() != null) ops.params[0] = Double.parseDouble(txtR0.getText());
		if(ops.paramsAuto[1] == false && txtF.getSelectedText() != null) ops.params[1] = Double.parseDouble(txtF.getText());
		if(ops.paramsAuto[2] == false && txtAlpha.getSelectedText() != null) ops.params[2] = Double.parseDouble(txtAlpha.getText());
		if(ops.paramsAuto[3] == false && txtR1.getSelectedText() != null) ops.params[3] = Double.parseDouble(txtR1.getText());
		if(ops.paramsAuto[4] == false && txtL.getSelectedText() != null) ops.params[4] = Double.parseDouble(txtL.getText())/(1e3);
		if(ops.paramsAuto[5] == false && txtC0.getSelectedText() != null) ops.params[5] = Double.parseDouble(txtC0.getText())/(1e6);
		if(ops.paramsAuto[6] == false && txtC1.getSelectedText() != null) ops.params[6] = Double.parseDouble(txtC1.getText())/(1e6);

		return ops;
	}


	//================================================================================
    // Listener
    //================================================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnCancel) {
			dialog.dispose();
		}
		
		if(e.getSource() == btnAllAuto) {
			txtAlpha.setText("");
			txtR0.setText("");
			txtC0.setText("");
			txtC1.setText("");
			txtCompMax.setText("");
			txtCompMin.setText("");
			txtF.setText("");
			txtFmax.setText("");
			txtFmin.setText("");
			txtR1.setText("");
			txtL.setText("");
			list.setSelectedIndex(0);
		}
		
		if(e.getSource() == btnGenerate) {
			MCOptions ops = this.parseInput();
			controller.createEqCircuit(ops);
			dialog.dispose();
		}	

	}
	
	
	/**
	 * Parameter match by change the model
	 */
	public void comboBoxSelected(ActionEvent e) {
		lblCompMin.setForeground(Color.LIGHT_GRAY);
		txtCompMin.setEditable(false);
		
		lblCompMax.setForeground(Color.LIGHT_GRAY);
		txtCompMax.setEditable(false);
		
		list = (JComboBox)e.getSource();
		switch (list.getSelectedIndex()) {
		case 0:
			lblL.setForeground(Color.BLACK);
			txtL.setEditable(true);
			lblMh.setForeground(Color.BLACK);
			
			lblC0.setForeground(Color.BLACK);
			txtC0.setEditable(true);
			lblUf.setForeground(Color.BLACK);
			
			lblC1.setForeground(Color.BLACK);
			txtC1.setEditable(true);
			lblUf_1.setForeground(Color.BLACK);
			
			lblR0.setForeground(Color.BLACK);
			txtR0.setEditable(true);
			lblOhm0.setForeground(Color.BLACK);
			
			lblR1.setForeground(Color.BLACK);
			txtR1.setEditable(true);
			lblOhm1.setForeground(Color.BLACK);
			
			lblAlpha.setForeground(Color.BLACK);
			txtAlpha.setEditable(true);
			
			lblF.setForeground(Color.BLACK);
			txtF.setEditable(true);
			lblHz_2.setForeground(Color.BLACK);
			
			lblCompMin.setForeground(Color.BLACK);
			txtCompMin.setEditable(true);
			
			lblCompMax.setForeground(Color.BLACK);
			txtCompMax.setEditable(true);
			break;
		case 1:
		case 2:
			lblL.setForeground(Color.BLACK);
			txtL.setEditable(true);
			lblMh.setForeground(Color.BLACK);
			
			lblC0.setForeground(Color.LIGHT_GRAY);
			txtC0.setEditable(false);
			lblUf.setForeground(Color.LIGHT_GRAY);
			
			lblC1.setForeground(Color.LIGHT_GRAY);
			txtC1.setEditable(false);
			lblUf_1.setForeground(Color.LIGHT_GRAY);
			
			lblR0.setForeground(Color.BLACK);
			txtR0.setEditable(true);
			lblOhm0.setForeground(Color.BLACK);
			
			lblR1.setForeground(Color.LIGHT_GRAY);
			txtR1.setEditable(false);
			lblOhm1.setForeground(Color.LIGHT_GRAY);
			
			lblAlpha.setForeground(Color.LIGHT_GRAY);
			txtAlpha.setEditable(false);
			
			lblF.setForeground(Color.LIGHT_GRAY);
			txtF.setEditable(false);
			lblHz_2.setForeground(Color.LIGHT_GRAY);
			break;
			
		case 3:
		case 4:
			lblL.setForeground(Color.LIGHT_GRAY);
			txtL.setEditable(false);
			lblMh.setForeground(Color.LIGHT_GRAY);
			
			lblC0.setForeground(Color.BLACK);
			txtC0.setEditable(true);
			lblUf.setForeground(Color.BLACK);
			
			lblC1.setForeground(Color.LIGHT_GRAY);
			txtC1.setEditable(false);
			lblUf_1.setForeground(Color.LIGHT_GRAY);
			
			lblR0.setForeground(Color.BLACK);
			txtR0.setEditable(true);
			lblOhm0.setForeground(Color.BLACK);
			
			lblR1.setForeground(Color.LIGHT_GRAY);
			txtR1.setEditable(false);
			lblOhm1.setForeground(Color.LIGHT_GRAY);
			
			lblAlpha.setForeground(Color.LIGHT_GRAY);
			txtAlpha.setEditable(false);
			
			lblF.setForeground(Color.LIGHT_GRAY);
			txtF.setEditable(false);
			lblHz_2.setForeground(Color.LIGHT_GRAY);
			break;
			
		case 5:
		case 6:
		case 7:
		case 8:
			lblL.setForeground(Color.BLACK);
			txtL.setEditable(true);
			lblMh.setForeground(Color.BLACK);
			
			lblC0.setForeground(Color.BLACK);
			txtC0.setEditable(true);
			lblUf.setForeground(Color.BLACK);
			
			lblC1.setForeground(Color.LIGHT_GRAY);
			txtC1.setEditable(false);
			lblUf_1.setForeground(Color.LIGHT_GRAY);
			
			lblR0.setForeground(Color.BLACK);
			txtR0.setEditable(true);
			lblOhm0.setForeground(Color.BLACK);
			
			lblR1.setForeground(Color.LIGHT_GRAY);
			txtR1.setEditable(false);
			lblOhm1.setForeground(Color.LIGHT_GRAY);
			
			lblAlpha.setForeground(Color.LIGHT_GRAY);
			txtAlpha.setEditable(false);
			
			lblF.setForeground(Color.LIGHT_GRAY);
			txtF.setEditable(false);
			lblHz_2.setForeground(Color.LIGHT_GRAY);
			break;
			
		case 9:
			lblL.setForeground(Color.LIGHT_GRAY);
			txtL.setEditable(false);
			lblMh.setForeground(Color.LIGHT_GRAY);
			
			lblC0.setForeground(Color.BLACK);
			txtC0.setEditable(true);
			lblUf.setForeground(Color.BLACK);
			
			lblC1.setForeground(Color.LIGHT_GRAY);
			txtC1.setEditable(false);
			lblUf_1.setForeground(Color.LIGHT_GRAY);
			
			lblR0.setForeground(Color.BLACK);
			txtR0.setEditable(true);
			lblOhm0.setForeground(Color.BLACK);
			
			lblR1.setForeground(Color.BLACK);
			txtR1.setEditable(true);
			lblOhm1.setForeground(Color.BLACK);
			
			lblAlpha.setForeground(Color.LIGHT_GRAY);
			txtAlpha.setEditable(false);
			
			lblF.setForeground(Color.LIGHT_GRAY);
			txtF.setEditable(false);
			lblHz_2.setForeground(Color.LIGHT_GRAY);
			break;
			
		case 10:
		case 11:
		case 12:
			lblL.setForeground(Color.BLACK);
			txtL.setEditable(true);
			lblMh.setForeground(Color.BLACK);
			
			lblC0.setForeground(Color.BLACK);
			txtC0.setEditable(true);
			lblUf.setForeground(Color.BLACK);
			
			lblC1.setForeground(Color.LIGHT_GRAY);
			txtC1.setEditable(false);
			lblUf_1.setForeground(Color.LIGHT_GRAY);
			
			lblR0.setForeground(Color.BLACK);
			txtR0.setEditable(true);
			lblOhm0.setForeground(Color.BLACK);
			
			lblR1.setForeground(Color.BLACK);
			txtR1.setEditable(true);
			lblOhm1.setForeground(Color.BLACK);
			
			lblAlpha.setForeground(Color.LIGHT_GRAY);
			txtAlpha.setEditable(false);
			
			lblF.setForeground(Color.LIGHT_GRAY);
			txtF.setEditable(false);
			lblHz_2.setForeground(Color.LIGHT_GRAY);
			break;
			
		case 13:
			lblL.setForeground(Color.BLACK);
			txtL.setEditable(true);
			lblMh.setForeground(Color.BLACK);
			
			lblC0.setForeground(Color.BLACK);
			txtC0.setEditable(true);
			lblUf.setForeground(Color.BLACK);
			
			lblC1.setForeground(Color.BLACK);
			txtC1.setEditable(true);
			lblUf_1.setForeground(Color.BLACK);
			
			lblR0.setForeground(Color.BLACK);
			txtR0.setEditable(true);
			lblOhm0.setForeground(Color.BLACK);
			
			lblR1.setForeground(Color.LIGHT_GRAY);
			txtR1.setEditable(false);
			lblOhm1.setForeground(Color.LIGHT_GRAY);
			
			lblAlpha.setForeground(Color.LIGHT_GRAY);
			txtAlpha.setEditable(false);
			
			lblF.setForeground(Color.LIGHT_GRAY);
			txtF.setEditable(false);
			lblHz_2.setForeground(Color.LIGHT_GRAY);
			break;
			
		case 14:
		case 15:
		case 16:
			lblL.setForeground(Color.BLACK);
			txtL.setEditable(true);
			lblMh.setForeground(Color.BLACK);
			
			lblC0.setForeground(Color.BLACK);
			txtC0.setEditable(true);
			lblUf.setForeground(Color.BLACK);
			
			lblC1.setForeground(Color.LIGHT_GRAY);
			txtC1.setEditable(false);
			lblUf_1.setForeground(Color.LIGHT_GRAY);
			
			lblR0.setForeground(Color.BLACK);
			txtR0.setEditable(true);
			lblOhm0.setForeground(Color.BLACK);
			
			lblR1.setForeground(Color.LIGHT_GRAY);
			txtR1.setEditable(false);
			lblOhm1.setForeground(Color.LIGHT_GRAY);
			
			lblAlpha.setForeground(Color.BLACK);
			txtAlpha.setEditable(true);
			
			lblF.setForeground(Color.BLACK);
			txtF.setEditable(true);
			lblHz_2.setForeground(Color.BLACK);
			break;
			
		case 17:
			lblL.setForeground(Color.LIGHT_GRAY);
			txtL.setEditable(false);
			lblMh.setForeground(Color.LIGHT_GRAY);
			
			lblC0.setForeground(Color.BLACK);
			txtC0.setEditable(true);
			lblUf.setForeground(Color.BLACK);
			
			lblC1.setForeground(Color.LIGHT_GRAY);
			txtC1.setEditable(false);
			lblUf_1.setForeground(Color.LIGHT_GRAY);
			
			lblR0.setForeground(Color.BLACK);
			txtR0.setEditable(true);
			lblOhm0.setForeground(Color.BLACK);
			
			lblR1.setForeground(Color.BLACK);
			txtR1.setEditable(true);
			lblOhm1.setForeground(Color.BLACK);
			
			lblAlpha.setForeground(Color.BLACK);
			txtAlpha.setEditable(true);
			
			lblF.setForeground(Color.BLACK);
			txtF.setEditable(true);
			lblHz_2.setForeground(Color.BLACK);
			break;
			
		case 18:
		case 19:
		case 20:
			lblL.setForeground(Color.BLACK);
			txtL.setEditable(true);
			lblMh.setForeground(Color.BLACK);
			
			lblC0.setForeground(Color.BLACK);
			txtC0.setEditable(true);
			lblUf.setForeground(Color.BLACK);
			
			lblC1.setForeground(Color.LIGHT_GRAY);
			txtC1.setEditable(false);
			lblUf_1.setForeground(Color.LIGHT_GRAY);
			
			lblR0.setForeground(Color.BLACK);
			txtR0.setEditable(true);
			lblOhm0.setForeground(Color.BLACK);
			
			lblR1.setForeground(Color.BLACK);
			txtR1.setEditable(true);
			lblOhm1.setForeground(Color.BLACK);
			
			lblAlpha.setForeground(Color.BLACK);
			txtAlpha.setEditable(true);
			
			lblF.setForeground(Color.BLACK);
			txtF.setEditable(true);
			lblHz_2.setForeground(Color.BLACK);
			break;
			
		case 21:
			lblL.setForeground(Color.BLACK);
			txtL.setEditable(true);
			lblMh.setForeground(Color.BLACK);
			
			lblC0.setForeground(Color.BLACK);
			txtC0.setEditable(true);
			lblUf.setForeground(Color.BLACK);
			
			lblC1.setForeground(Color.BLACK);
			txtC1.setEditable(true);
			lblUf_1.setForeground(Color.BLACK);
			
			lblR0.setForeground(Color.BLACK);
			txtR0.setEditable(true);
			lblOhm0.setForeground(Color.BLACK);
			
			lblR1.setForeground(Color.LIGHT_GRAY);
			txtR1.setEditable(false);
			lblOhm1.setForeground(Color.LIGHT_GRAY);
	
			lblAlpha.setForeground(Color.BLACK);
			txtAlpha.setEditable(true);
			
			lblF.setForeground(Color.BLACK);
			txtF.setEditable(true);
			lblHz_2.setForeground(Color.BLACK);
			break;			
		}
		
	}
}
