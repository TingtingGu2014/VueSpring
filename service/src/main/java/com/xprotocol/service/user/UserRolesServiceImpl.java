/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.user;

import com.xprotocol.persistence.dao.UserRolesRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Tao Zhao
 */
public class UserRolesServiceImpl implements UserRolesService {
    
    @Autowired
    UserRolesRepository userRolesRepo;

    @Override
    public int addUserRolesByUserIdRoleId(int userId, int roleId) {
        return userRolesRepo.addUserRolesByUserIdRoleId(userId, roleId);
    }

    @Override
    public void deleteUserRolesByUserId(int userId) {
        userRolesRepo.deleteUserRolesByUserId(userId);
    }

    @Override
    public void deleteUserRolesByUserIdRoleId(int userId, int roleId) {
         userRolesRepo.deleteUserRolesByUserIdRoleId(userId, roleId);
    }

    @Override
    public List<Integer> findRolesByRoleId(int roleId) {
        return userRolesRepo.findRolesByRoleId(roleId);
    }

    @Override
    public List<Integer> findRolesByUserId(int userId) {
        return userRolesRepo.findRolesByUserId(userId);
    }

    @Override
    public List<String> findRoleNamesByUserId(int userId) {
        return userRolesRepo.findRoleNamesByUserId(userId);
    }
    
}
