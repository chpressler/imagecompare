package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import com.blogofbug.swing.components.JCarosel;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ImageBaseChangedListener;
import de.fherfurt.imagecompare.swing.controller.PreviewCarouselComponentExternalDropTarget;

public class PreviewCarouselComponent extends JCarosel implements ImageBaseChangedListener {

	private static final long serialVersionUID = 1L;
	
	public PreviewCarouselComponent() {
		super(128);
		new PreviewCarouselComponentExternalDropTarget(this);
		setPreferredSize(new Dimension(400, 300));
		setMinimumSize(new Dimension(300, 100));
		setMaximumSize(new Dimension(400, 400));
		setBackground(Color.black, new Color(80, 30, 30));
		ImageBase.getInstance().addImageBaseChangedListener(this);
	}
	
	public void clear() {
		setBackground(Color.black, new Color(80, 30, 30));
		removeAll();
		setBackground(Color.black, new Color(80, 30, 30));
		updateUI();
		setBackground(Color.black, new Color(80, 30, 30));
		System.gc();
		setBackground(Color.black, new Color(80, 30, 30));
	}
	
	public void add(BufferedImage image, String path, boolean b) {
		try {
			if(getComponentCount() < 10) {
				add(image, path);
				updateUI();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setBackground(Color.black, new Color(80, 30, 30));
	}

}
