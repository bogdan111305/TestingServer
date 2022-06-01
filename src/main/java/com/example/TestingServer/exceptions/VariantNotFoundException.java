package com.example.TestingServer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VariantNotFoundException extends RuntimeException{
    public VariantNotFoundException(String message){
        super(message);
    }
}
