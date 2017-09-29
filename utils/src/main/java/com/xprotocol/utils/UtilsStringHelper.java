/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.utils;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author zhao0677
 */
public class UtilsStringHelper {
    public static boolean isEmptyString(String str){
        return (null == str || str.equals(""));
    }
    
    public static boolean isEqualStringLists(List<String> a, List<String> b){     

        if (a == null && b == null) return true;

        if ((a == null && b!= null) || (a != null && b== null) || (a.size() != b.size())){
            return false;
        }
         
        Collections.sort(a);
        Collections.sort(b);      
        return a.equals(b);
    }
}
