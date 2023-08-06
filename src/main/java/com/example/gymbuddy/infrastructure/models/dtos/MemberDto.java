package com.example.gymbuddy.infrastructure.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Integer id;

    @Size(max = 50, message = "The length of first name must be between less than 50 characters.")
    @NotNull
    private String firstName;

    @Size(max = 50, message = "The length of last name must be between less than 50 characters.")
    @NotNull
    private String lastName;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "The phone number must be valid.")
    @NotNull
    private String phoneNumber;
}
