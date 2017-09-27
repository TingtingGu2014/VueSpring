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
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author zhao0677
 */
public class XprotocolWebUtils {
    
    public static String getContextUrlFromRequest(HttpServletRequest request){
        String contextUrl = request.getServletPath();
        if(!contextUrl.startsWith("/") && contextUrl.contains("/")){
            contextUrl = contextUrl.split("/")[1];
        }
        return contextUrl;
    }
    
    public static org.springframework.security.core.userdetails.User getCurrentSessionUser (){
        return (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    
    public static Collection<GrantedAuthority> getCurrentSessionUserAuthorities(){
        org.springframework.security.core.userdetails.User currentUser = getCurrentSessionUser();
        return currentUser.getAuthorities();
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
