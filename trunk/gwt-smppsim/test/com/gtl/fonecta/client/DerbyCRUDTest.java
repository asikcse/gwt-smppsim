package com.gtl.fonecta.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author devang
 * 
 */
public class DerbyCRUDTest {

	private static Connection conn = null;
	/*private static String url = "jdbc:derby:gwt-smppsim-database;create=true;user=gateway;password=gateway12@";*/
	private static String url = "jdbc:derby:gwt-smppsim-database;create=true;";
	private static Statement stmt = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 connnect();
		// insertRecord();
		// showRecords();

		// updateRecords();
		// deleteRecords();
	}

		
	public static void connnect() {
		String strCreateTable = "CREATE TABLE message (msgId INTEGER NOT NULL, source_addr BIGINT, dest_addr BIGINT, short_message VARCHAR(160), message_type VARCHAR(10), send_time TIMESTAMP)";
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			conn = DriverManager.getConnection(url);
			stmt = conn.createStatement();
			System.out.println("Creating MESSAGE table...");
			stmt.executeUpdate(strCreateTable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static void insertRecord() {
		try {
			Date now = new Date();
			Timestamp timestamp = new Timestamp(now.getYear(), now.getMonth(),
					now.getDate(), now.getHours(), now.getMinutes(), now
							.getSeconds(), 0);

			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			conn = DriverManager.getConnection(url);
			PreparedStatement pstmt = conn
					.prepareStatement("insert into MESSAGE (msgId, source_addr, dest_addr, short_message, message_type, send_time) values (?,?,?,?,?,?)");
			pstmt.setLong(1, 1);
			pstmt.setLong(2, 4477665544L);
			pstmt.setLong(3, 337788665522L);
			pstmt.setString(4, "This is TEST Message1");
			pstmt.setString(5, "MO");
			pstmt.setTimestamp(6, timestamp);
			System.out
					.println("Inserting value into the message table as mo message...");
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showRecords() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			/*String sql = "select * from MESSAGE where source_addr=4477665544 and dest_addr=337788665522";*/
			String sql = "select * from MESSAGE";
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getString(2)+ "\t" + rs.getString(3)+ "\t" + rs.getString(4)+ "\t" + rs.getString(5)+ "\t" + rs.getString(6));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateRecords() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE MESSAGE set message_type='MT'");		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteRecords() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("delete from MESSAGE");
			//stmt.executeUpdate("TRUNCATE TABLE MESSAGE"); // NOT SUPPORTED 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
