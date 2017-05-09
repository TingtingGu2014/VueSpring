package com.xprotocol.persistence.dao;

import com.xprotocol.persistence.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhao0677 on 4/26/17.
 */

@Repository
public class RoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<Role> findAll() {

        List<Role> result = jdbcTemplate.query("Select roleId, roleName FROM roles ",
                (rs, rowNum) -> new Role(rs.getInt("roleId"),rs.getString("roleName"))
        );
        return result;

    }

    @Transactional
    public int addRole(String roleName) {
        jdbcTemplate.update("INSERT INTO roles(roleName) VALUES (?)", new Object[]{roleName});
        return jdbcTemplate.queryForObject(" SELECT last_insert_id()", Integer.class);
    }

    @Transactional(readOnly=true)
    public Role findRoleById(int roleId) {
        Role role = null;
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from roles where roleId=? ", new Object[]{roleId}, Integer.class);
        while(rs.next()){
            role = new Role(rs.getInt("roleId"), rs.getString("roleName"));
        }
        return role;
    }

    @Transactional(readOnly=true)
    public Role findRoleByName(String roleName) {
        Role role = null;
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from roles where roleName=? ", new Object[]{roleName}, String.class);
        while(rs.next()){
            role = new Role(rs.getInt("roleId"), rs.getString("roleName"));
        }
        return role;
    }

    @Transactional
    public void deleteRoleById(int roleId){
        jdbcTemplate.queryForObject(" DELETE FROM roles WHERE roleId = ?", new Object[]{roleId}, Integer.class);
    }
    
    @Transactional
    public void updateRoleById(int roleId, String newName){
        jdbcTemplate.update(" UPDATE roles SET roleName = ? WHERE roleid = ? ", new Object[]{newName, roleId});
    }
    
    @Transactional
    public void updateRoleByName(String oldName, String newName){
        jdbcTemplate.update(" UPDATE roles SET roleName = ? WHERE roleid = ? ", new Object[]{newName, oldName});
    }
}
