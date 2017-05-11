/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.security;

/**
 *
 * @author Tao Zhao
 */
public interface SecurityService {
    public String hashPassword(String password);
    public boolean authenticate(String password, String token);
}
