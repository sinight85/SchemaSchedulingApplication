/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 *
 * @author Shannon
 */
public class JDBC {
    
    Connection dbConnection;
    

    private static final String protocol = "jdbc";

    private static final String vendor = ":mysql:";

    private static final String location = "//localhost/";

    private static final String databaseName = "client_schedule";

    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL

    private static final String driver = "com.mysql.cj.jdbc.Driver"; 

    private static final String userName = "sqlUser"; // Username

    private static String password = "Passw0rd!"; // Password

    public static Connection connection;  
    
    /**
     * Open connection to DB.
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static void openConnection() throws ClassNotFoundException, SQLException {
         
            Class.forName(driver);
            
            System.out.println("Connecting to Database...");
            connection = DriverManager.getConnection(jdbcUrl, userName, password); 
            System.out.println("Connection Successful.");
           
    }
    
    /**
     * Closes connection to DB.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Closing connection...");
        }
        catch (SQLException ex)
        {
            System.out.println("Error: " + ex.getMessage());
        }
    }
        
}  
