/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.user;

import com.xprotocol.persistence.exceptions.EntityAlreadyExistsDuringInsertionException;
import com.xprotocol.persistence.model.Role;
import java.util.List;

/**
 *
 * @author Tao Zhao
 */
public interface RoleService {

    int addRole(String roleName) throws EntityAlreadyExistsDuringInsertionException;

    void deleteRoleById(int roleId);

    List<Role> findAll();

    Role findRoleById(int roleId);

    Role findRoleByName(String roleName);
    
}
