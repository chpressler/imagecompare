package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static volatile ControlPanel instance;
	
	private SearchComponent sc;
	
	private FilterComponent fc;
	
	private SortComponent soc;
	
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
		setBackground(new Color(60, 25, 25, 200));
//	    setOpaque(false);       
		setLayout(new GridLayout(0, 1));
		
		sc = new SearchComponent();
		fc = new FilterComponent();
		soc = new SortComponent();
		
		add(sc);
		add(fc);
		add(soc);
	}

	public SearchComponent getSearchcomponent() {
		return sc;
	}

	public FilterComponent getFilterComponent() {
		return fc;
	}

	public SortComponent getSortComponent() {
		return soc;
	}
	
}
