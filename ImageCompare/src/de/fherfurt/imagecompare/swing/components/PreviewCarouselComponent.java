package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.dnd.DnDConstants;
import java.awt.image.BufferedImage;

import com.blogofbug.swing.components.JCarosel;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ImageBaseChangedListener;
import de.fherfurt.imagecompare.swing.controller.ImageComponentDragSource;

public class PreviewCarouselComponent extends JCarosel implements ImageBaseChangedListener {

	private static final long serialVersionUID = 1L;
	
	boolean b = true;
	
	public PreviewCarouselComponent() {
		super(128);
		setPreferredSize(new Dimension(400, 300));
		setMinimumSize(new Dimension(300, 100));
		setMaximumSize(new Dimension(400, 400));
		setBackground(Color.GRAY, Color.BLACK);
		ImageBase.getInstance().addImageBaseChangedListener(this);
	}
	
	public void clear() {
		removeAll();
		b = true;
	}
	
	public void add(BufferedImage image) {
		try {
			if(b) {
				add(image, "");
				updateUI();
			}
		} catch (Exception e) {
			b = false;
		}
	}

}
