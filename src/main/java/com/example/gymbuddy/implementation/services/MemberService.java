package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMemberDataProvider;
import com.example.gymbuddy.infrastructure.exceptions.MemberNotFoundException;
import com.example.gymbuddy.infrastructure.models.daos.MemberDao;
import com.example.gymbuddy.infrastructure.services.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements IMemberService {
    private final IMemberDataProvider memberDataProvider;

    @Override
    public List<MemberDao> findAll() {
        return memberDataProvider.findAll();
    }

    @Override
    public MemberDao addMember(MemberDao memberDao) {
        return memberDataProvider.addMember(memberDao);
    }

    @Override
    public void deleteMember(int id) {
        memberDataProvider.deleteMember(memberDataProvider.findById(id).orElseThrow(() -> new MemberNotFoundException(id)).getId());
    }
}
