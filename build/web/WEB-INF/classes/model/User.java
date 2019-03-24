/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dbUtil.UserDB;
import java.io.Serializable;

/**
 *
 * @author Jachin
 */
public class User implements Serializable{
 
 private int userID;
 private String username="";
 private String password=""; 
 private String securityQ1="";
 private String salt="";
 private String firstName="";
 private String lastName="";
 private String email="";
 private String addressField1="";
 private String addressField2="";
 private String city="";
 private String state;
 private String zipCode="";
 private String country="";
 private boolean admin=false;
 

 
    
 

    public User() {
    }
    
     public User(int userID, String firstName, String lastName, String email,
            String address1, String address2, String city, String state,
            String zipcode, String country, String username, String password, String salt, String securityQ1, boolean admin) {

        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.addressField1 = address1;
        this.addressField2 = address2;
        this.city = city;
        this.state = state;
        this.zipCode = zipcode;
        this.country = country;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.securityQ1= securityQ1;
        this.admin = admin;

    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressField1() {
        return addressField1;
    }

    public void setAddressField1(String addressField1) {
        this.addressField1 = addressField1;
    }

    public String getAddressField2() {
        return addressField2;
    }

    public void setAddressField2(String addressField2) {
        this.addressField2 = addressField2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSecurityQ1() {
        return securityQ1;
    }

    public void setSecurityQ1(String securityQ1) {
        this.securityQ1 = securityQ1;
    }

    public boolean getIsAdmin() {
        return UserDB.checkAdmin(this.userID);
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }

  



 
 
    
}
