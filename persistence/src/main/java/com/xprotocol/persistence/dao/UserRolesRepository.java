/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.persistence.dao;

import com.xprotocol.persistence.model.UserRoles;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
    
    @Autowired
    private NamedParameterJdbcTemplate namedTemplate;
    
    @Transactional(readOnly = true)
    public List<String> findRoleNamesByUserId(int userId){
        List<String> result = jdbcTemplate.query("Select roleName FROM userRoles, roles WHERE userRoles.userId=? AND userRoles.roleId=roles.roleId ",
                new Object[]{userId}, (rs, rowNum) -> (rs.getString("roleName"))
        );
        return result;
    }
    
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
    
    @Transactional
    public void updateUserRolesByUserIdAndRoleNames(int userId, String roles){        
        jdbcTemplate.update("DELETE FROM userRoles WHERE userId = ? ", new Object[]{userId});
        String[] rolesArr = roles.split(",");
        if(rolesArr.length > 0){
            String sql = "INSERT INTO userRoles (userId, roleId) " +
                         "SELECT 5 as userId, roleId FROM roles WHERE roles.roleName IN ('admin','regular');";
        }
    }
    
    @Transactional
    public void selectUserRolesByUserIds(List<Integer> userIds){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userIds", userIds);
        
        String query = "SELECT * FROM userRoles WHERE userId IN (:userIds)";
        List<UserRoles> userRoles = namedTemplate.query(query, parameters, new UserRolesMapper());
        if(userRoles != null)
        System.out.println("Got the results!");
    }
    
    @Transactional
    public int addUserRolesByUserIdAndRoleNames(int userId, List<String> roleNames){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("roleNames", roleNames);
        
        String sql = "INSERT INTO userRoles (userId, roleId) " +
                       "SELECT "+userId+" AS userId, roleId " +
                       "FROM roles WHERE roleName IN (:roleNames) ";
        
        return namedTemplate.update(sql, parameters);
    }
    
    @Transactional
    public int updateUserRolesByUserIdAndRoleNames(int userId, List<String> roleNames){
        
        deleteUserRolesByUserId(userId);
        
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("roleNames", roleNames);
        
        String sql = "INSERT INTO userRoles (userId, roleId) " +
                       "SELECT "+userId+" AS userId, roleId " +
                       "FROM roles WHERE roleName IN (:roleNames) ";
        
        return namedTemplate.update(sql, parameters);
    }
}
