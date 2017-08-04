/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.persistence.exceptions;

/**
 *
 * @author zhao0677
 */
public class NoExistingIdColumnForAddOrUpdateDataOpExcpetion extends Exception {

    /**
     *
     * @param message
     */
    public NoExistingIdColumnForAddOrUpdateDataOpExcpetion(String message){
        super(message);
    }
}