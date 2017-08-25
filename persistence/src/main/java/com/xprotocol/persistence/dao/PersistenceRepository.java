/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.persistence.dao;

import com.xprotocol.persistence.exceptions.NoExistingIdColumnForAddOrUpdateDataOpExcpetion;
import com.xprotocol.utils.UtilsHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zhao0677
 */
@Repository
public class PersistenceRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Transactional
    public int addOrUpdateEntityWithVlues(String tableName, String idColumn, Map<String, Object>valueMap) throws NoExistingIdColumnForAddOrUpdateDataOpExcpetion{

        if(!valueMap.containsKey(idColumn)){
            throw new NoExistingIdColumnForAddOrUpdateDataOpExcpetion("The value map do NOT contain the id column: "+idColumn);
        }
        
        String sql = "INSERT INTO " + tableName +"(";
        String sql2 = ") VALUES (";
        String sql3 = " ON DUPLICATE KEY UPDATE ";
        List<Object> objList1 = new ArrayList<>();
        List<Object> objList2 = new ArrayList<>();
        
        try{
            for(String columnName : valueMap.keySet()){
                sql += columnName + ", ";
                sql2 += "?, ";            
                sql3 += columnName + "=?, ";
                objList1.add(valueMap.get(columnName));
                objList2.add(valueMap.get(columnName));
            }
            sql = sql.substring(0, sql.length()-2);
            sql2 = sql2.substring(0, sql2.length()-2) + ") ";
            sql3 = sql3.substring(0, sql3.length()-2);
            sql += sql2 + sql3;
            objList1.addAll(objList2);
        }
        catch(Exception ex){
            System.out.println("Cannot prepare the parameters for the add/update op: ex: "+ex.getMessage());
        }
        
        int rowsAffected = -1;
        Object[] params = objList1.toArray(new Object[objList1.size()]);
        try{
            rowsAffected = jdbcTemplate.update(sql, params);
        }
        catch(Exception ex){
            System.out.println("add or update ex: "+ex.getMessage());
        }
        
        return rowsAffected;
    }
    
    @Transactional
    public int updateEntityByUUID(String tableName, String UUIDColName, String UUIDValue, Map<String, Object> valueMap){
        
        UUID uuid = UUID.fromString(UUIDValue);
        byte[] uuidArr = UtilsHelper.getBytesFromUUID(uuid);
        valueMap.put(UUIDColName, uuidArr);
        
        List<Object> paramList = new ArrayList<>();
        String sql = "UPDATE " + tableName + " SET ";
        
        for(String key : valueMap.keySet()){
            sql += key + "=?,";
            paramList.add(valueMap.get(key));
        }
        sql = sql.substring(0, sql.length()-1);
        sql += " WHERE " + UUIDColName + "=?";
        
        paramList.add(uuidArr);
        Object[] params = paramList.toArray(new Object[paramList.size()]);
        return jdbcTemplate.update(sql, params);
    }
    
    @Transactional
    public int updateEntityById(String tableName, String idColName, Object idValue, Map<String, Object> valueMap){

        List<Object> paramList = new ArrayList<>();
        String sql = "UPDATE " + tableName + " SET ";
        
        for(String key : valueMap.keySet()){
            sql += key + "=?,";
            paramList.add(valueMap.get(key));
        }
        sql = sql.substring(0, sql.length()-1);
        sql += " WHERE " + idColName + "=?";
        
        paramList.add(idValue);
        Object[] params = paramList.toArray(new Object[paramList.size()]);        
        return jdbcTemplate.update(sql, params);
    }
}
