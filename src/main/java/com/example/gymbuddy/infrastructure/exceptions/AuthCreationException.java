package com.example.gymbuddy.infrastructure.exceptions;

public class AuthCreationException extends AuthApiException{
    public AuthCreationException(Exception e) {
        super(e);
    }
}
