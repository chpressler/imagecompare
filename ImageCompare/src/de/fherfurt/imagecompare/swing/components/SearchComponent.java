package de.fherfurt.imagecompare.swing.components;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchComponent extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField string = new JTextField(10);
	
	private JTextField a = new JTextField(5);
	
	private JTextField f = new JTextField(5);
	
	JPanel panel;
	
	public SearchComponent() {
		panel = new JPanel();
		panel.add(new JLabel("String: "));
		panel.add(string);
		panel.add(new JLabel("Aperture: "));
		panel.add(a);
		panel.add(new JLabel("Focus: "));
		panel.add(f);
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Search"));
		add(panel);
		setAlwaysOnTop(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
