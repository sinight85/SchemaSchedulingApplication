/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import Database.AppointmentDBQuery;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Appointments;

/**
 * Alerts.
 * @author Shannon
 */
public class Alerts {

    /**
     * Appointment in 15 minutes alert English.
     * @param alertID
     * @throws SQLException 
     */
    public static void apptWarningEN(int alertID) throws SQLException {
        ObservableList<Appointments> appointments = AppointmentDBQuery.getAllAppointments();
        for (Appointments appointment : appointments) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            switch (alertID) {
                case 1 -> {
                    alert.setHeaderText("Appointment in 15 minutes!");
                    alert.setContentText("Appointment ID: " + appointment.getAppointmentID() 
                            + "\n Date and Time: " + appointment.getStart());
                    alert.showAndWait();
                    return;
                }
                case 2 -> {
                    alert.setHeaderText("Notice");
                    alert.setContentText("No appointments within 15 minutes.");
                    alert.showAndWait();
                    return;
                }
            }
        }
    }

    /**
     * Appointment in 15 minutes alert French.
     * @param alertID
     * @throws SQLException 
     */
    public static void apptWarningFR(int alertID) throws SQLException {
        ObservableList<Appointments> appointments = AppointmentDBQuery.getAllAppointments();
        for (Appointments appointment : appointments) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement!");
            switch (alertID) {
                case 1 -> {
                    alert.setHeaderText("Rendez-vous dans 15 minutes !");
                    alert.setContentText("ID de rendez-vous: " + appointment.getAppointmentID() 
                            + "\n Date et l'heure: " + appointment.getStart());
                    alert.showAndWait();
                    return;
                }
                case 2 -> {
                    alert.setHeaderText("Remarquer");
                    alert.setContentText("Aucun rendez-vous dans les 15 minutes.");
                    alert.showAndWait();
                    return;
                }
            }

        }
    }
    
    
    /**
     * Appointment verification alerts English.
     * @param alertID 
     */
    public static void verifyAlertEN(int alertID) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        switch (alertID) {
            case 1 -> {
                alert.setHeaderText("Error!");
                alert.setContentText("Chosen appointment time is out of business hours. "
                        + "Please choose a time within the business hours of 8:00 AM to 10:00 AM EST.");
                alert.showAndWait();
                return;
            }
            case 2 -> {
                alert.setHeaderText("Error!");
                alert.setContentText("The selected appointment time overlaps with an existing appointment. "
                        + "Please try again.");
                alert.showAndWait();
                return;
            }

        }

    }


    /**
     * Alerts associated to creating/modifying appointments.
     * @param alertID
     * @return 
     */
    public static boolean apptAlertEN(int alertID) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        switch (alertID) {
            case 1 -> {
                alert.setHeaderText("Error!");
                alert.setContentText("No appointment selected! Please select an appointment.");
                alert.showAndWait();

            }
            case 2 -> {
                alert.setHeaderText("Error!");
                alert.setContentText("The selected times overlap with an existing appointment! "
                        + "Please select another time.");
                alert.showAndWait();

            }
            case 3 -> {
                alert.setHeaderText("Error!");
                alert.setContentText("Proposed appointment day/time falls out of business hours. Please "
                        + "select a time between 8AM - 10PM EST Monday-Friday.");
                alert.showAndWait();

            }
            case 4 -> {
                alert.setHeaderText("Error!");
                alert.setContentText("One or more fields have been left blank. Please fill them and try again.");
                alert.showAndWait();
            }
            case 5 -> {
                alert.setHeaderText("Error!");
                alert.setContentText("End date or time must be before the start date or time of the appointment.");
                alert.showAndWait();
            }
            
            case 6 -> {
                alert.setHeaderText("Error!");
                alert.setContentText("No longer used");
                alert.showAndWait();
            }
            case 7 -> {
                alert.setHeaderText("Error!");
                alert.setContentText("Please pick an appointment date and time in the future.");
                alert.showAndWait();
            }
            case 8 -> {
                alert.setHeaderText("Error!");
                alert.setContentText("Appointments must start and end on the same date.");
                alert.showAndWait();
            }

        }
        return false;
    }
    
    /**
     * Alerts associated to creating/modifying customers.
     * @param alertID
     * @return 
     */
    public static boolean custAlertEN (int alertID) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error!");
        switch (alertID) {
            case 1 -> {
                alert.setHeaderText("Error!");
                alert.setContentText("No customer selected! Please select an customer.");
                alert.showAndWait();

            }
            
            case 2 -> {
                alert.setHeaderText("Error!");
                alert.setContentText("Error! Customers with existing appointments can not be deleted.");
                alert.showAndWait();

            }
            
            case 3 -> {
                alert.setHeaderText("Error!");
                alert.setContentText("Error! Unable to delete customer.");
                alert.showAndWait();

            }
        }
        
        return false;
    }
    
    
}
