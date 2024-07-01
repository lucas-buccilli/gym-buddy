package com.example.gymbuddy.implementation.validators.requests;

import com.example.gymbuddy.infrastructure.exceptions.AuthApiException;
import com.example.gymbuddy.infrastructure.models.AuthRoles;
import com.example.gymbuddy.infrastructure.models.requests.MemberRequests;
import com.example.gymbuddy.infrastructure.services.IAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberRequestValidatorTest {

    @Mock
    IAuthService authService;
    @InjectMocks
    MemberRequestValidator memberRequestValidator;

    @Test
    void validate() throws AuthApiException {
        var addRequest = MemberRequests.AddRequest.builder().firstName("TestFirst").lastName("TestLast").phoneNumber("0000000000")
                .email("email@test.com").password("password").roles(List.of(AuthRoles.MEMBER)).build();
        when(authService.searchUsersByEmail(any())).thenReturn(List.of(new Object()));

        assertFalse(memberRequestValidator.validate(addRequest).isEmpty());
    }
}