package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;

public interface IReportActiveMembershipsService {

    void addActiveMemberships(AdminReportDto adminReportDto, Period period);
}
