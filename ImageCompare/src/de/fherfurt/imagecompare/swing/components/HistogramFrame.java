package de.fherfurt.imagecompare.swing.components;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class HistogramFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	
	public HistogramFrame() {
		panel = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		panel.setLayout(gbl);
		addComponent(panel, gbl, new HistogramComponent(), 0, 0, 1, 1, 1.0, 1.0);
		addComponent(panel, gbl, new HistogramComponent(), 0, 2, 1, 1, 1.0, 1.0);
		addComponent(panel, gbl, new HistogramComponent(), 0, 3, 1, 1, 1.0, 1.0);
		addComponent(panel, gbl, new HistogramComponent(), 0, 4, 1, 1, 1.0, 1.0);
		validate();
		getContentPane().add(panel);
	}
	
	private static void addComponent(JPanel p, GridBagLayout gbl,
			Component c, int x, int y, int width, int height, double weightx,
			double weighty) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbl.setConstraints(c, gbc);
		p.add(c);
	}

}
