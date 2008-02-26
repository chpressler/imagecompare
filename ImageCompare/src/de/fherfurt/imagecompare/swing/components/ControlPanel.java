package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static volatile ControlPanel instance;
	
	public static synchronized ControlPanel getInstance() {
		if(instance == null) {
			synchronized (ControlPanel.class) {
				if(instance == null) {
					instance = new ControlPanel();
				}
			}
		}
		return instance;
	}

	private ControlPanel() {
		setBackground(new Color(80, 30, 30, 160));
//	    setOpaque(false);       
		setLayout(new GridLayout(0, 1));
		add(new SearchComponent());
		add(new FilterComponent());
		add(new SortComponent());
	}
	
}
