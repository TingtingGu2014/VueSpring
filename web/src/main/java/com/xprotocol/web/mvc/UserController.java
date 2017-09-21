/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.web.mvc;

import com.xprotocol.persistence.exceptions.NoExistingIdColumnForAddOrUpdateDataOpExcpetion;
import com.xprotocol.persistence.model.User;
import com.xprotocol.persistence.model.UserDetails;
import com.xprotocol.service.exceptions.InvalidUUIDException;
import com.xprotocol.service.user.UserDetailsService;
import com.xprotocol.service.user.UserService;
import com.xprotocol.service.exceptions.UserDoesNotExistException;
import com.xprotocol.service.persistence.PersistenceService;
import com.xprotocol.utils.Validators;
import com.xprotocol.web.config.XprotocolWebUtils;
import com.xprotocol.web.exceptions.EntityUpdateException;
import com.xprotocol.web.exceptions.IncompleteRegistrationInformationException;
import com.xprotocol.web.exceptions.UserAlreadyExistsException;
import com.xprotocol.web.exceptions.UserAuthorizationException;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tao Zhao
 */

@RestController
@ComponentScan("com.xprotocol.service")
public class UserController {
    @Autowired
    UserService userSrv;
    
    @Autowired
    UserDetailsService userDetailsSrv;
    
    @Autowired
    PersistenceService persistenceSrv;
    
    @RequestMapping(value="/api/admin/users")
    public List<User> findAll(HttpServletRequest request){
        return userSrv.findAll();
    }
    
    /**
     *
     * @param request : http request
     * @param user : user information
     * @param response : http response
     * @return : new user
     */
    @RequestMapping(value="/api/signUp", method=RequestMethod.POST)
    public User signUp(HttpServletRequest request, @ModelAttribute("SpringWeb") User user, HttpServletResponse response){
        try{
            if(Validators.isEmptyString(user.getEmail())){            
                throw new IncompleteRegistrationInformationException("The user email cannot be empty!");
            }
            else if(Validators.isEmptyString(user.getPassword())){
                throw new IncompleteRegistrationInformationException("The user password is empty!");
            }
            else if(!Validators.emailValidator(user.getEmail())){
                throw new IncompleteRegistrationInformationException("The user email is NOT valid!");
            }
            User existingUser = userSrv.findUserByEmail(user.getEmail());
            if(null != existingUser){
                throw new UserAlreadyExistsException("User email "+user.getEmail()+" already exists!");
            }
            Map<Integer, String> idMap = userSrv.addUser(user.getEmail(), user.getAlias(), user.getPassword());
            if(null != idMap && !idMap.isEmpty()){
                Iterator it = idMap.entrySet().iterator();
                Map.Entry element = (Map.Entry)it.next();
                Integer id = (Integer)element.getKey();
                String userUUID = (String)element.getValue();
                user.setUserId(id);
                user.setUserUUID(userUUID);
                user.setPassword("");
                Cookie loggedIn = new Cookie("loggedIn", "true");
                loggedIn.setMaxAge(60*60);
                loggedIn.setPath("/");
                response.addCookie(loggedIn);
                userSrv.setCurrentLoggedinUser(user);
            }            
        }
        catch(IncompleteRegistrationInformationException ex){
            try {
                response.sendError(400, "Incomplete or invalid user registration information!");
            } catch (IOException ex1) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch(UserAlreadyExistsException ex){
            try {
                response.sendError(400, "User already exists!");
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
     * @param session : HttpSession
     * @param response : HttpServletResponse
     */
    @RequestMapping(value = "/api/signout", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signOut(HttpServletRequest request, HttpServletResponse response) {
        userSrv.removeCurrentLoggedinUser(userSrv.findUserByEmail(XprotocolWebUtils.getCurrentSessionUser().getUsername()));
        Cookie loggedIn = new Cookie("loggedIn", "");
        loggedIn.setMaxAge(0);
        loggedIn.setPath("/");
        response.addCookie(loggedIn);        
    }
    
    @RequestMapping(value = "/api/userProfile/{userUUIDStr}", method = RequestMethod.GET)
    public Map<String, Object> getUserProfile(HttpServletResponse response, @PathVariable("userUUIDStr") String userUUIDStr) {
        
        try{           
            if(!XprotocolWebUtils.currentSessionUserHasAuthority("admin")){
                User currentUser = userSrv.getCurrentLoggedinUser(userSrv, XprotocolWebUtils.getCurrentSessionUser().getUsername());
                if(!currentUser.getUserUUID().equals(userUUIDStr)){
                    throw new UserAuthorizationException("Non-admin user "+currentUser.getEmail()+ " cannot check other user's profile!");
                }
            }
            Map<String, Object> userProfileMap = new HashMap<>();
            if(!Validators.isValidUUID(userUUIDStr)){
                throw new InvalidUUIDException("Invalid user UUID: "+userUUIDStr);
            }
            User user = userSrv.findUserByUUID(userUUIDStr);
            if(null != user){
                Map<String, Object> userDetailsMap = new HashMap<>();
                UserDetails details = userDetailsSrv.findUserDetailsByUserId(user.getUserId());
                if(null != details){
                    userProfileMap.put("userDetails", details);
                }
                return userProfileMap;
            }
            else{
                throw new UserDoesNotExistException("The user with UUID = " + userUUIDStr + "does NOT exist!");
            }
        }
        catch(NumberFormatException | UserDoesNotExistException ex){
            try {
                response.sendError(400, ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (InvalidUUIDException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UserAuthorizationException ex) {
            try {
                response.sendError(403, ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @ResponseBody
    @RequestMapping(value = "/api/userProfile/{userUUIDStr}", method = RequestMethod.POST)
    public Map<String, Object> updateUserProfile(HttpServletRequest request, HttpServletResponse response, @PathVariable("userUUIDStr") String userUUIDStr, @ModelAttribute UserDetails details) {
        
        int rowAffected = -1;
        
        try{            
            if(!Validators.isValidUUID(userUUIDStr)){
                throw new InvalidUUIDException("Invalid user UUID: "+userUUIDStr);
            }
            
            User user = userSrv.findUserByUUID(userUUIDStr);            
            Map<String, Object> userProfileMap = new HashMap<>();
   
            if(null != user){
                
                String email = (String)request.getParameter("email");
                String firstName = (String)request.getParameter("firstName");
                String lastName = (String)request.getParameter("lastName");
                String alias = (String)request.getParameter("alias");

                Map<String, Object> valueMap = new HashMap<>();
                valueMap.put("email", email);
                valueMap.put("firstName", firstName);
                valueMap.put("lastName", lastName);
                valueMap.put("alias", alias);
                valueMap.put("userUUID", userUUIDStr);
                rowAffected = persistenceSrv.updateEntityByUUID("users", "userUUID", userUUIDStr, valueMap);
                
                if(rowAffected > 0){
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setAlias(alias);
                    userSrv.setCurrentLoggedinUser(user);
                    
                    details.setUserId(user.getUserId());
                    rowAffected = userDetailsSrv.addOrUpdateUserDetailsWithUserId(details);
                    if(rowAffected == -1){
                        response.sendError(500, "Cannot update or add the user detail information for UUID: "+userUUIDStr+"! ");
                    }
                    else{                    
                        valueMap.put("createdDate", user.getCreatedDate());
                        valueMap.put("password", "");
                        valueMap.put("roles", user.getRoles());
                        valueMap.put("userUUID", userUUIDStr);
                        userProfileMap.put("user", valueMap);                    
                        userProfileMap.put("userDetails", details);
                    }
                }
                else {
                    throw new EntityUpdateException("Cannot update user information with UUID = "+userUUIDStr);
                }
   
                return userProfileMap;
            }
            else{
                throw new UserDoesNotExistException("The user with UUID = " + userUUIDStr + "does NOT exist!");
            }
        }
        catch(NumberFormatException | UserDoesNotExistException ex){
            try {
                response.sendError(400, ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (InvalidUUIDException | IOException | NoExistingIdColumnForAddOrUpdateDataOpExcpetion ex) {
            try {
                response.sendError(400, ex.getMessage());
            } catch (IOException ex1) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EntityUpdateException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     *
     * @param request : http request
     * @param user : user information
     * @param response : http response
     * @return : logged in user
     */
    @RequestMapping(value="/api/signIn", method=RequestMethod.POST)
    public User singIn(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
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
                if(null != user){
                    userSrv.setCurrentLoggedinUser(user);
                    Cookie loggedIn = new Cookie("loggedIn", "true");
                    loggedIn.setMaxAge(60*60);
                    loggedIn.setPath("/");
                    response.addCookie(loggedIn);
                }
                else{
                    throw new UserDoesNotExistException("Cannot find the user with email: "+email+".\n");
                }
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
}
