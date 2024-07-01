package com.example.gymbuddy.infrastructure.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

public class AuthApiException extends Exception{
    public AuthApiException(Exception e) {
        super(e);
    }
}
