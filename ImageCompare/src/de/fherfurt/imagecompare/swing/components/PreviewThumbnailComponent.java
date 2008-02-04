package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.dnd.DnDConstants;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import de.fherfurt.imagecompare.ImageBase;
import de.fherfurt.imagecompare.ImageBaseChangedListener;
import de.fherfurt.imagecompare.swing.controller.ImageComponentDragSource;
import de.fherfurt.imagecompare.swing.controller.PreviewThumbnailComponentExternalDropTarget;
import de.fherfurt.imagecompare.swing.controller.ThumbnailDragSource;
import de.fherfurt.imagecompare.swing.layout.PreviewThumbnailComponentLayout;

public class PreviewThumbnailComponent extends JPanel implements ImageBaseChangedListener {

	private static final long serialVersionUID = 1L;
	
	private int h = 100;
	
	public PreviewThumbnailComponent() {
		new PreviewThumbnailComponentExternalDropTarget(this);
		setBackground(Color.DARK_GRAY);
//		setLayout(new FlowLayout());
		setLayout(new PreviewThumbnailComponentLayout());
		ImageBase.getInstance().addImageBaseChangedListener(this);
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
		updateUI();
		System.gc();
	}
	
	public void add(BufferedImage image, String path, boolean b) {
//		addThumbnail(new ImageComponent(image));
		ImageThumbnailComponent itc = new ImageThumbnailComponent(image, path);
		new ThumbnailDragSource(itc, DnDConstants.ACTION_COPY);
		add(itc);
		updateUI();
	}
	
	@Override
	public void paint(Graphics g) {
		if(this != null) {
			if(getComponentCount() > (getParent().getWidth() / 100)) {
				System.out.println("sdfsfsdfsfsfds");
				h = (getComponentCount() / (getParent().getWidth() / 100)) * 100;
				System.out.println(h);
			}
			setPreferredSize(new Dimension(getParent().getWidth()-50, h));
		}
		super.paint(g);
	}

}
