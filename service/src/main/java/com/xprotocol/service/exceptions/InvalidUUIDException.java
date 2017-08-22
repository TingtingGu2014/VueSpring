/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.exceptions;

/**
 *
 * @author zhao0677
 */
public class InvalidUUIDException extends Exception {

    /**
     *
     * @param message
     */
    public InvalidUUIDException(String message){
        super(message);
    }
}