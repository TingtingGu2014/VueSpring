/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.persistence.model;

import org.springframework.stereotype.Component;

/**
 *
 * @author zhao0677
 */
@Component
public class UserDetails {
    
    private int userDetailsId;
    private int userId;
    private String address;
    private String city;
    private String state;
    private String zipcode;

    public UserDetails() {
    }

    public UserDetails(int userDetailsId, int userId, String address, String city, String state, String zipcode) {
        this.userDetailsId = userDetailsId;
        this.userId = userId;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }
    
    public int getUserDetailsId() {
        return userDetailsId;
    }

    public int getUserId() {
        return userId;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setUserDetailsId(int userDetailsId) {
        this.userDetailsId = userDetailsId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }    
    
    public String toString() {
        return "User Details{" +
                "userDetailsId='" + userDetailsId + '\'' +
                ", userId='" + userId + '\'' +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}
