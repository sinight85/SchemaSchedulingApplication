/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import model.Appointments;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;



/**
 *
 * @author Shannon
 */
public class AppointmentDBQuery {

    /**
     * Retrieves all Appointments in the database.
     * @return
     * @throws SQLException 
     */
    public static ObservableList<Appointments> getAllAppointments() throws SQLException {

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try {
            String getAllApptQuery = "SELECT * from appointments";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(getAllApptQuery);

            ResultSet rSet = preparedStatement.executeQuery();

            while (rSet.next()) {
                int appointmentID = rSet.getInt("Appointment_ID");
                String title = rSet.getString("Title");
                String description = rSet.getString("Description");
                String location = rSet.getString("Location");
                String type = rSet.getString("Type");
                LocalDateTime start = rSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rSet.getTimestamp("End").toLocalDateTime();
                int userID = rSet.getInt("User_ID");
                int customerID = rSet.getInt("Customer_ID");
                int contactID = rSet.getInt("Contact_ID");
                

                Appointments appointments = new Appointments(appointmentID, title, description, location, type, start, end, userID, customerID, contactID);
                appointmentList.add(appointments);
            }
        } catch (SQLException ex) {
            System.out.println("SQL error selecting all appointments " + ex.getMessage());
        }
        return appointmentList;
    }
    
    /**
     * Retrieves all Appointment given the Appointment_ID.
     * @return
     * @throws SQLException 
     */
    public static ObservableList<Appointments> getAppointmentID() throws SQLException {

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try {
            String getAllApptQuery = "SELECT * from appointments WHERE Appointment_ID = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(getAllApptQuery);

            ResultSet rSet = preparedStatement.executeQuery();

            while (rSet.next()) {
                int appointmentID = rSet.getInt("Appointment_ID");
                String title = rSet.getString("Title");
                String description = rSet.getString("Description");
                String location = rSet.getString("Location");
                String type = rSet.getString("Type");
                LocalDateTime start = rSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rSet.getTimestamp("End").toLocalDateTime();
                int userID = rSet.getInt("User_ID");
                int customerID = rSet.getInt("Customer_ID");
                int contactID = rSet.getInt("Contact_ID");

                

                Appointments appointments = new Appointments(appointmentID, title, description, location, type, start, end, userID, customerID, contactID);
                appointmentList.add(appointments);
            }
        } catch (SQLException ex) {
            System.out.println("SQL error selecting appointments by ID " + ex.getMessage());
        }
        return appointmentList;
    }
    

    /**
     * Retrieves appointments by Week.
     * @return
     * @throws SQLException 
     */
    public static ObservableList<Appointments> getAppointmentsByWeek() throws SQLException {

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments WHERE Start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 1 WEEK)";

        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(sql);



        try {
            preparedStatement.execute();
            ResultSet rSet = preparedStatement.executeQuery();

            while (rSet.next()) {
                int appointmentID = rSet.getInt("Appointment_ID");
                String title = rSet.getString("Title");
                String description = rSet.getString("Description");
                String location = rSet.getString("Location");
                String type = rSet.getString("Type");
                LocalDateTime start = rSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rSet.getTimestamp("End").toLocalDateTime();
                int userID = rSet.getInt("User_ID");
                int customerID = rSet.getInt("Customer_ID");
                int contactID = rSet.getInt("Contact_ID");

                Appointments appointmentsByWeek = new Appointments(appointmentID, title, description, location, type, start, end, userID, customerID, contactID);
                appointmentList.add(appointmentsByWeek);
            }
        } catch (SQLException ex) {
            System.out.println("SQL error selecting appointments by week " + ex.getMessage());
        }
        return appointmentList;
    }

    /**
     * Retrieves appointments by Month.
     * @return
     * @throws SQLException 
     */
    public static ObservableList<Appointments> getAppointmentsByMonth() throws SQLException {

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();
        
        String apptByMonthQuery = "SELECT * FROM appointments WHERE Start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 1 MONTH)";

        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(apptByMonthQuery);
        
        try {
            preparedStatement.execute();
            ResultSet rSet = preparedStatement.executeQuery();

            while (rSet.next()) {
                int appointmentID = rSet.getInt("Appointment_ID");
                String title = rSet.getString("Title");
                String description = rSet.getString("Description");
                String location = rSet.getString("Location");
                int contactID = rSet.getInt("Contact_ID");
                String type = rSet.getString("Type");
                LocalDateTime start = rSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rSet.getTimestamp("End").toLocalDateTime();
                int customerID = rSet.getInt("Customer_ID");
                int userID = rSet.getInt("User_ID");

                Appointments appointmentsByMonth = new Appointments(appointmentID, title, description, location, type, start, end, userID, customerID, contactID);
                appointmentList.add(appointmentsByMonth);
            }
        } catch (SQLException ex) {
            System.out.println("SQL error selecting appointments by month " + ex.getMessage());
        }
        return appointmentList;
    }

    /**
     * Retrieves appointments by Customer_ID.
     * @param customerID
     * @return
     * @throws SQLException 
     */
    public static ObservableList<Appointments> getAppointmentsByCustomerID(int customerID) throws SQLException {
        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try {
            String getApptByCustIDQuery = "SELECT * FROM appointments AS a INNER JOIN customers AS c ON a.Customer_ID = c.Customer_ID WHERE Customer_ID = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(getApptByCustIDQuery);
            preparedStatement.setInt(1, customerID);

            ResultSet rSet = preparedStatement.executeQuery();

            while (rSet.next()) {
                int appointmentID = rSet.getInt("Appointment_ID");
                String title = rSet.getString("Title");
                String description = rSet.getString("Description");
                String location = rSet.getString("Location");
                String type = rSet.getString("Type");
                LocalDateTime start = rSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rSet.getTimestamp("End").toLocalDateTime();
                int userID = rSet.getInt("User_ID");
                rSet.getInt("Customer_ID");
                int contactID = rSet.getInt("Contact_ID");

                Appointments appointments = new Appointments(appointmentID, title, description, location, type, start, end, userID, customerID, contactID);
                appointmentList.add(appointments);
            }
        } catch (SQLException ex) {
            System.out.println("SQL error selecting appointments by customerID " + ex.getMessage());
        }
        return appointmentList;
    }

    /**
     * Adds appointments to database.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     * @throws SQLIntegrityConstraintViolationException
     * @throws SQLException 
     */
    public static void insertAppts(String title, String description, String location, String type, Timestamp start, Timestamp end,
            Integer customerID, Integer userID, Integer contactID) throws SQLException, SQLException {

        try {
            String addApptQuery = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(addApptQuery);

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, type);
            preparedStatement.setTimestamp(5, start);
            preparedStatement.setTimestamp(6, end);
            preparedStatement.setInt(7, userID);
            preparedStatement.setInt(8, customerID);
            preparedStatement.setInt(9, contactID);
            
            preparedStatement.executeUpdate();
            
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Inserted " + preparedStatement.getUpdateCount() + " row(s)");
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            System.out.println("SQL error inserting appointments " + ex.getMessage());
        }

    }

    /**
     * Modifies existing appointments.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     * @param apptID
     * @return
     * @throws SQLException 
     */
    public static boolean updateAppts(String title, String description, String location, String type, Timestamp start, Timestamp end,
            Integer customerID, Integer userID, Integer contactID, Integer apptID) throws SQLException, SQLException {

        try {
            String updateQuery = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, type);
            preparedStatement.setTimestamp(5, start);
            preparedStatement.setTimestamp(6, end);
            preparedStatement.setInt(7, userID);
            preparedStatement.setInt(8, customerID);
            preparedStatement.setInt(9, contactID); 
            preparedStatement.setInt(10, apptID);
            
            System.out.println(updateQuery);
            preparedStatement.executeUpdate();

            preparedStatement.executeUpdate();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Updated " + preparedStatement.getUpdateCount() + " row(s)");
            }
            return true;
        } catch (SQLException ex) {
            System.out.println("SQL error updating appointments " + ex.getMessage());
        }
        return false;

    }

    /**
     * Deletes existing appointments found by appointment ID.
     * @param appointmentID
     * @return
     * @throws SQLException 
     */
    public static boolean deleteAppts(int appointmentID) throws SQLException {
        try {
            String deleteApptQuery = "DELETE from appointments WHERE Appointment_ID = ?";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(deleteApptQuery);

            preparedStatement.setInt(1, appointmentID);

            preparedStatement.executeUpdate();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Deleted " + preparedStatement.getUpdateCount() + " row(s)");
            } 
            return true;
        } catch (SQLException ex) {
            System.out.println("SQL error deleting appointments " + ex.getMessage());
        }
        return false;
    }
    
    /**
     * Finds the contact ID given the contact name.
     * @throws SQLException
     * @param contactID
     * @return contactID
     */
    public static String findContactID(String contactID) throws SQLException {

        String findContactQuery = "SELECT * FROM contacts WHERE Contact_Name = ?";

        PreparedStatement preparedStatement = JDBC.connection.prepareStatement(findContactQuery);

        preparedStatement.setString(1, contactID);
        ResultSet rSet = preparedStatement.executeQuery();
        while (rSet.next()) {
            contactID = rSet.getString("Contact_ID");
        }
        return contactID;
    }

    /**
     * Generates appointments reports by Contacts in the Organization.
     * @return 
     */
    public static String apptReportByContact() {
        try {

            StringBuilder apptReportByContact = new StringBuilder("Reports for Each Contact in the Organization");
            
            apptReportByContact.append("\n");
            apptReportByContact.append("_________________________________________ \n");

            
            String reportByContactQuery = "SELECT a.Contact_ID, a.Appointment_ID, a.Title, a.Type, a.Description, a.Start, a.End, a.Customer_ID, cr.Customer_Name FROM appointments AS a "
                    + " INNER JOIN customers AS cr ON cr.Customer_ID = a.Customer_ID ORDER BY Customer_ID";

            PreparedStatement preparedStatement =  JDBC.connection.prepareStatement(reportByContactQuery);

            ResultSet rSet = preparedStatement.executeQuery();

            while (rSet.next()) {
                int customerID = rSet.getInt("Customer_ID");
                int appointmentID = rSet.getInt("Appointment_ID");
                String title = rSet.getString("Title");
                String type = rSet.getString("Type");
                String description = rSet.getString("Description");
                LocalDateTime start = rSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rSet.getTimestamp("End").toLocalDateTime();

                StringBuilder appendReport = apptReportByContact.append("\n");
                appendReport.append("CUSTOMER ID: ");
                appendReport.append(customerID);
                appendReport.append("\t APPOINTMENT ID: ");
                appendReport.append(appointmentID);
                appendReport.append("\t TITLE: ");
                appendReport.append(title);
                appendReport.append("\t START: ");
                appendReport.append(start);
                appendReport.append("\t DESCRIPTION: ");
                appendReport.append(description);
                appendReport.append("\t TYPE: ");
                appendReport.append(type);
                appendReport.append("\n");
                appendReport.append("\t \t \t \t \t \t \t \t \t");
                appendReport.append("\t \t \t END: ");
                appendReport.append(end);
                appendReport.append("   \n");
            }
            return apptReportByContact.toString();
        } catch (SQLException ex) {
            System.out.print("Error retreiving appt by contact" + ex.getMessage());
            return "Error retreiving appt by contact";
        }
    }


    /**
     * Generates appointments report by Type and Month.
     * @return 
     */
    public static String apptReportTypeMonth() {
        try {
            StringBuilder apptReportTypeMonth = new StringBuilder("Number of Customer Appointments by Type and Month");
            apptReportTypeMonth.append("\n");
            apptReportTypeMonth.append("_________________________________________ \n");


            
            String monthQuery = "SELECT MONTHNAME(start) as Month, Type, COUNT(*)  as Amount FROM appointments GROUP BY MONTH(start), type";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(monthQuery);

            ResultSet rSet = preparedStatement.executeQuery();
            while (rSet.next()) {
                String month = rSet.getString("Month");
                String type = rSet.getString("Type");
                String amount = rSet.getString("Amount");

                StringBuilder appendReport = apptReportTypeMonth.append("\n");
                appendReport.append("MONTH: ");
                appendReport.append(month);
                appendReport.append("\t AMOUNT: ");
                appendReport.append(amount);
                appendReport.append("\t TYPE: ");
                appendReport.append(type);
                appendReport.append("\n");
            }
            return apptReportTypeMonth.toString();
        } catch (SQLException ex) {
            System.out.print("Error retreiving Appt by Type and Month" + ex.getMessage());
            return "Error retreiving Appt by Type and Month";
        }
    }



    /**
     * Generates appointments reports by Location and Type.
     * @return 
     */
    public static String apptReportByLocation() {
        try {
            StringBuilder apptReportByLocaiton = new StringBuilder("Reports Total Appointments By Location and Type");
            apptReportByLocaiton.append("\n");
            apptReportByLocaiton.append("_________________________________________ \n");


            String locationQuery = "SELECT Location, Type, COUNT(*) as Amount FROM appointments GROUP BY type";

            PreparedStatement preparedStatement = JDBC.connection.prepareStatement(locationQuery);

            ResultSet rSet = preparedStatement.executeQuery();
            while (rSet.next()) {
                String type = rSet.getString("Type");
                String location = rSet.getString("Location");
                String amount = rSet.getString("Amount");
                
                StringBuilder appendReport;
                appendReport = apptReportByLocaiton;
                appendReport.append("AMOUNT: ");
                appendReport.append(amount);
                appendReport.append("\t \t \t LOCATION: ");
                appendReport.append(location);
                appendReport.append("\t \t TYPE: ");
                appendReport.append(type);
                appendReport.append("\n");
                appendReport.append("\n");
            }
            return apptReportByLocaiton.toString();
        } catch (SQLException ex) {
            System.out.print("Error retreiving appt by type" + ex.getMessage());
            return "Error retreiving appt by type";
        }
    }
    
}
