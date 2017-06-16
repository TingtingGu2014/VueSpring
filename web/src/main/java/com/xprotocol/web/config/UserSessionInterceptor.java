/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.web.config;

import com.xprotocol.utils.UtilsStringHelper;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.ExpiringSession;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tao Zhao
 */
@Component
public class UserSessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        System.out.println("PreHandle is done!");
        String contextUrl = getContextUrlFromRequest(request);
        if(!UtilsStringHelper.isEmptyString(contextUrl)){
            String method = request.getMethod();
            if(!contextUrl.equals("user")){
                SessionRepository<Session> repo = (SessionRepository<Session>) request.getAttribute(SessionRepository.class.getName());
                HttpSession httpSession = (HttpSession)request.getSession();
                if(null != httpSession){
                    ExpiringSession session = (ExpiringSession) repo.getSession(httpSession.getId());
                    if(null != session && !session.isExpired()){
//                        String email = (String) session.getAttribute("email");
//                        if(null != email){
//                            RedisUser userPersisted = redisUserService.findAuthenticatedUser(email, session.getId());
//                            if(null != userPersisted){
//                                sessionValidated = true;
//                            }
//                        }
                    }
                }
            }


        }
        return true;
    }

    /**
     * Adds session to successful user registration
     * 
     * @param request
     * @param response
     * @param o
     * @param mav
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView mav) throws Exception {
        System.out.println("PostHandle is done!");
//        int status = response.getStatus();
//        String authHeader = response.getHeader("x-auth-token");
//        if(status == 200 && !UtilsStringHelper.isEmptyString(authHeader)){
//            Cookie loggedIn = new Cookie("loggedIn", "true");
//            loggedIn.setMaxAge(60*60);
//            response.addCookie(loggedIn);
//            
//            Cookie token = new Cookie("x-auth-token", authHeader);
//            token.setMaxAge(60*60);
//            token.setHttpOnly(true);
//            response.addCookie(token);
            
//            SessionRepository<Session> repo = (SessionRepository<Session>) request.getAttribute(SessionRepository.class.getName());
//            String contextUrl = getContextUrlFromRequest(request);
//            if(!UtilsStringHelper.isEmptyString(contextUrl) && contextUrl.equals("user")){                
//                String method = request.getMethod();
//                if(method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("GET")){                    
//                    HttpSession httpSession = (HttpSession)request.getSession();
//                    ExpiringSession session = (ExpiringSession) repo.getSession(httpSession.getId());
//                    if(null == session){
//                        session = (ExpiringSession) repo.createSession();
//                    }             
//                    String userId = (String)httpSession.getAttribute("userId");
//                    String name = SecurityContextHolder.getContext().getAuthentication().getName();
//                    session.setAttribute("userName", userId);
////                    String sessionId = session.getId();
//                    repo.save(session);
//                }
//            }
            
//        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception excptn) throws Exception {
        System.out.println("AfterCompletion is done!");
        int status = response.getStatus();
        String authHeader = response.getHeader("x-auth-token");
        if(status == 200 && !UtilsStringHelper.isEmptyString(authHeader)){
//            Cookie loggedIn = new Cookie("loggedIn", "true");
//            loggedIn.setMaxAge(60*60);
//            loggedIn.setPath("/");
//            response.addCookie(loggedIn);
//            
//            Cookie token = new Cookie("x-auth-token", authHeader);
//            token.setMaxAge(60*60);
//            token.setHttpOnly(true);
//            token.setPath("/");
//            response.addCookie(token);
        }
    }
    
    private String getContextUrlFromRequest(HttpServletRequest request){
        String contextUrl = request.getServletPath();
        if(!contextUrl.startsWith("/") && contextUrl.contains("/")){
            contextUrl = contextUrl.split("/")[1];
        }
        return contextUrl;
    }
    
}
