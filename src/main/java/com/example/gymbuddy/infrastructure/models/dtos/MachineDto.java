package com.example.gymbuddy.infrastructure.models.dtos;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class MachineDto {
    @Setter(AccessLevel.NONE)
    private Integer id;
    @Size(max = 500, message = "The length of name must be between less than 500 characters.")
    private String name;
}
