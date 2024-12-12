// @author KAN/IT/2022/F/0036
package Controller;

import java.sql.Connection; // Connection interface for database connectivity
import java.sql.DriverManager; // DriverManager to manage the database connection
import java.sql.SQLException; // SQLException for handling SQL errors

public class DBConnection {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/librarydb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Static method to get a connection to the database
    public static Connection getConnection() throws SQLException {
        // Attempt to establish and return the connection
        return DriverManager.getConnection(URL, USER, PASSWORD); // Returns a new connection object
    }
}
