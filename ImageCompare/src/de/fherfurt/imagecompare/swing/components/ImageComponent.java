package de.fherfurt.imagecompare.swing.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.sun.jimi.core.JimiUtils;
import com.sun.jimi.core.util.JimiUtil;

public class ImageComponent extends JLabel {
	
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	int width, height;
	
	public ImageComponent(String file) {
		ImageIcon i = new ImageIcon(file);
//		setPreferredSize(new Dimension(i.getIconHeight(), i.getIconWidth()));
		height = i.getIconHeight();
		width = i.getIconWidth();
//		setPreferredSize(new Dimension(10, 10));
//		setMaximumSize(new Dimension(10, 10));
		try {
			image = (BufferedImage) i.getImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setIcon(i);
		setSize(new Dimension(width / 10, height / 10));
	}
	
	public ImageComponent() {
		
	}
	
	public ImageComponent(BufferedImage i) {
//		setPreferredSize(new Dimension(i.getHeight(), i.getWidth()));
		image = i;
		height = image.getHeight();
		width = image.getWidth();
//		setMaximumSize(new Dimension(60, 60));
		ImageIcon ii = new ImageIcon();
		ii.setImage(image);
		setIcon(ii);
		setSize(new Dimension(width, height));
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public void setThumbnail(int size) {
		if(width > height) {
			this.height = size * height / width;
			this.width = size;
		}
		else if(width < height) {
			this.width = size * width / height;
			this.height = size;
		}
		else {
			this.height = size;
			this.width = size;
		}
		setSize(new Dimension(width, height));
		repaint();
	}
	
	public void setNewSize(final int width, final int height) {
//		int oldwidth = this.width;
//		int oldheight = this.height;
		this.width = width;
		this.height = height;
		setSize(new Dimension(width, height));
//		setLocation(getLocation().x + (oldwidth-width/2), getLocation().y + (oldheight-height/2));
		repaint();
	}
	
	public void paintComponent( Graphics g ) {
//		setBounds((getParent().getWidth() - width)/2, (getParent().getHeight() - height)/2, width, height);
		
		Graphics2D g2d = (Graphics2D) g;
//      Rectangle r = this.getParent().getBounds();
        
        g2d.drawImage(image,0,0,width,height,this);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(0, 0, width-1, height-1);
        this.setPreferredSize(new Dimension(width,height));
        this.revalidate();
    }
	
	public void setTransparency(double val) {
//		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment(). 
//	    getDefaultScreenDevice().getDefaultConfiguration(); 
//		image = gc.createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT );
	}

}
