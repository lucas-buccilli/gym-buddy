package com.example.gymbuddy.infrastructure.models.dtos;

import jakarta.validation.constraints.NotNull;
import jdk.jfr.BooleanFlag;
import lombok.*;

@Data
@Builder
public class MembershipDto {
    @Setter(AccessLevel.NONE)
    private Integer id;

    @NonNull
    private Integer memberId;

    @BooleanFlag
    private boolean active;
}
