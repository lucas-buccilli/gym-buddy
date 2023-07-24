package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.implementation.repositories.MemberRepository;
import com.example.gymbuddy.infrastructure.entities.Member;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    @Override
    public List<MemberDto> findAll() {
        return memberRepository.findAll().stream()
                .map(memberEntity -> modelMapper.map(memberEntity, MemberDto.class))
                .toList();
    }

    @Override
    public MemberDto addMember(MemberDto memberDto) {
        var member = modelMapper.map(memberDto, Member.class);
        return modelMapper.map(memberRepository.save(member), MemberDto.class);
    }
}
