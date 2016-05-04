package pro2.Model;

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
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import pro2.MVC.Controller;
import pro2.Plot.Figure;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

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
	
	private Image[] modelImage = new Image[21];
	
	//================================================================================
    // Constructors
    //================================================================================
	public ModelLabelPanel() {
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(150, 100));
		setMaximumSize(new Dimension(32767, 100));
		setMinimumSize(new Dimension(10, 100));
		setBorder(new LineBorder(Color.BLACK));
		setLayout(new GridLayout(0, 1, 0, 0));
		
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
		
		//ImageIcon imag = new ImageIcon("model_0.png");
		
		JPanel pnlModelImage = new JPanel(new GridLayout(0, 1, 0, 0));
		JLabel label = new JLabel();
		label.setIcon(new ImageIcon("model_0.png"));
		
		pnlModelImage.add(label);
	
		add(pnlModelImage);
		
		//Parameters of R, L, C, ...
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
		
		JLabel lblAlpha = new JLabel("\u03B1");
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
	}
	
	//================================================================================
    // Public Functions
    //================================================================================
	/**
	 * load model images
	 */
	public static Image loadResourceImage(String strBild) {
		Container p = new Container();
		
		MediaTracker tracker = new MediaTracker(p);
		Image img = (new ImageIcon(ModelLabelPanel.class.getClassLoader().getResource("../../images/RLC/" + strBild))).getImage();
		tracker.addImage(img, 0);
		try {
			tracker.waitForID(0);
		} catch (InterruptedException ex) {
			System.out.println("Can not load image: " + strBild);
		}
		return img;
	}
	
	
}
