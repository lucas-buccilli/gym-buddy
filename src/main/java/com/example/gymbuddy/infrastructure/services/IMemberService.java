package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;

import java.util.List;

public interface IMemberService {
    List<MemberDto> findAll(PageRequest pageRequest);

    MemberDto addMember(MemberDto memberDao);

    void deleteMember(int id);

    MemberDto replaceMember(int id, MemberDto memberDao);

    MemberDto modifyMember(int id, MemberDto partialMemberDao) throws IllegalAccessException;
}
