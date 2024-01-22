package com.example.gymbuddy.infrastructure.daos;

import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;

import java.util.List;

public interface IMembershipDao {
    List<MembershipDto> findAll();

    MembershipDto addMembership(MembershipDto memberDto);

    boolean isActive();

    Integer getNumberOfActiveMemberships();
}
