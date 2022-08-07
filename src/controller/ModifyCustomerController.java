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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Countries;
import model.Customers;
import model.Divisions;

/**
 * FXML Controller class and contains Lambda #2.
 *
 * @author Shannon
 */
public class ModifyCustomerController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    public static int id;

    @FXML
    private Label customerIDLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label divisionLabel;

    @FXML
    private Label postCodeLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private TextField customerIDField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private ComboBox<Divisions> divisionComboBx;
    
    @FXML
    private ComboBox<Countries> countryComboBx;

    @FXML
    private TextField postCodeField;

    @FXML
    private TextField phoneField;

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
     * Lambda expression #2 is used on line 136 to get the divisions for the modify customer screen.
     * @param customer
     * @return
     * @throws SQLException 
     */
    public Customers getCustomer(Customers customer) throws SQLException {
        
        Customers getCustomer = customer;
        Countries country = CountryDBQuery.getCountryByDivisionID(getCustomer.getDivisionID());
        ObservableList<Countries> countries = CountryDBQuery.getAllCountries();
        ObservableList<Divisions> division = DivisionDBQuery.getDivisionsByCountryID(country.getCountryID());

        customerIDField.setText(String.valueOf(getCustomer.getCustomerID()));
        nameField.setText(getCustomer.getCustomerName());
        addressField.setText(getCustomer.getAddress());
        postCodeField.setText(getCustomer.getPostalCode());

        divisionComboBx.setItems(division);
        division.forEach(Divisions -> {
        if(Divisions.getDivisionID() == customer.getDivisionID()) 
            divisionComboBx.setValue(Divisions);
        });

        countryComboBx.setItems(countries);
        countryComboBx.setValue(country);
        phoneField.setText(getCustomer.getPhoneNumber());

        return getCustomer;
    }
    


    /**
     * Fills the Country Combo Box.
     * @param event 
     */
    @FXML
    public void onActionCountryComboBx(ActionEvent event) {
        int countryID = countryComboBx.getValue().getCountryID();
        try {
            divisionComboBx.setItems(DivisionDBQuery.getDivisionsByCountryID(countryID));
        } catch (SQLException ex) {
            System.out.println("Error at Select Country Action! " + ex.getMessage());
        }
    }

    /**
     * 
     * @param event 
     */
    @FXML
    public void onActionDivisionComboBx(ActionEvent event) {


    }

    /**
     * Saves modification of existing customer.
     * @param event
     * @throws IOException 
     */
    @FXML
    public void onActionSave(ActionEvent event) throws IOException {
        
        boolean isEmpty = isFieldsEmpty();
        
        int customerID = Integer.parseInt(customerIDField.getText());
        String customerName = nameField.getText();
        String address = addressField.getText();
        String postalCode = postCodeField.getText();
        String phoneNumber = phoneField.getText();
        Divisions division = divisionComboBx.getValue();
        int divisionID = division.getDivisionID();

        if (isEmpty) {
            try {
                
                CustomerDBQuery.updateCustomer(customerName, address, postalCode, phoneNumber, divisionID, customerID);
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Main Menu");
                stage.show();
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    public boolean isFieldsEmpty () {
        
        if (nameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Name is required.");
            alert.showAndWait();
            return false; 
        }
            if( addressField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Address is required.");
            alert.showAndWait();
            return false; 
            }
            if ( postCodeField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Postcode is required.");
            alert.showAndWait();
            return false; 
            }
            if(phoneField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Phone is required.");
            alert.showAndWait();
            return false; 
            }
            if(countryComboBx == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Country is required.");
            alert.showAndWait();
            return false; 
            }
            if (divisionComboBx == null) {
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Division is required.");
            alert.showAndWait();
            return false; 
            }
            
            return true;

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
                        Scene scene = new Scene(main);

                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);

                        stage.show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }));
        
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }

}