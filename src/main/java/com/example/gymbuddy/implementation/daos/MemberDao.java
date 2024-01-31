package com.example.gymbuddy.implementation.daos;

import com.example.gymbuddy.implementation.repositories.MemberRepository;
import com.example.gymbuddy.infrastructure.daos.IMemberDao;
import com.example.gymbuddy.infrastructure.entities.Member;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberDao implements IMemberDao {
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    @Override
    public List<MemberDto> findAll() {
        return memberRepository.findAll().stream()
                .map(memberEntity -> modelMapper.map(memberEntity, MemberDto.class))
                .toList();
    }

    @Override
    public MemberDto saveMember(MemberDto memberDao) {
        var member = modelMapper.map(memberDao, Member.class);
        return modelMapper.map(memberRepository.save(member), MemberDto.class);
    }

    @Override
    public Optional<MemberDto> findById(Integer memberId) {
        return memberRepository.findById(memberId).map(member -> modelMapper.map(member, MemberDto.class));
    }

    @Override
    public Optional<MemberDto> findByAuthId(String authId) {
        return memberRepository.findByAuthId(authId).map(member -> modelMapper.map(member, MemberDto.class));
    }

    @Override
    public void deleteMember(int id) {
        memberRepository.deleteById(id);
    }
}
