package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.UserReportDto;

public interface IReportMachineProgressService {
    void addMachineProgress(UserReportDto dto, Period period, Integer memberId, Integer machineId);
}
