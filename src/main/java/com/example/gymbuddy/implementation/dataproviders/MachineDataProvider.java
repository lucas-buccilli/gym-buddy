package com.example.gymbuddy.implementation.dataproviders;

import com.example.gymbuddy.implementation.repositories.MachineRepository;
import com.example.gymbuddy.infrastructure.dataproviders.IMachineDataProvider;
import com.example.gymbuddy.infrastructure.entities.Machine;
import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MachineDataProvider implements IMachineDataProvider {
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

    @Override
    public Optional<MachineDto> findById(Integer machineId) {
        return machineRepository.findById(machineId).map(machine -> modelMapper.map(machine, MachineDto.class));
    }

    @Override
    public Optional<MachineDto> findByName(String name) {
        return machineRepository.findByName(name).map(machine -> modelMapper.map(machine, MachineDto.class));
    }
}
