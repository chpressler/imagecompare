package de.fherfurt.imagecompare;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;
import com.yahoo.search.ImageSearchResult;
import com.yahoo.search.ImageSearchResults;

import de.fherfurt.imagecompare.swing.components.ImageThumbnailComponent;
import de.fherfurt.imagecompare.swing.components.StatusBar;
import de.fherfurt.imagecompare.util.ICUtil;

public class ImageBase {
	
	private static volatile ImageBase instance;
	
	private volatile ArrayList<ImageThumbnailComponent> images = new ArrayList<ImageThumbnailComponent>();
	
	private volatile ArrayList<ImageBaseChangedListener> listeners = new ArrayList<ImageBaseChangedListener>();
	
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
	
	public void sort() {
		Collections.sort(images);
		for(ImageBaseChangedListener ibcl : listeners) {
			ibcl.sorted();
		}
	}
	
	public void exportToDir(String dir) {
		File d = new File(dir);
		if(!d.exists()) {
			d.mkdirs();
		}
		Object[] ids = images.toArray();
		int iii = 0;
		for(Object itc : ids) {
			iii++;
			if(((ImageThumbnailComponent) itc).getPath().toString().startsWith("http")) {
				try {
					URL ur = new URL(((ImageThumbnailComponent) itc).getPath().toString());
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
				File f = new File(((ImageThumbnailComponent) itc).getPath().toString());
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
	
	public void clear() {
		images.clear();
		for (ImageBaseChangedListener ibcl : listeners) {
			ibcl.clear();
		}
		System.gc();
	}
	
	public void remove(ImageThumbnailComponent itc) {
		images.remove(itc);
		for (ImageBaseChangedListener ibcl : listeners) {
			ibcl.removedImage(itc.getPath());
		}
	}
	
	public void setImageBase(ImageSearchResults results) {	
		boolean b = false;
		for (ImageSearchResult r : results.listResults()) {
			StatusBar.getInstance().activateProgressBar();
			Object[] ids = images.toArray();
			for(Object itc : ids) {
				if(((ImageThumbnailComponent) itc).getPath().toString().equalsIgnoreCase(r.getClickUrl())) {
					StatusBar.getInstance().deactivateProgressBar();
					b = true;
				} else {
					b = false;
				}
			}
			if(b) {
				continue;
			}
			try {
				image = ICUtil.getInstance().getThumbnal(
						ImageIO.read(new URL(r.getClickUrl())));
//				image = ImageIO.read(new URL(r.getClickUrl()));
			} catch (Exception e) {
				StatusBar.getInstance().setStatusText("");
				StatusBar.getInstance().deactivateProgressBar();
				e.printStackTrace();
			}
			for (ImageBaseChangedListener ibcl : listeners) {
				StatusBar.getInstance().setStatusText("adding: " + r.getClickUrl());
				ImageThumbnailComponent imtc = new ImageThumbnailComponent(image, r.getClickUrl());
				images.add(imtc);
				ibcl.add(imtc, true);
			}
			StatusBar.getInstance().setStatusText("");
			StatusBar.getInstance().deactivateProgressBar();
		}
	}
	
	public void setImageBase(File dir) throws IOException {
		boolean b = true;
		StatusBar.getInstance().activateProgressBar();
		if(dir.isFile()) {
			//Prüfen, ob unterstützte Bilddatei...
			Object[] ids = images.toArray();
			for(Object itc : ids) {
				if(((ImageThumbnailComponent) itc).getPath().toString().equalsIgnoreCase(dir.getAbsolutePath())) {
					StatusBar.getInstance().deactivateProgressBar();
					return;
				}
			}
			image = ICUtil.getInstance().getThumbnal(
					ImageIO.read(dir));
			for (ImageBaseChangedListener ibcl : listeners) {
				StatusBar.getInstance().setStatusText("adding: " + dir.getAbsolutePath());
				ImageThumbnailComponent imtc = new ImageThumbnailComponent(image, dir.getAbsolutePath());
				images.add(imtc);
				ibcl.add(imtc, true);
			}
			StatusBar.getInstance().setStatusText("");
			StatusBar.getInstance().deactivateProgressBar();
			return;
		}
		
		for(File file : dir.listFiles()) {
			boolean b1 = false;
			if(file.isFile()) {
				Object[] ids = images.toArray();
				for(Object itc : ids) {
					if(((ImageThumbnailComponent) itc).getPath().toString().equalsIgnoreCase(file.getAbsolutePath())) {
						StatusBar.getInstance().deactivateProgressBar();
						b1 = true;
					}
				}
				if(b1) {
					continue;
				}
				if (file.getName().endsWith(".jpg")
						|| file.getName().endsWith(".gif")
						|| file.getName().endsWith(".bmp")
						|| file.getName().endsWith(".png")
						|| file.getName().endsWith(".JPG")) {
					try {
						
						try {
							Metadata metadata = JpegMetadataReader.readMetadata(file);
							Iterator directories = metadata.getDirectoryIterator();
							while (directories.hasNext()) {
								Directory directory = (Directory) directories.next();
								if (directory instanceof ExifDirectory){
									ExifDirectory exifDir = (ExifDirectory) directory;
									if (exifDir.containsThumbnail()){
											InputStream in = new ByteArrayInputStream(exifDir.getThumbnailData());
											image = ImageIO.read(in);
											b = false;
									}
								}
							}
						} catch (Exception e) {
							b = true;
						}
						
						if(b) {
						image = ICUtil.getInstance().getThumbnal(
								ImageIO.read(file));
						}
						for (ImageBaseChangedListener ibcl : listeners) {
							StatusBar.getInstance().setStatusText("adding: " + file.getAbsolutePath());
							ImageThumbnailComponent imtc = new ImageThumbnailComponent(image, file.getAbsolutePath());
							images.add(imtc);
							ibcl.add(imtc, true);
						}
						b = true;
					} catch (Exception e) {
						StatusBar.getInstance().setStatusText("");
						StatusBar.getInstance().deactivateProgressBar();
						b = true;
					}
				}
			}
		}
		StatusBar.getInstance().setStatusText("");
		StatusBar.getInstance().deactivateProgressBar();
	}
	
	public void addImageBaseChangedListener(ImageBaseChangedListener ibcl) {
		listeners.add(ibcl);
	}
	
	public void removeImageBaseChangedListener(ImageBaseChangedListener ibcl) {
		listeners.remove(ibcl);
	}
	
}
