package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.daos.IMembershipDao;
import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;
import com.example.gymbuddy.infrastructure.services.IMembershipHistoryService;
import com.example.gymbuddy.infrastructure.services.IMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipService implements IMembershipService {
    private final IMembershipDao membershipDataProvider;
    private final IMembershipHistoryService membershipHistoryService;

    @Override
    public List<MembershipDto> findAll() {
        return membershipDataProvider.findAll();
    }

    @Override
    public MembershipDto addMembership(MembershipDto membershipDao) {
        var membership = membershipDataProvider.addMembership(membershipDao);
        membershipHistoryService.addHistory(membership);
        return membership;
    }
}
