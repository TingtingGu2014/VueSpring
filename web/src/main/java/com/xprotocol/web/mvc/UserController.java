/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.web.mvc;

import com.xprotocol.persistence.model.User;
import com.xprotocol.service.user.UserService;
import com.xprotocol.utils.Validators;
import com.xprotocol.web.exceptions.IncompleteRegistrationInformationException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    
    @RequestMapping(value="/users")
    public List<User> findAll(){
        return userSrv.findAll();
    }
    
    @RequestMapping(value="/user", method=RequestMethod.POST)
    public User signUp(@ModelAttribute("SpringWeb") User user, HttpServletResponse response){
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
                response.sendError(400, "The user email is empty!");
            } catch (IOException ex1) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return user;
    }
}
