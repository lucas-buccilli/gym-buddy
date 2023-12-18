package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.UserReportDto;
import jakarta.persistence.criteria.CriteriaBuilder;

public interface IReportMachineProgressService {
    void addMachineProgress(UserReportDto dto, Period period, Integer memberId, Integer machineId);
}
