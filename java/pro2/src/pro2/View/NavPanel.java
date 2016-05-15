package pro2.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import pro2.MVC.Controller;
import pro2.MVC.Controller.DataSource;
import pro2.MVC.Model;
import pro2.MVC.Model.UpdateEvent;
import pro2.Plot.DataSetLabelPanel;
import pro2.Plot.RectPlot.RectPlotNewMeasurement;
import pro2.View.WorkPanel.ViewType;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import javax.swing.Box;
import java.awt.FlowLayout;

public class NavPanel extends JPanel implements ActionListener, Observer {
	
	//================================================================================
    // Local Variables
    //================================================================================
	private Controller controller;
	
	private FileChooser fileChooser;
	private GraphWindow graphWindow;
	private NewModelWindow newModelWindow;
	
	private JButton btnLoadFile, btnNewModel, btnNewGraph, btnFigure, btnIGAssist;
	private JLabel lblInputFile;
	private File file;
	private JPanel pnlModel;
	private GridBagLayout gbl_pnlModel;
	private List<ModelLabelPanel> modelLabelPanels = new ArrayList<ModelLabelPanel>();
	
	private int modelPnlRowCnt = 0;
	
	
	//================================================================================
    // Constructors
    //================================================================================
	public NavPanel(Controller controller) {
		this.controller = controller;	
		this.fileChooser = new FileChooser(controller);
		this.graphWindow = new GraphWindow(controller);
		this.newModelWindow = new NewModelWindow(controller);
	}
		
	
	//================================================================================
    // Public Functions
    //================================================================================
	/**
	 * Builds the navigation Panel
	 * @wbp.parser.entryPoint
	 */
	public void build() {
		this.setBorder(null);
		this.setMinimumSize(new Dimension(200, 200));
		
		GridBagLayout gbl_navPanel = new GridBagLayout();
		gbl_navPanel.columnWidths = new int[] {0, 0};
		gbl_navPanel.rowHeights = new int[] {0, 0, 0, 0};
		gbl_navPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_navPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0};
		this.setLayout(gbl_navPanel);
		
		this.buildFilePanel();
		this.buildViewTypePanel();
		this.buildGraphPanel();
		this.buildModelPanel();
	}
	
	
	//================================================================================
    // Private Functions
    //================================================================================
	/**
	 * build the input file panel
	 */
	private void buildFilePanel() {	
		JPanel pnlInFile = new JPanel();
		pnlInFile.setMaximumSize(new Dimension(0, 0));
		pnlInFile.setPreferredSize(new Dimension(200, 85));
		pnlInFile.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Input File", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlInFile = new GridBagConstraints();
		gbc_pnlInFile.anchor = GridBagConstraints.NORTH;
		gbc_pnlInFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlInFile.insets = new Insets(5, 0, 0, 0);
		gbc_pnlInFile.gridx = 0;
		gbc_pnlInFile.gridy = 0;
		this.add(pnlInFile, gbc_pnlInFile);
		GridBagLayout gbl_pnlInFile = new GridBagLayout();
		gbl_pnlInFile.columnWidths = new int[]{189};
		gbl_pnlInFile.rowHeights = new int[] {29};
		gbl_pnlInFile.columnWeights = new double[]{1.0};
		gbl_pnlInFile.rowWeights = new double[]{0.0};
		pnlInFile.setLayout(gbl_pnlInFile);
		
		//File Label
		file = null;
		lblInputFile = new JLabel("");
		lblInputFile.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_lblInputFile = new GridBagConstraints();
		gbc_lblInputFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblInputFile.insets = new Insets(0, 4, 0, 0);
		gbc_lblInputFile.gridx = 0;
		gbc_lblInputFile.gridy = 0;
		pnlInFile.add(lblInputFile, gbc_lblInputFile);
		
		//LoadFile Button
		btnLoadFile = new JButton("Load File...");
		GridBagConstraints gbc_btnLoadFile = new GridBagConstraints();
		gbc_btnLoadFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLoadFile.insets = new Insets(4, 4, 4, 4);
		gbc_btnLoadFile.gridx = 0;
		gbc_btnLoadFile.gridy = 1;
		pnlInFile.add(btnLoadFile, gbc_btnLoadFile);
		btnLoadFile.addActionListener(this);
		
		fileChooser.fileFilter();
	}

	
	/**
	 * Build the panel with the view type switch
	 */
	private void buildViewTypePanel() {
		JPanel pnlPanel = new JPanel();
		pnlPanel.setMaximumSize(new Dimension(0, 0));
		pnlPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "View Type", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlPanel = new GridBagConstraints();
		gbc_pnlPanel.anchor = GridBagConstraints.NORTH;
		gbc_pnlPanel.insets = new Insets(5, 0, 0, 0);
		gbc_pnlPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlPanel.gridx = 0;
		gbc_pnlPanel.gridy = 1;
		this.add(pnlPanel, gbc_pnlPanel);
//		GridBagLayout gbl_pnlPanel = new GridBagLayout();
//		gbl_pnlPanel.columnWidths = new int[] {0,0};
//		gbl_pnlPanel.rowHeights = new int[] {0,0};
//		gbl_pnlPanel.columnWeights = new double[]{1.0,1.0};
//		gbl_pnlPanel.rowWeights = new double[]{0.0,0.0};
//		pnlPanel.setLayout(gbl_pnlPanel);
		pnlPanel.setLayout(new GridLayout(1, 2));
		
		//IGAssist Button
		btnIGAssist = new JButton("IGAssist");
		btnIGAssist.setSelected(true);
		//btnNewGraph.setPreferredSize(new Dimension(87, 23));
		//btnNewGraph.setMinimumSize(new Dimension(87, 23));
		//btnNewGraph.setMaximumSize(new Dimension(87, 23));
		GridBagConstraints gbc_btnIGAssist = new GridBagConstraints();
		gbc_btnIGAssist.insets = new Insets(4, 4, 4, 0);
		gbc_btnIGAssist.fill = GridBagConstraints.BOTH;
		gbc_btnIGAssist.gridx = 0;
		gbc_btnIGAssist.gridy = 0;
		pnlPanel.add(btnIGAssist, gbc_btnIGAssist);
		btnIGAssist.addActionListener(this);
		
		//Figure Button
		btnFigure = new JButton("Figure");
		btnFigure.setSelected(false);
		//btnNewGraph.setPreferredSize(new Dimension(87, 23));
		//btnNewGraph.setMinimumSize(new Dimension(87, 23));
		//btnNewGraph.setMaximumSize(new Dimension(87, 23));
		GridBagConstraints gbc_btnFigure = new GridBagConstraints();
		gbc_btnFigure.insets = new Insets(4, 0, 4, 4);
		gbc_btnFigure.fill = GridBagConstraints.BOTH;
		gbc_btnFigure.gridx = 1;
		gbc_btnFigure.gridy = 0;
		pnlPanel.add(btnFigure, gbc_btnFigure);
		btnFigure.addActionListener(this);
	}
	
	/**
	 * build the Graph panel
	 */
	private void buildGraphPanel() {
		JPanel pnlPanel = new JPanel();
		pnlPanel.setMaximumSize(new Dimension(0, 0));
		pnlPanel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Graph", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlPanel = new GridBagConstraints();
		gbc_pnlPanel.anchor = GridBagConstraints.NORTH;
		gbc_pnlPanel.insets = new Insets(5, 0, 0, 0);
		gbc_pnlPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlPanel.gridx = 0;
		gbc_pnlPanel.gridy = 2;
		this.add(pnlPanel, gbc_pnlPanel);
		GridBagLayout gbl_pnlPanel = new GridBagLayout();
		gbl_pnlPanel.columnWidths = new int[] {52};
		gbl_pnlPanel.rowHeights = new int[] {29};
		gbl_pnlPanel.columnWeights = new double[]{1.0};
		gbl_pnlPanel.rowWeights = new double[]{0.0};
		pnlPanel.setLayout(gbl_pnlPanel);
		
		//New Graph Button
		btnNewGraph = new JButton("New Graph");
		//btnNewGraph.setPreferredSize(new Dimension(87, 23));
		//btnNewGraph.setMinimumSize(new Dimension(87, 23));
		//btnNewGraph.setMaximumSize(new Dimension(87, 23));
		GridBagConstraints gbc_btnNewGraph = new GridBagConstraints();
		gbc_btnNewGraph.insets = new Insets(4, 4, 4, 4);
		gbc_btnNewGraph.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewGraph.gridx = 0;
		gbc_btnNewGraph.gridy = 0;
		pnlPanel.add(btnNewGraph, gbc_btnNewGraph);
		btnNewGraph.addActionListener(this);
	}
		
	/**
	 * build the model panel
	 * @wbp.parser.entryPoint
	 */
	private void buildModelPanel() {
		JPanel pnlModelBorder = new JPanel();
		pnlModelBorder.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Models", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_pnlModel = new GridBagConstraints();
		gbc_pnlModel.insets = new Insets(5, 0, 5, 0);
		gbc_pnlModel.anchor = GridBagConstraints.NORTH;
		gbc_pnlModel.fill = GridBagConstraints.BOTH;
		gbc_pnlModel.gridx = 0;
		gbc_pnlModel.gridy = 3;
		this.add(pnlModelBorder, gbc_pnlModel);
		GridBagLayout gbl_pnlModelBorder = new GridBagLayout();
		gbl_pnlModelBorder.columnWidths = new int[]{189};
		gbl_pnlModelBorder.rowHeights = new int[] {29};
		gbl_pnlModelBorder.columnWeights = new double[]{1.0};
		gbl_pnlModelBorder.rowWeights = new double[]{1.0};
		pnlModelBorder.setLayout(gbl_pnlModelBorder);
		
		JScrollPane spModel = new JScrollPane();
		spModel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spModel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_spModel = new GridBagConstraints();
		gbc_spModel.fill = GridBagConstraints.BOTH;
		gbc_spModel.gridx = 0;
		gbc_spModel.gridy = 0;
		pnlModelBorder.add(spModel, gbc_spModel);
		spModel.getVerticalScrollBar().setUnitIncrement(25);
		
		pnlModel = new JPanel();
		spModel.setColumnHeaderView(pnlModel);
		spModel.setViewportView(pnlModel);
		gbl_pnlModel = new GridBagLayout();
		gbl_pnlModel.columnWidths = new int[]{0};
		gbl_pnlModel.rowHeights = new int[]{0};
		gbl_pnlModel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_pnlModel.rowWeights = new double[]{Double.MIN_VALUE};
		pnlModel.setLayout(gbl_pnlModel);
		
		Component verticalGlue = Box.createVerticalGlue();
		verticalGlue.setMaximumSize(new Dimension(0, 0));
		GridBagConstraints gbc_verticalGlue = new GridBagConstraints();
		gbc_verticalGlue.weighty = 0.0;
		gbc_verticalGlue.weightx = 1.0;
		gbc_verticalGlue.fill = GridBagConstraints.BOTH;
		gbc_verticalGlue.gridx = 0;
		gbc_verticalGlue.gridy = 99;
		pnlModel.add(verticalGlue, gbc_verticalGlue);
		
		//New Model Button
		btnNewModel = new JButton("New Model");
		//btnNewModel.setPreferredSize(new Dimension(87, 23));
		//btnNewModel.setMinimumSize(new Dimension(87, 23));
		//btnNewModel.setMaximumSize(new Dimension(87, 23));
		GridBagConstraints gbc_btnNewModel = new GridBagConstraints();
		gbc_btnNewModel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewModel.insets = new Insets(4, 4, 4, 4);
		gbc_btnNewModel.gridx = 0;
		gbc_btnNewModel.gridy = 1;
		pnlModelBorder.add(btnNewModel, gbc_btnNewModel);
		btnNewModel.addActionListener(this);
	}
	

	/**
	 * Builds the necessary datasets for the IGAssist panel
	 */
	private void buildIGAssistDataSet() {
		// create datasets
		controller.buildIGAssistDataSet();
	}

	/**
	 * Adds a new Model label panel based on the new generated Equivalent circuit
	 * @param o
	 */
	private void addNewModelLabel(Model m) {
		modelLabelPanels.add(new ModelLabelPanel(m.getEQCID(), m.getEquivalentCircuit(m.getEQCID()).getCircuitType()));
		
		pnlModel.add(modelLabelPanels.get(modelLabelPanels.size()-1), 
				new GridBagConstraints(0, modelPnlRowCnt++, 1, 1, 1.0, 0.0, 
						GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, 
						new Insets(0,0,5,0), 0, 0));
		// adjust pnlDataSets gridbaglayout so that row weights are zero except last one
		double[] d = new double[modelPnlRowCnt+1];
		for (int i = 0; i<d.length; i++) { d[i] = 0.0; }
		d[modelPnlRowCnt] = 1.0;
		gbl_pnlModel.rowWeights = d;
		pnlModel.setLayout(gbl_pnlModel);
		super.updateUI();
		updateUI();
	}
	
	//================================================================================
    // Public Functions
    //================================================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//handle the file chooser
		if(e.getSource() == btnLoadFile) {
			String fName = fileChooser.showFileChooser();			
			lblInputFile.setText(fName);
			File f = fileChooser.getFile();
			if (f != null) {
				controller.loadFile(f);
				this.buildIGAssistDataSet();
				controller.manualNotify();
			}	
		}
		
		//handle new Graphs
		if(e.getSource() == btnNewGraph) {	
			graphWindow.buildDialog();
		}
		
		//handle new Model
		if(e.getSource() == btnNewModel) {
			newModelWindow.buildNewModelWindow();
		}
		
		// handle work panel switcher
		if(e.getSource() == btnFigure) {
			controller.setWorkPanelView(ViewType.FIGURE);
			this.setViewButtonStatus(ViewType.FIGURE);
		}
		if(e.getSource() == btnIGAssist) {
			controller.setWorkPanelView(ViewType.IGASSIST);
			this.setViewButtonStatus(ViewType.IGASSIST);
		}
		
	}


	/**
	 * Sets the selected status of the view switcher of the work panel
	 * @param t ViewType
	 */
	public void setViewButtonStatus (ViewType t) {
		if(t == ViewType.FIGURE) {
			btnFigure.setSelected(true);
			btnIGAssist.setSelected(false);
		}else if(t == ViewType.IGASSIST) {
			btnFigure.setSelected(false);
			btnIGAssist.setSelected(true);
		}
	}


	/**
	 * Sets the file name label
	 * @param name
	 */
	public void setFileName(String name) {
		lblInputFile.setText(name);
	}


	@Override
	public void update(Observable o, Object arg) {
		if(arg == UpdateEvent.NEW_EQC) {
			addNewModelLabel((Model)o);
		}
		for (ModelLabelPanel modelLabelPanel : modelLabelPanels) {
			modelLabelPanel.update(o, arg);
		}
	}



	/**
	 * Sets the status of the new graph button
	 * @param b enabled(true) or disabled(flase)
	 */
	public void setNewGraphButtonEnabled(boolean b) {
		btnNewGraph.setEnabled(b);
	}
	
}
