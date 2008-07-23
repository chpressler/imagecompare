package de.fherfurt.imagecompare;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class DerbyImageSearcher implements ILocalImageSearcher {
	
	private Properties props;
	
	private static String driver = "org.apache.derby.jdbc.EmbeddedDriver"; 
	
	private static String protocol = "jdbc:derby:";
	
	private static String dbName = "IC";
	
	private Connection conn;
	
	public DerbyImageSearcher() {
		try {
			props = new Properties();
			props.load(new FileInputStream("resources/preferences"));
			
			try {
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(protocol + dbName
					+ ";create=true", props);
//				conn.setAutoCommit(false);
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
	public void deleteImage(String path) {
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(protocol + dbName
				+ ";create=true", props);
			conn.setAutoCommit(false);
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

	@Override
	public ArrayList<String> getResults(String pattern) {
		ArrayList<String> results = new ArrayList<String>();
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(protocol + dbName
				+ ";create=true", props);
//			conn.setAutoCommit(false);
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

}
