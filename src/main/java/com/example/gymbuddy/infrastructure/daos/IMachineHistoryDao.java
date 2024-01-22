package com.example.gymbuddy.infrastructure.daos;

import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IMachineHistoryDao {
    List<MachineHistoryDto> findAll();

    MachineHistoryDto addMachineHistory(Integer memberId, Integer machineId, MachineHistoryDto machineHistoryDao);

    List<MachineHistoryDto> findBy(Integer memberId, Integer machineId, LocalDateTime workoutDate);

    Optional<MachineHistoryDto> findLatestWorkout(Integer memberId, Integer machineId);

    Integer getNumberOfVisitorsWithinTimeframe(LocalDateTime startDate, LocalDateTime endDate);

    Map<String, Long> getMachineUsage(LocalDateTime startDate, LocalDateTime endDate);

    Map<String, List<MachineHistoryDto>> getMachineProgress(LocalDateTime startDate, LocalDateTime endDate, Integer memberId, Integer machineId);

    Integer getNumberOfWorkoutDays(LocalDateTime startDate, LocalDateTime endDate, Integer memberId);
}
