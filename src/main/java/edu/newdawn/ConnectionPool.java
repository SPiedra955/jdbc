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
            createData("Java", 10);
            createData("JavaScript", 9);
            createData("C++", 8);
            createData("Html", 9);
            readData();
            updateData("C++", 5);
            updateData("Java", 5);
            readData();
            deleteData("Html");
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
					    INSERT INTO programming_language(name, rating)
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
                            FROM programming_language
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
                            UPDATE programming_language
                            SET rating = ?
                            WHERE name = ?
                        """)) {
                    statement.setInt(1, newRating);
                    statement.setString(2, name);
                    int rowsUpdated = statement.executeUpdate();
                    System.out.println("Rows updated: " + rowsUpdated);
                }
            }
        }
    
        /* DELETE DATA */
    	private static void deleteData(String nameExpression) throws SQLException {
            System.out.print("Deleting data...");
            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("""
                            DELETE FROM programming_language
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
            dataSource.setJdbcUrl("jdbc:mariadb://localhost:3306/uni");
            dataSource.setUsername("root");
            dataSource.setPassword("1234");
        }
    
        private static void closeDatabaseConnectionPool() {
            dataSource.close();
        }
}
