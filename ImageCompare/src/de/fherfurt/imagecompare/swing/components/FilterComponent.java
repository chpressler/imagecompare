package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import de.fherfurt.imagecompare.Attributes;

public class FilterComponent extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField string = new JTextField(10);
	
	private JTextField a = new JTextField(5);
	
	private JTextField f = new JTextField(5);
	
	public FilterComponent() {
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(Color.white, Color.lightGray), "Filter"));
		setBackground(null);
	    setOpaque(false);
		
	    JButton b = new JButton("options");
	    b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FilterFrame.getInstance().setVisible(true);
			}});
	    add(b);
	}

}
