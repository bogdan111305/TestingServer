package com.example.TestingServer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AnswerNotFoundException extends RuntimeException{
    public AnswerNotFoundException(String message){
        super(message);
    }
}
