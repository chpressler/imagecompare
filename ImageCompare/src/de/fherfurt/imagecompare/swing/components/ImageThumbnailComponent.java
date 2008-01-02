package de.fherfurt.imagecompare.swing.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class ImageThumbnailComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	private String path;
	
	public ImageThumbnailComponent(BufferedImage image, String path) {
		this.image = image;
		this.path = path;
		if(image.getWidth() > image.getHeight()) {
			setPreferredSize(new Dimension(80, 80 * image.getHeight() / image.getWidth()));
		}
		else if(image.getWidth() < image.getHeight()) {
			setPreferredSize(new Dimension(80 * image.getWidth() / image.getHeight(), 80));
		}
		else {
			setPreferredSize(new Dimension(80, 80));
		}
	}
	
	public BufferedImage getImage() {
		return image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image != null)
			g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}

	public String getPath() {
		return path;
	} 
	
}
