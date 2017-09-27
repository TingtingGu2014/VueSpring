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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    public Map<Integer, String> addUser(String email, String password) {
        return userRepo.addUser(email, password);
    }

    @Override
    public Map<Integer, String> addUser(String email, String alias, String password) {
        return userRepo.addUser(email, alias, password);
    }

    @Override
    public Map<Integer, String> addUser(String firstName, String lastName, String email, String alias, String password) {
        return userRepo.addUser(firstName, lastName, email, alias, password);
    }

    @Override
    public Map<Integer, String> addUser(String firstName, String lastName, String email, String alias, String password, boolean active) {
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
    
       /**
     * Find the logged in user
     * @param userSrv: user service instance
     * @param email: user email
     * @return User
     */
    @Override
    @Cacheable(value="currentLoggedinUser", key="#email")
    public User getCurrentLoggedinUser(UserService userSrv, String email){
        return userSrv.findUserByEmail(email);
    }
    
    /**
     * Update the logged in user
     * @param user
     * @return 
     */
    @Override
    @CachePut(value="currentLoggedinUser", key="#user.getEmail()")
    public User setCurrentLoggedinUser(User user){
        return user;
    }
    
    /**
     * Update the logged in user
     * @param user
     * @return 
     */
    @Override
    @CacheEvict(value="currentLoggedinUser", key="#user.getEmail()")
    public User removeCurrentLoggedinUser(User user){
        return user;
    }
}
