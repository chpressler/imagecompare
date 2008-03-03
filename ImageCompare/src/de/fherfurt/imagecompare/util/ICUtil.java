package de.fherfurt.imagecompare.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import de.offis.faint.controller.HotSpotController;
import de.offis.faint.controller.MainController;
import de.offis.faint.interfaces.IDetectionFilter;
import de.offis.faint.interfaces.IDetectionPlugin;
import de.offis.faint.interfaces.IModule;
import de.offis.faint.interfaces.IRecognitionFilter;
import de.offis.faint.interfaces.IRecognitionPlugin;
import de.offis.faint.model.ImageModel;
import de.offis.faint.model.Region;

public class ICUtil {
	
	private static volatile ICUtil instance;
	
	HashMap<Integer, Integer> lum = new HashMap<Integer, Integer>();
	
	HashMap<Integer, Integer> red = new HashMap<Integer, Integer>();
	
	HashMap<Integer, Integer> blue = new HashMap<Integer, Integer>();
	
	HashMap<Integer, Integer> green = new HashMap<Integer, Integer>();
	
	private ICUtil() {
		clearHistogramm();
	}
	
	public int getFaceCount(String path) {
		//TODO -> implement FaceCount
		ImageModel im = new ImageModel(new File(path));
		Region[] faces = MainController.getInstance().detectFaces(im, false);
		return faces.length;
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
//		int[] argbArray = new int[ (w * h) / 10 ]; 
//		original.getRGB( 0 /* startX */, 0 /* startY */, 
//		              w/10,  h/10, argbArray, 
//		              0 /* offset */, w/10 /* scansize */ );
		
		BufferedImage thumbnail = null;
		if(original == null) {
			return null;
		}
		 if ((original.getWidth() * original.getHeight()) < 10000) {
			 thumbnail = new BufferedImage(
						original.getWidth(), original.getHeight(),
						BufferedImage.TYPE_INT_ARGB);
				for (int i1 = 0; i1 < thumbnail.getWidth(); i1++) {
					for (int i2 = 0; i2 < thumbnail.getHeight(); i2++) {
						thumbnail.setRGB(i1, i2, original.getRGB(i1, i2));
					}
				}
		} else {
			//Anpassen an 10000px -> Unterscheiden nach Format!!!
//			thumbnail = new BufferedImage(w/10, h/10, BufferedImage.TYPE_INT_ARGB);
//			thumbnail.setRGB(0, 0, w/10, h/10, argbArray, 0, w/10);
			
			thumbnail = new BufferedImage(
			original.getWidth() / 10, original.getHeight() / 10,
			BufferedImage.TYPE_INT_ARGB);
			for (int i1 = 0; i1 < thumbnail.getWidth(); i1++) {
				for (int i2 = 0; i2 < thumbnail.getHeight(); i2++) {
					thumbnail.setRGB(i1, i2, original.getRGB(i1 * 10, i2 * 10));
				}
			}
		}
		
		original.flush();
		System.gc();
		return thumbnail;
	}
	
	private void getHistogramData(BufferedImage image) {
		int w = image.getWidth(), h = image.getHeight(); 
		int[] argbArray = new int[ w * h ]; 
		image.getRGB( 0 /* startX */, 0 /* startY */, 
		              w,  h, argbArray, 
		              0 /* offset */, w /* scansize */ );
		int r,g,b,l;
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
		
//Alte langsame Variante
//		int r,g,b,l;
//		clearHistogramm();
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
	
	public int getContrast(BufferedImage image, boolean hist) {
		if(hist) {
			clearHistogramm();
			getHistogramData(image);
		}
		int smallest = 0, biggest = 0;
		for(int i = 0; i < 256; i++) {
			if(lum.get(i) > 0) {
				smallest = i;
				break;
			}
		}
		for(int i = 255; i >= 0; i--) {
			if(lum.get(i) > 0) {
				biggest = i;
				break;
			}
		}
		return (biggest - smallest) * 100 / 255;
	}
	
	public int getDynamic(BufferedImage image, boolean hist) {
		if(hist) {
			clearHistogramm();
			getHistogramData(image);
		}
		Iterator it = lum.values().iterator();
		int i = 0;
		while(it.hasNext()) {
			if(it.next().equals(0)) {
				i++;
			}
		}
		return 100 * (255-i) / 255;
	}
	
//	public boolean isColored(BufferedImage image) {
//		int w = image.getWidth(), h = image.getHeight(); 
//		int[] argbArray = new int[ w * h ]; 
//		image.getRGB( 0 /* startX */, 0 /* startY */, 
//		              w,  h, argbArray, 
//		              0 /* offset */, w /* scansize */ );
//		int r, g, b;
//		int e = 0;
//		//alle Pixel analysieren auf Farbsättigung
//		for (int is = 0; is < argbArray.length; is+=4) {
//			r   = (argbArray[is] >> 16) & 0xff; 
//			g = (argbArray[is] >> 8)  & 0xff; 
//			b  = (argbArray[is])       & 0xff;
//				if( !(((r - b) > -10) && ((r - b) < 10)) && !(((r - g) > -10) && ((r - g) < 10)) && !(((b - g) > -10) && ((b - g) < 10))) {
////					if( ((r < 230) && (r > 20)) ) {
////						System.out.println("extrem: " + r + " - " + g + " - " + b);
////					}
//					e++;
//				}
//			//wenn mehr als 10% farbige pixel, dann bild wahrscheinlisch auch sw
//			if(e > ((argbArray.length/4)*10/100)) {
//				return true;
//			}
//		}
//		return false;
//	}
	
	public int getAverageLum(BufferedImage image, boolean hist) {
		if(hist) {
			clearHistogramm();
			getHistogramData(image);
		}
		Iterator<Integer> iter = lum.keySet().iterator();
		int al = 0;
		int c = 0;
		while(iter.hasNext()) {
			int key = iter.next();
			al += key * lum.get(key);
			c += lum.get(key);
		}
		return ((al / c) * 100) / 256;
	}
	
	public int getAverageSat(BufferedImage image) {
		int w = image.getWidth(), h = image.getHeight(); 
		int[] argbArray = new int[ w * h ]; 
		image.getRGB( 0 /* startX */, 0 /* startY */, 
		              w,  h, argbArray, 
		              0 /* offset */, w /* scansize */ );
		int r = 0, g = 0, b = 0, as = 0;
		int a[] = new int[3]; 
		for (int is = 0; is < argbArray.length; is+=4) {
			r   = (argbArray[is] >> 16) & 0xff; 
			g = (argbArray[is] >> 8)  & 0xff; 
			b  = (argbArray[is])       & 0xff;
			a[0] = r;
			a[1] = g;
			a[2] = b;
			Arrays.sort(a);
			as += a[2] - a[0];
		}
		return (as / argbArray.length) * 100 / 255;
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
