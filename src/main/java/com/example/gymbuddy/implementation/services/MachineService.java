package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.daos.IMachineDao;
import com.example.gymbuddy.infrastructure.exceptions.AlreadyExistsException;
import com.example.gymbuddy.infrastructure.exceptions.MachineNotFoundException;
import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import com.example.gymbuddy.infrastructure.services.IMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineService implements IMachineService {
    private final IMachineDao machineDataProvider;

    @Override
    public List<MachineDto> findAll(PageRequest pageRequest) {
        return machineDataProvider.findAll(pageRequest);
    }

    @Override
    public MachineDto addMachine(MachineDto machineDao) {
        var exists = machineDataProvider.findByName(machineDao.getName()).isPresent();
        if(exists) throw new AlreadyExistsException(machineDao.getClass(), machineDao.getName());
        return machineDataProvider.addMachine(machineDao);
    }

    @Override
    public void deleteMachineByName(String name) {
        machineDataProvider.deleteMachine(machineDataProvider.findByName(name).orElseThrow(() -> new MachineNotFoundException(name)).getId());
    }

    @Override
    public MachineDto replaceMachine(int id, MachineDto machineDto) {
        machineDto.setId(machineDataProvider.findById(id).orElseThrow(() -> new MachineNotFoundException(id)).getId());
        return machineDataProvider.addMachine(machineDto);
    }
}
