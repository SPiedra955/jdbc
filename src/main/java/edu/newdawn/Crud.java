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
        dropTable();
        createTable();
        readData();
        createData("Justin", "Perez", "Oms", 7, false);
        createData("Joan", "Marti", "Marti", 1, false);
        createData("Luis", "Garcia", "Paz", 5, false);
        createData("Andres", "Naranjo", "Valdez", 2, false);
        createData("Anabel", "Rigo", "Font", 2, false);
        readData();
        updateData(true);
        updateData(true);
        readData();
        deleteData("Rigo", "Font");
        readData();
        } finally{
            closeDatabaseConnection();
        }
    }

    /* DROP TABLE    */

   private static void dropTable() throws SQLException {
    try (PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS db_course")) {
        statement.executeUpdate();
        System.out.println("Deleting records...");
        }
    }

    /* CREATE TABLE */

    private static void createTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                CREATE TABLE db_course (
                    name VARCHAR(50) NOT NULL,
                    surname VARCHAR(50) NOT NULL,
                    last_name VARCHAR(50) NOT NULL,
                    score INT NOT NULL,
                    approved TINYINT NOT NULL
                )
                """)) {
            statement.executeUpdate();
            System.out.println("Creating table...");
        }
    }

    /* INSERTING DATA (create table and database before run the file) */

    private static void createData(String name, String surname, String last_name, int score, Boolean approved) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO db_course(name, surname, last_name, score, approved)
                    VALUES(?, ?, ?, ?, ?)
                    """)) {
            statement.setString(1, name); /* inserting values by index of the parameters */
            statement.setString(2, surname);
            statement.setString(3, last_name);
            statement.setInt(4, score);
            statement.setBoolean(5, approved);
            int rowsInserted = statement.executeUpdate();
            System.out.println("Creating data...");
            System.out.println("Rows inserted: " + rowsInserted);
        }
    }


    /* READ DATA */

    private static void readData() throws SQLException{
        try(PreparedStatement statement =connection.prepareStatement("""
                    SELECT name, surname, score, approved
                    FROM db_course
                    ORDER by score DESC
                    """)) {
        try(ResultSet resultSet = statement.executeQuery()){
                        boolean empty = true;
                        System.out.println("Reading data...");
                        while(resultSet.next()){
                            empty = false;
                            String name = resultSet.getString("name"); /* reading data by the name of the column */
                            String surname = resultSet.getString("surname");
                            int score = resultSet.getInt("score");
                            Boolean approved = resultSet.getBoolean("approved");
                            System.out.println("\t> " + name + " " + surname + " = " + 
                                               " aprobado -> " + approved
                                               + "(" + score + ")");
                        }
                        if(empty){
                            System.out.println("Reading data...");
                            System.out.println("\t (no data)");
                        }
                    }
                }
            }       
    
    /* UPDATE DATA */

    private static void updateData(Boolean approved) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("""
                    UPDATE db_course
                    SET approved = ?
                    WHERE score >= 5
                    """)) {
            statement.setBoolean(1, approved);
            int rowsUpdated = statement.executeUpdate();
            System.out.println("Updating data...");
            System.out.println("Rows update: " + rowsUpdated);                
            }
        }

    /* DELETE DATA */

    private static void deleteData(String surnameExpression, String last_nameExpression) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("""
                    DELETE FROM db_course
                    WHERE surname LIKE ?
                    AND last_name = ? 
                    """)){
            statement.setString(1, surnameExpression);
            statement.setString(2, last_nameExpression);
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Deleting data...");
            System.out.println("Rows deleted: " + rowsDeleted);
            }
        }
    
    /* OPEN CONNECTION */

    private static void initDatabaseConnection() throws SQLException{
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/students", 
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
