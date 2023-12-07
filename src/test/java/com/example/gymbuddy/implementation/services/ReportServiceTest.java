package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import com.example.gymbuddy.infrastructure.services.IReportActiveMembershipsService;
import com.example.gymbuddy.infrastructure.services.IReportDateService;
import com.example.gymbuddy.infrastructure.services.IReportMachineUsageService;
import com.example.gymbuddy.infrastructure.services.IReportNumberOfVisitorsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
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
}