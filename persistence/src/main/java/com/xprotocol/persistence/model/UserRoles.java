/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.persistence.model;

import org.springframework.stereotype.Component;

/**
 *
 * @author Tao Zhao
 */

@Component
public class UserRoles {
    private int userId;
    private int roleId;
    
    public UserRoles(){}
    
    @Override
    public String toString() {
        return "UserRoles{" +
                "userId=" + userId +
                ", roleId='" + roleId + '\'' +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    
}
