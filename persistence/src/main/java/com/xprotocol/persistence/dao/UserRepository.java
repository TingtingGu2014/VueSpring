package com.xprotocol.persistence.dao;

import com.xprotocol.persistence.model.User;
import com.xprotocol.utils.UtilsHelper;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<User> findAll() {
        return jdbcTemplate.query("select * from users",
                new UserRowMapper());
    }

    @Transactional
    public int  addUser(String email, String password) {
        return addUser("", "", email, "", password, true);
    }

    @Transactional
    public int addUser(String email, String alias, String password) {
        return addUser("", "", email, alias, password, true);
    }

    @Transactional
    public int addUser(String firstName, String lastName, String email, String alias, String password) {
        return addUser(firstName, lastName, email, alias, password, true);
    }

    @Transactional
    public int addUser(String firstName, String lastName, String email, String alias, String password, boolean active) {
        
        UUID userUUID = UtilsHelper.getUUIDBasedOnTime();
        byte[] userUUIDArr = UtilsHelper.getBytesFromUUID(userUUID);
        
        jdbcTemplate.update("INSERT INTO users(firstName, lastName, email, alias, password, userUUID, createdDate, active) VALUES (?,?,?,?,?,?,?,?)",
                new Object[]{firstName, lastName, email, alias, password, userUUIDArr, new Date(), active});
        
        return jdbcTemplate.queryForObject(" SELECT last_insert_id()", Integer.class);
    }

    @Transactional(readOnly=true)
    public User findUserById(int userId) {
        return jdbcTemplate.queryForObject(
                "select * from users where userId=? AND active=? ",
                new Object[]{userId, true}, new UserRowMapper());
    }
    
    @Transactional(readOnly=true)
    public User findUserByUUID(String userUUIDStr) {
        UUID userUUID = UUID.fromString(userUUIDStr);
        return jdbcTemplate.queryForObject(
                "select * from users where userUUID=? AND active=? ",
                new Object[]{UtilsHelper.getBytesFromUUID(userUUID), true}, new UserRowMapper());
    }
    
    @Transactional(readOnly=true)
    public User userLogin(String email, String password) {
        return jdbcTemplate.queryForObject(
                "select * from users where email=? AND password=? AND active=? ",
                new Object[]{email, password, true}, new UserRowMapper());
    }

    @Transactional(readOnly=true)
    public User findUserByEmail(String email) {
        return jdbcTemplate.queryForObject(
                "select * from users where email=? AND active=? ",
                new Object[]{email, true}, new UserRowMapper());
    }

    @Transactional
    public int inactivateUserByUserId(int userId) {
        int rowCount = jdbcTemplate.update("UPDATE users SET active = ? WHERE userId = ? ", false, userId);
        return rowCount;
    }
    
    @Transactional
    public int inactivateUserByUserUUID(String userUUIDStr) {
        UUID userUUID = UUID.fromString(userUUIDStr);
        int rowCount = jdbcTemplate.update("UPDATE users SET active = ? WHERE userUUID = ? ", false, UtilsHelper.getBytesFromUUID(userUUID));
        return rowCount;
    }

    @Transactional
    public int inactivateUserByUserEmail(String email) {
        int rowCount = jdbcTemplate.update("UPDATE users SET active = ? WHERE email = ? ", false, email);
        return rowCount;
    }
    
    @Transactional
    public void updateUserByUserId(int userId, Map<String, Object> valueMap){
        
        List<Object> paramList = new ArrayList<>();
        String sql = "UPDATE users SET ";
        
        for(String key : valueMap.keySet()){
            sql += key + "=?,";
            paramList.add(valueMap.get(key));
        }
        sql = sql.substring(0, sql.length()-1);
        sql += " WHERE userId=?";
        
        paramList.add(userId);
        Object[] params = paramList.toArray(new Object[paramList.size()]);
        jdbcTemplate.update(sql, params);
    }
    
    @Transactional
    public void updateUserByUserUUID(String userUUIDStr, Map<String, Object> valueMap){
        
        UUID userUUID = UUID.fromString(userUUIDStr);
        
        List<Object> paramList = new ArrayList<>();
        String sql = "UPDATE users SET ";
        
        for(String key : valueMap.keySet()){
            sql += key + "=?,";
            paramList.add(valueMap.get(key));
        }
        sql = sql.substring(0, sql.length()-1);
        sql += " WHERE userUUID=?";
        
        paramList.add(UtilsHelper.getBytesFromUUID(userUUID));
        Object[] params = paramList.toArray(new Object[paramList.size()]);
        jdbcTemplate.update(sql, params);
    }
}
