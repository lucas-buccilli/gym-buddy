package com.example.gymbuddy.integration;

import com.example.gymbuddy.infrastructure.services.IAuthService;
import lombok.SneakyThrows;
import org.mockito.Mockito;

public class AuthServiceStubber {
    private final IAuthService authService;

    private AuthServiceStubber(IAuthService authService) {
        this.authService = authService;
    }

    public static AuthServiceStubber builder(IAuthService authService) {
        return new AuthServiceStubber(authService);
    }

    public AuthServiceCreateUserStubber createUser() {
        return new AuthServiceCreateUserStubber(this);
    }


    public static class AuthServiceCreateUserStubber {
        private final AuthServiceStubber authServiceStubber;
        private String username;
        private String password;

        private AuthServiceCreateUserStubber(AuthServiceStubber authServiceStubber) {
            this.authServiceStubber = authServiceStubber;
        }

        public AuthServiceCreateUserStubber when(String username, String password) {
            this.username = username;
            this.password = password;
            return this;
        }

        @SneakyThrows
        public AuthServiceStubber thenReturn(String response) {
            Mockito.when(authServiceStubber.authService.createUser(username, password)).thenReturn(response);
            return authServiceStubber;
        }
    }
}
