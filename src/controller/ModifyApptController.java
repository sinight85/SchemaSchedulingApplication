/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import Database.AppointmentDBQuery;
import Database.ContactDBQuery;
import Database.CustomerDBQuery;
import Database.UserDBQuery;
import static helper.Alerts.apptAlertEN;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class and contains Lambdas #3 and 4.
 *
 * @author Shannon
 */
public class ModifyApptController implements Initializable {
       
    Stage stage;
    Parent scene;

    public Customers selectedCustomer;
    public static int apptID;
    private ZoneId utcTimeZone = ZoneId.of("UTC");


    @FXML
    private Label AppointmentIDLabel;

    @FXML
    private Label UserIDLabel;

    @FXML
    private Label CustomerIDLabel;

    @FXML
    private Label TitleLabel;

    @FXML
    private Label DescriptionLabel;

    @FXML
    private Label LocationLabel;

    @FXML
    private Label ContactLabel;

    @FXML
    private Label TypeLabel;

    @FXML
    private Label StartDateLabel;

    @FXML
    private Label StartTimeLabel;

    @FXML
    private Label EndDateLabel;

    @FXML
    private Label EndTime;

    @FXML
    private TextField apptIDField;

    @FXML
    private ComboBox<Integer> userComboBx;

    @FXML
    private ComboBox<Integer> customerComboBx;

    @FXML
    private TextField titleField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField locationField;
   
    @FXML
    private TextField typeField;

    @FXML
    private ComboBox<String> contactComboBx;

    @FXML
    private DatePicker startDateSelection;

    @FXML
    private ComboBox<String> startTimeComboBx;

    @FXML
    private DatePicker endDateSelection;

    @FXML
    private ComboBox<String> endTimeComboBx;

    @FXML
    private Button modApptSaveBtn;

    @FXML
    private Button apptCancelBtn;

    private ObservableList<Customers> setCustomers;

    //private Appointments getAppointment;
   

    private ZonedDateTime timeToEST(LocalDateTime time) {
        return ZonedDateTime.of(time,ZoneId.of("America/New_York"));
    }
   
    private ZoneId zoneIDLocal = ZoneId.of(TimeZone.getDefault().getID());
   
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
     * Retrieves appointment information and populates the fields.
     * <P> LAMBDA expression #3 on line 191 populates the Contact combo box with the names
     * associated to the contact ID. </P>
     * @param appointment the appointment
     */
    public void setApptFields(Appointments appointment) {
       
        // Fills the contact name combo box.
                Appointments setAppointment = appointment;
       
                ObservableList<Contacts> contactList = ContactDBQuery.getAllContacts();
                ObservableList<String> contactNames = FXCollections.observableArrayList();
                String showContactName = "";
       
                contactList.forEach(contacts -> contactNames.add(contacts.getContactName()));
                contactComboBx.setItems(contactNames);

                for (Contacts contact: contactList) {
                    if (setAppointment.getContactID() == contact.getContactID()) {
                        showContactName = contact.getContactName();
                    }
                }

        try {

            String startZoneID = setAppointment.getStart().atZone(zoneIDLocal).format(DateTimeFormatter.ofPattern("HH:mm"));
            String endZoneID = setAppointment.getEnd().atZone(zoneIDLocal).format(DateTimeFormatter.ofPattern("HH:mm"));

            apptIDField.setText(String.valueOf(setAppointment.getAppointmentID()));
            userComboBx.setValue(setAppointment.getUserID());
            customerComboBx.setValue(setAppointment.getCustomerID());
            titleField.setText(setAppointment.getTitle());
            descriptionField.setText(String.valueOf(setAppointment.getDescription()));
            locationField.setText(setAppointment.getLocation());
            contactComboBx.setValue(showContactName);
            typeField.setText(setAppointment.getType());
            startDateSelection.setValue(setAppointment.getStart().toLocalDate());
            startTimeComboBx.getSelectionModel().select(String.valueOf(startZoneID));
            endDateSelection.setValue(setAppointment.getEnd().toLocalDate());
            endTimeComboBx.getSelectionModel().select(String.valueOf(endZoneID));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Saves user input to database and takes the user back to the home screen.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    public void onActionSave(ActionEvent event) throws SQLException, IOException {
       
        int contactID = Integer.parseInt(ContactDBQuery.nameToContactID(contactComboBx.getValue()));
       
        boolean verifiedFields = verifyAppt();
       
            LocalDateTime apptStart = LocalDateTime.of(startDateSelection.getValue(), LocalTime.parse(startTimeComboBx.getSelectionModel().getSelectedItem()));
            LocalDateTime apptEnd = LocalDateTime.of(endDateSelection.getValue(), LocalTime.parse(endTimeComboBx.getSelectionModel().getSelectedItem()));
            ZonedDateTime startTimeDateUTC = apptStart.atZone(utcTimeZone).withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endTimeDateUTC = apptEnd.atZone(utcTimeZone).withZoneSameInstant(ZoneId.of("UTC"));
            Timestamp startTS = Timestamp.valueOf(startTimeDateUTC.toLocalDateTime());
            Timestamp endTS = Timestamp.valueOf(endTimeDateUTC.toLocalDateTime());


            if (verifiedFields) {
                try {

                    AppointmentDBQuery.updateAppts(titleField.getText(),
                            descriptionField.getText(),
                            locationField.getText(),
                            typeField.getText(),
                            startTS,
                            endTS,
                            userComboBx.getValue(),
                            customerComboBx.getValue(),
                            contactID,
                            Integer.parseInt(apptIDField.getText()));
                   
                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.setTitle("Main Menu");
                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

            } else {
                System.out.println("Error updating appointment!");
               
            }
   
    }

    /**
     * Checks for overlap of times and with other appointments.
     * @return
     * @throws SQLException
     */
    public boolean verifyAppt() throws SQLException {

            LocalDateTime proposedApptStart = LocalDateTime.of(startDateSelection.getValue(), LocalTime.parse(startTimeComboBx.getSelectionModel().getSelectedItem()));
            LocalDateTime proposedApptEnd = LocalDateTime.of(endDateSelection.getValue(), LocalTime.parse(endTimeComboBx.getSelectionModel().getSelectedItem()));
            ZonedDateTime startTimeDateEST = proposedApptStart.atZone(utcTimeZone).withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endTimeDateEST = proposedApptEnd.atZone(utcTimeZone).withZoneSameInstant(ZoneId.of("UTC"));
            Timestamp proposedApptStartTS = Timestamp.valueOf(startTimeDateEST.toLocalDateTime());
            Timestamp proposedApptEndTS = Timestamp.valueOf(endTimeDateEST.toLocalDateTime());

            int importedCustID = customerComboBx.getSelectionModel().getSelectedItem();
            int importedApptID = Integer.parseInt(apptIDField.getText());

            // check for empty fields
            if (titleField.getText().isEmpty() || descriptionField.getText().isEmpty() ||
                        locationField.getText().isEmpty() || typeField.getText().isEmpty() || startDateSelection.getValue() == null ||
                        endDateSelection.getValue() == null || startTimeComboBx.getValue().equals(null) || endTimeComboBx.getValue().equals(null)
                        || customerComboBx.getValue().equals(null) || userComboBx.getValue().equals(null) ||
                        contactComboBx.getValue().equals(null)) {
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

                            if ((importedCustID == appts.getCustomerID()) && (importedApptID != appts.getAppointmentID()) &&
                                    (proposedApptStartTS.after(existingApptStTS)) && proposedApptStartTS.before(exisitingApptEndTS)) {
                                   apptAlertEN(2);
                                   return false;
                            } if ((importedCustID == appts.getCustomerID()) && (importedApptID != appts.getAppointmentID()) &&
                                   (proposedApptStartTS.before(existingApptStTS)) && proposedApptEndTS.after(existingApptStTS))  {
                                   apptAlertEN(2);
                                   return false;
                            } if ((importedCustID == appts.getCustomerID()) && (importedApptID != appts.getAppointmentID()) &&
                                   (proposedApptEndTS.after(existingApptStTS)) && proposedApptEndTS.before(exisitingApptEndTS))  {
                                   apptAlertEN(2);
                                   return false;
                            } if ((importedCustID == appts.getCustomerID()) && (importedApptID != appts.getAppointmentID()) &&
                                   (proposedApptStartTS.equals(exisitingApptEndTS) || proposedApptEndTS.equals(exisitingApptEndTS)))  {
                                   apptAlertEN(2);
                                   return false;
                            } else if ((importedCustID == appts.getCustomerID()) && (importedApptID != appts.getAppointmentID()) &&
                                   (proposedApptStartTS.equals(existingApptStTS)) || proposedApptEndTS.equals(existingApptStTS)) {
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
     * sets the user id combo box
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
            ex.printStackTrace();
        }
        userComboBx.setItems(userIDCombo);
    }

    /**
     * sets the customer id combo box
     */
    public void fillCustomerIDBx() {
        ObservableList<Integer> customerIDCombo = FXCollections.observableArrayList();

        try {
            ObservableList<Customers> selectCustomer = CustomerDBQuery.getAllCustomers();
            if (selectCustomer != null) {
                for (Customers cust : selectCustomer) {
                    customerIDCombo.add(cust.getCustomerID());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        customerComboBx.setItems(customerIDCombo);
    }
   

    @FXML
    void onActionSelectStartDate(ActionEvent event) {

    }

    @FXML
    void onActionSelectEndDate(ActionEvent event) {

    }

    /**
     * Takes the user back to the main screen.
     * <P> LAMBDA expression #4 on line 448 is used to alert the user, confirm, and take the user back to the main screen. It was used
     * to practice a different way to practice button response while switching screens.</P>
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
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        try {
            setCustomers = CustomerDBQuery.getAllCustomers();
        } catch (SQLException ex) {
            Logger.getLogger(ModifyApptController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        fillUserIDBx();
        fillCustomerIDBx();
       
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
