/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import Database.AppointmentDBQuery;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Shannon
 */
public class ReportsController implements Initializable {
    
    Stage stage;
    Parent scene;


    @FXML
    private RadioButton option1Radio;

    @FXML
    private RadioButton option2Radio;

    @FXML
    private RadioButton option3Radio;

    @FXML
    private ToggleGroup viewReportTG;

    @FXML
    private Button GenerateButton;

    @FXML
    private Button ResetButton;

    @FXML
    private Button CancelButton;
    
    @FXML
    private Button clearBtn;

    @FXML
    private TextArea reportsTxtArea;
    
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
     * Generates reports through SQL queries based on user radio button selection.
     * @param event the event
     */
    @FXML
    public void onActionGenerate(ActionEvent event) {
        if (option1Radio.isSelected()) {
            reportsTxtArea.setText(AppointmentDBQuery.apptReportTypeMonth());
        }
        if (option2Radio.isSelected()) {
            reportsTxtArea.setText(AppointmentDBQuery.apptReportByContact());
        }
        if (option3Radio.isSelected()) {
            reportsTxtArea.setText(AppointmentDBQuery.apptReportByLocation());
        }
    }


    @FXML
    void onActionOption1Radio(ActionEvent event) {

    }

    @FXML
    void onActionOption2Radio(ActionEvent event) {

    }

    @FXML
    void onActionOption3Radio(ActionEvent event) {

    }

    /**
     * Clears the report field.
     * @param event the event
     */
    @FXML
    public void onActionClearBtn(ActionEvent event) {
        reportsTxtArea.clear();
    }

    /**
     * Confirms with the user the intention to cancel and returns user to the main screen.
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
        // TODO
    }

}