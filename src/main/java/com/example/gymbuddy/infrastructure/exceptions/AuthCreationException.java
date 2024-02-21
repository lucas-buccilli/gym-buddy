package com.example.gymbuddy.infrastructure.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;

public class AuthCreationException extends Exception{
    public AuthCreationException(JsonProcessingException e) {
        super(e);
    }
}
