package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.implementation.repositories.MachineHistoryRepository;
import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.infrastructure.services.MachineHistoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.gymbuddy.implementation.database.specifications.MachineHistorySpecifications.hasMachineId;
import static com.example.gymbuddy.implementation.database.specifications.MachineHistorySpecifications.hasMemberId;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
public class MachineHistoryServiceImpl implements MachineHistoryService {
    private final MachineHistoryRepository machineHistoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MachineHistoryDto> findAll() {
        return machineHistoryRepository.findAll().stream()
                .map(machineHistoryEntity -> modelMapper.map(machineHistoryEntity, MachineHistoryDto.class))
                .toList();
    }

    @Override
    public MachineHistoryDto addMachineHistory(MachineHistoryDto machineHistoryDto) {
        var machineHistory = modelMapper.map(machineHistoryDto, MachineHistory.class);
        return modelMapper.map(machineHistoryRepository.save(machineHistory), MachineHistoryDto.class);
    }

    @Override
    public List<MachineHistoryDto> findByMachineIdAndMemberId(int machineId, int memberId) {
        var histories = machineHistoryRepository.findAll(
                where(hasMachineId(machineId))
                        .and(hasMemberId(memberId))
        );
        return histories.stream()
                .map(machineHistory -> modelMapper.map(machineHistory, MachineHistoryDto.class))
                .toList();
    }
}
