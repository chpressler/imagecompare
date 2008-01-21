package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSeparator;

public class FilterAndSortComponent extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public FilterAndSortComponent() {
		setBackground(new Color(80, 30, 30, 160));
//	    setOpaque(false);       
		setLayout(new GridLayout(0, 1));
		add(new FilterComponent());
		add(new SortComponent());
	}

}
