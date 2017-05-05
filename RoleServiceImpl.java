/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.user;

import com.xprotocol.persistence.dao.RoleRepository;
import com.xprotocol.persistence.exceptions.EntityAlreadyExistsDuringInsertionException;
import com.xprotocol.persistence.model.Role;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tao Zhao
 */

@Service
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    RoleRepository roleRepo;

    @Override
    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    @Override
    public int addRole(String roleName) throws EntityAlreadyExistsDuringInsertionException {
        return roleRepo.addRole(roleName);
    }

    @Override
    public Role findRoleById(int roleId) {
        return roleRepo.findRoleById(roleId);
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepo.findRoleByName(roleName);
    }

    @Override
    public void deleteRoleById(int roleId){
        roleRepo.deleteRoleById(roleId);
    }
}
