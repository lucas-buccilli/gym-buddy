package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import com.example.gymbuddy.infrastructure.models.dtos.UserReportDto;
import com.example.gymbuddy.infrastructure.services.IReportActiveMembershipsService;
import com.example.gymbuddy.infrastructure.services.IReportDateService;
import com.example.gymbuddy.infrastructure.services.IReportMachineProgressService;
import com.example.gymbuddy.infrastructure.services.IReportMachineUsageService;
import com.example.gymbuddy.infrastructure.services.IReportNumberOfVisitorsService;
import com.example.gymbuddy.infrastructure.services.IReportNumberOfWorkoutDaysService;
import com.example.gymbuddy.infrastructure.services.IReportService;
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
    private final IReportMachineProgressService reportMachineProgress;
    private final IReportNumberOfWorkoutDaysService reportNumberOfWorkoutDays;

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

    @Override
    public UserReportDto getUserReport(LocalDateTime startDate, LocalDateTime endDate, Integer memberId, Integer machineId) {
        var userReportDto = new UserReportDto();
        Period period = new Period(startDate, endDate);

        List<Runnable> services = List.of(
                () -> reportDateService.addStartDate(userReportDto, period),
                () -> reportDateService.addEndDate(userReportDto, period),
                () -> reportMachineProgress.addMachineProgress(userReportDto, period, memberId, machineId),
                () -> reportNumberOfWorkoutDays.addNumberOfWorkoutDays(userReportDto, period, memberId)
        );
        services.forEach(Runnable::run);
        return userReportDto;
    }
}
