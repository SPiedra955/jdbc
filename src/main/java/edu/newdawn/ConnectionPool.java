package edu.newdawn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPool {

    private static HikariDataSource dataSource;

    public static void main (String[] args) throws SQLException{
        try {
            initDatabaseConnectionPool();
            deleteData("%");
            readData();
            createData("Guardianes de la galaxia", 4);
            createData("Black Panther", 9);
            createData("John Wick", 4);
            createData("Super Mario Bros", 6);
            readData();
            updateData("John Wick", 9);
            updateData("Super Mario Bros", 10);
            readData();
            deleteData("Guardianes de la galaxia");
            readData();
		} finally {
			closeDatabaseConnectionPool();
		}
	}
    
        /* INSERTING DATA (create table before run the file) */

        private static void createData(String name, int rating) throws SQLException {
        System.out.println("Creating data...");
        int rowsInserted;
		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement("""
					    INSERT INTO films(name, rating)
					    VALUES (?, ?)
					""")) {
				statement.setString(1, name);
				statement.setInt(2, rating);
				rowsInserted = statement.executeUpdate();

			}
		}
        System.out.println("Rows inserted: " + rowsInserted);
	}
    
        /* READ DATA */
    
        private static void readData() throws SQLException {
            System.out.println("Reading data...");
            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("""
                            SELECT name, rating
                            FROM films
                            ORDER BY rating DESC
                        """)) {
                    try (ResultSet resultSet = statement.executeQuery()) {
                        boolean empty = true;
                        while (resultSet.next()) {
                            empty = false;
                            String name = resultSet.getString("name");
                            int rating = resultSet.getInt("rating");
                            System.out.println("\t> " + name + ": " + rating);
                        }
                        if (empty) {
                            System.out.println("\t (no data)");
                        }
                    }
                }
            }
        }
        
        /* UPDATE DATA */
        private static void updateData(String name, int newRating) throws SQLException {
            System.out.print("Updating data...");
            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("""
                            UPDATE films
                            SET rating = ?
                            WHERE name = ?
                        """)) {
                    statement.setInt(1, newRating);
                    statement.setString(2, name);
                    int rowsUpdated = statement.executeUpdate();
                    System.out.println("\nRows updated: " + rowsUpdated);
                }
            }
        }
    
        /* DELETE DATA */
    	private static void deleteData(String nameExpression) throws SQLException {
            System.out.print("Deleting data...");
            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("""
                            DELETE FROM films
                            WHERE name LIKE ?
                        """)) {
                    statement.setString(1, nameExpression);
                    int rowsDeleted = statement.executeUpdate();
                    System.out.println("Rows deleted: " + rowsDeleted);
                }
            }
        }

        private static void initDatabaseConnectionPool() {
            dataSource = new HikariDataSource();
            dataSource.setJdbcUrl("jdbc:mariadb://localhost:[db_port]/[db_name]");
            dataSource.setUsername("[db_user]");
            dataSource.setPassword("[db_password]");
        }
    
        private static void closeDatabaseConnectionPool() {
            dataSource.close();
        }
}
