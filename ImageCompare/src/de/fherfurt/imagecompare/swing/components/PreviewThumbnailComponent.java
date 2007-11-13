package de.fherfurt.imagecompare.swing.components;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PreviewThumbnailComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public PreviewThumbnailComponent() {
		setLayout(new FlowLayout());
		ImageIcon ii = new ImageIcon();
		
//		add();
	}
	
	public void addThumbnail(BufferedImage image) {
		add(new ImageComponent(image));
	}

}
