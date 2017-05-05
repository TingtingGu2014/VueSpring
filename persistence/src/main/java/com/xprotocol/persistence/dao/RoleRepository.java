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
                (rs, rowNum) -> new Role(rs.getInt("roleId"),
                        rs.getString("roleName"))
        );
        return result;

    }

    @Transactional
    public int addRole(String roleName) {
//        Role role = findRoleByName(roleName);
//        if(null != role){
//            throw new EntityAlreadyExistsDuringInsertionException("The user role with name "+roleName+" has already existed in the database!");
//        }
        jdbcTemplate.update("INSERT INTO roles(roleName) VALUES (?)", roleName);
        return jdbcTemplate.queryForObject(" SELECT last_insert_id()", Integer.class);
    }

    @Transactional(readOnly=true)
    public Role findRoleById(int roleId) {
        Role role = null;
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from roles where roleId=? ", new Object[]{new Integer(roleId)});
        while(rs.next()){
            role = new Role(rs.getInt("roleId"), rs.getString("roleName"));
        }
        return role;
    }

    @Transactional(readOnly=true)
    public Role findRoleByName(String roleName) {
        Role role = null;
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from roles where roleId=? ", new Object[]{new Integer(roleName)});
        while(rs.next()){
            role = new Role(rs.getInt("roleId"), rs.getString("roleName"));
        }
        return role;
    }

    @Transactional
    public void deleteRoleById(int roleId){
        jdbcTemplate.execute(" DELETE FROM roles WHERE roleId = '"+roleId+"'");
    }
}
