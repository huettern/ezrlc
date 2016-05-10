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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import pro2.MVC.Controller;
import pro2.Plot.Figure;
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
	
	private ImageIcon[] modelImage = new ImageIcon[10];
	int j = 0;
	
	//================================================================================
    // Constructors
    //================================================================================
	public ModelLabelPanel() {
		int panelWidth = 220;
		int panelLength = 150;
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(150, 220));
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
		for (int i = 1; i <= modelImage.length; i++) {
			modelImage[0] = UIUtil.loadResourceIcon("model_" + i + ".png", 160, 100);
		}
				
		// create new label with the image and add it
		JLabel label = new JLabel("", modelImage[0], JLabel.CENTER);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	//================================================================================
    // Paint Functions
    //================================================================================
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
