package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMembershipDataProvider;
import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;
import com.example.gymbuddy.infrastructure.services.IMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipService implements IMembershipService {
    private final IMembershipDataProvider membershipDataProvider;

    @Override
    public List<MembershipDto> findAll() {
        return membershipDataProvider.findAll();
    }

    @Override
    public MembershipDto addMembership(MembershipDto membershipDto) {
        return membershipDataProvider.addMembership(membershipDto);
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
