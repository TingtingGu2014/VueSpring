package com.xprotocol.persistence.dao;

import com.xprotocol.persistence.model.User;
import com.xprotocol.utils.UtilsHelper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zhao0677 on 4/26/17.
 */

class UserRowMapper implements RowMapper<User>
{
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userId"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        user.setEmail(rs.getString("email"));
        user.setAlias(rs.getString("alias"));
        user.setUserUUID(UtilsHelper.getUUIDFromBytes(rs.getBytes("userUUID")).toString());
        user.setCreatedDate(rs.getDate("createdDate"));
        user.setActive(rs.getBoolean("active"));
        return user;
    }
}