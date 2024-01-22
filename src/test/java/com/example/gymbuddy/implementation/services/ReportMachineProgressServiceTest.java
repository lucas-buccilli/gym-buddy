package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.daos.IMachineHistoryDao;
import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.infrastructure.models.dtos.MachineProgressDto;
import com.example.gymbuddy.infrastructure.models.dtos.UserReportDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportMachineProgressServiceTest {
    @InjectMocks
    private ReportMachineProgressService reportMachineProgressService;

    @Mock
    IMachineHistoryDao machineHistoryDataProvider;

    @Mock
    ModelMapper modelMapper;

    @Test
    void addMachineProgress() {
        var dto = new UserReportDto();
        var period = new Period(LocalDateTime.MIN, LocalDateTime.MAX);
        var machineId = 1;
        var memberId = 1;

        Map<String, List<MachineHistoryDto>> machineProgressMap = new HashMap<>();
        var machineHistoryDao = new MachineHistoryDto();
        machineProgressMap.put("MachineName", List.of(machineHistoryDao));
        var dao = modelMapper.map(machineHistoryDao, MachineProgressDto.class);

        when(machineHistoryDataProvider.getMachineProgress(any(), any(), anyInt(), anyInt())).thenReturn(machineProgressMap);
        reportMachineProgressService.addMachineProgress(
                new UserReportDto(), new Period(LocalDateTime.MIN, LocalDateTime.MAX), memberId, machineId);

        verify(machineHistoryDataProvider).getMachineProgress(period.startDate(), period.endDate(), memberId, machineId);


    }
}