package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import com.example.gymbuddy.infrastructure.services.IReportDateService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportDateServiceTest {
    AdminReportDto dto = new AdminReportDto();
    Period period = new Period(LocalDateTime.MIN, LocalDateTime.MAX);
    IReportDateService reportDateService = new ReportDateService();

    @Test
    void addStartDate() {
        reportDateService.addStartDate(dto, period);
        assertEquals(period.startDate(), dto.getStartDate());
    }

    @Test
    void addEndDate() {
        reportDateService.addEndDate(dto, period);
        assertEquals(period.endDate(), dto.getEndDate());
    }
}