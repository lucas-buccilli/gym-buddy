package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;

import java.util.List;

public interface IMachineService {
    List<MachineDto> findAll();

    MachineDto addMachine(MachineDto machineDao);

    void deleteMachineByName(String name);

    MachineDto replaceMachine(int id, MachineDto machineDto);
}
