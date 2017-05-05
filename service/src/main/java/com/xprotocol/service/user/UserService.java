/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.user;

import com.xprotocol.persistence.model.User;
import java.util.List;

/**
 *
 * @author Tao Zhao
 */
public interface UserService {

    int addUser(String email);

    int addUser(String email, String alias);

    int addUser(String firstName, String lastName, String email, String alias);

    int addUser(String firstName, String lastName, String email, String alias, boolean active);

    List<User> findAll();

    User findUserByEmail(String email);

    User findUserById(int userId);

    int inactivateUserByUserEmail(String email);

    int inactivateUserByUserId(int userId);
    
}
