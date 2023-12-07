package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import com.example.gymbuddy.infrastructure.services.IMembershipHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportActiveMembershipsServiceTest {
    @Mock
    IMembershipHistoryService membershipHistoryService;

    @InjectMocks
    ReportActiveMembershipsService reportActiveMembershipsService;

    @Test
    void shouldSddActiveMemberships() {
        int activeMemberships = 5;
        AdminReportDto dto = new AdminReportDto();
        Period period = new Period(LocalDateTime.MIN, LocalDateTime.MAX);

        when(membershipHistoryService.getNumberOfActiveMembershipsBetweenDates(any(), any()))
                .thenReturn(activeMemberships);

        reportActiveMembershipsService.addActiveMemberships(dto, period);
        verify(membershipHistoryService).getNumberOfActiveMembershipsBetweenDates(period.startDate(), period.endDate());
        assertEquals(activeMemberships, dto.getActiveMemberships());
    }
}