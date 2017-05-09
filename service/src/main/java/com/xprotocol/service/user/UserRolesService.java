/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.user;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tao Zhao
 */
public interface UserRolesService {

    public int addUserRolesByUserIdRoleId(int userId, int roleId);

    public void deleteUserRolesByUserId(int userId);

    public void deleteUserRolesByUserIdRoleId(int userId, int roleId);

    public List<Integer> findRolesByRoleId(int roleId);

    public List<Integer> findRolesByUserId(int userId);
    
    public List<String> findRoleNamesByUserId(int userId);
    
}