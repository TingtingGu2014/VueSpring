/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author zhao0677
 */
public class PasswordEncoderGenerator {
    
    public static String getEncodedPassword (String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	return passwordEncoder.encode(password);
    }
}
