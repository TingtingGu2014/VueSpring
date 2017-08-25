/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.persistence;

import com.xprotocol.persistence.dao.PersistenceRepository;
import com.xprotocol.persistence.exceptions.NoExistingIdColumnForAddOrUpdateDataOpExcpetion;
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
public class PersistenceServiceImpl implements PersistenceService {
    
    @Autowired
    private PersistenceRepository persistRepo;

    @Override
    public int addOrUpdateEntityWithVlues(String tableName, String idColumn, Map<String, Object> valueMap) throws NoExistingIdColumnForAddOrUpdateDataOpExcpetion {
        return persistRepo.addOrUpdateEntityWithVlues(tableName, idColumn, valueMap);
    }

    @Override
    public int updateEntityByUUID(String tableName, String UUIDColName, String UUIDValue, Map<String, Object> valueMap) {
        return persistRepo.updateEntityByUUID(tableName, UUIDColName, UUIDValue, valueMap);
    }

    @Override
    public void updateEntityById(String tableName, String idColName, Object idValue, Map<String, Object> valueMap) {
        persistRepo.updateEntityByUUID(tableName, idColName, idColName, valueMap);
    }
    
    
}
