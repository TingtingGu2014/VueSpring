/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.persistence.dao;

import com.xprotocol.persistence.model.UserDetails;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author zhao0677
 */
public class UserDetailsRowMapper implements RowMapper<UserDetails>
{
    @Override
    public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserDetailsId(rs.getInt("userDetailsId"));
        userDetails.setUserId(rs.getInt("userId"));
        userDetails.setMajor(rs.getString("major"));
        userDetails.setAffiliation(rs.getString("affiliation"));
        userDetails.setAddress(rs.getString("address"));
        userDetails.setCity(rs.getString("city"));
        userDetails.setState(rs.getString("state"));
        userDetails.setZipcode(rs.getString("zipcode"));
        return userDetails;
    }
}