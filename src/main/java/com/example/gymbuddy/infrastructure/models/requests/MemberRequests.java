package com.example.gymbuddy.infrastructure.models.requests;

import com.example.gymbuddy.infrastructure.models.AuthRoles;
import com.example.gymbuddy.infrastructure.models.enums.Roles;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

public class MemberRequests {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddRequest {
        @Size(max = 50, message = "The length of first name must be between less than 50 characters.")
        @NotNull
        String firstName;
        @Size(max = 50, message = "The length of last name must be between less than 50 characters.")
        @NotNull
        String lastName;
        @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "The phone number must be valid.")
        @NotNull
        String phoneNumber;
        @NotNull
        String email;
        @NotNull
        String password;
        @NotNull
        List<AuthRoles> roles;
    }

    @Data
    public static class ReplaceRequest {
        @Size(max = 50, message = "The length of first name must be between less than 50 characters.")
        @NotNull
        String firstName;
        @Size(max = 50, message = "The length of last name must be between less than 50 characters.")
        @NotNull
        String lastName;
        @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "The phone number must be valid.")
        @NotNull
        String phoneNumber;
        @NotNull
        String authId;
    }

    @Data
    public static class UpdateRequest {
            @Size(max = 50, message = "The length of first name must be between less than 50 characters.")
            String firstName;
            @Size(max = 50, message = "The length of last name must be between less than 50 characters.")
            String lastName;
            @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "The phone number must be valid.")
            String phoneNumber;
        }
}