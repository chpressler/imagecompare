package de.fherfurt.imagecompare.util;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ICUtil {
	
	private static volatile ICUtil instance;
	
	HashMap<Integer, Integer> lum = new HashMap<Integer, Integer>();
	
	HashMap<Integer, Integer> red = new HashMap<Integer, Integer>();
	
	HashMap<Integer, Integer> blue = new HashMap<Integer, Integer>();
	
	HashMap<Integer, Integer> green = new HashMap<Integer, Integer>();
	
	private ICUtil() {
		System.out.println("start");
		for(int ii = 0; ii < 256; ii++) {
			lum.put(ii, 0);
			red.put(ii, 0);
			green.put(ii, 0);
			blue.put(ii, 0);
		}
		System.out.println("finish");
	}
	
	private void clearHistogramm() {
		System.out.println("start");
		for(int ii = 0; ii < 256; ii++) {
			lum.put(ii, 0);
			red.put(ii, 0);
			green.put(ii, 0);
			blue.put(ii, 0);
		}
		System.out.println("finish");
	}
	
	public static synchronized ICUtil getInstance() {
		if(instance == null) {
			synchronized (ICUtil.class) {
				if(instance == null) {
					instance = new ICUtil();
				}
			}
		}
		return instance;
	}
	
	public BufferedImage copy(BufferedImage source) {
		BufferedImage newImage = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
		newImage.setData(source.getData());
		return source;
	}
	
	public BufferedImage getThumbnal(BufferedImage original) {
		
//		int w = original.getWidth(), h = original.getHeight(); 
//		int[] argbArray = new int[ (w * h) / 4 ]; 
//		original.getRGB( 0 /* startX */, 0 /* startY */, 
//		              w,  h, argbArray, 
//		              0 /* offset */, w /* scansize */ );
		
		BufferedImage thumbnail = new BufferedImage(original.getWidth()/10, original.getHeight()/10, BufferedImage.TYPE_INT_ARGB);
		for(int i1 = 0; i1 < thumbnail.getWidth(); i1++) {
			for(int i2 = 0; i2 < thumbnail.getHeight(); i2++) {
				thumbnail.setRGB(i1, i2, original.getRGB(i1*10, i2*10));
			}
		}
		original.flush();
//		System.gc();
		return thumbnail;
	}
	
	public void getHistogramData(BufferedImage image) {
		int r,g,b,l;
		clearHistogramm();
		for(int i1 = 0; i1 < image.getWidth(); i1++) {
			for(int i2 = 0; i2 < image.getHeight(); i2++) {
				r = binaryToInteger(Integer.toBinaryString(image.getRGB(i1, i2)).substring(8).substring(0, 8));
				g = binaryToInteger(Integer.toBinaryString(image.getRGB(i1, i2)).substring(8).substring(8, 16));
				b = binaryToInteger(Integer.toBinaryString(image.getRGB(i1, i2)).substring(8).substring(16, 24));
				l = (r+b+g) / 3;
				
				lum.put(l, lum.get(l)+1);
				red.put(r, red.get(r)+1);
				green.put(g, green.get(g)+1);
				blue.put(b, blue.get(b)+1);
			}
		}
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

}
