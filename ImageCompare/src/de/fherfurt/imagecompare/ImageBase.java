package de.fherfurt.imagecompare;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;
import com.yahoo.search.ImageSearchResult;
import com.yahoo.search.ImageSearchResults;

import de.fherfurt.imagecompare.swing.components.StatusBar;
import de.fherfurt.imagecompare.util.ICUtil;

public class ImageBase {
	
	private static volatile ImageBase instance;
	
	//HashMap <String, BufferedImage>...
	
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
	
	public void clear() {
		//HashMap...
		imagePaths.clear();
		for (ImageBaseChangedListener ibcl : listeners) {
			ibcl.clear();
		}
		System.gc();
	}
	
	public void setImageBase(ImageSearchResults results) {	
		for (ImageSearchResult r : results.listResults()) {
			StatusBar.getInstance().activateProgressBar();
			for(String s : imagePaths) {
				if(s.equalsIgnoreCase(r.getClickUrl())) {
					StatusBar.getInstance().deactivateProgressBar();
					return;
				}
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
				ibcl.add(image, r.getClickUrl(), true);
				imagePaths.add(r.getClickUrl());
				StatusBar.getInstance().setStatusText("adding: " + r.getClickUrl());
				// System.out.println("added " + file);
			}
			StatusBar.getInstance().setStatusText("");
			StatusBar.getInstance().deactivateProgressBar();
		}
	}
	
	public void setImageBase(File dir) throws IOException {
		boolean b = true;
		StatusBar.getInstance().activateProgressBar();
		
		if(dir.isFile()) {
			for(String s : imagePaths) {
				if(s.equalsIgnoreCase(dir.getAbsolutePath())) {
					StatusBar.getInstance().deactivateProgressBar();
					return;
				}
			}
			image = ICUtil.getInstance().getThumbnal(
					ImageIO.read(dir));
			for (ImageBaseChangedListener ibcl : listeners) {
				ibcl.add(image, dir.getAbsolutePath(), true);
				imagePaths.add(dir.getAbsolutePath());
				StatusBar.getInstance().setStatusText("adding: " + dir.getAbsolutePath());
			}
			StatusBar.getInstance().setStatusText("");
			StatusBar.getInstance().deactivateProgressBar();
			return;
		}
		
		for(File file : dir.listFiles()) {
			if(file.isFile()) {
				for(String s : imagePaths) {
					if(s.equalsIgnoreCase(file.getAbsolutePath())) {
						StatusBar.getInstance().deactivateProgressBar();
						return;
					}
				}
				if (file.getName().endsWith(".jpg")
						|| file.getName().endsWith(".gif")
						|| file.getName().endsWith(".bmp")
						|| file.getName().endsWith(".png")
						|| file.getName().endsWith(".JPG")) {
					try {
						
						//Versuche Thumbnail aus Metadaten zu lesen (falls möglich)
						try {
						Metadata metadata = new Metadata();
						metadata = JpegMetadataReader.readMetadata(file);
						Iterator directories = metadata.getDirectoryIterator();
						while (directories.hasNext()) {
							
//							final Directory directory = metadata.getDirectory(ExifDirectory.class); 
//							byte[] b_arr = directory.getByteArray(ExifDirectory.TAG_THUMBNAIL_DATA);
//							int[] i_arr = new int[b_arr.length];
//							for(int i = 0; i < b_arr.length; i++) {
//								i_arr[i] = (int) b_arr[i];
//							}
//							image = new BufferedImage(160, 120, BufferedImage.TYPE_INT_ARGB);
//							image.setRGB(0, 0, 160, 120, i_arr, 0, 120);
//							b = false;
							
							
							final Directory directory = (Directory) directories.next();
							Iterator tags = directory.getTagIterator();
							while (tags.hasNext()) {
								final Tag tag = (Tag) tags.next();
								final int tt = tag.getTagType();
								if(tt == ExifDirectory.TAG_THUMBNAIL_DATA) {
									String[] s_arr = directory.getString(tt).split(" ");
									int[] i_arr = new int[s_arr.length];
									for(int i = 0; i < s_arr.length; i++) {
										i_arr[i] = Integer.parseInt(s_arr[i]);
									}
//									image = new BufferedImage(160, 120, BufferedImage.TYPE_INT_ARGB);
//									image.setRGB(0, 0, i_arr.length/2, i_arr.length/2, i_arr, 0, 120);
//									b = false;
								}
							}
						}
						} catch (Exception e) {
							System.out.println("kein Exif Thumbnail vorhanden");
							e.printStackTrace();
						}
						
						if(b) {
						image = ICUtil.getInstance().getThumbnal(
								ImageIO.read(file));
						}
						for (ImageBaseChangedListener ibcl : listeners) {
							ibcl.add(image, file.getAbsolutePath(), true);
							imagePaths.add(file.getAbsolutePath());
							StatusBar.getInstance().setStatusText("adding: " + file.getAbsolutePath());
							// System.out.println("added " + file);
						}
						b = true;
					} catch (Exception e) {
						StatusBar.getInstance().setStatusText("");
						StatusBar.getInstance().deactivateProgressBar();
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
