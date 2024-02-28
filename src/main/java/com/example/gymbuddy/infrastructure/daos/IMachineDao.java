package com.example.gymbuddy.infrastructure.daos;

import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;

import java.util.List;
import java.util.Optional;

public interface IMachineDao {

    List<MachineDto> findAll(PageRequest pageRequest);

    MachineDto addMachine(MachineDto machineDao);

    Optional<MachineDto> findById(Integer machineId);

    Optional<MachineDto> findByName(String name);

    void deleteMachine(int machineId);
}
