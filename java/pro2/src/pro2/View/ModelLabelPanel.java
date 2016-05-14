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
import pro2.Plot.Figure;
import pro2.util.MathUtil;
import pro2.util.UIUtil;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JTextField;
import java.awt.Rectangle;

public class ModelLabelPanel extends JPanel implements ActionListener {

	private JTextField txtC0;
	private JTextField txtR0;
	private JTextField txtAlpha;
	private JTextField txtC1;
	private JTextField txtL0;
	private JTextField txtF;
	private JTextField txtR1;
	
	private ImageIcon[] modelImage = new ImageIcon[21];
	int j = 0;
	
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
	public ModelLabelPanel() {
		int panelWidth = 220;
		int panelLength = 150;
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(340, 220));
		setMaximumSize(new Dimension(32767, 32767));
		setMinimumSize(new Dimension(10, 100));
		setBorder(new LineBorder(Color.BLACK));
		setLayout(new GridLayout(2, 1, 0, 0));
		
		//Model-Image
//		for (int i = 0; i < modelImage.length; i++) {
//			//modelImage[i] = loadResourceImage("model_"+i+".png");	
//			File file = new File("../../images/RLC/model_"+i+".png");
//            try {
//				modelImage[i] = ImageIO.read(file);
//			} catch (IOException e) {
//				System.out.println("Can not load image");
//				e.printStackTrace();
//			}
//		}
		
		// load image icon
		for (int i = 0; i < modelImage.length; i++) {
			modelImage[i] = UIUtil.loadResourceIcon("model_" + i + ".png", 160, 100);
		}	

		// create new label with the image and add it
		JLabel label = new JLabel("", JLabel.CENTER);
		label.setOpaque(false);
		add( label, 0 );
				
		//Parameters of R, L, C, ...
		JPanel pnlModelLabel = new JPanel();
		add(pnlModelLabel,1);
		pnlModelLabel.setLayout(new GridLayout(4, 4, 4, 1));
		
		JLabel lblL = new JLabel("L");
		pnlModelLabel.add(lblL);
		
		txtL0 = new JTextField();
		pnlModelLabel.add(txtL0);
		txtL0.setColumns(10);
		
		JLabel lblR0 = new JLabel("<html> R<sub>0</sub> </html>");
		pnlModelLabel.add(lblR0);
		
		txtR0 = new JTextField();
		pnlModelLabel.add(txtR0);
		txtR0.setColumns(10);
		
		JLabel lblC = new JLabel("<html> C<sub>0</sub> </html>");
		pnlModelLabel.add(lblC);
		
		txtC0 = new JTextField();
		pnlModelLabel.add(txtC0);
		txtC0.setColumns(10);
		
		JLabel lblAlpha = new JLabel("\u03B1");
		pnlModelLabel.add(lblAlpha);
		
		txtAlpha = new JTextField();
		pnlModelLabel.add(txtAlpha);
		txtAlpha.setColumns(10);
		
		JLabel lblR1 = new JLabel("<html> R<sub>1</sub> </html>");
		pnlModelLabel.add(lblR1);
		
		txtR1 = new JTextField();
		pnlModelLabel.add(txtR1);
		txtR1.setColumns(10);
		
		JLabel f = new JLabel("f");
		pnlModelLabel.add(f);
		
		txtF = new JTextField();
		pnlModelLabel.add(txtF);
		txtF.setColumns(10);
		
		JLabel lblC1 = new JLabel("<html> C<sub>1</sub> </html>");
		pnlModelLabel.add(lblC1);
		
		txtC1 = new JTextField();
		pnlModelLabel.add(txtC1);
		txtC1.setColumns(10);
	}

	/**
	 * Builds a new ModelLabelPanel with the id to the equivalent circuit
	 * @param id ID to the EQC in the model
	 */
	public ModelLabelPanel(int id) {
		this();
		eqcID = id;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	//================================================================================
    // Paint Functions
    //================================================================================
	/**
	 * Adapts the UI to the stored EQC
	 */
	private void adaptToEQC(MCEqCircuit e) {
		// create new label with the image and add it
		JLabel label = new JLabel("", modelImage[e.getCircuitType().ordinal()], JLabel.CENTER);
		label.setOpaque(false);
		add( label, 0 );
		
		txtR0.setEditable(r0EditableLUT[e.getCircuitType().ordinal()]);
		txtF.setEditable(f0EditableLUT[e.getCircuitType().ordinal()]);
		txtAlpha.setEditable(alphaEditableLUT[e.getCircuitType().ordinal()]);
		txtR1.setEditable(r1EditableLUT[e.getCircuitType().ordinal()]);
		txtL0.setEditable(lEditableLUT[e.getCircuitType().ordinal()]);
		txtC0.setEditable(c0EditableLUT[e.getCircuitType().ordinal()]);
		txtC1.setEditable(c1EditableLUT[e.getCircuitType().ordinal()]);
	}
	
	/**
	 * Updates the parameters
	 */
	private void updateParams(MCEqCircuit e) {
		double[] p = e.getParameters();
		txtR0.setText(MathUtil.num2eng(p[0], 2));
		txtF.setText(MathUtil.num2eng(p[1], 2));
		txtAlpha.setText(MathUtil.num2eng(p[2], 2));
		txtR1.setText(MathUtil.num2eng(p[3], 2));
		txtL0.setText(MathUtil.num2eng(p[4], 2));
		txtC0.setText(MathUtil.num2eng(p[5], 2));
		txtC1.setText(MathUtil.num2eng(p[6], 2));
	}
	
//	@Override
//	public void paintComponent(Graphics g) {
//		System.out.println("paint image");
//		modelImage[0] = UIUtil.loadResourceImage("model_0.png", this.getWidth(), this.getHeight()/2);
//		
//		//g.drawImage(this.modelImage[0], 0, 0, null);
//		g.fillOval(0, 0, 100, 100);
//
//		System.out.println("paint parent");
//		super.paintComponent(g);
//	}
	
	//================================================================================
    // Public Functions
    //================================================================================
	public void update(Observable o, Object arg) {
		Model m = (Model)o;
		adaptToEQC(m.getEquivalentCircuit(this.eqcID));
		updateParams(m.getEquivalentCircuit(this.eqcID));
		updateUI();
	}
	
//	/**
//	 * load model images
//	 */
//	public static Image loadResourceImage(String strBild) {
//		Container p = new Container();
//		
//		MediaTracker tracker = new MediaTracker(p);
//		Image img = (new ImageIcon(ModelLabelPanel.class.getClassLoader().getResource("../../images/RLC/" + strBild))).getImage();
//		tracker.addImage(img, 0);
//		try {
//			tracker.waitForID(0);
//		} catch (InterruptedException ex) {
//			System.out.println("Can not load image: " + strBild);
//		}
//		return img;
//	}
	
	
}
