package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMachineDataProvider;
import com.example.gymbuddy.infrastructure.exceptions.AlreadyExistsException;
import com.example.gymbuddy.infrastructure.models.daos.MachineDao;
import com.example.gymbuddy.infrastructure.services.IMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineService implements IMachineService {
    private final IMachineDataProvider machineDataProvider;

    @Override
    public List<MachineDao> findAll() {
        return machineDataProvider.findAll();
    }

    @Override
    public MachineDao addMachine(MachineDao machineDao) {
        var exists = machineDataProvider.findByName(machineDao.getName()).isPresent();
        if(exists) throw new AlreadyExistsException(machineDao.getClass(), machineDao.getName());
        return machineDataProvider.addMachine(machineDao);
    }
}
