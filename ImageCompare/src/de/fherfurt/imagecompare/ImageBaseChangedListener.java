package de.fherfurt.imagecompare;

import java.awt.image.BufferedImage;

public interface ImageBaseChangedListener {
	
	void clear();
	
	void add(BufferedImage image, String path, boolean b);
	
	void removedImage(String path);

}
