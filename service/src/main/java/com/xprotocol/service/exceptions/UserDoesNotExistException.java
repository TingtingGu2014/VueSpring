/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.service.exceptions;

/**
 *
 * @author Tao Zhao
 */
public class UserDoesNotExistException extends Exception {

    /**
     *
     * @param message
     */
    public UserDoesNotExistException(String message){
        super(message);
    }
}
