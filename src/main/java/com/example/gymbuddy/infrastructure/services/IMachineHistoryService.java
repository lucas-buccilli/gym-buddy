package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IMachineHistoryService {
//    List<MachineHistoryDto> findAll(PageRequest pageRequest);

    MachineHistoryDto addMachineHistory(Integer memberId, Integer machineId, MachineHistoryDto machineHistoryDao);

    List<MachineHistoryDto> findBy(Integer memberId, Integer machineId, PageRequest pageRequest);

    Optional<MachineHistoryDto> findLatestWorkout(Integer memberId, Integer machineId);
}