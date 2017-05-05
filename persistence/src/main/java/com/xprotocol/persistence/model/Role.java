package com.xprotocol.persistence.model;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by zhao0677 on 4/26/17.
 */

@Component
public class Role {
    private int roleId;
    private String roleName;
    
    public Role(){}

    public Role(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
