package com.example.gymbuddy.infrastructure.dataproviders;

import com.example.gymbuddy.infrastructure.models.daos.MembershipDao;

import java.time.LocalDateTime;

public interface IMembershipHistoryDataProvider {
    void addHistory(MembershipDao membershipDao);
    Integer getNumberOfActiveMembershipsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
}
