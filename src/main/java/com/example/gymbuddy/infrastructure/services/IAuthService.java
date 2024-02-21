package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.exceptions.AuthCreationException;

public interface IAuthService {
    String createUser(String email, String password) throws AuthCreationException;
}
