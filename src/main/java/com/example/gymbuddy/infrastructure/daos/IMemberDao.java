package com.example.gymbuddy.infrastructure.daos;

import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;

import java.util.List;
import java.util.Optional;

public interface IMemberDao {
    List<MemberDto> findAll();

    MemberDto addMember(MemberDto memberDao);

    Optional<MemberDto> findById(Integer memberId);

    void deleteMember(int id);

    MemberDto modifyMember(int id, MemberDto memberDto);

    MemberDto editMember(MemberDto memberDao);
}
