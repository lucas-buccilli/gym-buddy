package com.example.gymbuddy.infrastructure.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MachineDto implements Dto{

    private Integer id;

    @Size(max = 500, message = "The length of name must be between less than 500 characters.")
    @NotNull
    private String name;
}
