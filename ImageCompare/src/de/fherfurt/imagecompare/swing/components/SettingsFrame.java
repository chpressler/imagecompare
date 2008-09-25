package de.fherfurt.imagecompare.swing.components;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import de.fherfurt.imagecompare.swing.actions.SavePropertiesAction;

public class SettingsFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static volatile SettingsFrame instance;
	
	private JTabbedPane tabbedPane;
	
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
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setSize(new Dimension(400, 300));
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("global", new SettingsPanel(SettingsPanelType.GLOBAL));
		tabbedPane.addTab("database", new SettingsPanel(SettingsPanelType.DB));
		tabbedPane.addTab("picasa", new SettingsPanel(SettingsPanelType.PICASA));
		tabbedPane.addTab("flickr", new SettingsPanel(SettingsPanelType.FLICKR));
		add(tabbedPane);
	}
	
	public enum 
	SettingsPanelType {	
		GLOBAL, 
		DB, 
		PICASA, 
		FLICKR 
	}
	
	class SettingsPanel extends JPanel {
		
		private static final long serialVersionUID = 1L;

		
		
		public SettingsPanel(SettingsPanelType type) {

			switch (type) {
			case GLOBAL:
				break;
			case DB:
				break;
			case PICASA:
				break;
			case FLICKR:
				final JTextField uname = new JTextField();
				final JPasswordField pwd = new JPasswordField();
				uname.setText(props.getProperty("picasauser"));
				pwd.setText(props.getProperty("picasapw"));
				
				uname.addKeyListener(new KeyListener() {
					@Override
					public void keyPressed(KeyEvent e) {
						
					}
					@Override
					public void keyReleased(KeyEvent e) {
						props.setProperty("picasauser", uname.getText());
					}
					@Override
					public void keyTyped(KeyEvent e) {
						
					}});
				pwd.addKeyListener(new KeyListener() {
					@Override
					public void keyPressed(KeyEvent e) {
						
					}
					@Override
					public void keyReleased(KeyEvent e) {
						props.setProperty("picasapw", pwd.getText());
					}
					@Override
					public void keyTyped(KeyEvent e) {
						
					}});
				
				JButton save = new JButton("save");
				save.addActionListener(new SavePropertiesAction(props));
				
				GridLayout layout = new GridLayout(0, 1);
				setLayout(layout);
				
				add(uname);
				add(pwd);
				add(save);
				break;
			default:
				break;
			}

		}
		
	}

}

