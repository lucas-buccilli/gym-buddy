package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.exceptions.AuthCreationException;
import com.example.gymbuddy.infrastructure.models.AuthRoles;

import java.util.List;

public interface IAuthService {
    String createUser(String email, String password) throws AuthCreationException;

    void addRole(String authId, List<AuthRoles> roles) throws AuthCreationException;
}
