package edu.newdawn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC {

	private static Connection connection;

	/**
	 * Entry point of the application. Opens and closes the database connection.
	 *
	 * @param args (not used)
	 * @throws SQLException if an error occurs when interacting with the database
	 */
	public static void main(String[] args) throws SQLException {
		try {
			initDatabaseConnection();

		} finally {
			if (connection != null) {
				closeDatabaseConnection();
			}
		}
	}

	private static void initDatabaseConnection() throws SQLException {
		System.out.println("Connecting to the database...");
		connection = DriverManager.getConnection(
				"jdbc:mariadb://localhost:[port_number]/[db_name]",
				"[db_user]", "[db_password]");
		System.out.println("Connection valid: " + connection.isValid(5)); /* time of response */
	}

	private static void closeDatabaseConnection() throws SQLException {
		System.out.println("Closing database connection...");
		connection.close();
		System.out.println("Connection valid: " + connection.isValid(5));
	}
}