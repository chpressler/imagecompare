package de.fherfurt.imagecompare;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class MySQLImageSearcher implements ILocalImageSearcher {

	private Properties properties;
	
	private String dbhost; 
	
	private String dbport;
	
	private String db;
	
	private String user;
	
	private String password;
	
	@Override
	public ArrayList<String> getResults(String pattern) {
		ArrayList<String> results = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://"
					+ dbhost + ":" + dbport + "/" + db + "?user=" + user
					+ "&password=" + password);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT path FROM images WHERE path LIKE '%"
							+ pattern + "%'");
			while (rs.next()) {
				results.add(rs.getString(1));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public MySQLImageSearcher() {
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

	@Override
	public void deleteImage(String path) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://"
					+ dbhost + ":" + dbport + "/" + db + "?user=" + user
					+ "&password=" + password);
			Statement stmt = conn.createStatement();
			String subquery = "(select id FROM images where path = '" + path
					+ "')";
			stmt.execute("DELETE FROM attributes where image_id = " + subquery);
			stmt.execute("DELETE FROM images where path = '" + path + "'");
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
