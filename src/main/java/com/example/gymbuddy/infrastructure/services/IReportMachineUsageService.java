package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;

public interface IReportMachineUsageService {

    void addMachineUsage(AdminReportDto adminReportDto, Period period);
}
