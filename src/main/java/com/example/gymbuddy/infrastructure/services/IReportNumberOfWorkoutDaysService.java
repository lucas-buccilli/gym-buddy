package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.UserReportDto;

public interface IReportNumberOfWorkoutDaysService {
    void addNumberOfWorkoutDays(UserReportDto userReportDto, Period period, Integer memberId);
}
