package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.daos.IMachineHistoryDao;
import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import com.example.gymbuddy.infrastructure.services.IReportMachineUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportMachineUsageService implements IReportMachineUsageService {
    private final IMachineHistoryDao machineHistoryDataProvider;
    @Override
    public void addMachineUsage(AdminReportDto adminReportDto, Period period) {
        adminReportDto.setMachineUsage(machineHistoryDataProvider.getMachineUsage(period.startDate(), period.endDate()));
    }
}
