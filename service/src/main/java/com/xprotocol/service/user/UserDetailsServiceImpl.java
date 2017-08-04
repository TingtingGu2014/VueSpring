/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.user;

import com.xprotocol.persistence.dao.UserDetailsRepository;
import com.xprotocol.persistence.exceptions.NoExistingIdColumnForAddOrUpdateDataOpExcpetion;
import com.xprotocol.persistence.model.UserDetails;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

/**
 *
 * @author zhao0677
 */
@Service
@ComponentScan("com.xprotocol.persistence")
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    UserDetailsRepository userDetailsRepo;

    @Override
    public int addUserDetails(int userId, String address, String state, String city, String zipcode, String major, String affiliation) {
        return userDetailsRepo.addUserDetails(userId, address, state, city, zipcode, major, affiliation);
    }

    @Override
    public UserDetails findUserDetailsByUserId(int userId) {
        return userDetailsRepo.findUserDetailsByUserId(userId);
    }

    @Override
    public void updateUserDetailsByUserDetailsId(int userDetailsId, Map<String, Object> valueMap) {
        userDetailsRepo.updateUserDetailsByUserDetailsId(userDetailsId, valueMap);
    }

    @Override
    public void addOrUpdateUserDetailsWithUserId(UserDetails details) throws NoExistingIdColumnForAddOrUpdateDataOpExcpetion {
        userDetailsRepo.addOrUpdateUserDetailsWithUserId(details);
    }
    
}
