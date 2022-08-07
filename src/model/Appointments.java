/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;


/**
 *
 * @author Shannon
 */
public class Appointments {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * Appointment Constructor
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param contactID
     * @param start
     * @param end
     * @param type
     * @param customerID
     * @param userID 
     */
    public Appointments(int appointmentID, String title, String description, String location, String type, 
            LocalDateTime start, LocalDateTime end, int userID, int customerID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;         
        this.userID = userID;
        this.customerID = customerID;
        this.contactID = contactID;
    }

    /**
     * AppointmentID getter.
     * @return 
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * AppointmentID setter.
     * @param appointmentID 
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Title getter.
     * @return 
     */
    public String getTitle() {
        return title;
    }

    /**
     * Title setter.
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Description getter.
     * @return 
     */
    public String getDescription() {
        return description;
    }

    /**
     * Description setter.
     * @param description 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Location getter.
     * @return 
     */
    public String getLocation() {
        return location;
    }

    /**
     * Location setter.
     * @param location 
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * ContactID getter.
     * @return 
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * ContactID setter.
     * @param contactID 
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Type getter.
     * @return 
     */
    public String getType() {
        return type;
    }

    /**
     * Type setter.
     * @param type 
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * Start getter.
     * @return 
     */
    public LocalDateTime getStart() {
        return start;
    }
    
    /**
     * Start Setter.
     * @param start 
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * End getter.
     * @return 
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * End setter.
     * @param end 
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    
    /**
     * CustomerID getter.
     * @return 
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * CustomerID setter.
     * @param customerID 
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * UserID getter.
     * @return 
     */
    public int getUserID() {
        return userID;
    }

    /**
     * UserID setter.
     * @param userID 
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

}