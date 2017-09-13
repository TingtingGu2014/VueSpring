/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.web.config;

import com.xprotocol.persistence.model.User;
import com.xprotocol.service.user.UserService;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author zhao0677
 */
public class XprotocolWebUtils {
    
    @Autowired
    public static UserService userSrv;
    
    public static String getContextUrlFromRequest(HttpServletRequest request){
        String contextUrl = request.getServletPath();
        if(!contextUrl.startsWith("/") && contextUrl.contains("/")){
            contextUrl = contextUrl.split("/")[1];
        }
        return contextUrl;
    }
    
    public static User getCurrentSessionUser (){
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = user.getUsername();
        return userSrv.findUserByEmail(email);
    }
    
    public static Collection<GrantedAuthority> getCurrentSessionUserAuthorities(){
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getAuthorities();
    }
    
    public static boolean currentSessionUserHasAuthority(String role){
        Collection<GrantedAuthority> authorities = getCurrentSessionUserAuthorities();
        Iterator<GrantedAuthority> iterator = authorities.iterator();
        while(iterator.hasNext()) {
            GrantedAuthority auth = iterator.next();
            if(role.equals(auth.getAuthority())){
                return true;
            }
        }
        return false;
    }
}
