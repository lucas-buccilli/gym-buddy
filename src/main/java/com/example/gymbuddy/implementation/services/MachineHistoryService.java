package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.daos.IMachineHistoryDao;
import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.infrastructure.services.IMachineHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MachineHistoryService implements IMachineHistoryService {
    private final IMachineHistoryDao machineHistoryDataProvider;

//    @Override
//    public List<MachineHistoryDto> findAll(PageRequest pageRequest) {
//        return machineHistoryDataProvider.findAll(pageRequest);
//    }

    @Override
    public MachineHistoryDto addMachineHistory(Integer memberId, Integer machineId, MachineHistoryDto machineHistoryDao) {
        return machineHistoryDataProvider.addMachineHistory(memberId, machineId, machineHistoryDao);
    }

    @Override
    public List<MachineHistoryDto> findBy(Integer memberId, Integer machineId, PageRequest pageRequest) {
       return machineHistoryDataProvider.findBy(memberId, machineId, pageRequest);
    }

    @Override
    public Optional<MachineHistoryDto> findLatestWorkout(Integer memberId, Integer machineId) {
        return machineHistoryDataProvider.findLatestWorkout(memberId, machineId);
    }
}
