/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;

/**
 *
 * @author Shannon
 */
public class UserDBQuery {

    /**
     * Retrieves all users.
     * @return
     * @throws SQLException 
     */
    public static ObservableList<Users> getAllUsers() throws SQLException {

        ObservableList<Users> userList = FXCollections.observableArrayList();

        try {
            String userQuery = "SELECT * from users;";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(userQuery);

            ResultSet rSet = preparedStatement.executeQuery();

            while (rSet.next()) {
                int userID = rSet.getInt("User_ID");
                String username = rSet.getString("User_Name");
                String password = rSet.getString("Password");

                Users users = new Users(userID, username, password);
                userList.add(users);
            }
        } catch (SQLException ex) {
            System.out.println("SQL error selecting users " + ex.getMessage());
        }
        return userList;
    }

    /**
     * Retrieves user login information.
     * @param username
     * @param password
     * @return
     * @throws SQLException 
     */
    public static boolean verifyUserInformation(String username, String password) throws SQLException {
        try {
            String userLoginQuery = "SELECT User_Name, Password FROM users WHERE User_Name = ? AND Password = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(userLoginQuery);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rSet = preparedStatement.executeQuery();

                        return (rSet.next());
            
            } catch (SQLException ex) {
                System.out.println("SQL Error verifying username and password! " + ex.getMessage());

                return false;
            }
    }

}