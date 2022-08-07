/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Shannon
 */
public class Contacts {

    private int contactID;
    public String contactName;
    private String contactEmail;

    /**
     * Contacts Constructor.
     * @param contactID
     * @param contactName
     * @param contactEmail 
     */
    public Contacts(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
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
     * Contact name getter.
     * @return 
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Contact name setter.
     * @param contactName 
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Contact email getter.
     * @return 
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Contact email setter.
     * @param contactEmail 
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

}