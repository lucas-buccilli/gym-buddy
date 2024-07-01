package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.exceptions.AuthApiException;
import com.example.gymbuddy.infrastructure.exceptions.AuthCreationException;
import com.example.gymbuddy.infrastructure.models.AuthRoles;
import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.models.enums.Roles;
import com.example.gymbuddy.infrastructure.models.requests.MemberRequests;

import java.util.List;

public interface IMemberService {
    List<MemberDto> findAll(PageRequest pageRequest);

    MemberDto addMember(MemberDto dto, String email, String password, List<AuthRoles> role) throws AuthApiException;

    void deleteMember(int id);

    MemberDto replaceMember(int id, MemberDto memberDao);

    MemberDto modifyMember(int id, MemberDto partialMemberDao) throws IllegalAccessException;
}
