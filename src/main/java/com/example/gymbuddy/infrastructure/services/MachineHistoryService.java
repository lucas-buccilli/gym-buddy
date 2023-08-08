package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;

import java.util.List;

public interface MachineHistoryService {
    List<MachineHistoryDto> findAll();

    MachineHistoryDto addMachineHistory(MachineHistoryDto machineHistoryDto);
}
