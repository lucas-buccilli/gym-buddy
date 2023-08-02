package com.example.gymbuddy.infrastructure.models.dtos;

import jdk.jfr.BooleanFlag;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipDto {
    private Integer id;

    @NonNull
    private Integer memberId;

    @BooleanFlag
    private boolean active;
}
