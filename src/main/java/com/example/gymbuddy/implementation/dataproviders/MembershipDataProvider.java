package com.example.gymbuddy.implementation.dataproviders;

import com.example.gymbuddy.implementation.repositories.MembershipRepository;
import com.example.gymbuddy.infrastructure.dataproviders.IMembershipDataProvider;
import com.example.gymbuddy.infrastructure.entities.Membership;
import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipDataProvider implements IMembershipDataProvider {
    private final MembershipRepository membershipRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MembershipDto> findAll() {
        return membershipRepository.findAll().stream()
                .map(membershipEntity -> modelMapper.map(membershipEntity, MembershipDto.class))
                .toList();
    }

    @Override
    public MembershipDto addMembership(MembershipDto membershipDto) {
        var membership = modelMapper.map(membershipDto, Membership.class);
        return modelMapper.map(membershipRepository.save(membership), MembershipDto.class);
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
