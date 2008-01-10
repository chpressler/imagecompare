package de.fherfurt.imagecompare;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.yahoo.search.ImageSearchResult;
import com.yahoo.search.ImageSearchResults;

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
	
	public void setImageBase(ImageSearchResults results) {
		for (ImageBaseChangedListener ibcl : listeners) {
			ibcl.clear();
			System.gc();
		}
		for (ImageSearchResult r : results.listResults()) {
			try {
//				image = ICUtil.getInstance().getThumbnal(
//						ImageIO.read(new URL(r.getClickUrl())));
				image = ImageIO.read(new URL(r.getClickUrl()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (ImageBaseChangedListener ibcl : listeners) {
				ibcl.add(image, r.getClickUrl(), true);
				// System.out.println("added " + file);
			}
		}
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
//						ImageIO.setCacheDirectory(new File("C:/cache"));
//						ImageIO.setUseCache(true);
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
