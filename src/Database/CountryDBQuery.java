/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;


import model.Countries;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 *
 * @author Shannon
 */
public class CountryDBQuery {

    /**
     * Retrieves all countries.
     * @return
     * @throws SQLException 
     */
    public static ObservableList<Countries> getAllCountries() throws SQLException {

        ObservableList<Countries> countryList = FXCollections.observableArrayList();

        try {
            String countryQuery = "SELECT * from countries";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(countryQuery);

            ResultSet rSet = preparedStatement.executeQuery();

            while (rSet.next()) {
                int countryID = rSet.getInt("Country_ID");
                String country = rSet.getString("Country");
                Countries countries = new Countries(countryID, country);
                countryList.add(countries);
            }
        } catch (SQLException ex) {
            System.out.println("SQL error selecting countries. " + ex.getMessage());
        }
        return countryList;
    }

    /**
     * Retrieves countries by division ID.
     * @param divisionID
     * @return
     * @throws SQLException 
     */
    public static Countries getCountryByDivisionID(int divisionID) throws SQLException {

        try {
            String countryByDivisionQuery = "SELECT * from countries AS c INNER JOIN first_level_divisions AS fld ON c.Country_ID = fld.Country_ID WHERE fld.Division_ID = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(countryByDivisionQuery);
            preparedStatement.setInt(1, divisionID);
            ResultSet rSet = preparedStatement.executeQuery();

            while (rSet.next()) {
                Countries countries = new Countries(
                        rSet.getInt("Country_ID"),
                        rSet.getString("Country"));

                return countries;
            }
        } catch (SQLException ex) {
            System.out.println("SQL error selecting country by division. " + ex.getMessage());
        }
        return null;

    }

}