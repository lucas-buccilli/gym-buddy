package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMachineHistoryDataProvider;
import com.example.gymbuddy.infrastructure.entities.Machine;
import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportMachineUsageServiceTest {
    @Mock
    IMachineHistoryDataProvider machineHistoryDataProvider;

    @InjectMocks
    ReportMachineUsageService reportMachineUsageService;
    @Test
    void shouldAddMachineUsage() {
        AdminReportDto dto = new AdminReportDto();
        Period period = new Period(LocalDateTime.MIN, LocalDateTime.MAX);
        Machine machine = new Machine();
        machine.setName("Machine");
        MachineHistory machineHistory = new MachineHistory();
        machineHistory.setMachine(machine);
        Map<String, Long> machineUsageMap = new HashMap<String, Long>();
        machineUsageMap.put("Machine", 1L);

        when(machineHistoryDataProvider.getMachineUsage(any(), any())).thenReturn(machineUsageMap);

        reportMachineUsageService.addMachineUsage(dto, period);
        verify(machineHistoryDataProvider).getMachineUsage(period.startDate(), period.endDate());
        assertEquals(machineUsageMap, dto.getMachineUsage());
    }
}