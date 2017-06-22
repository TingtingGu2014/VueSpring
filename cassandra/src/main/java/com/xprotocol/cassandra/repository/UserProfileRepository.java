/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.cassandra.repository;

import com.xprotocol.cassandra.model.UserProfile;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tao Zhao
 */
@Repository
public interface UserProfileRepository extends CassandraRepository<UserProfile> {
    @Query("select * from userProfile where userId = ?0 ")
    Iterable<UserProfile> findUserProfileByUserId(int userId);
}
