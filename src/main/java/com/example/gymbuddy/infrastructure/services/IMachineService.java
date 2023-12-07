package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.daos.MachineDao;

import java.util.List;

public interface IMachineService {
    List<MachineDao> findAll();

    MachineDao addMachine(MachineDao machineDao);
}
