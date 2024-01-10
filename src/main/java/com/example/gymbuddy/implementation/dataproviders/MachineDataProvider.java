package com.example.gymbuddy.implementation.dataproviders;

import com.example.gymbuddy.implementation.repositories.MachineRepository;
import com.example.gymbuddy.infrastructure.dataproviders.IMachineDataProvider;
import com.example.gymbuddy.infrastructure.entities.Machine;
import com.example.gymbuddy.infrastructure.exceptions.MachineNotFoundException;
import com.example.gymbuddy.infrastructure.models.daos.MachineDao;
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
    public List<MachineDao> findAll() {
        return machineRepository.findAll().stream()
                .map(machineEntity -> modelMapper.map(machineEntity, MachineDao.class))
                .toList();
    }

    @Override
    public MachineDao addMachine(MachineDao machineDao) {
        var machine = modelMapper.map(machineDao, Machine.class);
        return modelMapper.map(machineRepository.save(machine), MachineDao.class);
    }

    @Override
    public Optional<MachineDao> findById(Integer machineId) {
        return machineRepository.findById(machineId).map(machine -> modelMapper.map(machine, MachineDao.class));
    }

    @Override
    public Optional<MachineDao> findByName(String name) {
        return machineRepository.findByName(name).map(machine -> modelMapper.map(machine, MachineDao.class));
    }

    @Override
    public void deleteMachine(int machineId) {
        machineRepository.deleteById(machineId);
    }
}
