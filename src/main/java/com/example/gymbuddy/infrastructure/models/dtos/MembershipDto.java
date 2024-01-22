package com.example.gymbuddy.infrastructure.models.dtos;

import jakarta.validation.constraints.NotNull;
import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
