package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;

import java.util.List;

public interface MemberService {
    List<MemberDto> findAll();

    MemberDto addMember(MemberDto memberDto);
}
