package ezrlc.View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import ezrlc.util.UIUtil;

class ComboBoxRenderer extends JLabel implements ListCellRenderer {
	//================================================================================
    // Local Variables
    //================================================================================
	private Font uhOhFont;
	private ImageIcon[] image;
	private String[] text;
	
	//================================================================================
    // Constructors
    //================================================================================
	public ComboBoxRenderer(ImageIcon[] image, String[] text) {
		this.image = new ImageIcon[image.length];
		this.text = new String[text.length];
		System.arraycopy(image, 0, this.image, 0, image.length);
		System.arraycopy(text, 0, this.text, 0, text.length);
		setOpaque(true);
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
	}
	
	//================================================================================
    // Public Functions
    //================================================================================
	/**
	* This method finds the image and text corresponding
	* to the selected value and returns the label, set up
	* to display the text and image.
	*/
	public Component getListCellRendererComponent(
					                JList list,
					                Object value,
					                int index,
					                boolean isSelected,
					                boolean cellHasFocus) {
		//Get the selected index. (The index param isn't
		//always valid, so just use the value.)
		int selectedIndex = ((Integer)value).intValue();
		
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		
		//Set the icon and text.  If icon was null, say so.
		ImageIcon icon = image[selectedIndex];
		String model = text[selectedIndex];
		setIcon(icon);
		if (icon != null) {
			setText(model);
			setFont(list.getFont());
		} else {
			setUhOhText(model + " (no image available)",
						list.getFont());
		}
		
		this.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		return this;
	}
	
	//Set the font and text when no image was found.
	protected void setUhOhText(String uhOhText, Font normalFont) {
		if (uhOhFont == null) { //lazily create this font
			uhOhFont = normalFont.deriveFont(Font.ITALIC);
		}
		setFont(uhOhFont);
		setText(uhOhText);
	}
}
