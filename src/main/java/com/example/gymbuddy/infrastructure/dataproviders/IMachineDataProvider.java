package com.example.gymbuddy.infrastructure.dataproviders;

import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;

import java.util.List;
import java.util.Optional;

public interface IMachineDataProvider {

    List<MachineDto> findAll();

    MachineDto addMachine(MachineDto machineDto);

    Optional<MachineDto> findById(Integer machineId);

    Optional<MachineDto> findByName(String name);
}
