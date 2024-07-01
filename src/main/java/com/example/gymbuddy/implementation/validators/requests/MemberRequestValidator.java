package com.example.gymbuddy.implementation.validators.requests;

import com.example.gymbuddy.infrastructure.exceptions.AuthApiException;
import com.example.gymbuddy.infrastructure.models.requests.MemberRequests;
import com.example.gymbuddy.infrastructure.services.IAuthService;
import com.example.gymbuddy.infrastructure.validation.ValidationError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberRequestValidator {
    private final IAuthService authService;

    public List<ValidationError> validate(MemberRequests.AddRequest addRequest) throws AuthApiException {
        var validationErrors = new ArrayList<ValidationError>();
        if(!authService.searchUsersByEmail(addRequest.getEmail()).isEmpty()) {
            validationErrors.add(new ValidationError("Member ID exists"));
        }
        return validationErrors;
    }
}
