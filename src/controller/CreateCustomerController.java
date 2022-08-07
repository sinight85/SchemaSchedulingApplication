/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import Database.CountryDBQuery;
import Database.CustomerDBQuery;
import Database.DivisionDBQuery;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Countries;
import model.Divisions;
import static helper.Alerts.apptAlertEN;


/**
 * FXML Controller class
 *
 * @author Shannon
 */
public class CreateCustomerController implements Initializable {
    
    Stage stage;
    Parent scene;


    @FXML
    private Label CustomerIDLabel;

    @FXML
    private Label NameLabel;

    @FXML
    private Label Address1Label;

    @FXML
    private Label DivisionLabel;

    @FXML
    private Label PostalCodeLabel;

    @FXML
    private Label CountryLabel;

    @FXML
    private Label PhoneNumberLabel;

    @FXML
    private TextField customerIDField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;
    
    @FXML
    private TextField postCodeField;

    @FXML
    private ComboBox<Divisions> divisionComboBx;

    @FXML
    private ComboBox<Countries> countryComboBx;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    
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
     * @throws IOException 
     */
    @FXML
    public void onActionSave(ActionEvent event) throws IOException {
        
        verifyCustFields();
        
        String customerName = nameField.getText();
        String address = addressField.getText();
        String postalCode = postCodeField.getText();
        String phoneNumber = phoneNumberField.getText();
        Divisions division = divisionComboBx.getValue();
        int divisionID = division.getDivisionID();

        try {
        
            CustomerDBQuery.insertCustomer(customerName, address, postalCode, phoneNumber, divisionID);
        
            } catch (Exception ex) {
                ex.printStackTrace();       
            }
        
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Main");
        stage.show();
    }
    
    /**
     * Verifies fields have user input and throws an error if empty.
     */
    public void verifyCustFields(){
        
        String nameF = nameField.getText();
        String addressF = addressField.getText();
        String postCodeF = postCodeField.getText();
        String phoneF = phoneNumberField.getText();

        if (nameF.isEmpty() || addressF.isEmpty() || phoneF.isEmpty() || divisionComboBx.getSelectionModel().isEmpty()
                || countryComboBx.getSelectionModel().isEmpty() || postCodeF.isEmpty()){

            apptAlertEN(4);

        }

    }

    /**
     * Retrieves country data from the database and sets items the division combo box based on user country selection. 
     * @param event
     * @throws SQLException 
     */
    @FXML
    void onActionCountryComboBx(ActionEvent event) throws SQLException {

        int countryID = countryComboBx.getValue().getCountryID();
        
        try {
            
            divisionComboBx.setItems(DivisionDBQuery.getDivisionsByCountryID(countryID));
        
        } catch (SQLException ex) {
           
            ex.printStackTrace();
       
        }
    }

    /**
     * 
     * @param event 
     */
    @FXML
    void onActionDivisionComboBx(ActionEvent event) throws SQLException {

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
     * Initializes the controller class and sets the Country Combo box.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            countryComboBx.setItems(CountryDBQuery.getAllCountries());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


}