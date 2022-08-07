/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Shannon
 */
public class Users {

    private int userID;
    private String username;
    private String password;

    /**
     * Users Constructor.
     * @param userID the userID
     * @param username the username
     * @param password the password
     */
    public Users(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    /**
     * User ID getter.
     *
     * @return the user id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * User ID setter.
     *
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * User getter.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * User setter.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Password getter.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Password setter.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}