/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service;

import com.xprotocol.persistence.model.User;
import com.xprotocol.service.user.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author Tao Zhao
 */

@SpringBootApplication
@PropertySource(value = { "application-xprotocol-persistence.properties" })
public class Application implements CommandLineRunner {
    
    @Autowired
    private UserService userSrv;
    
    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(Application.class)
                        .build()
                        .run(Application.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        List<User> users = userSrv.findAll();
        users.forEach(x -> System.out.println(x));
    }
}
