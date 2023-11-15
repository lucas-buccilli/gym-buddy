package com.example.gymbuddy.infrastructure.dataproviders;

import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IMachineHistoryDataProvider {
    List<MachineHistoryDto> findAll();

    MachineHistoryDto addMachineHistory(Integer memberId, Integer machineId, MachineHistoryDto machineHistoryDto);

    List<MachineHistoryDto> findBy(Integer memberId, Integer machineId, LocalDateTime workoutDate);

    Optional<MachineHistoryDto> findLatestWorkout(Integer memberId, Integer machineId);
}
