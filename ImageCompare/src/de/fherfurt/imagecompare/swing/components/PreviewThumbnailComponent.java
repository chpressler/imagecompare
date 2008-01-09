package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.dnd.DnDConstants;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JPanel;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ImageBaseChangedListener;
import de.fherfurt.imagecompare.swing.controller.ImageComponentDragSource;
import de.fherfurt.imagecompare.swing.controller.ThumbnailDragSource;

public class PreviewThumbnailComponent extends JPanel implements ImageBaseChangedListener {

	private static final long serialVersionUID = 1L;
	
	public PreviewThumbnailComponent() {
		setBackground(Color.DARK_GRAY);
		setLayout(new FlowLayout());
		ImageBase.getInstance().addImageBaseChangedListener(this);
		try {
			ImageBase.getInstance().setImageBase(new File("C:/Dokumente und Einstellungen/All Users/Dokumente/Eigene Bilder/Beispielbilder"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void addThumbnail(ImageComponent image) {
		image.setThumbnail(80);
		new ImageComponentDragSource(image, DnDConstants.ACTION_COPY);
		add(image);
	}
	
	public void addThumbnail(BufferedImage image, String path) {
		ImageComponent ic = new ImageComponent(image, path);
		ic.setThumbnail(80);
		new ImageComponentDragSource(ic, DnDConstants.ACTION_COPY);
		add(ic);
	}

	public void clear() {
		removeAll();	
		System.gc();
	}
	
	public void add(BufferedImage image, String path, boolean b) {
//		addThumbnail(new ImageComponent(image));
		ImageThumbnailComponent itc = new ImageThumbnailComponent(image, path);
		new ThumbnailDragSource(itc, DnDConstants.ACTION_COPY);
		add(itc);
		updateUI();
	}

}
