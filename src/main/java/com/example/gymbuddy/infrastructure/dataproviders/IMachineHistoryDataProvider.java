package com.example.gymbuddy.infrastructure.dataproviders;

import com.example.gymbuddy.infrastructure.models.daos.MachineHistoryDao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IMachineHistoryDataProvider {
    List<MachineHistoryDao> findAll();

    MachineHistoryDao addMachineHistory(Integer memberId, Integer machineId, MachineHistoryDao machineHistoryDao);

    List<MachineHistoryDao> findBy(Integer memberId, Integer machineId, LocalDateTime workoutDate);

    Optional<MachineHistoryDao> findLatestWorkout(Integer memberId, Integer machineId);

    Integer getNumberOfVisitorsWithinTimeframe(LocalDateTime startDate, LocalDateTime endDate);

    Map<String, Long> getMachineUsage(LocalDateTime startDate, LocalDateTime endDate);
}
