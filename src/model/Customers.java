/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Shannon
 */
public class Customers {

    private int customerID;
    private String customerName;
    private String address;
    private String division;
    private String postalCode;
    private String country;
    private String phoneNumber;
    private int divisionID;

    /**
     * Customers Constructor.
     * @param customerID
     * @param customerName
     * @param address
     * @param division
     * @param postalCode
     * @param country
     * @param phoneNumber
     * @param divisionID 
     */
    public Customers(int customerID, String customerName, String address, String division, String postalCode, String country, String phoneNumber, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.division = division;
        this.postalCode = postalCode;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
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
     * Customer name getter.
     * @return 
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Customer name setter.
     * @param customerName 
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Address getter.
     * @return 
     */
    public String getAddress() {
        return address;
    }

    /**
     * Address setter.
     * @param address 
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Division getter.
     * @return 
     */
    public String getDivision() {
        return division;
    }

    /**
     * Division setter.
     * @param division 
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Post code getter.
     * @return 
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Post code setter.
     * @param postalCode 
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Country getter.
     * @return 
     */
    public String getCountry() {
        return country;
    }

    /**
     * Country setter.
     * @param country 
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Phone number getter.
     * @return 
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Phone number setter.
     * @param phoneNumber 
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * DivisionID getter.
     * @return 
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * DivisionID setter.
     * @param divisionID 
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
    
    /**
     * Overrides default format to user friendly data.
     * @return 
     */
    @Override
    public String toString() {
        return (customerName);
    }
    

}