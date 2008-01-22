package de.fherfurt.imagecompare.swing.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageComponent extends JLabel {
	
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	private HashMap<Integer, Integer> lum = new HashMap<Integer, Integer>();
	
	private HashMap<Integer, Integer> red = new HashMap<Integer, Integer>();
	
	private HashMap<Integer, Integer> blue = new HashMap<Integer, Integer>();
	
	private HashMap<Integer, Integer> green = new HashMap<Integer, Integer>();
	
	private boolean t = false;
	
	int width, height;
	
	private String path;
	
	private int pc = 0;
	
	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public ImageComponent(String file) {
//		setOpaque(false);
		setToolTipText(file);
		this.path = file;
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
		new Thread(new Runnable() {
			public void run() {
				initHistogramm();
				calcHistogramm(image);
			}}).start();
	}
	
	public ImageComponent() {
		
	}
	
	public int getPixelCount() {
		return pc;
	}
	
	private void initHistogramm() {
		for(int ii = 0; ii < 256; ii++) {
			lum.put(ii, 0);
			red.put(ii, 0);
			green.put(ii, 0);
			blue.put(ii, 0);
		}
	}
	
	public void calcHistogramm(BufferedImage image) {
		int w = image.getWidth(), h = image.getHeight(); 
		int[] argbArray = new int[ w * h ]; 
		image.getRGB( 0 /* startX */, 0 /* startY */, 
		              w,  h, argbArray, 
		              0 /* offset */, w /* scansize */ );
		
		pc = argbArray.length;
		
		int r,g,b,l;
		
		System.out.println("start");
		for(Integer i : argbArray) {
			r   = (i >> 16) & 0xff; 
			g = (i >> 8)  & 0xff; 
			b  = (i)       & 0xff;
			l = (r+b+g) / 3;
			lum.put(l, lum.get(l)+1);
			red.put(r, red.get(r)+1);
			green.put(g, green.get(g)+1);
			blue.put(b, blue.get(b)+1);
		}
		System.out.println("finish");
	
//		int argb  = image.getRGB( x, y ); 
//		int alpha = (argb >> 24) & 0xff; 
//		int red   = (argb >> 16) & 0xff; 
//		int green = (argb >> 8)  & 0xff; 
//		int blue  = (argb)       & 0xff;
		
		
//Alte langsamere Variante 
//		int r,g,b,l;
//		initHistogramm();
//		for(int i1 = 0; i1 < image.getWidth(); i1++) {
//			for(int i2 = 0; i2 < image.getHeight(); i2++) {
//				r = binaryToInteger(Integer.toBinaryString(image.getRGB(i1, i2)).substring(8).substring(0, 8));
//				g = binaryToInteger(Integer.toBinaryString(image.getRGB(i1, i2)).substring(8).substring(8, 16));
//				b = binaryToInteger(Integer.toBinaryString(image.getRGB(i1, i2)).substring(8).substring(16, 24));
//				l = (r+b+g) / 3;
//				
//				lum.put(l, lum.get(l)+1);
//				red.put(r, red.get(r)+1);
//				green.put(g, green.get(g)+1);
//				blue.put(b, blue.get(b)+1);
//			}
//		}
	}
	
	private int binaryToInteger(String bin) {
		int i = 0;
		int s = bin.length()-1;
		for(Character c : bin.toCharArray()) {
			i += Integer.parseInt(c.toString()) * (Math.pow(2d, s));
			s--;
		}
		return i;
	}

	public HashMap<Integer, Integer> getLum() {
		return lum;
	}

	public HashMap<Integer, Integer> getRed() {
		return red;
	}

	public HashMap<Integer, Integer> getBlue() {
		return blue;
	}

	public HashMap<Integer, Integer> getGreen() {
		return green;
	}
	
	public ImageComponent(BufferedImage i, String path) {
		this.path = path;
//		setPreferredSize(new Dimension(i.getHeight(), i.getWidth()));
		image = i;
		height = image.getHeight();
		width = image.getWidth();
//		setMaximumSize(new Dimension(60, 60));
		ImageIcon ii = new ImageIcon();
		ii.setImage(image);
		setIcon(ii);
		setSize(new Dimension(width, height));
		new Thread(new Runnable() {
			public void run() {
				initHistogramm();
				calcHistogramm(image);
			}}).start();
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
	
	@Override
	public void paint( Graphics g ) {
//		setBounds((getParent().getWidth() - width)/2, (getParent().getHeight() - height)/2, width, height);
		
		Graphics2D g2d = (Graphics2D) g;
		
//      Rectangle r = this.getParent().getBounds();
		if(t) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		}
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
	
	public void rotate() {
		
	}

	public void setT(boolean t) {
		this.t = t;
	}

}
