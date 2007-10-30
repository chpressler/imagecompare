package de.fherfurt.imagecompare;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class PreferencesHandler {
	
	private static volatile Properties instance = null;
	
	private PreferencesHandler() {}
	
	public static synchronized Properties getInstance() {
		if(instance == null) {
			synchronized (Properties.class) {
				if(instance == null) {
					instance = new Properties();
					try {
						instance.load(new FileInputStream("resources/preferences"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return instance;
	}
	
	public void save() {
		try {
			instance.store(new FileOutputStream("resources/preferences"), "SiriusClient Preferences");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
