package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import com.example.gymbuddy.infrastructure.models.dtos.UserReportDto;
import com.example.gymbuddy.infrastructure.services.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {
    @InjectMocks
    ReportService reportService;
    @Mock
    IReportDateService reportDateService;
    @Mock
    IReportMachineUsageService reportMachineUsageService;
    @Mock
    IReportActiveMembershipsService reportActiveMembershipsService;
    @Mock
    IReportNumberOfVisitorsService reportNumberOfVisitorsService;
    @Mock
    IReportMachineProgressService reportMachineProgressService;

    @Mock
    IReportNumberOfWorkoutDaysService reportNumberOfWorkoutDaysService;

    @Test
    void shouldPopulateAdminReport() {
        var dtoCaptor = ArgumentCaptor.forClass(AdminReportDto.class);
        var period = new Period(LocalDateTime.MAX, LocalDateTime.MIN);
        AdminReportDto dto;

        reportService.getAdminReport(period.startDate(), period.endDate());

        //Get Dto from out first service call and make sure we are using the same dto for each subsequent call
        verify(reportDateService).addStartDate(dtoCaptor.capture(), eq(period));
        assertNotNull(dtoCaptor.getValue());
        dto = dtoCaptor.getValue();

        verify(reportDateService).addEndDate(same(dto), eq(period));
        verify(reportNumberOfVisitorsService).addNumberOfVisitors(same(dto), eq(period));
        verify(reportActiveMembershipsService).addActiveMemberships(same(dto), eq(period));
        verify(reportMachineUsageService).addMachineUsage(same(dto), eq(period));
    }

    @Test
    void shouldPopulateUserReport() {
        var dtoCaptor = ArgumentCaptor.forClass(UserReportDto.class);
        var period = new Period(LocalDateTime.MAX, LocalDateTime.MIN);
        UserReportDto dto;
        var memberId = 1;
        var machineId = 2;
        reportService.getUserReport(period.startDate(), period.endDate(), memberId, machineId);

        verify(reportDateService).addStartDate(dtoCaptor.capture(), eq(period));
        assertNotNull(dtoCaptor.getValue());
        dto = dtoCaptor.getValue();

        verify(reportDateService).addEndDate(same(dto), eq(period));
        verify(reportMachineProgressService).addMachineProgress(same(dto), eq(period), eq(memberId), eq(machineId));
    }
}