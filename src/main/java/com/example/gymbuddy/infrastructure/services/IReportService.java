package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import com.example.gymbuddy.infrastructure.models.dtos.UserReportDto;

import java.time.LocalDateTime;

public interface IReportService {

    AdminReportDto getAdminReport(LocalDateTime startDate, LocalDateTime endDate);

    UserReportDto getUserReport(LocalDateTime startDate, LocalDateTime endDate, Integer memberId, Integer machineId);
}
