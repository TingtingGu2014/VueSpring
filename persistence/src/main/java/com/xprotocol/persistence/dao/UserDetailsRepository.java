package com.xprotocol.persistence.dao;

import com.xprotocol.persistence.model.UserDetails;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zhao0677
 */
@Repository
public class UserDetailsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Transactional
    public int addUserDetails(int userId, String address, String state, String city, String zipcode) {
        
        jdbcTemplate.update("INSERT INTO userDetails(userId, address, state, city, zipcode) VALUES (?,?,?,?,?)",
                new Object[]{userId, address, state, city, zipcode});
        
        return jdbcTemplate.queryForObject(" SELECT last_insert_id()", Integer.class);
    }
    
    @Transactional(readOnly=true)
    public UserDetails findUserDetailsByUserId(int userId) {
        return jdbcTemplate.queryForObject(
                "select * from userDetails where userId=? ",
                new Object[]{userId, true}, new UserDetailsRowMapper());
    }
    
    @Transactional
    public void updateUserDetailsByUserDetailsId(int userDetailsId, Map<String, Object> valueMap){
        
        List<Object> paramList = new ArrayList<>();
        String sql = "UPDATE userDetails SET ";
        
        for(String key : valueMap.keySet()){
            sql += key + "=?,";
            paramList.add(valueMap.get(key));
        }
        sql = sql.substring(0, sql.length()-1);
        sql += " WHERE userDetailsId=?";
        
        paramList.add(userDetailsId);
        Object[] params = paramList.toArray(new Object[paramList.size()]);
        jdbcTemplate.update(sql, params);
    }
}
