package de.fherfurt.imagecompare;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceHandler {
	
	private static volatile ResourceHandler instance = null;
	
	private ResourceBundle strings = null;
	
	private ResourceBundle icons = null;
	
	private ResourceHandler() {
		strings = ResourceBundle.getBundle("strings.Strings");
		icons = ResourceBundle.getBundle("icons.Icons");
	}
	
	public static synchronized ResourceHandler getInstance() {
		if(instance == null) {
			synchronized(ResourceHandler.class) {
				if(instance == null) {
					instance = new ResourceHandler();
				}
			}
		}
		return instance;
	}

	public ResourceBundle getStrings() {
		return strings;
	}

	public ResourceBundle getIcons() {
		return icons;
	}

}
