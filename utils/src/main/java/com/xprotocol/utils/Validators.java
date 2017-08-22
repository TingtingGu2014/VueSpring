/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Tao Zhao
 */
public class Validators {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$"; 
    private static final String UUID_PATTERN = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    public static boolean emailValidator(String email){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();        
    }
    
    public static boolean isEmptyString(String str){
        return (null == str || str.equals(""));
    }
    
    public static boolean isValidUUID(String uuidStr){
        Pattern pattern = Pattern.compile(UUID_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(uuidStr);
        return matcher.matches();        
    }
}
