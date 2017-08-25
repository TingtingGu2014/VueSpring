/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.user;

import com.xprotocol.persistence.dao.UserRepository;
import com.xprotocol.persistence.model.User;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tao Zhao
 */

@Service
@ComponentScan("com.xprotocol.persistence")
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository userRepo;

    @Override
    public int addUser(String email, String password) {
        return userRepo.addUser(email, password);
    }

    @Override
    public int addUser(String email, String alias, String password) {
        return userRepo.addUser(email, alias, password);
    }

    @Override
    public int addUser(String firstName, String lastName, String email, String alias, String password) {
        return userRepo.addUser(firstName, lastName, email, alias, password);
    }

    @Override
    public int addUser(String firstName, String lastName, String email, String alias, String password, boolean active) {
        return userRepo.addUser(firstName, lastName, email, alias, password, active);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }
    
    @Override
    public User userLogin(String email, String password) {
        return userRepo.userLogin(email, password);
    }

    @Override
    public User findUserById(int userId) {
        return userRepo.findUserById(userId);
    }

    @Override
    public int inactivateUserByUserEmail(String email) {
        return userRepo.inactivateUserByUserEmail(email);
    }

    @Override
    public int inactivateUserByUserId(int userId) {
        return userRepo.inactivateUserByUserId(userId);
    }

    @Override
    public User findUserByUUID(String userUUIDStr) {
        return userRepo.findUserByUUID(userUUIDStr);
    }

    @Override
    public int inactivateUserByUserUUID(String userUUIDStr) {
        return userRepo.inactivateUserByUserUUID(userUUIDStr);
    }
    
    @Override
    public void updateUserByUserUUID(String userUUIDStr, Map<String, Object> valueMap) {
        userRepo.updateUserByUserUUID(userUUIDStr, valueMap);
    }
}
