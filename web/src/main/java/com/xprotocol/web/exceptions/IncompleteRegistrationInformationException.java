/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xprotocol.web.exceptions;

/**
 *
 * @author Tao Zhao
 */
public class IncompleteRegistrationInformationException extends Exception {

    /**
     *
     * @param message
     */
    public IncompleteRegistrationInformationException(String message){
        super(message);
    }
}
