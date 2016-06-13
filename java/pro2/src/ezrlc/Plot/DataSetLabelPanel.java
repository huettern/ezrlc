package ezrlc.Plot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 * Data Set label panel displays the dataset info in the figure
 * 
 * @author noah
 *
 */
public class DataSetLabelPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private int id = 0;
	private JLabel lblTraceName;
	private JLabel lblTraceType;
	private JPanel pnlDrawing;
	private JButton btnRemove;
	private Figure figure;

	/**
	 * Create new data set label panel
	 * 
	 * @param fig
	 *            figure object
	 * @param c
	 *            color of the dataset
	 * @param id
	 *            id of the associated data set, used to delete it
	 * @param name
	 *            name of the dataset, printed bold
	 * @param type
	 *            type of the dataset, printed regular
	 */
	public DataSetLabelPanel(Figure fig, Color c, int id, String name, String type) {
		this.id = id;
		this.figure = fig;
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(80, 130));
		setMaximumSize(new Dimension(32767, 32767));
		setMinimumSize(new Dimension(10, 100));
		setBorder(new LineBorder(Color.BLACK));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[] { 1.0 };
		setLayout(gridBagLayout);

		pnlDrawing = new JPanel();
		pnlDrawing.setBackground(c);
		GridBagConstraints gbc_pnlDrawing = new GridBagConstraints();
		gbc_pnlDrawing.insets = new Insets(0, 5, 5, 5);
		gbc_pnlDrawing.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlDrawing.weightx = 1.0;
		gbc_pnlDrawing.gridx = 0;
		gbc_pnlDrawing.gridy = 0;
		add(pnlDrawing, gbc_pnlDrawing);

		lblTraceName = new JLabel(name, SwingConstants.CENTER);
		GridBagConstraints gbc_lblTraceName = new GridBagConstraints();
		gbc_lblTraceName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTraceName.insets = new Insets(0, 5, 5, 5);
		gbc_lblTraceName.gridx = 0;
		gbc_lblTraceName.gridy = 1;
		add(lblTraceName, gbc_lblTraceName);

		lblTraceType = new JLabel(type, SwingConstants.CENTER);
		GridBagConstraints gbc_lblTraceType = new GridBagConstraints();
		gbc_lblTraceType.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTraceType.insets = new Insets(0, 5, 5, 5);
		gbc_lblTraceType.gridx = 0;
		gbc_lblTraceType.gridy = 2;
		add(lblTraceType, gbc_lblTraceType);

		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(this);
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.insets = new Insets(0, 5, 0, 5);
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.weightx = 1.0;
		gbc_btnRemove.gridx = 0;
		gbc_btnRemove.gridy = 3;
		add(btnRemove, gbc_btnRemove);
	}

	public void setColor(Color c) {
		this.pnlDrawing.setBackground(c);
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return this.id;
	}

	public void setLabel(String s) {
		this.lblTraceName.setText(s);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRemove) {
			figure.removeDataset(id);
		}
	}
}
