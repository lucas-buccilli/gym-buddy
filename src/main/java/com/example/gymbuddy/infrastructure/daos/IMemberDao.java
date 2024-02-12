package com.example.gymbuddy.infrastructure.daos;

import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;

import java.util.List;
import java.util.Optional;

public interface IMemberDao {
    List<MemberDto> findAll(PageRequest pageRequest);

    MemberDto saveMember(MemberDto memberDao);

    Optional<MemberDto> findById(Integer memberId);

    Optional<MemberDto> findByAuthId(String authId);

    void deleteMember(int id);
}
