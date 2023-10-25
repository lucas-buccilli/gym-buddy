package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;

import java.time.LocalDate;
import java.util.List;

public interface MachineHistoryService {
    List<MachineHistoryDto> findAll();

    MachineHistoryDto addMachineHistory(Integer memberId, Integer machineId, MachineHistoryDto machineHistoryDto);

    List<MachineHistoryDto> findBy(Integer memberId, Integer machineId, LocalDate workoutDate);

    List<MachineHistoryDto> findLatestWorkout(Integer memberId, Integer machineId);
}