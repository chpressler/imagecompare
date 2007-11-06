package de.fherfurt.imagecompare.swing.components;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class LightTableImage extends JLabel {
	
	private static final long serialVersionUID = 1L;
	
	private static BufferedImage image;
	
	public LightTableImage(String file) {
		super(new ImageIcon(file));
		setPreferredSize(new Dimension(10, 10));
		setMaximumSize(new Dimension(10, 10));
		try {
			image = ImageIO.read(new File(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public LightTableImage(BufferedImage i) {
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
	
	public void setNewSize(final int height, final int width) {
		setSize(height, width);
		new Thread(new Runnable() {
			public void run() {
				getGraphics().drawImage(image, 0, 0, width, height, null);
			}}).start();
		repaint();
	}
	
}
