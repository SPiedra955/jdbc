package edu.newdawn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

public class Crud {

    private static Connection connection;

    public static void main (String[] args) throws SQLException{
    try{
        initDatabaseConnection();
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
        } finally{
            closeDatabaseConnection();
        }
    }

    /* INSERTING DATA (create table before run the file) */
    private static void createData(String name, int rating) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO programming_language(name, rating)
                    VALUES(?, ?)
                    """)) {
            statement.setString(1, name); /* inserting values by index of the parameters */
            statement.setInt(2, rating);
            int rowsInserted = statement.executeUpdate();
            System.out.println("Creating data...");
            System.out.println("Rows inserted: " + rowsInserted);
        }
    }


    /* READ DATA */

    private static void readData() throws SQLException{
        try(PreparedStatement statement =connection.prepareStatement("""
                    SELECT name, rating
                    FROM programming_language
                    ORDER by rating DESC
                    """)) {
        try(ResultSet resultSet = statement.executeQuery()){
                        boolean empty = true;
                        System.out.println("Reading data...");
                        while(resultSet.next()){
                            empty = false;
                            String name = resultSet.getString("name"); /* reading data by the name of the column */
                            int rating = resultSet.getInt("rating");
                            System.out.println("\t> " + name + ": " + rating);
                        }
                        if(empty){
                            System.out.println("Reading data...");
                            System.out.println("\t (no data)");
                        }
                    }
                }
            }       
    
    /* UPDATE DATA */

    private static void updateData(String name, int newRating) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("""
                    UPDATE programming_language
                    SET rating = ?
                    WHERE name = ?
                    """)) {
            statement.setInt(1, newRating);
            statement.setString(2, name);
            int rowsUpdated = statement.executeUpdate();
            System.out.println("Updating data...");
            System.out.println("Rows update: " + rowsUpdated);                
            }
        }

    /* DELETE DATA */

    private static void deleteData(String nameExpression) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("""
                    DELETE FROM programming_language
                    WHERE name LIKE ? 
                    """)){
            statement.setString(1, nameExpression);
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Deleting data...");
            System.out.println("Rows deleted: " + rowsDeleted);
            }
        }
    
    /* OPEN CONNECTION */

    private static void initDatabaseConnection() throws SQLException{
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/uni", 
                "root", "1234");
            System.out.println("Connection valid: " + connection.isValid(5));
    }

    /* CLOSE CONNECTION */

    private static void closeDatabaseConnection() throws SQLException{
            System.out.println("Closing database connection...");
            connection.close();
            System.out.println("Connection valid: " + connection.isValid(5));
    }
}
