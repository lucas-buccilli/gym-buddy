package com.example.gymbuddy.infrastructure.dataproviders;

import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;

import java.util.List;

public interface IMembershipDataProvider {
    List<MembershipDto> findAll();

    MembershipDto addMembership(MembershipDto memberDto);

    boolean isActive();
}
