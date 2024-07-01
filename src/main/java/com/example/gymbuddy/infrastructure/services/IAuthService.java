package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.exceptions.AuthApiException;
import com.example.gymbuddy.infrastructure.exceptions.AuthCreationException;
import com.example.gymbuddy.infrastructure.models.AuthRoles;

import java.util.List;

public interface IAuthService {
    String createUser(String email, String password) throws AuthApiException;

    void addRole(String authId, List<AuthRoles> roles) throws AuthApiException;

    List<Object> searchUsersByEmail(String email) throws AuthApiException;
}
