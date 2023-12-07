package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;

public interface IReportNumberOfVisitorsService {

    void addNumberOfVisitors(AdminReportDto adminReportDto, Period period);
}
