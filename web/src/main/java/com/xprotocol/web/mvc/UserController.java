/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.web.mvc;

import com.xprotocol.persistence.model.User;
import com.xprotocol.service.user.UserService;
import com.xprotocol.service.user.exceptions.UserDoesNotExistException;
import com.xprotocol.utils.Validators;
import com.xprotocol.web.exceptions.IncompleteRegistrationInformationException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tao Zhao
 */

@RestController
@ComponentScan("com.xprotocol.service")
public class UserController {
    @Autowired
    UserService userSrv;
    
    @RequestMapping(value="/users")
    public List<User> findAll(HttpServletRequest request){
        HttpSession session = request.getSession();
        return userSrv.findAll();
    }
    
    /**
     *
     * @param request : http request
     * @param user : user information
     * @param response : http response
     * @return : new user
     */
    @RequestMapping(value="/user", method=RequestMethod.POST)
    public User signUp(HttpServletRequest request, @ModelAttribute("SpringWeb") User user, HttpServletResponse response){
        try{
            if(Validators.isEmptyString(user.getEmail())){            
                throw new IncompleteRegistrationInformationException("The user email is empty!");
            }
            else if(Validators.isEmptyString(user.getPassword())){
                throw new IncompleteRegistrationInformationException("The user password is empty!");
            }
            else if(!Validators.emailValidator(user.getEmail())){
                throw new IncompleteRegistrationInformationException("The user email is NOT valid!");
            }
            int id = userSrv.addUser(user.getEmail(), user.getAlias(), user.getPassword());
            user.setUserId(id);
        }
        catch(IncompleteRegistrationInformationException ex){
            try {
                response.sendError(400, "Incomplete user registration information!");
            } catch (IOException ex1) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch(Exception ex){
            try {
                response.sendError(500, ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return user;
    }
    
    /**
     *
     * @param request : http request
     * @param user : user information
     * @param response : http response
     * @return : logged in user
     */
    @RequestMapping(value="/user", method=RequestMethod.GET)
    public User login(HttpServletRequest request, HttpServletResponse response) {
        User user = null;
        String authCredentials = request.getHeader("Authorization");
        
        try{
            authCredentials = new String(Base64.getDecoder().decode(authCredentials.split(" ")[1]));
            String[] authCredentialsArr = authCredentials.split(":");
            String email = authCredentialsArr[0];
            String password = authCredentialsArr[1];
            if(Validators.isEmptyString(email)){            
                throw new IncompleteRegistrationInformationException("The user email is empty!");
            }
            else if(Validators.isEmptyString(password)){
                throw new IncompleteRegistrationInformationException("The user password is empty!");
            }
            else if(!Validators.emailValidator(email)){
                throw new IncompleteRegistrationInformationException("The user email is NOT valid!");
            }
            try{
                user = userSrv.userLogin(email, password);
                Cookie loggedIn = new Cookie("loggedIn", "true");
                loggedIn.setMaxAge(60*60);
                loggedIn.setPath("/");
                response.addCookie(loggedIn);
            }
            catch(Exception ex){
                throw new UserDoesNotExistException("Cannot find the user with email: "+email+".\nException message: "+ex.getMessage());
            }
        }
        catch(IncompleteRegistrationInformationException ex){
            try {
                response.sendError(400, ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch(UserDoesNotExistException ex){
            try {
                response.sendError(400, ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return user;
    }
    
    /**
     * 
     * @param session : HttpSession
     * @param response : HttpServletResponse
     */
    @RequestMapping(value = "/signout", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signOut(HttpSession session, HttpServletResponse response) {
        session.invalidate();
        Cookie loggedIn = new Cookie("loggedIn", "false");
        loggedIn.setMaxAge(0);
        loggedIn.setPath("/");
        response.addCookie(loggedIn);
    }
    
//    @RequestMapping(value = "/signup")
//    public ModelAndView getHome() {
//        ModelAndView view = new ModelAndView();
//        view.setViewName("/");
//        return view;
//    }
}
