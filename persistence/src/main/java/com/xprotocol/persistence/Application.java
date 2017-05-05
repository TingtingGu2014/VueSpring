/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.persistence;

/**
 *
 * @author Tao Zhao
 */
import com.xprotocol.persistence.dao.UserRepository;
import com.xprotocol.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.util.List;

import static java.lang.System.exit;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = { "application-xprotocol-persistence.properties" })
public class Application implements CommandLineRunner {

    @Autowired
    DataSource dataSource;

    @Autowired
    private UserRepository customerRepository;

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(Application.class)
                        .properties("classpath:persistence_application")
                        .build()
                        .run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("DATASOURCE = " + dataSource);

        // If you want to check the HikariDataSource settings
        //HikariDataSource newds = (HikariDataSource)dataSource;
        //System.out.println("DATASOURCE = " + newds.getMaximumPoolSize());

        if (args.length <= 0) {
            System.err.println("[Usage] java xxx.jar {insert name email | display}");
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("email", "tinggia@xprotocol.com");
            paramMap.put("active", 0);
            customerRepository.updateUserByUserId(2, paramMap);
            
        } else {
            if (args[0].equalsIgnoreCase("insert")) {
                System.out.println("Add user...");
                String firstName = args[1];
                String lastName = args[2];
                String email = args[3];
                String alias = args[4];

                customerRepository.addUser(firstName, lastName, email, alias);
            }

            if (args[0].equalsIgnoreCase("display")) {
                System.out.println("Display all users...");
                List<User> list = customerRepository.findAll();
                list.forEach(x -> System.out.println(x));
            }
            System.out.println("Done!");
        }
        exit(0);
    }
}