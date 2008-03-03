package de.fherfurt.imagecompare.swing.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.dnd.DnDConstants;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ImageBaseChangedListener;
import de.fherfurt.imagecompare.swing.controller.PreviewThumbnailComponentExternalDropTarget;
import de.fherfurt.imagecompare.swing.controller.ThumbnailDragSource;
import de.fherfurt.imagecompare.swing.layout.PreviewThumbnailComponentLayout;

public class PreviewThumbnailComponent extends JPanel implements ImageBaseChangedListener, ThumbnailSizeListener {
	
	private static final long serialVersionUID = 1L;
	
	private PreviewThumbnailComponentLayout layout;
	
	private static volatile PreviewThumbnailComponent instance;
	
	public static synchronized PreviewThumbnailComponent getInstance() {
		if(instance == null) {
			synchronized (PreviewThumbnailComponent.class) {
				if(instance == null) {
					instance = new PreviewThumbnailComponent();
				}
			}
		}
		return instance;
	}
	
	private PreviewThumbnailComponent() {
		ImageBase.getInstance().addImageBaseChangedListener(this);
		StatusBar.getInstance().addThumbnailSizeListener(this);
		layout = new PreviewThumbnailComponentLayout();
		setLayout(layout);
		new PreviewThumbnailComponentExternalDropTarget(this);
	}

	@Override
	public void add(ImageThumbnailComponent image, boolean b) {
		new ThumbnailDragSource(image, DnDConstants.ACTION_COPY);
		add(image);
		validate();
		updateUI();
	}

	@Override
	public void clear() {
		removeAll();
		validate();
		updateUI();
	}

	@Override
	public void thumbnailSizChanged() {
		validate();
		updateUI();
	}
	
	@Override
	public void paint(Graphics g) {
		if(getParent() != null) {
			setPreferredSize(new Dimension(getParent().getWidth()-10, layout.getY() + StatusBar.getInstance().getSliderValue()));
		}
		super.paint(g);
	}

	@Override
	public void removedImage(String path) {
		for(Component c : getComponents()) {
			if(((ImageThumbnailComponent) c).getPath().equals(path)) {
				remove(((ImageThumbnailComponent) c));
			}
		}
		this.validate();
		this.updateUI();
	}

	@Override
	public void sorted() {
		updateUI();
	}

}
