/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.user;

import com.xprotocol.persistence.model.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tao Zhao
 */
public interface UserService {

    int addUser(String email, String password);

    int addUser(String email, String alias, String password);

    int addUser(String firstName, String lastName, String email, String alias, String password);

    int addUser(String firstName, String lastName, String email, String alias, String password, boolean active);

    List<User> findAll();
    
    User userLogin(String email, String password);

    User findUserByEmail(String email);

    User findUserById(int userId);
    
    User findUserByUUID(String userUUIDStr);

    int inactivateUserByUserEmail(String email);

    int inactivateUserByUserId(int userId);
    
    int inactivateUserByUserUUID(String userUUIDStr);
    
    void updateUserByUserUUID(String userUUIDStr, Map<String, Object> valueMap);
    
}
