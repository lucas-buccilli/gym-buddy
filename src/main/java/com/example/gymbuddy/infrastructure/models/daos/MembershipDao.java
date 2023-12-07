package com.example.gymbuddy.infrastructure.models.daos;

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
public class MembershipDao {
    private Integer id;

    @NotNull
    private Integer memberId;

    @BooleanFlag
    @NotNull
    private boolean active;
}
