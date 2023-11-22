package com.example.gymbuddy.infrastructure.dataproviders;

import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;

import java.util.List;
import java.util.Optional;

public interface IMemberDataProvider {
    List<MemberDto> findAll();

    MemberDto addMember(MemberDto memberDto);

    Optional<MemberDto> findById(Integer memberId);
}
