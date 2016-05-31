package pro2.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalComboBoxUI;

import pro2.util.UIUtil;

public class ImageComboBox extends JPanel implements ActionListener{

	//================================================================================
    // Private Variables
    //================================================================================
	private ImageIcon[] images;
	private JComboBox list;
	private NewModelWindow parent;

	//================================================================================
    // Constructor
    //================================================================================
	public ImageComboBox(NewModelWindow parent, String[] imagesName, String[] imagesText) {
		super(new BorderLayout());
		this.parent = parent;
		
		//Load images
		images = new ImageIcon[imagesName.length];
        Integer[] intArray = new Integer[imagesName.length];
        for (int i = 0; i < imagesName.length; i++) {
            intArray[i] = new Integer(i);
            images[i] = UIUtil.loadResourceIcon(imagesName[i]);
            if (images[i] != null) {
                images[i].setDescription(imagesText[i]);
            }
        }
        
        //create JComboBox
        list = new JComboBox(intArray);
        Color bg = (Color) UIManager.get("ComboBox.background");
        Color fg = (Color) UIManager.get("ComboBox.foreground");
        UIManager.put("ComboBox.selectionBackground", bg);
        UIManager.put("ComboBox.selectionForeground", fg);
        list.setUI(new MetalComboBoxUI());
        list.setBackground(Color.WHITE);
        ComboBoxRenderer renderer= new ComboBoxRenderer(images, imagesText);
        renderer.setPreferredSize(new Dimension(200, 150));
        list.setRenderer(renderer);
        list.setMaximumRowCount(3);
        list.addActionListener(this);

        //Lay out the demo.
        add(list, BorderLayout.PAGE_START);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	}


	//================================================================================
    // Public methods
    //================================================================================
	/**
	 * Returns the combobox
	 * @return
	 */
	public JComboBox getList () {
		return this.list;
	}
	
	//================================================================================
    // Listener
    //================================================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		parent.comboBoxSelected(e);
		
	}

}
