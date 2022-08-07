/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import Database.AppointmentDBQuery;
import Database.UserDBQuery;
import java.net.URL;
import java.time.ZoneId;
import java.util.*;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Appointments;
interface LogInActivity{
    public String getFileName();
}



/**
 * FXML Controller class and contains Lambda #1.
 *
 * @author Shannon
 */
public class LoginController implements Initializable {
    
    Stage stage;
    Parent scene;

    private ResourceBundle resourceBundle;
    
    @FXML
    private Label userNameLbl;

    @FXML
    private Label pwdLbl;

    @FXML
    private Label localeLbl;
    
    @FXML
    private Label locationLbl;
    
    @FXML
    private Label TitleLabel;
    
    @FXML
    private Label sloganLbl;
    
    @FXML
    private Label brandLbl;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button submitBtn;

    @FXML
    private Button cancelBtn;
    
    /**
     * Declares the file path that will later be used for the File Writes.
     * <P> Lambda expression #1, on line 89, is used to pass the login activity file name and location to 
     * login success and failure functions. I used this Lambda expression as a introduction way to practice lambda code and 
     * understand how Lambda's work.</P>
     */
    LogInActivity logInActivity = () -> {
        return "src/main/login_activity.txt";
    };

    /**
     * Retrieves user input then verifies input with the Users database table and allows user login or throws alerts for 
     * incorrect/empty values.
     * @param event
     * @throws SQLException
     * @throws IOException 
     */
    @FXML
    public void onActionLogIn(ActionEvent event) throws SQLException, IOException {
        
        String loginText = userNameField.getText();
        String loginPwd = passwordField.getText();
       
            boolean loginVerify = UserDBQuery.verifyUserInformation(loginText, loginPwd);
            
            if (loginVerify == true) {
                apptAlert();
                fileWriteSuccess();
                try {
                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.setTitle("Main_Menu");
                    stage.show(); 
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }  if (loginText.isEmpty() || loginPwd.isEmpty()) {
                fileWriteFailure();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(resourceBundle.getString("Error"));
                    alert.setContentText(resourceBundle.getString("emptyCredentials"));
                    alert.showAndWait();   

            } else if (loginVerify != true){
                fileWriteFailure();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(resourceBundle.getString("Error"));
                        alert.setContentText(resourceBundle.getString("loginNotValid"));
                        alert.showAndWait(); 
                    
                     
                }
            
    }

    /**
     * Closes the application.
     * @param event 
     */
    @FXML
    public void onActionCancel(ActionEvent event) {
        System.exit(0);
    }


    /**
     * Alerts the user to upcoming appointments or notifies if there are no upcoming appointments.
     * @throws SQLException 
     */
    private void apptAlert() throws SQLException {
        
        ObservableList<Appointments> appointments = AppointmentDBQuery.getAllAppointments();
        LocalDateTime localDateTimeNow = LocalDateTime.now();
        LocalDateTime timeNowPlus15 = localDateTimeNow.plusMinutes(15);
        
        int displayApptID = 0;
        
        LocalDateTime displayApptTime = null;
        boolean apptSoon = false;

        for (Appointments appointment : appointments) {
                    LocalDateTime startTime = appointment.getStart();
    
                 if ((startTime.isAfter(localDateTimeNow) && startTime.isBefore(timeNowPlus15)) || startTime.equals(timeNowPlus15)) {
                        apptSoon = true;
                        displayApptTime = startTime;
                        displayApptID = appointment.getAppointmentID();
                 }
        }
                 if(apptSoon == true) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle(resourceBundle.getString("Warning"));
                        alert.setHeaderText(resourceBundle.getString("appointmentAlert"));
                        alert.setContentText("ID: " + displayApptID + 
                                "  " + displayApptTime.toLocalTime() + "  " + displayApptTime.toLocalDate());
                        alert.setResizable(true);
                        alert.showAndWait();             
                       
            } else if (apptSoon != true) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(resourceBundle.getString("AlertAppt"));
                    alert.setContentText(resourceBundle.getString("no15minAppt"));
                    alert.setResizable(true);
                    alert.showAndWait();
    
                } 
        } 
            
    
    

    /**
     * File writes successful logins and retrieves filename from lambda expression above.
     * @param user
     * @throws IOException 
     */
    private void fileWriteSuccess() throws IOException, SQLException {
       
        try {
            FileWriter fileWriter = new FileWriter(logInActivity.getFileName(), true);
            try (PrintWriter filePrint = new PrintWriter(fileWriter)) {
                LocalDateTime loginTime = LocalDateTime.now();
                DateTimeFormatter logFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String loginFile = logFormatter.format(loginTime);
                
                filePrint.println(userNameField.getText() + " logged in successfully at: " + loginFile);
                System.out.println("Successfully wrote login success to file");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * File writes unsuccessful logins and retrieves filename from lambda expression above.
     * @throws IOException 
     */
    private void fileWriteFailure() throws IOException {
        
        try {
            FileWriter fileWriter = new FileWriter(logInActivity.getFileName(), true);
            try (PrintWriter filePrint = new PrintWriter(fileWriter)) {
                LocalDateTime loginTime = LocalDateTime.now();
                DateTimeFormatter logFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String loginFile = logFormatter.format(loginTime);
                
                filePrint.println("Login failure at: " + loginFile);
                System.out.println("Successfully wrote login failure to file");
            }
            } catch (IOException ex) {
                ex.printStackTrace();
        }
    }


    /**
     * Initializes the controller class.
     *
     * @param url
     * @param resources 
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {       
        // I18N
        
        //Locale.setDefault(new Locale("fr"));
        Locale currentLocale = Locale.getDefault();
        System.out.println("Country: " + currentLocale.getDisplayCountry());
        System.out.println("Language: " + currentLocale.getLanguage());


        resourceBundle =  ResourceBundle.getBundle("Language/Lang", currentLocale);
        ZoneId zoneID = ZoneId.systemDefault();

        try {
            
            TitleLabel.setText(resourceBundle.getString("PageTitle"));
            userNameLbl.setText(resourceBundle.getString("Username"));
            pwdLbl.setText(resourceBundle.getString("Password"));
            localeLbl.setText(zoneID.toString());
            locationLbl.setText(resourceBundle.getString("Location"));
            submitBtn.setText(resourceBundle.getString("Login"));
            cancelBtn.setText(resourceBundle.getString("Cancel"));
            sloganLbl.setText(resourceBundle.getString("Slogan"));
            brandLbl.setText(resourceBundle.getString("Brand"));

    } catch (Exception ex) {
        System.out.println("Missing resource file: " + ex.getMessage());
    } 

}
}