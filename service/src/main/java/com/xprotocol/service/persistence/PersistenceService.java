/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.persistence;

import com.xprotocol.persistence.exceptions.NoExistingIdColumnForAddOrUpdateDataOpExcpetion;
import java.util.Map;

/**
 *
 * @author zhao0677
 */
public interface PersistenceService {

    public int addOrUpdateEntityWithVlues(String tableName, String idColumn, Map<String, Object>valueMap) throws NoExistingIdColumnForAddOrUpdateDataOpExcpetion;
    
    public void updateEntityByUUID(String tableName, String UUIDColName, String UUIDValue, Map<String, Object> valueMap);
    
    public void updateEntityById(String tableName, String idColName, Object idValue, Map<String, Object> valueMap);
}
