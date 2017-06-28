/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.cassandra.repository;

import com.xprotocol.cassandra.model.UserDetails;
import java.util.List;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tao Zhao
 */
@Repository
public interface UserDetailsRepository extends CassandraRepository<UserDetails> {
//    @Query("select * from user_details where user_id = ?0 LIMIT ?1 ")
    List<UserDetails> findUserDetailsByUserId(int userId);
    
    UUID deleteByUserDetailsId(UUID userDetailsId);
}
