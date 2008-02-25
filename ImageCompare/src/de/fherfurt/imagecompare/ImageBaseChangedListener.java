package de.fherfurt.imagecompare;

import java.awt.image.BufferedImage;

import de.fherfurt.imagecompare.swing.components.ImageThumbnailComponent;

public interface ImageBaseChangedListener {
	
	void clear();
	
	void add(ImageThumbnailComponent image, boolean b);
	
	void removedImage(String path);
	
	void sorted();

}
