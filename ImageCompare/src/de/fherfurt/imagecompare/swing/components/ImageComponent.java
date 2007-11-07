package de.fherfurt.imagecompare.swing.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class ImageComponent extends JLabel {
	
	private static final long serialVersionUID = 1L;
	
	private static BufferedImage image;
	
	int width, height;
	
	public ImageComponent(String file) {
		super(new ImageIcon(file));
		setPreferredSize(new Dimension(10, 10));
		setMaximumSize(new Dimension(10, 10));
		try {
			image = ImageIO.read(new File(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ImageComponent() {
		
	}
	
	public ImageComponent(BufferedImage i) {
		setPreferredSize(new Dimension(10, 10));
		setMaximumSize(new Dimension(10, 10));
		ImageIcon ii = new ImageIcon();
		ii.setImage(image);
		image = i;
		setIcon(ii);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public void setNewSize(final int width, final int height) {
		setSize(new Dimension(width, height));
//		new Thread(new Runnable() {
//			public void run() {
//				getGraphics().drawImage(image, 0, 0, width, height, null);
//			}}).start();
		repaint();
		this.width = width;
		this.height = height;
	}
	
	public void paintComponent( Graphics g ) {
        Graphics2D g2d = (Graphics2D) g;
        Rectangle r = this.getParent().getBounds();
        
        g2d.drawImage(image,0,0,width,height,this);
        this.setPreferredSize(new Dimension(width,height));
        this.revalidate();
    }
	
}