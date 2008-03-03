package de.fherfurt.imagecompare;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import de.fherfurt.imagecompare.util.ICUtil;

public class ImageAnalyser {
	
	private static volatile ImageAnalyser instance;
	
	public static synchronized ImageAnalyser getInstance() {
		if(instance == null) {
			synchronized (ImageAnalyser.class) {
				if(instance == null) {
					instance = new ImageAnalyser();
				}
			}
		}
		return instance;
	}
	
	public HashMap<String, String> getImageAttributes(File f, String url) {
		HashMap<String, String> metadatamap = new HashMap<String, String>();
		Metadata metadata = new Metadata();
		try {
			metadata = JpegMetadataReader.readMetadata(f);
		} catch (JpegProcessingException e) {
			e.printStackTrace();
		}
		
		Iterator directories = metadata.getDirectoryIterator();
		String model = "";
		String make = "";
		int x = 0, y = 0;
		
		if(url.isEmpty()) {
			metadatamap.put("path", f.getAbsolutePath().replaceAll("\\\\", "/"));
		} else {
			metadatamap.put("path", url);
		}
		while (directories.hasNext()) {
			final Directory directory = (Directory) directories.next();
			Iterator tags = directory.getTagIterator();
			while (tags.hasNext()) {
				final Tag tag = (Tag) tags.next();
				final int tt = tag.getTagType();
				if(tag.getTagName().equalsIgnoreCase("Model")) {
					model = directory.getString(tt);
				}
				if(tag.getTagName().equalsIgnoreCase("Make")) {
					make = directory.getString(tt);
				}
				if(tag.getTagName().startsWith("ISO")) {
					metadatamap.put("iso", directory.getString(tt));
				}
				if(tag.getTagName().equalsIgnoreCase("F-Number")) {
					metadatamap.put("fnumber", directory.getString(tt));
				}
				if(tag.getTagName().equalsIgnoreCase("Focal Length")) {
					metadatamap.put("focalLength", directory.getString(tt));
				}
				if(tag.getTagName().equalsIgnoreCase("Flash")) {
					metadatamap.put("flash", directory.getString(tt));
				}
				if(tag.getTagName().equalsIgnoreCase("Exposure Time")) {
					metadatamap.put("exposureTime", directory.getString(tt));
				}
				if(tag.getTagName().equalsIgnoreCase("Image Width")) {
					x = Integer.parseInt(directory.getString(tt));
				}
				if(tag.getTagName().equalsIgnoreCase("Image Height")) {
					y = Integer.parseInt(directory.getString(tt));
				}
				if(tag.getTagName().equalsIgnoreCase("Keywords")) {
					metadatamap.put("keywords", directory.getString(tt));
				}
			}
		}
		if(x > 0 && y > 0) {
			metadatamap.put("pixelCount", Integer.toString(x*y));
		}
		metadatamap.put("camera", make + " " + model);
		
		BufferedImage bi;
		try {
			bi = ICUtil.getInstance().getThumbnal(ImageIO.read(f));
		} catch (Exception e1) {
			System.out.println("cant read file: -> " + f.getAbsolutePath());
			return metadatamap;
		}
		try {
			metadatamap.put("faceCount", Integer.toString(ICUtil.getInstance()
					.getFaceCount(f.getAbsolutePath())));
		} catch (Exception e2) {
			System.out.println("faceCountError -> " + e2.getMessage());
		}
		try {
			metadatamap.put("lastModofied", Long.toString(f.lastModified()));
		} catch (Exception e3) {
		}
		try {
			metadatamap.put("filesize", Long.toString(f.length()));
		} catch (Exception e4) {
		}
		try {
			metadatamap.put("imageWidth", Integer.toString(bi.getWidth()));
		} catch (Exception e5) {
		}
		try {
			metadatamap.put("imageHeight", Integer.toString(bi.getHeight()));
		} catch (Exception e6) {
		}
		try {
			metadatamap.put("dynamic", Integer.toString(ICUtil.getInstance()
					.getDynamic(bi, true)));
		} catch (Exception e9) {
		}
		try {
			metadatamap.put("contrast", Integer.toString(ICUtil.getInstance()
					.getContrast(bi, false)));
		} catch (Exception e10) {
		}
		try {
			metadatamap.put("avgLum", Integer.toString(ICUtil.getInstance()
					.getAverageLum(bi, false)));
		} catch (Exception e11) {
		}
		try {
			metadatamap.put("avgSat", Integer.toString(ICUtil.getInstance()
					.getAverageSat(bi)));
		} catch (Exception e12) {
		}

		return metadatamap;
	}

}
