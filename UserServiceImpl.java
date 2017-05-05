/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.user;

import com.xprotocol.persistence.dao.UserRepository;
import com.xprotocol.persistence.model.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tao Zhao
 */

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserRepository userRepo;
    
    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public int addUser(String email) {
        return userRepo.addUser("", "", email, "", true);
    }

    @Override
    public int addUser(String email, String alias) {
        return userRepo.addUser("", "", email, alias, true);
    }

    @Override
    public int addUser(String firstName, String lastName, String email, String alias) {
        return userRepo.addUser(firstName, lastName, email, alias, true);
    }

    @Override
    public int addUser(String firstName, String lastName, String email, String alias, boolean active) {

        return userRepo.addUser(firstName, lastName, email, alias, active);

    }

    @Override
    public User findUserById(int userId) {
        return userRepo.findUserById(userId);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    @Override
    public int inactivateUserByUserId(int userId) {
        return userRepo.inactivateUserByUserId(userId);
    }

    @Override
    public int inactivateUserByUserEmail(String email) {
        return userRepo.inactivateUserByUserEmail(email);
    }
}
