package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMachineHistoryDataProvider;
import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.UserReportDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportNumberOfWorkoutDaysServiceTest {

    @InjectMocks
    ReportNumberOfWorkoutDaysService reportNumberOfWorkoutDaysService;

    @Mock
    IMachineHistoryDataProvider machineHistoryDataProvider;

    @Test
    void addNumberOfWorkoutDays() {
        UserReportDto dto = new UserReportDto();
        Period period = new Period(LocalDateTime.MIN, LocalDateTime.MAX);
        var memberId = 2;
        Integer numberOfWorkouts = 5;

        when(machineHistoryDataProvider.getNumberOfWorkoutDays(any(), any(), anyInt())).thenReturn(numberOfWorkouts);

        reportNumberOfWorkoutDaysService.addNumberOfWorkoutDays(dto, period, memberId);
        verify(machineHistoryDataProvider).getNumberOfWorkoutDays(period.startDate(), period.endDate(), memberId);
        assertEquals(numberOfWorkouts, dto.getNumberOfWorkouts());
    }
}