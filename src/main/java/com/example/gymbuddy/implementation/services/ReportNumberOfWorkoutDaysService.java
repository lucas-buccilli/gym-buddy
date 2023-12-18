package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMachineHistoryDataProvider;
import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.UserReportDto;
import com.example.gymbuddy.infrastructure.services.IReportNumberOfWorkoutDaysService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportNumberOfWorkoutDaysService implements IReportNumberOfWorkoutDaysService {
    private final IMachineHistoryDataProvider machineHistoryDataProvider;
    @Override
    public void addNumberOfWorkoutDays(UserReportDto userReportDto, Period period, Integer memberId) {
        userReportDto.setNumberOfWorkouts(
                machineHistoryDataProvider.getNumberOfWorkoutDays(period.startDate(), period.endDate(), memberId));
    }
}
