package de.fherfurt.imagecompare.swing.components;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchComponent extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField string = new JTextField(10);
	
	private JTextField a = new JTextField(5);
	
	private JTextField f = new JTextField(5);
	
	public SearchComponent() {
		add(new JLabel("String: "));
		add(string);
		add(new JLabel("Aperture: "));
		add(a);
		add(new JLabel("Focus: "));
		add(f);
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Search"));
	}

}
