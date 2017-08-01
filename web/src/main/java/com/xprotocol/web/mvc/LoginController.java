package com.xprotocol.web.mvc;

import com.xprotocol.persistence.model.User;
import com.xprotocol.service.user.UserService;
import com.xprotocol.service.exceptions.UserDoesNotExistException;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
@ComponentScan("com.xprotocol.service")
public class LoginController {
    
    @Autowired
    UserService userSrv;
    
    @RequestMapping(value = "/")
    public String test() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/");
        return "index.html";
    }
    
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
//
//    @RequestMapping(value = "/api/logout", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void logout(HttpSession session) {
//        session.invalidate();
//    }
    
    @RequestMapping(value = "/api/logout", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signOut(HttpServletResponse response) {
        Cookie loggedIn = new Cookie("loggedIn", "");
        loggedIn.setMaxAge(0);
        loggedIn.setPath("/");
        response.addCookie(loggedIn);        
    }
    
}
