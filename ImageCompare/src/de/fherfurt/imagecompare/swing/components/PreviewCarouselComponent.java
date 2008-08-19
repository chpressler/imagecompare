package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import com.blogofbug.swing.components.JCarosel;
import com.blogofbug.swing.components.ReflectedImageLabel;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ImageBaseChangedListener;
import de.fherfurt.imagecompare.ResourceHandler;
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
	
	@Override
	public void add(ImageThumbnailComponent image, boolean b) {
		try {
			if(getComponentCount() < 10) {
				add(image.getImage(), image.getPath());
				updateUI();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, ResourceHandler.getInstance().getStrings().getString("picasaconnecterror") + "\n" + e.getMessage());
			e.printStackTrace();
		}
		setBackground(Color.black, new Color(80, 30, 30));
	}

	@Override
	public void removedImage(String path) {
		for(Component c : getComponents()) {
			if(((ReflectedImageLabel) c).getPath().equals(path)) {
				remove(((ReflectedImageLabel) c));
			}
		}
		this.validate();
		this.updateUI();
		setBackground(Color.black, new Color(80, 30, 30));
	}

	@Override
	public void sorted() {
		// TODO sorted
	}

}
