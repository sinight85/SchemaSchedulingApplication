/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import Database.AppointmentDBQuery;
import Database.ContactDBQuery;
import Database.CustomerDBQuery;
import Database.UserDBQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;
import static helper.Alerts.apptAlertEN;


/**
 * FXML Controller class and contains Lambda #6.
 *
 * @author Shannon
 */
public class CreateApptController implements Initializable {
   
    Stage stage;
    Parent scene;
   
    public Customers selectedCustomer;
    public static int apptID;
    private ZoneId utcTimeZone = ZoneId.of("UTC");

    @FXML
    private Button createApptSaveBtn;

    @FXML
    private Button apptCancelBtn;

    @FXML
    private TextField apptIDField;
   
    @FXML
    private TextField titleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField typeField;

    @FXML
    private DatePicker startDateSelection;

    @FXML
    private DatePicker endDateSelection;
   
    @FXML
    private ComboBox<String> startTimeComboBx;

    @FXML
    private ComboBox<String> endTimeComboBx;
   
    @FXML
    private ComboBox<Integer> userComboBx;

    @FXML
    private ComboBox<Integer> customerComboBx;

    @FXML
    private ComboBox<String> contactComboBx;
   
    private ZonedDateTime timeToEST(LocalDateTime time) {
        return ZonedDateTime.of(time,ZoneId.of("America/New_York"));
    }

   
    /**
     * Clickable logo on every page that returns the user to the main screen.
     * @param event
     * @throws IOException
     */
    @FXML
    public void onActionHomeBtn(ActionEvent event) throws IOException {
       
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to "
                + "return home?");
       
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)          
        {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.setTitle("Main_Menu");
            stage.show();
        }
    }

   
       
    /**
     * Takes the user input and puts the values/text in the SQL insert query, adding it to the database
     * then returns user to the main screen.
     * @param event
     * @throws SQLException
     * @throws IOException
     * @throws NullPointerException
     */
    @FXML
    public void onActionAddAppointment(ActionEvent event) throws SQLException, IOException, NullPointerException {
       
        int contactID = Integer.parseInt(ContactDBQuery.nameToContactID(contactComboBx.getValue()));
       
        boolean verifiedFields = verifyAppt();
       
            int userID = userComboBx.getValue();
            int customerID = customerComboBx.getValue();
            String title = titleField.getText();
            String description = descriptionField.getText();
            String location = locationField.getText();
            //String contactName = contactComboBx.getValue();
            String type = typeField.getText();
            LocalDateTime apptStart = LocalDateTime.of(startDateSelection.getValue(), LocalTime.parse(startTimeComboBx.getSelectionModel().getSelectedItem()));
            LocalDateTime apptEnd = LocalDateTime.of(endDateSelection.getValue(), LocalTime.parse(endTimeComboBx.getSelectionModel().getSelectedItem()));
            ZonedDateTime startTimeDateUTC = apptStart.atZone(utcTimeZone).withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endTimeDateUTC = apptEnd.atZone(utcTimeZone).withZoneSameInstant(ZoneId.of("UTC"));
            Timestamp startTS = Timestamp.valueOf(startTimeDateUTC.toLocalDateTime());
            Timestamp endTS = Timestamp.valueOf(endTimeDateUTC.toLocalDateTime());

            if (verifiedFields) {
                try {

                    AppointmentDBQuery.insertAppts(title, description, location, type, startTS, endTS, userID, customerID,  contactID);

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.setTitle("Main");
                    stage.show();

            } catch (IOException ex) {
                ex.printStackTrace();
                }
        } else {
               
                System.out.println("Error saving appointment!");
               
            }
    }
   
   
   
    /**
     * Checks for overlap of times and with other appointments.
     * @throws SQLException
     */
    public boolean verifyAppt() throws SQLException {

            LocalDateTime proposedApptStart = LocalDateTime.of(startDateSelection.getValue(), LocalTime.parse(startTimeComboBx.getValue()));
            LocalDateTime proposedApptEnd = LocalDateTime.of(endDateSelection.getValue(), LocalTime.parse(endTimeComboBx.getValue()));
            ZonedDateTime startTimeDateUTC = proposedApptStart.atZone(utcTimeZone).withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endTimeDateUTC = proposedApptEnd.atZone(utcTimeZone).withZoneSameInstant(ZoneId.of("UTC"));
            Timestamp proposedApptStartTS = Timestamp.valueOf(startTimeDateUTC.toLocalDateTime());
            Timestamp proposedApptEndTS = Timestamp.valueOf(endTimeDateUTC.toLocalDateTime());
           
            // check for empty fields
            if (titleField.getText().isEmpty() || descriptionField.getText().isEmpty() ||
                        locationField.getText().isEmpty() || typeField.getText().isEmpty() || startDateSelection.getValue() == null ||
                        endDateSelection.getValue() == null || startTimeComboBx.getSelectionModel().isEmpty() || endTimeComboBx.getSelectionModel().isEmpty()
                        || customerComboBx.getSelectionModel().isEmpty() || userComboBx.getSelectionModel().isEmpty() ||
                        contactComboBx.getSelectionModel().isEmpty()) {
                    apptAlertEN(4);
                    return false;
            }
                LocalDate startDate = startDateSelection.getValue();
                LocalDate endDate = endDateSelection.getValue();
               
                // Verifies that appointment start dates/time are before end dates/time and falls on the same day
                if(startDate.isAfter(endDate) || endDate.isAfter(startDate)) {
                            apptAlertEN(8);  
                            return false;
                           
                } if(proposedApptEndTS.before(proposedApptStartTS) || proposedApptStartTS.after(proposedApptEndTS)) {
                            apptAlertEN(5);
                            return false;
                }
               
                // verify appt is within business hours
                ZonedDateTime startDT = ZonedDateTime.of(proposedApptStart, ZoneId.systemDefault());
                ZonedDateTime endDT = ZonedDateTime.of(proposedApptEnd, ZoneId.systemDefault());

                ZonedDateTime startToEST = startDT.withZoneSameInstant(ZoneId.of("America/New_York"));
                ZonedDateTime endToEST = endDT.withZoneSameInstant(ZoneId.of("America/New_York"));

                if (startToEST.toLocalTime().isAfter(LocalTime.of(22, 0))) {
                    apptAlertEN(3);
                    return false;
                }
                if (endToEST.toLocalTime().isAfter(LocalTime.of(22, 0))) {
                    apptAlertEN(3);
                    return false;
                }
                if(startToEST.toLocalTime().isBefore(LocalTime.of(8, 0))) {
                    apptAlertEN(3);
                    return false;
                }
                if (endToEST.toLocalTime().isBefore(LocalTime.of(8, 0))) {
                    apptAlertEN(3);
                    return false;
                }
     
                // verify no overlaps with other appointments
                LocalDateTime exisitingApptSt;
                LocalDateTime existingApptEnd;
                Timestamp existingApptStTS;
                Timestamp exisitingApptEndTS;
                               
                try{
                   
                    ObservableList<Appointments> getAllAppointments = AppointmentDBQuery.getAllAppointments();
                   
                    for (Appointments appts : getAllAppointments) {

                        exisitingApptSt = appts.getStart();
                        existingApptEnd = appts.getEnd();
                        existingApptStTS = Timestamp.valueOf(exisitingApptSt);
                        exisitingApptEndTS = Timestamp.valueOf(existingApptEnd);

                     if (proposedApptStartTS.after(existingApptStTS) && proposedApptStartTS.before(exisitingApptEndTS)) {
                            apptAlertEN(2);
                            return false;
                    } if (proposedApptStartTS.before(existingApptStTS) && proposedApptEndTS.after(existingApptStTS))  {
                            apptAlertEN(2);
                            return false;
                    } if (proposedApptEndTS.after(existingApptStTS) && proposedApptEndTS.before(exisitingApptEndTS))  {
                            apptAlertEN(2);
                            return false;
                    } if (proposedApptStartTS.equals(exisitingApptEndTS) || proposedApptEndTS.equals(exisitingApptEndTS))  {
                            apptAlertEN(2);
                            return false;
                    } else if (proposedApptStartTS.equals(existingApptStTS) || proposedApptEndTS.equals(existingApptStTS)) {
                            apptAlertEN(2);
                            return false;
           
                        }
                    }
   
    } catch (Exception ex) {
        ex.printStackTrace();
        }
                return true;
    }

    /**
     * Fills userID combo box.
     */
    public void fillUserIDBx() {
        ObservableList<Integer> userIDCombo = FXCollections.observableArrayList();

        try {
            ObservableList<Users> selectUser = UserDBQuery.getAllUsers();
            if (selectUser != null) {
                for (Users user : selectUser) {
                    userIDCombo.add(user.getUserID());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error populating userID combo box " + ex.getMessage());
        }
        userComboBx.setItems(userIDCombo);
    }

    /**
     * Fills customerID combo box.
     */
    public void fillCustIDBx() {
        ObservableList<Integer> customerIDCombo = FXCollections.observableArrayList();

        try {
            ObservableList<Customers> selectCustomer = CustomerDBQuery.getAllCustomers();
            if (selectCustomer != null) {
                for (Customers cust : selectCustomer) {
                    customerIDCombo.add(cust.getCustomerID());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error populating customerID combo box " + ex.getMessage());
        }
        customerComboBx.setItems(customerIDCombo);
    }

    /**
     * Fills contact name combo box.
     * <P> Lambda expression #6 is used on line 326 as it can efficiently add names to the contactNames array list with minimal code
     * which is then used to fill the combo box with the contact names.</P>
     */
    public void fillContactNameBx() {
       
        ObservableList<Contacts> contactList = ContactDBQuery.getAllContacts();
        ObservableList<String> contactNames = FXCollections.observableArrayList();

        contactList.forEach(Contacts -> contactNames.add(Contacts.getContactName()));
        contactComboBx.setItems(contactNames);

    }

    /**
     * Confirms with the user the intention to cancel and returns the user back to the main screen.
     * @param event
     * @throws IOException
     */
    @FXML
    public void onActionCancelBtn(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel");
            alert.setContentText("Are you sure you want to cancel?");
           
        alert.showAndWait().ifPresent((response -> {
                if (response == ButtonType.OK) {
                    System.out.println("Canceling");
                    Parent main = null;
                    try {
                        main = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                        Scene newScene = new Scene(main);
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(newScene);
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }));
       
    }


    /**
     * Initializes the controller class, fills form Combo Boxes, and sets start/end time.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        fillUserIDBx();
        fillCustIDBx();
        fillContactNameBx();

        ObservableList<String> apptTimes = FXCollections.observableArrayList();
        LocalTime startHours = LocalTime.of(1, 0);
        LocalTime endHours = LocalTime.of(23, 0);
       
        while (startHours.isBefore(endHours)) {
            apptTimes.add(String.valueOf(startHours));
            startHours = startHours.plusMinutes(15);
        }
       
        startTimeComboBx.setItems(apptTimes);
        endTimeComboBx.setItems(apptTimes);

    }


}