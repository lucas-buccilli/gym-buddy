package com.example.gymbuddy.infrastructure.models.dtos;

import jakarta.validation.constraints.NotNull;
import jdk.jfr.BooleanFlag;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipDto {
    private Integer id;

    @NotNull
    private Integer memberId;

    @BooleanFlag
    @NotNull
    private boolean active;
}
