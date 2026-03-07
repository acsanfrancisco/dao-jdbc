package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
	
	private static Connection conn = null;
	
	public static Connection getConnection() {
		if(conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			}
			catch(SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
		return conn;
	}
	
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			}
			catch(SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}
			catch(SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	public static void closePreparedStatement(PreparedStatement ps) {
		if(ps != null) {
			try {
				ps.close();
			}
			catch(SQLException e) {
				throw new DBException(e.getMessage());
			}
		}
	}
	
	
	private static Properties loadProperties() {
		Properties props = new Properties();
		
		try(FileInputStream fs = new FileInputStream("db.properties")){
			props.load(fs);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return props;
	}	
}
