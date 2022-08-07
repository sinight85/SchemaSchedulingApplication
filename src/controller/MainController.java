/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import Database.AppointmentDBQuery;
import Database.CustomerDBQuery;
import static helper.Alerts.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;

/**
 * FXML Controller class and contains Lambda #5.
 *
 * @author Shannon
 */
public class MainController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    private ResourceBundle resourceBundle;
    public static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

    @FXML
    private Label timeZoneLbl;

    @FXML
    private Button createApptBtn;

    @FXML
    private Button modApptBtn;

    @FXML
    private Button deleteApptBtn;

    @FXML
    private RadioButton apptAllRadio;

    @FXML
    private RadioButton apptWeekRadio;

    @FXML
    private RadioButton apptMonthRadio;

    @FXML
    private ToggleGroup ToggleAppt;

    @FXML
    private DatePicker ViewLocalDate;

    @FXML
    private TableView<Appointments> apptTblView;

    @FXML
    private TableColumn<Appointments, Integer> apptIDCol;

    @FXML
    private TableColumn<Appointments, String> apptTitleCol;

    @FXML
    private TableColumn<Appointments, String> apptDescCol;

    @FXML
    private TableColumn<Appointments, String> apptLocationCol;
    
    @FXML
    private TableColumn<Appointments, String> apptCustomerIDCol;

    @FXML
    private TableColumn<Appointments, Integer> apptContactCol;

    @FXML
    private TableColumn<Appointments, String> apptTypeCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> apptStartCol;

    @FXML
    private TableColumn<Appointments, LocalDateTime> apptEndCol;

    @FXML
    private TableColumn<Appointments, Integer> apptUserIDCol;

    @FXML
    private Button reportBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button createCustBtn;

    @FXML
    private Button modCustBtn;

    @FXML
    private Button deleteCustBtn;

    @FXML
    private TableView<Customers> custTblView;

    @FXML
    private TableColumn<Customers, Integer> custIDCol;

    @FXML
    private TableColumn<Customers, String> custNameCol;

    @FXML
    private TableColumn<Customers, String> custAddressCol;

    @FXML
    private TableColumn<Customers, String> custDivisionCol;

    @FXML
    private TableColumn<Customers, String> custPostCodeCol;

    @FXML
    private TableColumn<Customers, String> custCountryCol;

    @FXML
    private TableColumn<Customers, String> custPhoneCol;

    /**
     * Changes table data based on user radio selection. 
     * @param event
     * @throws SQLException 
     */
    @FXML
    public void onActionRadioChg(ActionEvent event) throws SQLException {
        
            if (apptAllRadio.isSelected()) {
                try{
                    ObservableList<Appointments> appointments = AppointmentDBQuery.getAllAppointments();
                    apptTblView.setItems(appointments);
                    apptTblView.refresh();
                } catch (SQLException ex) {
                    System.out.println("Radio Buttong Error: " + ex.getMessage());
                        }
                } else if (apptWeekRadio.isSelected()) {
                    try{
                        ObservableList<Appointments> appointments = AppointmentDBQuery.getAppointmentsByWeek();
                        apptTblView.setItems(appointments);
                        apptTblView.refresh();
                } catch (SQLException ex) {
                    System.out.println("Radio Buttong Error: " + ex.getMessage());
                        }
                } else if (apptMonthRadio.isSelected()) {
                    try{
                        ObservableList<Appointments> appointments = AppointmentDBQuery.getAppointmentsByMonth();
                        apptTblView.setItems(appointments);
                        apptTblView.refresh();
                } catch (SQLException ex) {
                    System.out.println("Radio Buttong Error: " + ex.getMessage());
                        }
                }         
    }
    
    @FXML
    void onActionViewLocalDate(ActionEvent event) {

    }


    /**
     * Takes the user to the Create Appointment screen. 
     * @param event
     * @throws IOException 
     */
    @FXML
    public void onActionAddAppointment(ActionEvent event) throws IOException {
        
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CreateApptScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Create New Appointment");
        stage.show();
    }

    /**
     * Checks that an appointment is selected then takes the user to the Modify Appointment screen. 
     * @param event
     * @throws IOException 
     */
    @FXML
    public void onActionModifyAppointment(ActionEvent event) throws IOException {
        
        Appointments selectedAppts = apptTblView.getSelectionModel().getSelectedItem();
               
        if (selectedAppts == null) {
             apptAlertEN(1);
            
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyApptScreen.fxml"));
            scene = loader.load();
            ModifyApptController controller = loader.getController();
            controller.setApptFields(selectedAppts);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.setTitle("Modify Existing Customer");
            stage.show();
        }
    }
    /**
     * Verifies an appointment has been selected, then deletes selected appointment. 
     * @param event
     * @throws SQLException 
     */
    @FXML
    public void onActionDeleteAppt(ActionEvent event) throws SQLException {
        Appointments selectedAppt = apptTblView.getSelectionModel().getSelectedItem();
        ObservableList<Appointments> appointments = AppointmentDBQuery.getAllAppointments();
                       
        if (selectedAppt == null) {
            apptAlertEN(1);
            
        } else if(selectedAppt != null) {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove the following appointment: \n" + "Appointment_ID: " + apptTblView.getSelectionModel().getSelectedItem().getAppointmentID()
                        + "\nType: " +  apptTblView.getSelectionModel().getSelectedItem().getType() + " \nAre you sure your want to proceed?");
                Optional<ButtonType> confirm = alert.showAndWait();

                if (confirm.isPresent() && (confirm.get() == ButtonType.OK)) {
                    try {
                        boolean deleteSuccessful = AppointmentDBQuery.deleteAppts(apptTblView.getSelectionModel().getSelectedItem().getAppointmentID());
                        if (deleteSuccessful) {
                            appointments = AppointmentDBQuery.getAllAppointments();
                            apptTblView.setItems(appointments);
                            apptTblView.refresh();
                        } else {
                                apptAlertEN(1);

        }
            } catch (SQLException ex) {
                    System.out.println("Error attempting to delete appointment!: " + ex.getMessage());
                }
            }  
        } catch (Exception ex) {
                    ex.printStackTrace();
        } 
    }
    } 
    

    /**
     * Takes the user to the report screen. 
     * @param event
     * @throws IOException 
     */
    @FXML
    public void onActionViewReports(ActionEvent event) throws IOException {
       
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/ReportsScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Reports");
        stage.show();
    }

    /**
     * Logs the user out the application and returns the user to the Login Screen. 
     * @param event
     * @throws IOException 
     */
    @FXML
    public void onActionLogout(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Login");
        stage.show();
    }

    /**
     * Takes the user to the Create Customer screen. 
     * @param event
     * @throws IOException 
     */
    @FXML
    public void onActionAddCustomer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreateCustomerScreen.fxml"));
        scene = loader.load();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CreateCustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Create New Customer");
        stage.show();
    }

    /**
     * Checks that a customer is selected then takes the user to the Modify Customer screen. 
     * @param event
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    public void onActionModifyCustomer(ActionEvent event) throws IOException, SQLException {
        
        Customers selectedCust = custTblView.getSelectionModel().getSelectedItem();
        
        if (selectedCust == null) {
            custAlertEN(1);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyCustomerScreen.fxml"));
            scene = loader.load();
            ModifyCustomerController controller = loader.getController();
            controller.getCustomer(selectedCust);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.setTitle("Modify Existing Customer");
            stage.show();
        }
    
    }
    
    /**
     * Verify function to verify if the customer has existing appointments. 
     * @param selectedCustomer
     * @return 
     */
    private boolean verifyAppts(Customers selectedCustomer) {
        try {
            ObservableList appointments = AppointmentDBQuery.getAppointmentsByCustomerID(selectedCustomer.getCustomerID());
            if (appointments != null) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex){
            System.out.println("Error verifying appointments! " + ex.getMessage());
            return false;
        }
    }


    /**
     * Delete selected existing customer. 
     * @param event
     * @throws SQLException 
     */
    @FXML
    public void onActionCustDeleteBtn(ActionEvent event) throws SQLException {
        Customers selectedCustomer = custTblView.getSelectionModel().getSelectedItem();
        ObservableList<Appointments> appointments = AppointmentDBQuery.getAllAppointments();
        ObservableList<Customers> customers = CustomerDBQuery.getAllCustomers();
               
        if (selectedCustomer == null) {
            custAlertEN(1);     
        }else if (custTblView.getSelectionModel().getSelectedItem() != null) {
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove the selected customer "
                    + "and all associated appointments from the database. Are you sure your want to proceed?");
            Optional<ButtonType> confirm = alert.showAndWait();

            if (confirm.isPresent() && (confirm.get() == ButtonType.OK)) {
                try {
                    boolean verifyCust = verifyAppts(selectedCustomer);
                    if (verifyCust) {
                        boolean deleteSuccessful = CustomerDBQuery.deleteCustomer(custTblView.getSelectionModel().getSelectedItem().getCustomerID());

                        if (deleteSuccessful) {
                            customers = CustomerDBQuery.getAllCustomers();
                            custTblView.setItems(customers);
                            custTblView.refresh();
                            appointments = AppointmentDBQuery.getAllAppointments();
                            apptTblView.setItems(appointments);
                            apptTblView.refresh();
                        } else {
                            custAlertEN(2);
                        }
                    } else {
                        custAlertEN(3);
                    }
                } catch (SQLException ex) {
                    System.out.println("Error attempting to delete customer!: " + ex.getMessage());
                }
            }
        }
    } 


    /**
     * Initializes the Customer and Appointments table views. 
     * <P> LAMBDA #5 expression, located on line 442, is used for a cleaner way to format the start and end time column. After researching
     * and trying several different methods, I found the lambda format to be the simplest and understandable method. </P>
     * @param url
     * @param resources 
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {

        ZoneId zID = ZoneId.systemDefault();
        timeZoneLbl.setText(zID.toString());
        try {

            ObservableList<Appointments> appointments = AppointmentDBQuery.getAllAppointments();

            apptTblView.setItems(appointments);
            apptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            apptDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            apptCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            apptUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
 
            // Formatting the Start and End columns with the use of a Lambda expression.
            apptStartCol.setCellFactory(column -> {
                TableCell<Appointments, LocalDateTime> columnStartTime = new TableCell<Appointments, LocalDateTime>() {
                    private DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

                    @Override
                    protected void updateItem(LocalDateTime appointmentTime, boolean empty) {
                        super.updateItem(appointmentTime, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(formatDateTime.format(appointmentTime));
                        }
                    }
                };
                return columnStartTime;
            }); 

            apptEndCol.setCellFactory(column -> {
                TableCell<Appointments, LocalDateTime> columnEndTime = new TableCell<Appointments, LocalDateTime>() {
                    private DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

                    @Override
                    protected void updateItem(LocalDateTime appointmentTime, boolean empty) {
                        super.updateItem(appointmentTime, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(formatDateTime.format(appointmentTime));
                        }
                    }
                };
                return columnEndTime;
            });

        } catch (SQLException ex) {
            System.out.println("Error formating start and end time." + ex.getMessage());
        }

        try {
            ObservableList<Customers> customers = CustomerDBQuery.getAllCustomers();

            custTblView.setItems(customers);
            custIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            custNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            custDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
            custPostCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            custCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
            custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
                        
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        ViewLocalDate.setValue(LocalDate.now());
    }

   
}