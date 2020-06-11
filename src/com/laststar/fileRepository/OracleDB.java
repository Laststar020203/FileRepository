package com.laststar.fileRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDB {
	
	private static Connection connection;
	
	static {
		String driverClass = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "system";
		String password = "1234";
		
		try {
			Class.forName(driverClass);
			connection = DriverManager.getConnection(url, username, password);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
	public static void setAutoCommit(boolean isAuto) throws SQLException {
		connection.setAutoCommit(isAuto);
	}
	
	public static void commit() throws SQLException {
		connection.commit();
	}
	
	public static void rollback() throws SQLException {
		connection.rollback();
	}
}
