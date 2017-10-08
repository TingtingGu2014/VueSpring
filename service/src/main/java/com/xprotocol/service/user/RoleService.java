/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.user;

import com.xprotocol.persistence.model.Role;
import java.util.List;

/**
 *
 * @author zhao0677
 */
public interface RoleService {
    List<Role> findAll();
}
