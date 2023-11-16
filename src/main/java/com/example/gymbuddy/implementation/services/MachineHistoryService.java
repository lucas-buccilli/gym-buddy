package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMachineHistoryDataProvider;
import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.infrastructure.services.IMachineHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MachineHistoryService implements IMachineHistoryService {
    private final IMachineHistoryDataProvider machineHistoryDataProvider;

    @Override
    public List<MachineHistoryDto> findAll() {
        return machineHistoryDataProvider.findAll();
    }

    @Override
    public MachineHistoryDto addMachineHistory(Integer memberId, Integer machineId, MachineHistoryDto machineHistoryDto) {
        return machineHistoryDataProvider.addMachineHistory(memberId, machineId, machineHistoryDto);
    }

    @Override
    public List<MachineHistoryDto> findBy(Integer memberId, Integer machineId, @Nullable LocalDateTime workoutDate) {
       return machineHistoryDataProvider.findBy(memberId, machineId, workoutDate);
    }

    @Override
    public Optional<MachineHistoryDto> findLatestWorkout(Integer memberId, Integer machineId) {
        return machineHistoryDataProvider.findLatestWorkout(memberId, machineId);
    }
}