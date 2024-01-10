package com.example.gymbuddy.infrastructure.dataproviders;

import com.example.gymbuddy.infrastructure.models.daos.MemberDao;

import java.util.List;
import java.util.Optional;

public interface IMemberDataProvider {
    List<MemberDao> findAll();

    MemberDao addMember(MemberDao memberDao);

    Optional<MemberDao> findById(Integer memberId);

    void deleteMember(int id);
}
