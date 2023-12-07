package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.IEndPeriod;
import com.example.gymbuddy.infrastructure.models.IStartPeriod;
import com.example.gymbuddy.infrastructure.models.dtos.IDatedReportDto;

import java.time.LocalDateTime;

public interface IReportDateService {

    void addStartDate(IDatedReportDto iDatedReportDto, IStartPeriod startPeriod);
    void addEndDate(IDatedReportDto iDatedReportDto, IEndPeriod endPeriod);

}
