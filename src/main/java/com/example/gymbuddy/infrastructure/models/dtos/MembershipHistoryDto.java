package com.example.gymbuddy.infrastructure.models.dtos;

import com.example.gymbuddy.infrastructure.entities.Membership;
import com.example.gymbuddy.infrastructure.entities.enums.MembershipOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MembershipHistoryDto {
    private Integer id;
    private LocalDateTime date;
    private Membership membership;
    private MembershipOperation operation;
}

