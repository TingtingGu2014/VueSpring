package com.xprotocol.web.mvc;

import com.xprotocol.persistence.model.User;
import com.xprotocol.service.user.UserService;
import com.xprotocol.service.user.exceptions.UserDoesNotExistException;
import com.xprotocol.utils.Validators;
import com.xprotocol.web.exceptions.IncompleteRegistrationInformationException;
import java.io.IOException;
import java.util.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.ModelAndView;

@RestController
@ComponentScan("com.xprotocol.service")
public class LoginController {
    
    @Autowired
    UserService userSrv;
    
    @RequestMapping(value = "/home")
    public ModelAndView getHome() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/");
        return view;
    }

    @RequestMapping(value = "/api/resource")
    public Map<String, String> getResource() {
        Map<String, String> resource = new HashMap<String, String>();
        resource.put("resource", "here is some resource");
        return resource;
    }

    @RequestMapping(value = "/api/logout", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpSession session) {
        session.invalidate();
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signOut(HttpServletResponse response) {
        Cookie loggedIn = new Cookie("loggedIn", "");
        loggedIn.setMaxAge(0);
        loggedIn.setPath("/");
        response.addCookie(loggedIn);        
    }
    
    /**
     *
     * @param request : http request
     * @param user : user information
     * @param response : http response
     * @return : logged in user
     */
    @RequestMapping(value="/signIn", method=RequestMethod.POST)
    public User singIn(HttpServletRequest request, HttpServletResponse response) {
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
}
