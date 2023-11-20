package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMachineDataProvider;
import com.example.gymbuddy.infrastructure.exceptions.AlreadyExistsException;
import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import com.example.gymbuddy.infrastructure.services.IMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineService implements IMachineService {
    private final IMachineDataProvider machineDataProvider;

    @Override
    public List<MachineDto> findAll() {
        return machineDataProvider.findAll();
    }

    @Override
    public MachineDto addMachine(MachineDto machineDto) {
        var exists = machineDataProvider.findByName(machineDto.getName()).isPresent();
        if(exists) throw new AlreadyExistsException(machineDto);
        return machineDataProvider.addMachine(machineDto);
    }
}
