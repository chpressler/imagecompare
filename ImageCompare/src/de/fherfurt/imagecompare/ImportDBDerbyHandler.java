package de.fherfurt.imagecompare;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
	
	private Connection conn;
	
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
			props = new Properties();
			props.load(new FileInputStream("resources/preferences"));
			
			try {
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(protocol + dbName
					+ ";create=true", props);
				Statement s = conn.createStatement();
				
//				s.execute("drop table image");
//				s.execute("drop table attributes");
				
				s.execute("create table image(id bigint, path varchar(500))");
				s.execute("create table attributes(id bigint, name varchar(500), value varchar(500), image_id bigint)");
				conn.close();
			} catch (SQLException sqle) {
				System.out.println("DB schon vorhanden ? -> " + sqle.getMessage());
//				sqle.printStackTrace();
			}
			
			// DriverManager.getConnection("jdbc:derby:MyDbTest;shutdown=true");
			// DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void addImport(String absolutePath, HashMap<String, String> metadata) {
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(protocol + dbName
					+ ";create=true", props);
			
			
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addImport(ImageThumbnailComponent imtc) {
		System.out.println("added Derby Import: " + imtc.getName());
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
