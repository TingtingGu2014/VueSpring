/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.user;

import com.xprotocol.persistence.exceptions.NoExistingIdColumnForAddOrUpdateDataOpExcpetion;
import com.xprotocol.persistence.model.UserDetails;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zhao0677
 */
public interface UserDetailsService {

    @Transactional
    int addUserDetails(int userId, String address, String state, String city, String zipcode, String major, String affiliation);

    @Transactional(readOnly = true)
    UserDetails findUserDetailsByUserId(int userId);

    @Transactional
    void updateUserDetailsByUserDetailsId(int userDetailsId, Map<String, Object> valueMap);
    
    void addOrUpdateUserDetailsWithUserId(UserDetails details) throws NoExistingIdColumnForAddOrUpdateDataOpExcpetion;
}
