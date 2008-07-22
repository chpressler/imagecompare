package de.fherfurt.imagecompare;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

import de.fherfurt.imagecompare.swing.components.ImageThumbnailComponent;

public class ImportDBDerbyHandler implements IImport {
	
	private static volatile ImportDBDerbyHandler instance;
	
	private static String driver = "org.apache.derby.jdbc.EmbeddedDriver"; 
	
	private static String protocol = "jdbc:derby:";
	
	private static String dbName = "IC";
	
	private Properties props;
	
	public static synchronized ImportDBDerbyHandler getInstance() {
		if(instance == null) {
			synchronized (ImportDBDerbyHandler.class) {
				if(instance == null) {
					instance = new ImportDBDerbyHandler();
				}
			}
		}
		return instance;
	}

	private ImportDBDerbyHandler() {
		try {
			Class.forName(driver).newInstance();
			Connection conn;
			props = new Properties();
			props.load(new FileInputStream("resources/preferences"));
			conn = DriverManager.getConnection(protocol + dbName
					+ ";create=true", props);

			System.out.println("Connected to and created database " + dbName);

			Statement s = conn.createStatement();

			s.execute("create table image(num int, addr varchar(40))");
			System.out.println("Created table image");

			// DriverManager.getConnection("jdbc:derby:MyDbTest;shutdown=true");
			// DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (Exception e) {

		}
	}
	
	@Override
	public void addImport(String absolutePath, HashMap<String, String> metadata) {
		System.out.println("added Import: " + absolutePath);
	}

	@Override
	public void addImport(ImageThumbnailComponent imtc) {
		System.out.println("added Import: " + imtc.getName());
	}

	@Override
	public HashMap<String, String> getAttributes(String path) {
		return new HashMap<String, String>();
	}

	@Override
	public boolean isImported(String absolutePath) {
		return false;
	}

}
