package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;

import java.time.LocalDateTime;

public interface IReportService {

    AdminReportDto getAdminReport(LocalDateTime startDate, LocalDateTime endDate);
}
