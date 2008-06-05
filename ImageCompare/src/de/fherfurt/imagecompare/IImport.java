package de.fherfurt.imagecompare;

import java.util.HashMap;

import de.fherfurt.imagecompare.swing.components.ImageThumbnailComponent;

public interface IImport {
	
	void addImport(String absolutePath, HashMap<String, String> metadata);
	
	void addImport(ImageThumbnailComponent imtc);
	
	HashMap<String, String> getAttributes(String path);
	
	boolean isImported(String absolutePath);

}
