/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.persistence.dao;

import com.xprotocol.persistence.model.User;
import com.xprotocol.persistence.model.UserRoles;
import com.xprotocol.utils.UtilsHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author zhao0677
 */
public class UserRolesMapper implements RowMapper<UserRoles>
{
    @Override
    public UserRoles mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        UserRoles userRoles = new UserRoles();
        userRoles.setRoleId(rs.getInt("roleId"));
        userRoles.setUserId(rs.getInt("userId"));
        return userRoles;
    }
}