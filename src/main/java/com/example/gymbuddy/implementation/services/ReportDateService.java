package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.models.IEndPeriod;
import com.example.gymbuddy.infrastructure.models.IStartPeriod;
import com.example.gymbuddy.infrastructure.models.dtos.IDatedReportDto;
import com.example.gymbuddy.infrastructure.services.IReportDateService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class ReportDateService implements IReportDateService {

    @Override
    public void addStartDate(IDatedReportDto datedReportDto, IStartPeriod startPeriod) {
        datedReportDto.setStartDate(startPeriod.startDate());
    }

    @Override
    public void addEndDate(IDatedReportDto datedReportDto, IEndPeriod endPeriod) {
        datedReportDto.setEndDate(endPeriod.endDate());
    }
}
