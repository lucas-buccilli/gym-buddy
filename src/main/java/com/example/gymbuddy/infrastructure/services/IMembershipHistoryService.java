package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.daos.MembershipDao;

import java.time.LocalDateTime;

public interface IMembershipHistoryService {
    void addHistory(MembershipDao membershipDao);

    Integer getNumberOfActiveMembershipsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
}
