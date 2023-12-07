package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import com.example.gymbuddy.infrastructure.services.IMembershipHistoryService;
import com.example.gymbuddy.infrastructure.services.IReportActiveMembershipsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportActiveMembershipsService implements IReportActiveMembershipsService {
    private final IMembershipHistoryService membershipHistoryService;

    @Override
    public void addActiveMemberships(AdminReportDto adminReportDto, Period period) {
        adminReportDto.setActiveMemberships(membershipHistoryService
                .getNumberOfActiveMembershipsBetweenDates(period.startDate(), period.endDate()));
    }
}
