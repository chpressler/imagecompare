package de.fherfurt.imagecompare;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

public class ImportDBMySQLHandler {
	
	private static volatile ImportDBMySQLHandler instance;
	
	private ResultSet rs;
	
	int imageid = -1;
	
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
	}

	public synchronized void addImport(String absolutePath, HashMap<String, String> metadata) {
		try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/ic?user=root&password=duluth");
		Statement stmt = conn.createStatement();

		//TODO -> Radiobutton if new... 
//		stmt.execute("DELETE FROM images");
//		stmt.execute("DELETE FROM attributes");

		absolutePath = absolutePath.replaceAll("\\\\", "/");
		System.out.println(absolutePath);
		
		//Table images
		try {
			stmt.execute("INSERT INTO `images` (`path`) VALUES (\"" + absolutePath + "\");");
		} catch(java.sql.SQLException sqle) {
			System.out.println("schon importiert");
			return;
		}
		
		//gerade angelegte id für das image
		try {
			ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID() FROM images");
//			imageid = stmt.getGeneratedKeys().getInt(0);
			if (rs.next()) {
				imageid = rs.getInt(1);
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
