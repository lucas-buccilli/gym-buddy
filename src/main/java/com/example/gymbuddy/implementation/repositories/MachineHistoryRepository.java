package com.example.gymbuddy.implementation.repositories;

import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MachineHistoryRepository extends JpaRepository<MachineHistory, Integer>,
        JpaSpecificationExecutor<MachineHistory> {
     Optional<MachineHistory> findTop1ByMemberIdAndMachineIdOrderByWorkoutDateDesc(Integer memberId, Integer machineId);

     @Query(value = "SELECT COUNT(Distinct DATE(workout_date), member_id) FROM machine_history " +
             "Where workout_date <= :endDate and workout_date >= :startDate", nativeQuery = true)
     Integer countFindDistinctMemberIdByWorkoutDateBetween(@Param("startDate") LocalDateTime startDate,
                                                           @Param("endDate") LocalDateTime endDate);
}
