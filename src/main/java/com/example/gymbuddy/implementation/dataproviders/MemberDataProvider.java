package com.example.gymbuddy.implementation.dataproviders;

import com.example.gymbuddy.implementation.repositories.MachineRepository;
import com.example.gymbuddy.implementation.repositories.MemberRepository;
import com.example.gymbuddy.infrastructure.dataproviders.IMachineDataProvider;
import com.example.gymbuddy.infrastructure.dataproviders.IMemberDataProvider;
import com.example.gymbuddy.infrastructure.entities.Machine;
import com.example.gymbuddy.infrastructure.entities.Member;
import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
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

    @Override
    public Optional<MemberDto> findById(Integer memberId) {
        return memberRepository.findById(memberId).map(member -> modelMapper.map(member, MemberDto.class));
    }
}
