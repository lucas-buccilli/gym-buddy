package com.example.gymbuddy.infrastructure.daos;

import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;

import java.time.LocalDateTime;

public interface IMembershipHistoryDao {
    void addHistory(MembershipDto membershipDao);
    Integer getNumberOfActiveMembershipsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
}
