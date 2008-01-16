package de.fherfurt.imagecompare.swing.components;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SortComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JComboBox comboBox1 = null;
	
	private JComboBox comboBox2 = null;
	
	public SortComponent() {
		comboBox1 = new JComboBox();
		comboBox1.setToolTipText("sort by Attribute ");
		comboBox1.setPreferredSize(new Dimension(120, 22));
		add(new JLabel("by Attribute: "));
		add(comboBox1);
		comboBox2 = new JComboBox();
		comboBox2.setToolTipText("sort by profile");
		comboBox2.setPreferredSize(new Dimension(120, 22));
		add(new JLabel("by Profile: "));
		add(comboBox2);
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Sort"));
	}
	
}
