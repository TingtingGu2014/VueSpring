/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.cassandra.model;

import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

/**
 *
 * @author Tao Zhao
 */
@Table
public class UserProfile {
    @PrimaryKeyColumn
    int userId;
    String address;
    String city;
    String state;
    String zipcode;
}
