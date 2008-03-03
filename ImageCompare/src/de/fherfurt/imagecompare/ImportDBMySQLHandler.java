package de.fherfurt.imagecompare;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import de.fherfurt.imagecompare.swing.components.ImageThumbnailComponent;

public class ImportDBMySQLHandler implements IImport {
	
	private static volatile ImportDBMySQLHandler instance;
	
	private Properties properties;
	
	private String dbhost;
	
	private String dbport;
	
	private String db;
	
	private String user;
	
	private String password;
	
	private ResultSet rs;
	
	long imageid = -1;
	
	public static synchronized ImportDBMySQLHandler getInstance() {
		if(instance == null) {
			synchronized (ImportDBMySQLHandler.class) {
				if(instance == null) {
					instance = new ImportDBMySQLHandler();
				}
			}
		}
		return instance;
	}

	private ImportDBMySQLHandler() {
		try {
			properties = new Properties();
			properties.load(new FileInputStream("resources/preferences"));
			dbhost = properties.getProperty("dbhost");
			dbport = properties.getProperty("dbport");
			db = properties.getProperty("db");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
		} catch(Exception e) {
			
		}
	}
	
	public synchronized HashMap<String, String> getAttributes(String path) {
		HashMap<String, String> attributes = new HashMap<String, String>();
		try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://" + dbhost + ":" + dbport + "/" + db + "?user=" + user + "&password=" + password);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from attributes where image_id = (select id from images where path = '" + path + "')");
		while(rs.next()) {
			attributes.put(rs.getString("name"), rs.getString("value"));
		}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return attributes;
	}
	
	public synchronized boolean isImported(String absolutePath) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://" + dbhost + ":" + dbport + "/" + db + "?user=" + user + "&password=" + password);
			Statement stmt = conn.createStatement();
			absolutePath = absolutePath.replace("\\", "/");
			ResultSet rs = stmt.executeQuery("SELECT * FROM images WHERE path = '" + absolutePath + "'");
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public synchronized void addImport(ImageThumbnailComponent imtc) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://" + dbhost + ":" + dbport + "/" + db + "?user=" + user + "&password=" + password);
			Statement stmt = conn.createStatement();

			//Table images
			try {
				stmt.execute("INSERT INTO `images` (`path`) VALUES (\"" + imtc.getPath() + "\");");
			} catch(java.sql.SQLException sqle) {
				System.out.println("already imported - UNIQE MySQL Exception - should not happen here");
				return;
			}
			
			//gerade angelegte id für das image
			try {
				ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID() FROM images");
//				imageid = stmt.getGeneratedKeys().getInt(0);
				if (rs.next()) {
					imageid = rs.getLong(1);
			    } else {
			    	return;
			    }
				//imageid = stmt.getGeneratedKeys().getInt(1);
				rs.close();
			} catch(Exception e) {
				e.printStackTrace();
				return;
			}
			
			//table attributes
			Iterator<String> iter = imtc.getAttributes().keySet().iterator();
			while(iter.hasNext()) {
				String s = iter.next().toString();
				if(s.startsWith("Thumbnail") || s.startsWith("Component")) {
					continue;
				}
				try {
					stmt.execute("INSERT INTO `attributes` (`name`, `value`, `image_id`) VALUES (\"" + s + "\", \""
							+ imtc.getAttributes().get(s).trim()
							+ "\", \""
							+ imageid
							+ "\");");
				} catch (Exception e) {
					stmt.execute("INSERT INTO `attributes` (`name`, `value`, `image_id`) VALUES (\"" + s + "\", \""
							+ ""
							+ "\", \""
							+ imageid
							+ "\");");
				}
			}

			conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

	public synchronized void addImport(String absolutePath, HashMap<String, String> metadata) {
		try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://" + dbhost + ":" + dbport + "/" + db + "?user=" + user + "&password=" + password);
		Statement stmt = conn.createStatement();

		//TODO -> Radiobutton if new... 
//		stmt.execute("DELETE FROM images");
//		stmt.execute("DELETE FROM attributes");

		absolutePath = absolutePath.replaceAll("\\\\", "/");
		
		//Table images
		try {
			stmt.execute("INSERT INTO `images` (`path`) VALUES (\"" + absolutePath + "\");");
		} catch(java.sql.SQLException sqle) {
			System.out.println("already imported - UNIQE MySQL Exception - should not happen here");
			return;
		}
		
		//gerade angelegte id für das image
		try {
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID() FROM images");
//			imageid = stmt.getGeneratedKeys().getInt(0);
			if (rs.next()) {
				imageid = rs.getLong(1);
		    } else {
		    	return;
		    }
			//imageid = stmt.getGeneratedKeys().getInt(1);
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		//table attributes
		Iterator<String> iter = metadata.keySet().iterator();
		while(iter.hasNext()) {
			String s = iter.next().toString();
			if(s.startsWith("Thumbnail") || s.startsWith("Component")) {
				continue;
			}
			try {
				stmt.execute("INSERT INTO `attributes` (`name`, `value`, `image_id`) VALUES (\"" + s + "\", \""
						+ metadata.get(s).trim()
						+ "\", \""
						+ imageid
						+ "\");");
			} catch (Exception e) {
				stmt.execute("INSERT INTO `attributes` (`name`, `value`, `image_id`) VALUES (\"" + s + "\", \""
						+ ""
						+ "\", \""
						+ imageid
						+ "\");");
			}
		}

		conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
