package com.example.gymbuddy.infrastructure.dataproviders;

import com.example.gymbuddy.infrastructure.models.daos.MachineDao;

import java.util.List;
import java.util.Optional;

public interface IMachineDataProvider {

    List<MachineDao> findAll();

    MachineDao addMachine(MachineDao machineDao);

    Optional<MachineDao> findById(Integer machineId);

    Optional<MachineDao> findByName(String name);
}
