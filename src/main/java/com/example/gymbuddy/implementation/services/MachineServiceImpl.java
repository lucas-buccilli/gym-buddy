package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.implementation.repositories.MachineRepository;
import com.example.gymbuddy.infrastructure.entities.Machine;
import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import com.example.gymbuddy.infrastructure.services.MachineService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineServiceImpl implements MachineService {
    private final MachineRepository machineRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MachineDto> findAll() {
        return machineRepository.findAll().stream()
                .map(machineEntity -> modelMapper.map(machineEntity, MachineDto.class))
                .toList();
    }

    @Override
    public MachineDto addMachine(MachineDto machineDto) {
        var machine = modelMapper.map(machineDto, Machine.class);
        return modelMapper.map(machineRepository.save(machine), MachineDto.class);
    }
}
