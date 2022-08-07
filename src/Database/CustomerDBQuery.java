/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;
import java.sql.*;

/**
 *
 * @author Shannon
 */
public class CustomerDBQuery {

    /**
     * Retrieves all customers.
     * @return
     * @throws SQLException 
     */
    public static ObservableList<Customers> getAllCustomers() throws SQLException {

        ObservableList<Customers> customerList = FXCollections.observableArrayList();

        try {
            String customerQuery = "SELECT * FROM customers AS c INNER JOIN first_level_divisions AS f ON c.Division_ID = f.Division_ID INNER JOIN countries AS r ON r.Country_ID = f.Country_ID ORDER BY r.Country_ID;";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(customerQuery);

            ResultSet rSet = preparedStatement.executeQuery();

            while (rSet.next()) {
                int customerID = rSet.getInt("Customer_ID");
                String customerName = rSet.getString("Customer_Name");
                String address = rSet.getString("Address");
                String division = rSet.getString("Division");
                String postalCode = rSet.getString("Postal_Code");
                String country = rSet.getString("Country");
                String phoneNumber = rSet.getString("Phone");
                int divisionID = rSet.getInt("Division_ID");

                Customers cust = new Customers(customerID, customerName, address, division, postalCode, country, phoneNumber, divisionID);
                customerList.add(cust);
            }
        } catch (SQLException ex) {
            System.out.println("SQL error selecting customers " + ex.getMessage());
        }
        return customerList;
    }
    
    /**
     * Retrieves all customer IDs.
     * @return
     * @throws SQLException 
     */
    public static ObservableList<Customers> getAllCustomerID() throws SQLException {

        ObservableList<Customers> customerList = FXCollections.observableArrayList();

        try {
            String customerIDQuery = "SELECT Customer_ID FROM customers";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(customerIDQuery);

            ResultSet rSet = preparedStatement.executeQuery();

            while (rSet.next()) {
                int customerID = rSet.getInt("Customer_ID");
                String customerName = rSet.getString("Customer_Name");
                String address = rSet.getString("Address");
                String division = rSet.getString("Division");
                String postalCode = rSet.getString("Postal_Code");
                String country = rSet.getString("Country");
                String phoneNumber = rSet.getString("Phone");
                int divisionID = rSet.getInt("Division_ID");

                Customers cust = new Customers(customerID, customerName, address, division, postalCode, country, phoneNumber, divisionID);
                customerList.add(cust);
            }
        } catch (SQLException ex) {
            System.out.println("SQL error selecting customerID " + ex.getMessage());
        }
        return customerList;
    }

    /**
     * Adds customer to the database.
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID 
     */
    public static void insertCustomer(String customerName, String address, String postalCode, String phone, Integer divisionID) {

        try {
            String insertCustomers = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (? , ?, ?, ?, ?)";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(insertCustomers);
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, postalCode);
            preparedStatement.setString(4, phone);
            preparedStatement.setInt(5, divisionID);

            preparedStatement.executeUpdate();
            
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Inserted " + preparedStatement.getUpdateCount() + " row(s)");
            }

        } catch (SQLException ex) {
            System.out.println("SQL error inserting customers " + ex.getMessage());
        }
    }

    /**
     * Updates existing customer.
     * @param customerName
     * @param address
     * @param postalCode
     * @param phone
     * @param divisionID
     * @param customerID 
     */
    public static void updateCustomer(String customerName, String address, String postalCode, String phone, Integer divisionID, int customerID) {

        try {
            String updateCustomer = "UPDATE customers SET Customer_name = ? , Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(updateCustomer);
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, postalCode);
            preparedStatement.setString(4, phone);
            preparedStatement.setInt(5, divisionID);
            preparedStatement.setInt(6, customerID);

            preparedStatement.executeUpdate();
                if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Updated " + preparedStatement.getUpdateCount() + " row(s)");
            } 
        } catch (SQLException ex) {
            System.out.println("SQL error updating customers " + ex.getMessage());
        }

    }


    /**
     * Deletes existing customer by customer ID.
     * @param customerID
     * @return
     * @throws SQLException 
     */
    public static boolean deleteCustomer(int customerID) throws SQLException {
                
        try {
            String sql = "DELETE from customers WHERE Customer_ID=?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);

            preparedStatement.setInt(1, customerID);

            preparedStatement.executeUpdate();
            
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Deleted " + preparedStatement.getUpdateCount() + " row(s)");
            } 
            return true;
        } catch (SQLException ex) {
            System.out.println("SQL error deleting customers " + ex.getMessage());
        }
        return false;
    }
    

}