package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import com.example.gymbuddy.infrastructure.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService implements IReportService {
    private final IReportDateService reportDateService;
    private final IReportMachineUsageService reportMachineUsageService;
    private final IReportActiveMembershipsService reportActiveMembershipsService;
    private final IReportNumberOfVisitorsService reportNumberOfVisitorsService;

    @Override
    public AdminReportDto getAdminReport(LocalDateTime startDate, LocalDateTime endDate) {

        var adminReportDto = new AdminReportDto();
        Period period = new Period(startDate, endDate);

        List<Runnable> services = List.of(
                () -> reportDateService.addStartDate(adminReportDto, period),
                () -> reportDateService.addEndDate(adminReportDto, period),
                () -> reportNumberOfVisitorsService.addNumberOfVisitors(adminReportDto, period),
                () -> reportActiveMembershipsService.addActiveMemberships(adminReportDto, period),
                () -> reportMachineUsageService.addMachineUsage(adminReportDto, period));

        services.forEach(Runnable::run);
        return adminReportDto;
    }
}
