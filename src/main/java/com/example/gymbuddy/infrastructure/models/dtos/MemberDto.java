package com.example.gymbuddy.infrastructure.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Integer id;

    private String firstName;

    private String lastName;

    private String phoneNumber;
    
    private String authId;
}
