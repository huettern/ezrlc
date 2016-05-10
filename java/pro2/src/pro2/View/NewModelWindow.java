package pro2.View;

import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

public class NewModelWindow{
	
	JDialog dilaog = new JDialog();
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
	
	
	//================================================================================
    // Constructors
    //================================================================================
	public NewModelWindow() {
		dilaog.setTitle("New Model");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		dilaog.getContentPane().setLayout(gridBagLayout);
		
		JPanel pnlFrequency = new JPanel();
		pnlFrequency.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Frequency", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pnlFrequency = new GridBagConstraints();
		gbc_pnlFrequency.insets = new Insets(0, 0, 5, 5);
		gbc_pnlFrequency.fill = GridBagConstraints.BOTH;
		gbc_pnlFrequency.gridx = 0;
		gbc_pnlFrequency.gridy = 0;
		dilaog.getContentPane().add(pnlFrequency, gbc_pnlFrequency);
		GridBagLayout gbl_pnlFrequency = new GridBagLayout();
		gbl_pnlFrequency.columnWidths = new int[]{60, 100, 17, 0, 0};
		gbl_pnlFrequency.rowHeights = new int[]{0, 0, 0, 0};
		gbl_pnlFrequency.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlFrequency.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlFrequency.setLayout(gbl_pnlFrequency);
		
		JLabel lblAuto = new JLabel("Auto");
		GridBagConstraints gbc_lblAuto = new GridBagConstraints();
		gbc_lblAuto.insets = new Insets(0, 0, 5, 0);
		gbc_lblAuto.gridx = 3;
		gbc_lblAuto.gridy = 0;
		pnlFrequency.add(lblAuto, gbc_lblAuto);
		
		JLabel lblFmin = new JLabel("<html> f<sub>min</sub> </html>");
		GridBagConstraints gbc_lblFmin = new GridBagConstraints();
		gbc_lblFmin.anchor = GridBagConstraints.WEST;
		gbc_lblFmin.insets = new Insets(0, 5, 5, 5);
		gbc_lblFmin.gridx = 0;
		gbc_lblFmin.gridy = 1;
		pnlFrequency.add(lblFmin, gbc_lblFmin);
		
		txtFmin = new JTextField();
		GridBagConstraints gbc_txtFmin = new GridBagConstraints();
		gbc_txtFmin.insets = new Insets(0, 0, 5, 5);
		gbc_txtFmin.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFmin.gridx = 1;
		gbc_txtFmin.gridy = 1;
		pnlFrequency.add(txtFmin, gbc_txtFmin);
		txtFmin.setColumns(10);
		
		JLabel lblHz = new JLabel("Hz");
		GridBagConstraints gbc_lblHz = new GridBagConstraints();
		gbc_lblHz.insets = new Insets(0, 0, 5, 5);
		gbc_lblHz.gridx = 2;
		gbc_lblHz.gridy = 1;
		pnlFrequency.add(lblHz, gbc_lblHz);
		
		JCheckBox chboxFmin = new JCheckBox("");
		GridBagConstraints gbc_chboxFmin = new GridBagConstraints();
		gbc_chboxFmin.insets = new Insets(0, 0, 5, 0);
		gbc_chboxFmin.gridx = 3;
		gbc_chboxFmin.gridy = 1;
		pnlFrequency.add(chboxFmin, gbc_chboxFmin);
		
		JLabel lblFmax = new JLabel("<html> f<sub>max</sub> </html>");
		GridBagConstraints gbc_lblFmax = new GridBagConstraints();
		gbc_lblFmax.anchor = GridBagConstraints.WEST;
		gbc_lblFmax.insets = new Insets(0, 5, 5, 5);
		gbc_lblFmax.gridx = 0;
		gbc_lblFmax.gridy = 2;
		pnlFrequency.add(lblFmax, gbc_lblFmax);
		
		txtFmax = new JTextField();
		GridBagConstraints gbc_txtFmax = new GridBagConstraints();
		gbc_txtFmax.insets = new Insets(0, 0, 0, 5);
		gbc_txtFmax.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFmax.gridx = 1;
		gbc_txtFmax.gridy = 2;
		pnlFrequency.add(txtFmax, gbc_txtFmax);
		txtFmax.setColumns(10);
		
		JLabel label = new JLabel("Hz");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 2;
		gbc_label.gridy = 2;
		pnlFrequency.add(label, gbc_label);
		
		JCheckBox chBoxFmax = new JCheckBox("");
		GridBagConstraints gbc_chBoxFmax = new GridBagConstraints();
		gbc_chBoxFmax.gridx = 3;
		gbc_chBoxFmax.gridy = 2;
		pnlFrequency.add(chBoxFmax, gbc_chBoxFmax);
		
		JPanel pnlModelSelection = new JPanel();
		pnlModelSelection.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Model List", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlModelSelection = new GridBagConstraints();
		gbc_pnlModelSelection.gridheight = 3;
		gbc_pnlModelSelection.insets = new Insets(0, 0, 5, 0);
		gbc_pnlModelSelection.fill = GridBagConstraints.BOTH;
		gbc_pnlModelSelection.gridx = 1;
		gbc_pnlModelSelection.gridy = 0;
		dilaog.getContentPane().add(pnlModelSelection, gbc_pnlModelSelection);
		GridBagLayout gbl_pnlModelSelection = new GridBagLayout();
		gbl_pnlModelSelection.columnWidths = new int[]{0, 0, 0};
		gbl_pnlModelSelection.rowHeights = new int[]{0, 0, 0};
		gbl_pnlModelSelection.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pnlModelSelection.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		pnlModelSelection.setLayout(gbl_pnlModelSelection);
		
		JCheckBox checkBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox = new GridBagConstraints();
		gbc_checkBox.insets = new Insets(0, 5, 5, 5);
		gbc_checkBox.gridx = 0;
		gbc_checkBox.gridy = 0;
		pnlModelSelection.add(checkBox, gbc_checkBox);
		
		JLabel lblAuto_4 = new JLabel("Auto");
		GridBagConstraints gbc_lblAuto_4 = new GridBagConstraints();
		gbc_lblAuto_4.anchor = GridBagConstraints.WEST;
		gbc_lblAuto_4.insets = new Insets(0, 0, 5, 0);
		gbc_lblAuto_4.gridx = 1;
		gbc_lblAuto_4.gridy = 0;
		pnlModelSelection.add(lblAuto_4, gbc_lblAuto_4);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		pnlModelSelection.add(scrollPane, gbc_scrollPane);
		
		JPanel pnlComponents = new JPanel();
		pnlComponents.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Components", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlComponents = new GridBagConstraints();
		gbc_pnlComponents.insets = new Insets(0, 0, 5, 5);
		gbc_pnlComponents.fill = GridBagConstraints.BOTH;
		gbc_pnlComponents.gridx = 0;
		gbc_pnlComponents.gridy = 1;
		dilaog.getContentPane().add(pnlComponents, gbc_pnlComponents);
		GridBagLayout gbl_pnlComponents = new GridBagLayout();
		gbl_pnlComponents.columnWidths = new int[]{60, 100, 17, 0, 0};
		gbl_pnlComponents.rowHeights = new int[]{0, 0, 0, 0};
		gbl_pnlComponents.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlComponents.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlComponents.setLayout(gbl_pnlComponents);
		
		JLabel lblAuto_1 = new JLabel("Auto");
		GridBagConstraints gbc_lblAuto_1 = new GridBagConstraints();
		gbc_lblAuto_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblAuto_1.gridx = 3;
		gbc_lblAuto_1.gridy = 0;
		pnlComponents.add(lblAuto_1, gbc_lblAuto_1);
		
		JLabel lblCompMin = new JLabel("<html> Comp<sub>min</sub> </html>");
		GridBagConstraints gbc_lblCompMin = new GridBagConstraints();
		gbc_lblCompMin.anchor = GridBagConstraints.WEST;
		gbc_lblCompMin.insets = new Insets(0, 5, 5, 5);
		gbc_lblCompMin.gridx = 0;
		gbc_lblCompMin.gridy = 1;
		pnlComponents.add(lblCompMin, gbc_lblCompMin);
		
		txtCompMin = new JTextField();
		GridBagConstraints gbc_txtCompMin = new GridBagConstraints();
		gbc_txtCompMin.insets = new Insets(0, 0, 5, 5);
		gbc_txtCompMin.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCompMin.gridx = 1;
		gbc_txtCompMin.gridy = 1;
		pnlComponents.add(txtCompMin, gbc_txtCompMin);
		txtCompMin.setColumns(10);
		
		JLabel label_1 = new JLabel("");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 1;
		pnlComponents.add(label_1, gbc_label_1);
		
		JCheckBox chBoxCompMin = new JCheckBox("");
		GridBagConstraints gbc_chBoxCompMin = new GridBagConstraints();
		gbc_chBoxCompMin.insets = new Insets(0, 0, 5, 0);
		gbc_chBoxCompMin.gridx = 3;
		gbc_chBoxCompMin.gridy = 1;
		pnlComponents.add(chBoxCompMin, gbc_chBoxCompMin);
		
		JLabel lblCompMax = new JLabel("<html> Comp<sub>max</sub> </html>");
		GridBagConstraints gbc_lblCompMax = new GridBagConstraints();
		gbc_lblCompMax.anchor = GridBagConstraints.WEST;
		gbc_lblCompMax.insets = new Insets(0, 5, 0, 5);
		gbc_lblCompMax.gridx = 0;
		gbc_lblCompMax.gridy = 2;
		pnlComponents.add(lblCompMax, gbc_lblCompMax);
		
		txtCompMax = new JTextField();
		GridBagConstraints gbc_txtCompMax = new GridBagConstraints();
		gbc_txtCompMax.insets = new Insets(0, 0, 0, 5);
		gbc_txtCompMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCompMax.gridx = 1;
		gbc_txtCompMax.gridy = 2;
		pnlComponents.add(txtCompMax, gbc_txtCompMax);
		txtCompMax.setColumns(10);
		
		JCheckBox chBoxCompMax = new JCheckBox("");
		GridBagConstraints gbc_chBoxCompMax = new GridBagConstraints();
		gbc_chBoxCompMax.gridx = 3;
		gbc_chBoxCompMax.gridy = 2;
		pnlComponents.add(chBoxCompMax, gbc_chBoxCompMax);
		
		JPanel pnlParameter = new JPanel();
		pnlParameter.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Parameters", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlParameter = new GridBagConstraints();
		gbc_pnlParameter.insets = new Insets(0, 0, 5, 5);
		gbc_pnlParameter.fill = GridBagConstraints.BOTH;
		gbc_pnlParameter.gridx = 0;
		gbc_pnlParameter.gridy = 2;
		dilaog.getContentPane().add(pnlParameter, gbc_pnlParameter);
		GridBagLayout gbl_pnlParameter = new GridBagLayout();
		gbl_pnlParameter.columnWidths = new int[]{0, 0, 0, 23, 0, 0, 0, 0};
		gbl_pnlParameter.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_pnlParameter.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_pnlParameter.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlParameter.setLayout(gbl_pnlParameter);
		
		JLabel lblAuto_2 = new JLabel("Auto");
		GridBagConstraints gbc_lblAuto_2 = new GridBagConstraints();
		gbc_lblAuto_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuto_2.gridx = 2;
		gbc_lblAuto_2.gridy = 0;
		pnlParameter.add(lblAuto_2, gbc_lblAuto_2);
		
		JLabel lblAuto_3 = new JLabel("Auto");
		GridBagConstraints gbc_lblAuto_3 = new GridBagConstraints();
		gbc_lblAuto_3.insets = new Insets(0, 0, 5, 0);
		gbc_lblAuto_3.gridx = 6;
		gbc_lblAuto_3.gridy = 0;
		pnlParameter.add(lblAuto_3, gbc_lblAuto_3);
		
		JLabel lblL = new JLabel("L");
		GridBagConstraints gbc_lblL = new GridBagConstraints();
		gbc_lblL.anchor = GridBagConstraints.WEST;
		gbc_lblL.insets = new Insets(0, 5, 5, 5);
		gbc_lblL.gridx = 0;
		gbc_lblL.gridy = 1;
		pnlParameter.add(lblL, gbc_lblL);
		
		txtL = new JTextField();
		GridBagConstraints gbc_txtL = new GridBagConstraints();
		gbc_txtL.insets = new Insets(0, 0, 5, 5);
		gbc_txtL.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtL.gridx = 1;
		gbc_txtL.gridy = 1;
		pnlParameter.add(txtL, gbc_txtL);
		txtL.setColumns(10);
		
		JCheckBox chBoxL = new JCheckBox("");
		GridBagConstraints gbc_chBoxL = new GridBagConstraints();
		gbc_chBoxL.insets = new Insets(0, 0, 5, 5);
		gbc_chBoxL.gridx = 2;
		gbc_chBoxL.gridy = 1;
		pnlParameter.add(chBoxL, gbc_chBoxL);
		
		JLabel lblR0 = new JLabel("<html> R<sub>0</sub> </html>");
		GridBagConstraints gbc_lblR0 = new GridBagConstraints();
		gbc_lblR0.insets = new Insets(0, 5, 5, 5);
		gbc_lblR0.anchor = GridBagConstraints.WEST;
		gbc_lblR0.gridx = 4;
		gbc_lblR0.gridy = 1;
		pnlParameter.add(lblR0, gbc_lblR0);
		
		txtR0 = new JTextField();
		GridBagConstraints gbc_txtR0 = new GridBagConstraints();
		gbc_txtR0.insets = new Insets(0, 0, 5, 5);
		gbc_txtR0.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtR0.gridx = 5;
		gbc_txtR0.gridy = 1;
		pnlParameter.add(txtR0, gbc_txtR0);
		txtR0.setColumns(10);
		
		JCheckBox chBoxR0 = new JCheckBox("");
		GridBagConstraints gbc_chBoxR0 = new GridBagConstraints();
		gbc_chBoxR0.insets = new Insets(0, 0, 5, 0);
		gbc_chBoxR0.gridx = 6;
		gbc_chBoxR0.gridy = 1;
		pnlParameter.add(chBoxR0, gbc_chBoxR0);
		
		JLabel lblC0 = new JLabel("<html> C<sub>0</sub> </html>");
		GridBagConstraints gbc_lblC0 = new GridBagConstraints();
		gbc_lblC0.anchor = GridBagConstraints.WEST;
		gbc_lblC0.insets = new Insets(0, 5, 5, 5);
		gbc_lblC0.gridx = 0;
		gbc_lblC0.gridy = 2;
		pnlParameter.add(lblC0, gbc_lblC0);
		
		txtC0 = new JTextField();
		GridBagConstraints gbc_txtC0 = new GridBagConstraints();
		gbc_txtC0.insets = new Insets(0, 0, 5, 5);
		gbc_txtC0.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtC0.gridx = 1;
		gbc_txtC0.gridy = 2;
		pnlParameter.add(txtC0, gbc_txtC0);
		txtC0.setColumns(10);
		
		JCheckBox chBoxC0 = new JCheckBox("");
		GridBagConstraints gbc_chBoxC0 = new GridBagConstraints();
		gbc_chBoxC0.insets = new Insets(0, 0, 5, 5);
		gbc_chBoxC0.gridx = 2;
		gbc_chBoxC0.gridy = 2;
		pnlParameter.add(chBoxC0, gbc_chBoxC0);
		
		JLabel lblAlpha = new JLabel("\u03B1");
		GridBagConstraints gbc_lblAlpha = new GridBagConstraints();
		gbc_lblAlpha.anchor = GridBagConstraints.WEST;
		gbc_lblAlpha.insets = new Insets(0, 5, 5, 5);
		gbc_lblAlpha.gridx = 4;
		gbc_lblAlpha.gridy = 2;
		pnlParameter.add(lblAlpha, gbc_lblAlpha);
		
		txtAlpha = new JTextField();
		GridBagConstraints gbc_txtAlpha = new GridBagConstraints();
		gbc_txtAlpha.insets = new Insets(0, 0, 5, 5);
		gbc_txtAlpha.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAlpha.gridx = 5;
		gbc_txtAlpha.gridy = 2;
		pnlParameter.add(txtAlpha, gbc_txtAlpha);
		txtAlpha.setColumns(10);
		
		JCheckBox chBoxAlpha = new JCheckBox("");
		GridBagConstraints gbc_chBoxAlpha = new GridBagConstraints();
		gbc_chBoxAlpha.insets = new Insets(0, 0, 5, 0);
		gbc_chBoxAlpha.gridx = 6;
		gbc_chBoxAlpha.gridy = 2;
		pnlParameter.add(chBoxAlpha, gbc_chBoxAlpha);
		
		JLabel lblR1 = new JLabel("<html> R<sub>1</sub> </html>");
		GridBagConstraints gbc_lblR1 = new GridBagConstraints();
		gbc_lblR1.anchor = GridBagConstraints.WEST;
		gbc_lblR1.insets = new Insets(0, 5, 5, 5);
		gbc_lblR1.gridx = 0;
		gbc_lblR1.gridy = 3;
		pnlParameter.add(lblR1, gbc_lblR1);
		
		txtR1 = new JTextField();
		GridBagConstraints gbc_txtR1 = new GridBagConstraints();
		gbc_txtR1.insets = new Insets(0, 0, 5, 5);
		gbc_txtR1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtR1.gridx = 1;
		gbc_txtR1.gridy = 3;
		pnlParameter.add(txtR1, gbc_txtR1);
		txtR1.setColumns(10);
		
		JCheckBox chBoxR1 = new JCheckBox("");
		GridBagConstraints gbc_chBoxR1 = new GridBagConstraints();
		gbc_chBoxR1.insets = new Insets(0, 0, 5, 5);
		gbc_chBoxR1.gridx = 2;
		gbc_chBoxR1.gridy = 3;
		pnlParameter.add(chBoxR1, gbc_chBoxR1);
		
		JLabel lblF = new JLabel("f");
		GridBagConstraints gbc_lblF = new GridBagConstraints();
		gbc_lblF.anchor = GridBagConstraints.WEST;
		gbc_lblF.insets = new Insets(0, 5, 5, 5);
		gbc_lblF.gridx = 4;
		gbc_lblF.gridy = 3;
		pnlParameter.add(lblF, gbc_lblF);
		
		txtF = new JTextField();
		GridBagConstraints gbc_txtF = new GridBagConstraints();
		gbc_txtF.insets = new Insets(0, 0, 5, 5);
		gbc_txtF.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtF.gridx = 5;
		gbc_txtF.gridy = 3;
		pnlParameter.add(txtF, gbc_txtF);
		txtF.setColumns(10);
		
		JCheckBox chBoxF = new JCheckBox("");
		GridBagConstraints gbc_chBoxF = new GridBagConstraints();
		gbc_chBoxF.insets = new Insets(0, 0, 5, 0);
		gbc_chBoxF.gridx = 6;
		gbc_chBoxF.gridy = 3;
		pnlParameter.add(chBoxF, gbc_chBoxF);
		
		JLabel lblC1 = new JLabel("<html> C<sub>1</sub> </html>");
		GridBagConstraints gbc_lblC1 = new GridBagConstraints();
		gbc_lblC1.anchor = GridBagConstraints.WEST;
		gbc_lblC1.insets = new Insets(0, 5, 5, 5);
		gbc_lblC1.gridx = 0;
		gbc_lblC1.gridy = 4;
		pnlParameter.add(lblC1, gbc_lblC1);
		
		txtC1 = new JTextField();
		GridBagConstraints gbc_txtC1 = new GridBagConstraints();
		gbc_txtC1.insets = new Insets(0, 0, 0, 5);
		gbc_txtC1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtC1.gridx = 1;
		gbc_txtC1.gridy = 4;
		pnlParameter.add(txtC1, gbc_txtC1);
		txtC1.setColumns(10);
		
		JCheckBox chBoxC1 = new JCheckBox("");
		GridBagConstraints gbc_chBoxC1 = new GridBagConstraints();
		gbc_chBoxC1.insets = new Insets(0, 0, 0, 5);
		gbc_chBoxC1.gridx = 2;
		gbc_chBoxC1.gridy = 4;
		pnlParameter.add(chBoxC1, gbc_chBoxC1);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		dilaog.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton btnAllAuto = new JButton("All Auto");
		GridBagConstraints gbc_btnAllAuto = new GridBagConstraints();
		gbc_btnAllAuto.insets = new Insets(0, 0, 0, 5);
		gbc_btnAllAuto.gridx = 0;
		gbc_btnAllAuto.gridy = 0;
		panel.add(btnAllAuto, gbc_btnAllAuto);
		
		JButton btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.gridx = 2;
		gbc_btnCancel.gridy = 0;
		panel.add(btnCancel, gbc_btnCancel);
		
		JButton btnGenerate = new JButton("Generate");
		GridBagConstraints gbc_btnGenerate = new GridBagConstraints();
		gbc_btnGenerate.gridx = 3;
		gbc_btnGenerate.gridy = 0;
		panel.add(btnGenerate, gbc_btnGenerate);


	}
}
