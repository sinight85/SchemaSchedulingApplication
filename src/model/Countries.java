/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Shannon
 */
public class Countries {

    private int countryID;
    private String country;

    /**
     * Countries Constructor.
     * @param countryID
     * @param country 
     */
    public Countries(int countryID, String country) {
        this.countryID = countryID;
        this.country = country;
    }

    /**
     * CountryID getter.
     * @return 
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * CountryID setter.
     * @param countryID 
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
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
     * Overrides default format to user friendly data.
     * @return 
     */
    @Override
    public String toString() {
        return (country);
    }

}