package de.fherfurt.imagecompare;

import java.util.HashMap;

import de.fherfurt.imagecompare.swing.components.ImageThumbnailComponent;

public class ImportDBDerbyHandler implements IImport {

	@Override
	public void addImport(String absolutePath, HashMap<String, String> metadata) {
		
	}

	@Override
	public void addImport(ImageThumbnailComponent imtc) {
		
	}

	@Override
	public HashMap<String, String> getAttributes(String path) {
		return null;
	}

	@Override
	public boolean isImported(String absolutePath) {
		return false;
	}

}
