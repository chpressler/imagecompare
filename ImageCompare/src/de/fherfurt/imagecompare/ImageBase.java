package de.fherfurt.imagecompare;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import de.fherfurt.imagecompare.swing.components.ImageComponent;

public class ImageBase {
	
	private static volatile ImageBase instance;
	
	private static volatile ArrayList<ImageComponent> images = new ArrayList<ImageComponent>();
	
	private static volatile ArrayList<ImageBaseChangedListener> listeners = new ArrayList<ImageBaseChangedListener>();
	
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

	public ArrayList<ImageComponent> getImages() {
		return images;
	}
	
	public void setImageBase(File dir) throws IOException {
		images.clear();
		for(File file : dir.listFiles()) {
			if(file.isFile()) {
				if(file.getName().endsWith(".jpg") || file.getName().endsWith(".gif") || file.getName().endsWith(".bmp") || file.getName().endsWith(".png")) {
					images.add(new ImageComponent(ImageIO.read(file)));
				}
			}
		}
		for(ImageBaseChangedListener ibcl : listeners) {
			ibcl.imageBaseChanged();
		}
	}
	
	public void addImageBaseChangedListener(ImageBaseChangedListener ibcl) {
		listeners.add(ibcl);
	}
	
	public void removeImageBaseChangedListener(ImageBaseChangedListener ibcl) {
		listeners.remove(ibcl);
	}
	
}
