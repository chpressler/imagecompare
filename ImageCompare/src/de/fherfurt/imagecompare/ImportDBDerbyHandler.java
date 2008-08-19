package de.fherfurt.imagecompare;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import de.fherfurt.imagecompare.swing.components.ImageThumbnailComponent;

public class ImportDBDerbyHandler implements IImport {
	
	private static volatile ImportDBDerbyHandler instance;
	
	private static String driver = "org.apache.derby.jdbc.EmbeddedDriver"; 
	
	private static String protocol = "jdbc:derby:";
	
	private static String dbName = "IC";
	
	private Properties props;
	
	private Connection conn;
	
	long imageid = -1;
	
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
				if(conn == null || conn.isClosed()) {
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(protocol + dbName
					+ ";create=true", props);
				}
				
//				System.out.println(protocol + dbName + ";create=true");
//				conn.setAutoCommit(false);
				Statement s = conn.createStatement();
				
//				ResultSet rs = s.executeQuery("select * from images");
//				while(rs.next()) {
//					System.out.println(rs.getString("path"));
//				}
//				
//				rs = s.executeQuery("select * from attributes");
//				while(rs.next()) {
//					System.out.println(rs.getString("id") + " - " + rs.getString("name") + " - " + rs.getString("value"));
//				}
				
//				s.execute("drop table attributes");
//				s.execute("drop table image");		
				
				s.execute("create table images(id bigint not null generated always as identity (start with 1, increment by 1), path varchar(500) not null, primary key(id))");
				s.execute("create table attributes(id bigint not null generated always as identity (start with 1, increment by 1), name varchar(500) not null, value varchar(500) not null, image_id bigint not null, primary key(id), foreign key(image_id) references images(id))");
				
//				conn.close();
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
			if(conn == null || conn.isClosed()) {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(protocol + dbName
					+ ";create=true", props);
			conn.setAutoCommit(false);
			}
			Statement stmt = conn.createStatement();

			//TODO -> Radiobutton if new... 
//			stmt.execute("DELETE FROM images");
//			stmt.execute("DELETE FROM attributes");

			if(!absolutePath.startsWith("http")) {
				absolutePath = absolutePath.replaceAll("\\\\", "/");
			}
			
			//Table images
			try {
				stmt.execute("INSERT INTO images (path) VALUES (\'" + absolutePath + "\')");
			} catch(java.sql.SQLException sqle) {
				sqle.printStackTrace();
				System.out.println("already imported - UNIQE MySQL Exception - should not happen here");
				return;
			}
			
			//gerade angelegte id für das image
			try {
				ResultSet rs = stmt.executeQuery("SELECT id FROM images WHERE path = (\'" + absolutePath + "\')");
//				imageid = stmt.getGeneratedKeys().getInt(0);
				if (rs.next()) {
					imageid = rs.getLong("id");
			    } else {
			    	rs.close();
			    	return;
			    }
//				//imageid = stmt.getGeneratedKeys().getInt(1);
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
					stmt.execute("INSERT INTO attributes (name, value, image_id) VALUES (\'" + s + "\', \'"
							+ metadata.get(s).trim()
							+ "\', "
							+ imageid
							+ ")");
				} catch (Exception e) {
					stmt.execute("INSERT INTO attributes (name, value, image_id) VALUES (\'" + s + "\', \'"
							+ ""
							+ "\', "
							+ imageid
							+ ")");
					e.printStackTrace();
				}
			}
//			conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public void addImport(ImageThumbnailComponent imtc) {
		try {
			if(conn == null || conn.isClosed()) {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(protocol + dbName
					+ ";create=true", props);
			}
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();

			//Table images
			try {
				stmt.execute("INSERT INTO images (path) VALUES (\'" + imtc.getPath() + "\')");
			} catch(java.sql.SQLException sqle) {
				System.out.println("already imported - UNIQE MySQL Exception - should not happen here");
				sqle.printStackTrace();
				return;
			}
			
			//gerade angelegte id für das image
			try {
				ResultSet rs = stmt.executeQuery("SELECT id FROM images WHERE path = (\'" + imtc.getPath() + "\')");
//				imageid = stmt.getGeneratedKeys().getInt(0);
				if (rs.next()) {
					imageid = rs.getLong("id");
			    } else {
			    	rs.close();
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
					stmt.execute("INSERT INTO attributes (name, value, image_id) VALUES (\'" + s + "\', \'"
							+ imtc.getAttributes().get(s).trim()
							+ "\', "
							+ imageid
							+ ")");
				} catch (Exception e) {
					e.printStackTrace();
					stmt.execute("INSERT INTO attributes (name, value, image_id) VALUES (\'" + s + "\', \'"
							+ ""
							+ "\', "
							+ imageid
							+ ")");
				}
			}
//			conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

	@Override
	public HashMap<String, String> getAttributes(String path) {
		HashMap<String, String> attributes = new HashMap<String, String>();
		try {
			if(conn == null || conn.isClosed()) {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(protocol + dbName
					+ ";create=true", props);
			}
//			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from attributes where image_id = (select id from images where path = '"
							+ path + "')");
			while (rs.next()) {
				attributes.put(rs.getString("name"), rs.getString("value"));
			}
//			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attributes;
	}

	@Override
	public boolean isImported(String absolutePath) {
		try {
			if(conn == null || conn.isClosed()) {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(protocol + dbName
					+ ";create=true", props);
			}
//			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			if(!absolutePath.startsWith("http")) {
				absolutePath = absolutePath.replace("\\", "/");
			}
			ResultSet rs = stmt.executeQuery("SELECT * FROM images WHERE path = '" + absolutePath + "'");
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
