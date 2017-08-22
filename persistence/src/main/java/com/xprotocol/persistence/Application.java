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
import com.xprotocol.persistence.dao.PersistenceRepository;
import com.xprotocol.persistence.dao.RoleRepository;
import com.xprotocol.persistence.dao.UserDetailsRepository;
import com.xprotocol.persistence.dao.UserRepository;
import com.xprotocol.persistence.dao.UserRolesRepository;
import com.xprotocol.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.util.List;

import static java.lang.System.exit;
import java.util.ArrayList;
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
    private UserRepository userRepo;
    
    @Autowired
    private RoleRepository roleRepo;
    
    @Autowired
    private UserRolesRepository userRolesRepo;
    
    @Autowired
    private UserDetailsRepository userDetailsRepo;
    
    @Autowired
    private PersistenceRepository perRepo;

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
            Map<String, Object> valueMap = new HashMap<>();
            valueMap.put("userId", "2");
            valueMap.put("zipcode", "34567");
            valueMap.put("address", "2288 Austin Ave");
            try{
                perRepo.addOrUpdateEntityWithVlues("userDetails", "userId", valueMap);
            }
            catch(Exception ex){
                
            }
//            perRepo.updateEntityById("UserDetails", "userId", 1, valueMap);
//              userDetailsRepo.addUserDetails(1, "0965 Houston Ave", "OK", "Norman", "73000", "Zoology", "University of Oklahoma");
//            Map<String, Object> paramMap = new HashMap<>();
//            paramMap.put("email", "tinggia@xprotocol.com");
//            paramMap.put("active", 0);
//            userRepo.updateUserByUserId(2, paramMap);
            
        } else {
            if (args[0].equalsIgnoreCase("insert")) {
                System.out.println("Add user...");
                String firstName = args[1];
                String lastName = args[2];
                String email = args[3];
                String alias = args[4];
                String password = args[5];

                userRepo.addUser(firstName, lastName, email, alias, password);
            }

            if (args[0].equalsIgnoreCase("display")) {
                System.out.println("Display all users...");
//                List<User> list = userRepo.findAll();
                List<User> list = new ArrayList<>();//userRepo.findAll();
                list.add(userRepo.findUserByUUID("6f2ffac1-6d17-11e7-b6f4-39d8342ffe89"));
                list.forEach(x -> System.out.println(x));
            }
            
            if (args[0].equalsIgnoreCase("role_insert")) {
                System.out.println("insert new rolws...");
                String roleName = args[1];
                int roleIndex = roleRepo.addRole(roleName);
                System.out.println("new role has been added with name = " + roleName + " and index = "+roleIndex);
            }
            
            if (args[0].equalsIgnoreCase("role_update")) {
                System.out.println("update roles...");
                String oldName = args[1];
                String roleName = args[2];
                roleRepo.updateRoleByName(oldName, roleName);
//                System.out.println("new role has been added with name = " + roleName + " and index = "+roleIndex);
            }
            
            if (args[0].equalsIgnoreCase("user_role_insert")) {
                System.out.println("insert new user role...");
                int userId = Integer.valueOf(args[1]);
                int roleId = Integer.valueOf(args[2]);
                int roleIndex = userRolesRepo.addUserRolesByUserIdRoleId(userId, roleId);
                System.out.println("new user role has been added with user id = " + userId + " and roleId = "+roleId+" with index = "+roleIndex);
            }
            
            if (args[0].equalsIgnoreCase("user_role_update")) {
                System.out.println("update user roles...");
                String oldName = args[1];
                String roleName = args[2];
                roleRepo.updateRoleByName(oldName, roleName);
//                System.out.println("new role has been added with name = " + roleName + " and index = "+roleIndex);
            }
            
            if (args[0].equalsIgnoreCase("user_role_select")) {
                System.out.println("select user roles...");
                int userId = Integer.valueOf(args[1]);
                List<String> roleNames = userRolesRepo.findRoleNamesByUserId(userId);
                System.out.println("role names = " + roleNames);
            }
            
            System.out.println("Done with arg = "+args[0]+"!");
        }
        exit(0);
    }
}