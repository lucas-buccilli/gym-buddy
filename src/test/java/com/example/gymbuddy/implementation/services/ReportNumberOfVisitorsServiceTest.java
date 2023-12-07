package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMachineHistoryDataProvider;
import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ReportNumberOfVisitorsServiceTest {

    @Mock
    IMachineHistoryDataProvider machineHistoryDataProvider;

    @InjectMocks
    ReportNumberOfVisitorsService reportNumberOfVisitorsService;

    @Test
    void shouldAddNumberOfVisitors() {
        Period period = new Period(LocalDateTime.MIN, LocalDateTime.MAX);
        AdminReportDto adminReportDto = new AdminReportDto();
        int count = 1;

        when(machineHistoryDataProvider.getNumberOfVisitorsWithinTimeframe(any(), any())).thenReturn(count);
        reportNumberOfVisitorsService.addNumberOfVisitors(adminReportDto, period);
        assertEquals(count, adminReportDto.getNumberOfVisitors());
    }

}