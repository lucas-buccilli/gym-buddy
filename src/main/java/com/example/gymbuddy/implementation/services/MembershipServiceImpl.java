package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.implementation.repositories.MembershipRepository;
import com.example.gymbuddy.infrastructure.entities.Membership;
import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;
import com.example.gymbuddy.infrastructure.services.MembershipService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MembershipServiceImpl implements MembershipService {
    private MembershipRepository membershipRepository;
    private ModelMapper modelMApper;

    @Override
    public List<MembershipDto> findAll() {
        return membershipRepository.findAll().stream()
                .map(membershipEntity -> modelMApper.map(membershipEntity, MembershipDto.class))
                .toList();
    }

    @Override
    public MembershipDto addMembership(MembershipDto membershipDto) {
        var membership = modelMApper.map(membershipDto, Membership.class);
        return modelMApper.map(membershipRepository.save(membership), MembershipDto.class);
    }

    @Override
    public boolean isActive(MembershipDto membershipDto) {
        return false;
    }
}
