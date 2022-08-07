/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import Database.JDBC;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author Shannon
 */
public class Main extends Application{

    /**
     * Database connection and disconnection and the first method to be called.
     * @param args
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        JDBC.openConnection();
        System.out.println("Connected to database");
        
        launch(args);
        
        JDBC.closeConnection();
        System.out.println("Closed connection.");
        
    }

    /**
     * Sets the first page the user will see upon starting up the program.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
    
}