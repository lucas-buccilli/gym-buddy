package com.example.gymbuddy.infrastructure.models.dtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class MemberDto {
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Size(max = 50, message = "The length of first name must be between less than 50 characters.")
    private String firstName;

    @Size(max = 50, message = "The length of last name must be between less than 50 characters.")
    private String lastName;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "The phone number must be valid.")
    private String phoneNumber;

}
