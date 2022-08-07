/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;
import java.sql.*;

/**
 *
 * @author Shannon
 */
public class ContactDBQuery {

    /**
     * Retrieves all contacts.
     * @return 
     */
    public static ObservableList<Contacts> getAllContacts() {

        ObservableList<Contacts> contactList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from contacts";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);

            ResultSet rSet = preparedStatement.executeQuery();

            while (rSet.next()) {
                int contactID = rSet.getInt("Contact_ID");
                String contactName = rSet.getString("Contact_Name");
                String contactEmail = rSet.getString("Email");
                Contacts contacts = new Contacts(contactID, contactName, contactEmail);
                contactList.add(contacts);
            }

        } catch (SQLException ex) {
            System.out.println("SQL error selecting contacts. " + ex.getMessage());
        }
        return contactList;
    }
    
        
    /**
     * Find contact ID given contact name.
     * @throws SQLException
     * @param contactID
     * @return contactID
     */
    public static String nameToContactID(String contactID) throws SQLException {
        
        
        String findContactQuery = "SELECT * FROM contacts WHERE Contact_Name = ?";

        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(findContactQuery);

        preparedStatement.setString(1, contactID);
        
        ResultSet rSet = preparedStatement.executeQuery();
        
        while (rSet.next()) {
            contactID = rSet.getString("Contact_ID");
        }
        
        return contactID;
    }
}

