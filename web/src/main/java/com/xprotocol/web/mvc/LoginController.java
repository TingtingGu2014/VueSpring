package com.xprotocol.web.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {
    
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
}
