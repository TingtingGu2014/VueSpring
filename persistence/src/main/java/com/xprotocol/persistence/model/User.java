package com.xprotocol.persistence.model;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class User {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String alias;
    private Date createdDate;
    private boolean active;

    public User(){}

    public User(int userId, String firstName, String lastName, String email, String password, String alias, Date createdDate, boolean active) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.alias = alias;
        this.createdDate = createdDate;
        this.active = active;
        this.password = password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAlias() {
        return alias;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", date=" + createdDate.toString() +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
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
    
    public String getPassword() {
        return this.password;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
