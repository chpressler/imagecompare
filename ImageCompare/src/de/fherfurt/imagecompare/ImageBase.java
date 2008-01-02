package de.fherfurt.imagecompare;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import de.fherfurt.imagecompare.util.ICUtil;

public class ImageBase {
	
	private static volatile ImageBase instance;
	
	private static volatile ArrayList<ImageBaseChangedListener> listeners = new ArrayList<ImageBaseChangedListener>();
	
	private BufferedImage image; 
	
	private ImageBase() {
	}
	
	public static synchronized ImageBase getInstance() {
		if(instance == null) {
			synchronized (ImageBase.class) {
				if(instance == null) {
					instance = new ImageBase();
				}
			}
		}
		return instance;
	}
	
	public void setImageBase(File dir) throws IOException {
		for(ImageBaseChangedListener ibcl : listeners) {
			ibcl.clear();
			System.gc();
		}
		for(File file : dir.listFiles()) {
			if(file.isFile()) {
				if (file.getName().endsWith(".jpg")
						|| file.getName().endsWith(".gif")
						|| file.getName().endsWith(".bmp")
						|| file.getName().endsWith(".png")
						|| file.getName().endsWith(".JPG")) {
					try {
						ImageIO.setCacheDirectory(new File("C:/cache"));
						ImageIO.setUseCache(true);
						// image = JimiUtils.getThumbnal(ImageIO.read(file));
						// //JIMI API
						image = ICUtil.getInstance().getThumbnal(
								ImageIO.read(file));
						for (ImageBaseChangedListener ibcl : listeners) {
							ibcl.add(image, file.getAbsolutePath(), true);
							// System.out.println("added " + file);
						}
					} catch (Exception e) {
					}
				}
			}
		}
	}
	
	public void addImageBaseChangedListener(ImageBaseChangedListener ibcl) {
		listeners.add(ibcl);
	}
	
	public void removeImageBaseChangedListener(ImageBaseChangedListener ibcl) {
		listeners.remove(ibcl);
	}
	
}
