package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;

import java.time.LocalDateTime;

public interface IMembershipHistoryService {
    void addHistory(MembershipDto membershipDao);

    Integer getNumberOfActiveMembershipsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
}
