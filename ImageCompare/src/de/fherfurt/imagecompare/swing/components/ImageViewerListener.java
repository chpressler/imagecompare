package de.fherfurt.imagecompare.swing.components;

public interface ImageViewerListener {
	
	void zoomedIn();
	
	void zoomedOut();

	void scrolledHorizontal(int i);
	
	void scrolledVertical(int i);
	
}
