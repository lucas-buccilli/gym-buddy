package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.implementation.patchers.MemberPatcher;
import com.example.gymbuddy.infrastructure.daos.IMemberDao;
import com.example.gymbuddy.infrastructure.exceptions.MemberNotFoundException;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.services.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements IMemberService {
    private final IMemberDao memberDataProvider;

    @Override
    public List<MemberDto> findAll() {
        return memberDataProvider.findAll();
    }

    @Override
    public MemberDto addMember(MemberDto memberDao) {
        return memberDataProvider.addMember(memberDao);
    }

    @Override
    public void deleteMember(int id) {
        memberDataProvider.deleteMember(memberDataProvider.findById(id).orElseThrow(() -> new MemberNotFoundException(id)).getId());
    }

    @Override
    public MemberDto modifyMember(int id, MemberDto partialMemberDto) throws IllegalAccessException {
        MemberDto memberDto = memberDataProvider.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
        MemberPatcher.patchMember(memberDto, partialMemberDto);
        return memberDataProvider.modifyMember(id, memberDto);
    }

    @Override
    public MemberDto replaceMember(int id, MemberDto memberDao) {
        memberDao.setId(memberDataProvider.findById(id).orElseThrow(() -> new MemberNotFoundException(id)).getId());
        return memberDataProvider.editMember(memberDao);
    }
}
