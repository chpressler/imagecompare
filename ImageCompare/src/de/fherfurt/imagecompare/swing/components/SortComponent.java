package de.fherfurt.imagecompare.swing.components;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class SortComponent extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JComboBox attribute;
	
	private JComboBox profile;
	
	public SortComponent() {
		setBackground(null);
	    setOpaque(false);
		setLayout(new GridLayout(0, 2));
		attribute = new JComboBox();
		profile = new JComboBox();
		add(new JLabel("by Attribute: "));
		add(attribute);
		add(new JLabel("by Profile: "));
		add(profile);
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Sort"));
	}

}
