package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMachineHistoryDataProvider;
import com.example.gymbuddy.infrastructure.models.daos.MachineHistoryDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MachineHistoryServiceTest {
    @InjectMocks
    private MachineHistoryService machineHistoryService;

    @Mock
    private IMachineHistoryDataProvider machineHistoryDataProvider;


    @Test
    void findAll() {
        List<MachineHistoryDao> machineHistoryDaos = List.of(new MachineHistoryDao());
        when(machineHistoryDataProvider.findAll()).thenReturn(machineHistoryDaos);
        var results = machineHistoryService.findAll();
        assertEquals(machineHistoryDaos, results);
        verify(machineHistoryDataProvider).findAll();
    }

    @Test
    void addMachineHistory() {
        var memberId = 1;
        var machineId = 1;
        var machineHistoryDto = MachineHistoryDao.builder().memberId(memberId).machineId(machineId).build();
        when(machineHistoryDataProvider.addMachineHistory
            (anyInt(), anyInt(), any())).thenReturn(machineHistoryDto);
        var result = machineHistoryService.addMachineHistory(memberId, machineId, machineHistoryDto);
        assertEquals(machineHistoryDto, result);
        verify(machineHistoryDataProvider).addMachineHistory(memberId, machineId, machineHistoryDto);

    }

    @Test
    void findByMachineIdAndMemberId() {
        var memberId = 1;
        var machineId = 1;
        var machineHistoryDto = MachineHistoryDao.builder().memberId(memberId).machineId(machineId).build();
        var machineHistoryDtos = List.of(machineHistoryDto);
        when(machineHistoryDataProvider.findBy(anyInt(), anyInt(), any())).thenReturn(machineHistoryDtos);
        var result = machineHistoryService.findBy(memberId, machineId, null);
        assertEquals(machineHistoryDtos, result);
        verify(machineHistoryDataProvider).findBy(memberId, machineId, null);
        assertEquals(memberId, machineHistoryDtos.get(0).getMemberId());
        assertEquals(machineId, machineHistoryDtos.get(0).getMachineId());
    }

    @Test
    void findLatestWorkout() {
        var memberId = 1;
        var machineId = 1;
        var machineHistoryDto = MachineHistoryDao.builder().memberId(memberId).machineId(machineId).build();
        var machineHistoryDtoOptional = Optional.of(machineHistoryDto);
        when(machineHistoryDataProvider.findLatestWorkout(anyInt(), anyInt()))
                .thenReturn(machineHistoryDtoOptional);
        var result = machineHistoryService.findLatestWorkout(memberId, machineId);
        assertEquals(machineHistoryDtoOptional, result);
        verify(machineHistoryDataProvider).findLatestWorkout(memberId, machineId);
    }
}