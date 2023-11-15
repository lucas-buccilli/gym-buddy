package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMemberDataProvider;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.services.IMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements IMemberService {
    private final IMemberDataProvider memberDataProvider;

    @Override
    public List<MemberDto> findAll() {
        return memberDataProvider.findAll();
    }

    @Override
    public MemberDto addMember(MemberDto memberDto) {
        return memberDataProvider.addMember(memberDto);
    }
}
