/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.persistence.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tao Zhao
 */

@Repository
public class UserRolesRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Transactional(readOnly = true)
    public List<Integer> findRolesByUserId(int userId){
        return jdbcTemplate.queryForList("SELECT roleId FROM userRoles WHERE userId=? ", new Object[]{userId}, Integer.class);
    }
    
    @Transactional(readOnly = true)
    public List<Integer> findRolesByRoleId(int roleId){
        return jdbcTemplate.queryForList("SELECT userId FROM userRoles WHERE roleId=? ", new Object[]{roleId}, Integer.class);
    }
    
    @Transactional
    public int addUserRolesByUserIdRoleId(int userId, int roleId){
        jdbcTemplate.update("INSERT INTO userRoles (userId, roleId) VALUES (?,?)", new Object[]{userId, roleId});
        
        return jdbcTemplate.queryForObject(" SELECT last_insert_id()", Integer.class);
    }
    
    @Transactional
    public void deleteUserRolesByUserIdRoleId(int userId, int roleId){
        jdbcTemplate.update("DELETE FROM userRoles WHERE userId = ? AND roleId = ? ", new Object[]{userId, roleId});
    }
    
    @Transactional
    public void deleteUserRolesByUserId(int userId){
        jdbcTemplate.update("DELETE FROM userRoles WHERE userId = ? ", new Object[]{userId});
    }
}
