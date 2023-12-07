package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMachineHistoryDataProvider;
import com.example.gymbuddy.infrastructure.models.daos.MachineHistoryDao;
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
    private final IMachineHistoryDataProvider machineHistoryDataProvider;

    @Override
    public List<MachineHistoryDao> findAll() {
        return machineHistoryDataProvider.findAll();
    }

    @Override
    public MachineHistoryDao addMachineHistory(Integer memberId, Integer machineId, MachineHistoryDao machineHistoryDao) {
        return machineHistoryDataProvider.addMachineHistory(memberId, machineId, machineHistoryDao);
    }

    @Override
    public List<MachineHistoryDao> findBy(Integer memberId, Integer machineId, @Nullable LocalDateTime workoutDate) {
       return machineHistoryDataProvider.findBy(memberId, machineId, workoutDate);
    }

    @Override
    public Optional<MachineHistoryDao> findLatestWorkout(Integer memberId, Integer machineId) {
        return machineHistoryDataProvider.findLatestWorkout(memberId, machineId);
    }
}
