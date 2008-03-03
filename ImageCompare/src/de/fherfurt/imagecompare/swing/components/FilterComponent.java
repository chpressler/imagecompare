package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FilterComponent extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField string = new JTextField(10);
	
	private JTextField a = new JTextField(5);
	
	private JTextField f = new JTextField(5);
	
	public FilterComponent() {
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.white, Color.lightGray), "Filter"));
		setBackground(null);
	    setOpaque(false);
		
	}

}
