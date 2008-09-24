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
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectory;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.PhotoEntry;
import com.yahoo.search.ImageSearchResult;
import com.yahoo.search.ImageSearchResults;

import de.fherfurt.imagecompare.swing.components.ImageThumbnailComponent;
import de.fherfurt.imagecompare.swing.components.SettingsFrame;
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
	
	public ArrayList<ImageThumbnailComponent> getimageList() {
		return images;
	}
	
	public void sort() {
//		System.out.println("sort by: " + ControlPanel.getInstance().getSortComponent().getSortBy());
		Collections.sort(images);
		for(ImageBaseChangedListener ibcl : listeners) {
			ibcl.sorted();
		}
	}
	
	public synchronized void exportToDir(String dir) {
		File d = new File(dir);
		if(!d.exists()) {
			d.mkdirs();
		}
		
		int iii = 0;
		for(ImageThumbnailComponent itc : images) {
			iii++;
			if(iii > StatusBar.getInstance().getImageBaseSize()) {
				return;
			}
			if(itc.getPath().startsWith("http")) {
				try {
					URL ur = new URL(itc.getPath());
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
					continue;
				}
			} else {
				//Original
				File f = new File(itc.getPath());
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
					continue;
				}
			}
		}
	}
	
	public synchronized void exportToPicasa(String album, boolean exists) {
		try {
			PicasawebService myService =
				  new PicasawebService("exampleCo-exampleApp-1");

				myService.setUserCredentials(SettingsFrame.getInstance().getProperties().getProperty("picasauser"), SettingsFrame.getInstance().getProperties().getProperty("picasapw"));

				if(!exists) {
				URL postUrl =
				  new URL("http://picasaweb.google.com/data/feed/api/user/" + SettingsFrame.getInstance().getProperties().getProperty("picasauser"));
				AlbumEntry myEntry = new AlbumEntry();

				myEntry.setTitle(new PlainTextConstruct(album));
				//TODO 
//				myEntry.setDescription(new
//				  PlainTextConstruct("My trip to Mongolia was most enjoyable, but cold."));
//
//				Person author = new Person("Elizabeth Bennet", null, "liz@gmail.com");
//				myEntry.getAuthors().add(author);

				// Send the request and receive the response:
				AlbumEntry insertedEntry = myService.insert(postUrl, myEntry);
				}
				
				URL feedUrl = new URL("http://picasaweb.google.com/data/feed/api/user/" + SettingsFrame.getInstance().getProperties().getProperty("picasauser") + "/album/" + album);

				for(ImageThumbnailComponent itc : images) {
					System.out.println(itc.getPath());
					PhotoEntry myPhoto = new PhotoEntry();
					myPhoto.setTitle(new PlainTextConstruct(itc.getPath()));
//					myPhoto.setDescription(new PlainTextConstruct("Darcy on the beach"));
//					myPhoto.setClient("myClientName");
					myPhoto.setTimestamp (new Date());
					
					MediaFileSource myMedia = new MediaFileSource(new File(itc.getPath()), "image/jpeg");
					myPhoto.setMediaSource(myMedia);

					PhotoEntry returnedPhoto = myService.insert(feedUrl, myPhoto);
				}
				
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public void setImageBase(ArrayList<String> urls) {
		boolean b = false;
		for(String s : urls) {
			StatusBar.getInstance().activateProgressBar();
			for(ImageThumbnailComponent itc : images) {
				if(itc.getPath().equalsIgnoreCase(s)) {
//					StatusBar.getInstance().deactivateProgressBar();
					b = true;
				}
			}
			if(b) {
				continue;
			}
			try {
				image = ICUtil.getInstance().getThumbnal(
						ImageIO.read(new URL(s)));
//				image = ImageIO.read(new URL(r.getClickUrl()));
			} catch (Exception e) {
				StatusBar.getInstance().setStatusText("");
				StatusBar.getInstance().deactivateProgressBar();
				e.printStackTrace();
			}
			ImageThumbnailComponent imtc = new ImageThumbnailComponent(image, s);
			if(imtc.getAttributes().isEmpty()) {
				continue;
			}
			images.add(imtc);
			sort();
			for (ImageBaseChangedListener ibcl : listeners) {
				StatusBar.getInstance().setStatusText(" adding: " + s);
				ibcl.add(imtc, true);
			}
			StatusBar.getInstance().setStatusText("");
			StatusBar.getInstance().deactivateProgressBar();
		}
	}
	
	public void setImageBase(ImageSearchResults results) {	
		boolean b = false;
		for (ImageSearchResult r : results.listResults()) {
			StatusBar.getInstance().activateProgressBar();
			for(ImageThumbnailComponent itc : images) {
				if(itc.getPath().equalsIgnoreCase(r.getClickUrl())) {
//					StatusBar.getInstance().deactivateProgressBar();
					b = true;
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
			ImageThumbnailComponent imtc = new ImageThumbnailComponent(image, r.getClickUrl());
			if(imtc.getAttributes().isEmpty()) {
				continue;
			}
			images.add(imtc);
			sort();
			for (ImageBaseChangedListener ibcl : listeners) {
				StatusBar.getInstance().setStatusText(" adding: " + r.getClickUrl());
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
			for(ImageThumbnailComponent itc : images) {
				String p = dir.getAbsolutePath().replaceAll("\\\\", "/");
				if(itc.getPath().equalsIgnoreCase(p)) {
					StatusBar.getInstance().deactivateProgressBar();
					return;
				}
			}
			
			if(dir.getName().endsWith(".bmp") || dir.getName().endsWith(".BMP")) {
				return;
			}
			image = ICUtil.getInstance().getThumbnal(
					ImageIO.read(dir));
			ImageThumbnailComponent imtc = new ImageThumbnailComponent(image, dir.getAbsolutePath());
			if(imtc.getAttributes().isEmpty()) {
				return;
			}
			images.add(imtc);
			
			sort();
			for (ImageBaseChangedListener ibcl : listeners) {
				StatusBar.getInstance().setStatusText(" adding: " + dir.getAbsolutePath());
				ibcl.add(imtc, true);
			}
		
			StatusBar.getInstance().setStatusText("");
			StatusBar.getInstance().deactivateProgressBar();
			return;
		}
		
		try {
		for(File file : dir.listFiles()) {
			boolean b1 = false;
			if(file.isFile()) {
				for(ImageThumbnailComponent itc : images) {
					String p = file.getAbsolutePath().replaceAll("\\\\", "/");
					if(itc.getPath().equalsIgnoreCase(p)) {
//						StatusBar.getInstance().deactivateProgressBar();
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
						ImageThumbnailComponent imtc = new ImageThumbnailComponent(image, file.getAbsolutePath());
						if(imtc.getAttributes().isEmpty()) {
							return;
						}
						images.add(imtc);
						sort();
						for (ImageBaseChangedListener ibcl : listeners) {
							StatusBar.getInstance().setStatusText(" adding: " + file.getAbsolutePath());
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
		} catch (Exception e) {
			StatusBar.getInstance().setStatusText("");
			StatusBar.getInstance().deactivateProgressBar();
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
