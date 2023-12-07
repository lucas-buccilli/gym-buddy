package com.example.gymbuddy.implementation.dataproviders;

import com.example.gymbuddy.implementation.repositories.MemberRepository;
import com.example.gymbuddy.infrastructure.dataproviders.IMemberDataProvider;
import com.example.gymbuddy.infrastructure.entities.Member;
import com.example.gymbuddy.infrastructure.models.daos.MemberDao;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberDataProvider implements IMemberDataProvider {
    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;

    @Override
    public List<MemberDao> findAll() {
        return memberRepository.findAll().stream()
                .map(memberEntity -> modelMapper.map(memberEntity, MemberDao.class))
                .toList();
    }

    @Override
    public MemberDao addMember(MemberDao memberDao) {
        var member = modelMapper.map(memberDao, Member.class);
        return modelMapper.map(memberRepository.save(member), MemberDao.class);
    }

    @Override
    public Optional<MemberDao> findById(Integer memberId) {
        return memberRepository.findById(memberId).map(member -> modelMapper.map(member, MemberDao.class));
    }
}
