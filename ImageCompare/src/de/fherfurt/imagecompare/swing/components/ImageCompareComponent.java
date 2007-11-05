package de.fherfurt.imagecompare.swing.components;

import javax.swing.JTabbedPane;

import de.fherfurt.imagecompare.ResourceHandler;

public class ImageCompareComponent extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	
	public ImageCompareComponent() {
		add(new LightTableComponent() , ResourceHandler.getInstance().getStrings().getString("lighttable"));
		add(new ImageViewerComponents(), ResourceHandler.getInstance().getStrings().getString("imageviewer"));
	}

}
