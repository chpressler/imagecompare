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
				conn.setAutoCommit(true);
				Statement s = conn.createStatement();
//				s.execute("drop table attributes");
//				s.execute("drop table images");
				
//				String test1 = "CREATE TABLE HOTELAVAILABILITY (HOTEL_ID INT NOT NULL, BOOKING_DATE DATE NOT NULL, ROOMS_TAKEN INT DEFAULT 0, PRIMARY KEY (HOTEL_ID, BOOKING_DATE))";
//				String test2 = "CREATE TABLE PEOPLE (PERSON_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT PEOPLE_PK PRIMARY KEY, PERSON VARCHAR(26))";
//				String test3 = "CREATE TABLE GROUP (GROUP_ID SMALLINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 5, INCREMENT BY 5), ADDRESS VARCHAR(100), PHONE VARCHAR(15))";
//				s.execute(test1);
//				s.execute(test2);
//				s.execute(test3);
				
				s.execute("create table images(id bigint not null generated always as identity (start with 1, increment by 1), path varchar(500) not null, primary key(id))");
				s.execute("create table attributes(id bigint not null generated always as identity (start with 1, increment by 1), name varchar(500) not null, value varchar(500) not null, image_id bigint not null, primary key(id), foreign key(image_id) references images(id))");
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
		synchronized (this) {
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
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
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
			try {
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return results;
	}

}
