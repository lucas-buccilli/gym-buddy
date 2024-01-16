package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.daos.MemberDao;

import java.util.List;

public interface IMemberService {
    List<MemberDao> findAll();

    MemberDao addMember(MemberDao memberDao);

    void deleteMember(int id);

    MemberDao editMember(int id, MemberDao memberDao);
}
