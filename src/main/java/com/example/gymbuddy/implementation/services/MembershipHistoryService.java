package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.daos.IMembershipHistoryDao;
import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;
import com.example.gymbuddy.infrastructure.services.IMembershipHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MembershipHistoryService implements IMembershipHistoryService {
    private final IMembershipHistoryDao membershipHistoryDataProvider;

    @Override
    public void addHistory(MembershipDto membershipDao) {
        membershipHistoryDataProvider.addHistory(membershipDao);
    }

    @Override
    public Integer getNumberOfActiveMembershipsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return membershipHistoryDataProvider.getNumberOfActiveMembershipsBetweenDates(startDate, endDate);
    }
}
