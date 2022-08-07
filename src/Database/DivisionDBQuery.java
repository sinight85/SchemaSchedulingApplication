/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import model.Divisions;


/**
 *
 * @author Shannon
 */
public class DivisionDBQuery {
    
    /**
     * Retrieves all divisions.
     * @return 
     */
    public static ObservableList<Divisions> getAllDivisions() {

        ObservableList<Divisions> divisionList = FXCollections.observableArrayList();

        try {
            String divisionQuery = "SELECT * from first_level_divisions";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(divisionQuery);

            ResultSet rSet = preparedStatement.executeQuery();

            while (rSet.next()) {
                int divisionID = rSet.getInt("Division_ID");
                String division = rSet.getString("Division");
                int countryID = rSet.getInt("Country_ID");
                Divisions newDivisions = new Divisions(divisionID, division, countryID);
                divisionList.add(newDivisions);
            }

        } catch (SQLException ex) {
            System.out.println("SQL error selecting divisions. " + ex.getMessage());
        }
        return divisionList;
    }
    
    /**
     * Retrieves divisions by Country ID.
     * @param countryID
     * @return
     * @throws SQLException 
     */
    public static ObservableList<Divisions> getDivisionsByCountryID(int countryID) throws SQLException {

        ObservableList<Divisions> divisionList = FXCollections.observableArrayList();

        try {
            String divisionByCountryIDQuery = "SELECT * from first_level_divisions WHERE Country_ID = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(divisionByCountryIDQuery);
            preparedStatement.setInt(1, countryID);
  
            ResultSet rSet = preparedStatement.executeQuery();

            while (rSet.next()) {
                int divisionID = rSet.getInt("Division_ID");
                String division = rSet.getString("Division");
                rSet.getInt("Country_ID");
                Divisions newDivisions = new Divisions(divisionID, division, countryID);
                divisionList.add(newDivisions);
            }

        } catch (SQLException ex) {
            System.out.println("SQL error selecting countries by ID " + ex.getMessage());
        }
        return divisionList;
    }
 
}