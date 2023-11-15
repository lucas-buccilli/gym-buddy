package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;

import java.util.List;

public interface IMembershipService {
    List<MembershipDto> findAll();

    MembershipDto addMembership(MembershipDto memberDto);

    boolean isActive();
}
