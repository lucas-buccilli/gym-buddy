package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.implementation.patchers.MemberPatcher;
import com.example.gymbuddy.infrastructure.daos.IMemberDao;
import com.example.gymbuddy.infrastructure.exceptions.AuthCreationException;
import com.example.gymbuddy.infrastructure.exceptions.MemberNotFoundException;
import com.example.gymbuddy.infrastructure.models.AuthRoles;
import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.models.enums.Roles;
import com.example.gymbuddy.infrastructure.models.requests.MemberRequests;
import com.example.gymbuddy.infrastructure.services.IAuthService;
import com.example.gymbuddy.infrastructure.services.IMemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements IMemberService {
    private final IMemberDao memberDataProvider;
    private final IAuthService authService;

    @Override
    public List<MemberDto> findAll(PageRequest pageRequest) {
        return memberDataProvider.findAll(pageRequest);
    }

    @Override
    public MemberDto addMember(MemberDto memberDto, String email, String password, List<AuthRoles> roles) throws AuthCreationException {
        var userId = authService.createUser(email, password);
        authService.addRole(userId, roles);
        memberDto.setAuthId(userId);
        return memberDataProvider.saveMember(memberDto);
    }

    @Override
    public void deleteMember(int id) {
        memberDataProvider.deleteMember(memberDataProvider.findById(id).orElseThrow(() -> new MemberNotFoundException(id)).getId());
    }

    @Override
    public MemberDto modifyMember(int id, MemberDto partialMemberDto) throws IllegalAccessException {
        MemberDto memberDto = memberDataProvider.findById(id).orElseThrow(() -> new MemberNotFoundException(id));
        var updatedMemberDto = MemberPatcher.patchMember(memberDto, partialMemberDto);
        return memberDataProvider.saveMember(updatedMemberDto);
    }

    @Override
    public MemberDto replaceMember(int id, MemberDto memberDao) {
        memberDao.setId(memberDataProvider.findById(id).orElseThrow(() -> new MemberNotFoundException(id)).getId());
        return memberDataProvider.saveMember(memberDao);
    }
}
