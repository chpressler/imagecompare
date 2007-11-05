package de.fherfurt.imagecompare.swing.components;

import java.awt.FlowLayout;

import javax.swing.JPanel;

public class ImageViewerComponents extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public ImageViewerComponents() {
		setLayout(new FlowLayout());
		add(new ImageViewerComponent());
		add(new ImageViewerComponent());
		add(new ImageViewerComponent());
		add(new ImageViewerComponent());
	}

}
