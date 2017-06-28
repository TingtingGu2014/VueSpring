/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.cassandra.model;

import com.datastax.driver.core.utils.UUIDs;
import java.util.UUID;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

/**
 *
 * @author Tao Zhao
 */
@Table(value = "user_details")
public class UserDetails {
    
    @PrimaryKeyColumn (name="user_details_id", type = PrimaryKeyType.PARTITIONED)
    private UUID userDetailsId = UUIDs.timeBased();
    
    @Column(value = "user_id")
    private int userId;
    
    @Column(value = "address")
    private String address;
    
    @Column(value = "city")
    private String city;
    
    @Column(value = "state")
    private String state;
    
    @Column(value = "zipcode")
    private String zipcode;

    public UserDetails() {
    }

    public UserDetails(UUID userDetailUuid, int userId, String address, String city, String state, String zipcode) {
        this.userDetailsId = userDetailUuid;
        this.userId = userId;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }
    
    public UUID getUserDetailsId() {
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

    public void setUserDetailsId(UUID userDetailsId) {
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
