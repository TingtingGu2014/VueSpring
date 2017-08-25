/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.web.exceptions;

/**
 *
 * @author zhao0677
 */
public class EntityUpdateException extends Exception {

    /**
     *
     * @param message
     */
    public EntityUpdateException(String message){
        super(message);
    }
}