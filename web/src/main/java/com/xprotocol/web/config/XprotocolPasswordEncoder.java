/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.web.config;

import com.xprotocol.service.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Tao Zhao
 */
public class XprotocolPasswordEncoder implements PasswordEncoder {
    
    @Autowired
    SecurityService securitySrv;

    @Override
    public String encode(CharSequence rawPassword) {
        return securitySrv.hashPassword(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return securitySrv.authenticate(securitySrv.hashPassword(rawPassword.toString()), encodedPassword);
    }
    
}
