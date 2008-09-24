package de.fherfurt.imagecompare.swing.components;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SettingsFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static volatile SettingsFrame instance;
	
	private Properties props;
	
	public Properties getProperties() {
		return props;
	}
	
	public static synchronized SettingsFrame getInstance() {
		if(instance == null) {
			synchronized (SettingsFrame.class) {
				if(instance == null) {
					instance = new SettingsFrame();
				}
			}
		}
		return instance;
	}
	
	private SettingsFrame() {
		props = new Properties();
		try {
			props.load(new FileReader(new File("resources/preferences")));
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

}
