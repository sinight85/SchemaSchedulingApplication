/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Shannon
 */
public class Divisions {

    private int divisionID;
    private String division;
    private int countryID;

    /**
     * Division Constructor.
     * @param divisionID
     * @param division
     * @param countryID 
     */
    public Divisions(int divisionID, String division, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.countryID = countryID;
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
     * Overrides default format of data and translates it to a user friendly format.
     * @return 
     */
    @Override
    public String toString() {
        return (division);
    }

}