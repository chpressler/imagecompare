package de.fherfurt.imagecompare.swing.components;

import java.awt.Dimension;

import javax.swing.JTabbedPane;

import de.fherfurt.imagecompare.ResourceHandler;

public class ImageCompareComponent extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	
	public ImageCompareComponent() {
//		setPreferredSize(new Dimension(1024, 500));
		add(new LightTableComponent() , ResourceHandler.getInstance().getStrings().getString("lighttable"));
		add(new ImageViewerComponents(), ResourceHandler.getInstance().getStrings().getString("imageviewer"));
	}

}
