package de.fherfurt.imagecompare.swing.components;

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
		setBackground(null);
	    setOpaque(false);
		setLayout(new GridLayout(0, 2));
		add(new JLabel("String: "));
		add(string);
		add(new JLabel("Aperture: "));
		add(a);
		add(new JLabel("Focus: "));
		add(f);
		
		add(new JLabel("String: "));
		add(string);
		add(new JLabel("String: "));
		add(string);
		add(new JLabel("String: "));
		add(string);
		add(new JLabel("String: "));
		add(string);
		add(new JLabel("String: "));
		add(string);
		add(new JLabel("String: "));
		add(string);
		add(new JLabel("String: "));
		add(string);
		add(new JLabel("String: "));
		add(string);
		add(new JLabel("String: "));
		add(string);
		
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Filter"));
	}

}
