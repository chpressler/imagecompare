package de.fherfurt.imagecompare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

public class ImportDBMySQLHandler {
	
	private static volatile ImportDBMySQLHandler instance;
	
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

		//Table images
		stmt.execute("INSERT INTO `images` (`path`) VALUES (\"" + absolutePath + "\");");
		
		//TODO -> gerade angelegte id für das image
		int imageid = 0;
		
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
