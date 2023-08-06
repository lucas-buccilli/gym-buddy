package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;

import java.util.List;

public interface MembershipService {
    List<MembershipDto> findAll();

    MembershipDto addMembership(MembershipDto memberDto);

    boolean isActive();
}
