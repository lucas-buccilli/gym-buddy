package com.example.gymbuddy.implementation.daos;

import com.example.gymbuddy.implementation.repositories.MachineRepository;
import com.example.gymbuddy.infrastructure.daos.IMachineDao;
import com.example.gymbuddy.infrastructure.entities.Machine;
import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MachineDao implements IMachineDao {
    private final MachineRepository machineRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MachineDto> findAll(PageRequest pageRequest) {
        return machineRepository.findAll(pageRequest.toSpecification(), pageRequest.toPageable()).stream()
                .map(machineEntity -> modelMapper.map(machineEntity, MachineDto.class))
                .toList();
    }

    @Override
    public MachineDto addMachine(MachineDto machineDao) {
        var machine = modelMapper.map(machineDao, Machine.class);
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

    @Override
    public void deleteMachine(int machineId) {
        machineRepository.deleteById(machineId);
    }
}
