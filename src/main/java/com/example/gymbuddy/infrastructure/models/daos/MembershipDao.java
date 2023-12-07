package com.example.gymbuddy.infrastructure.models.daos;

import jakarta.validation.constraints.NotNull;
import jdk.jfr.BooleanFlag;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipDao {
    private Integer id;

    @NotNull
    private Integer memberId;

    @BooleanFlag
    @NotNull
    private boolean active;
}
