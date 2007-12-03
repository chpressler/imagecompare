package de.fherfurt.imagecompare.swing.components;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ImageViewerComponents extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<ImageViewerListener> listeners = new ArrayList<ImageViewerListener>();
	
	public ImageViewerComponents() {
		setLayout(new FlowLayout());
		ImageViewerComponent iv1 = new ImageViewerComponent();
		ImageViewerComponent iv2 = new ImageViewerComponent();
		ImageViewerComponent iv3 = new ImageViewerComponent();
		ImageViewerComponent iv4 = new ImageViewerComponent();
		add(iv1);
		add(iv2);
		add(iv3);
		add(iv4);
		listeners.add(iv1);
		listeners.add(iv2);
		listeners.add(iv3);
		listeners.add(iv4);
	}

	public void zoomedIn() {
		for(ImageViewerListener ivl : listeners) {
			ivl.zoomedIn();
		}
	}
	
	public void zoomedOut() {
		for(ImageViewerListener ivl : listeners) {
			ivl.zoomedOut();
		}
	}
	
	public void scrolledHorizontal(int i) {
		for(ImageViewerListener ivl : listeners) {
			ivl.scrolledHorizontal(i);
		}
	}
	
	public void scrolledVertical(int i) {
		for(ImageViewerListener ivl : listeners) {
			ivl.scrolledVertical(i);
		}
	}
	
}
