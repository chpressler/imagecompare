package de.fherfurt.imagecompare.swing.components;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LightTableImage extends JLabel {
	
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	public LightTableImage(String file) {
		super(new ImageIcon(file));
		try {
			image = ImageIO.read(new File(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
