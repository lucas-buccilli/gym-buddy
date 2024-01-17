package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.daos.IMachineHistoryDao;
import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import com.example.gymbuddy.infrastructure.services.IReportNumberOfVisitorsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportNumberOfVisitorsService implements IReportNumberOfVisitorsService {
    private final IMachineHistoryDao machineHistoryDataProvider;
    @Override
    public void addNumberOfVisitors(AdminReportDto adminReportDto, Period period) {
        adminReportDto.setNumberOfVisitors(
                machineHistoryDataProvider.getNumberOfVisitorsWithinTimeframe(period.startDate(), period.endDate()));
    }
}
