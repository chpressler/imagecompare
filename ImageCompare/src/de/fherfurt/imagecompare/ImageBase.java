package de.fherfurt.imagecompare;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.yahoo.search.ImageSearchResult;
import com.yahoo.search.ImageSearchResults;

import de.fherfurt.imagecompare.util.ICUtil;

public class ImageBase {
	
	private static volatile ImageBase instance;
	
	private static volatile ArrayList<ImageBaseChangedListener> listeners = new ArrayList<ImageBaseChangedListener>();
	
	private BufferedImage image; 
	
	private volatile ArrayList<String> imagePaths = new ArrayList<String>();
	
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
	
	public void exportToDir(String dir) {
		File d = new File(dir);
		if(!d.exists()) {
			d.mkdirs();
		}
		ArrayList<String> ip = (ArrayList<String>) imagePaths.clone();
		int iii = 0;
		for(String s : ip) {
			iii++;
			if(s.startsWith("http")) {
				try {
					URL ur = new URL(s);
					String name = ur.getFile().split("/")[ur.getFile().split("/").length-1];
					File f;
					if(name != null || name != "") {
						f = new File(d + "/" + name);
					} else {
						f = new File(d + "/" + iii + "t.jpg");
					}
					f.createNewFile();
					FileOutputStream fos = new FileOutputStream(f);
					InputStream inputStream = ur.openConnection().getInputStream();
					int z = 0;
					while(z >= 0) {
						z = inputStream.read();
						fos.write(z);
					}
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				//Original
				File f = new File(s);
				String name = f.getName();
				
				//Export
				File ef = new File(d + "/" + name);
				try {
					ef.createNewFile();
					
					FileInputStream fis = new FileInputStream(f);
					FileOutputStream fos = new FileOutputStream(ef);
					int ii = 0;
					while(ii != -1) {
						ii = fis.read();
						fos.write(ii);
					}
					fis.close();
					fos.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setImageBase(ImageSearchResults results) {
		imagePaths.clear();
		for (ImageBaseChangedListener ibcl : listeners) {
			ibcl.clear();
			System.gc();
		}
		for (ImageSearchResult r : results.listResults()) {
			try {
				image = ICUtil.getInstance().getThumbnal(
						ImageIO.read(new URL(r.getClickUrl())));
//				image = ImageIO.read(new URL(r.getClickUrl()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (ImageBaseChangedListener ibcl : listeners) {
				ibcl.add(image, r.getClickUrl(), true);
				imagePaths.add(r.getClickUrl());
				// System.out.println("added " + file);
			}
		}
	}
	
	public void setImageBase(File dir) throws IOException {
		imagePaths.clear();
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
							imagePaths.add(file.getAbsolutePath());
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
