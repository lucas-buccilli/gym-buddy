package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMembershipDataProvider;
import com.example.gymbuddy.infrastructure.models.daos.MembershipDao;
import com.example.gymbuddy.infrastructure.services.IMembershipHistoryService;
import com.example.gymbuddy.infrastructure.services.IMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipService implements IMembershipService {
    private final IMembershipDataProvider membershipDataProvider;
    private final IMembershipHistoryService membershipHistoryService;

    @Override
    public List<MembershipDao> findAll() {
        return membershipDataProvider.findAll();
    }

    @Override
    public MembershipDao addMembership(MembershipDao membershipDao) {
        var membership = membershipDataProvider.addMembership(membershipDao);
        membershipHistoryService.addHistory(membership);
        return membership;
    }
}
