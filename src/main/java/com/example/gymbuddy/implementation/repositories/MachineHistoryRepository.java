package com.example.gymbuddy.implementation.repositories;

import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface MachineHistoryRepository extends JpaRepository<MachineHistory, Integer>,
        JpaSpecificationExecutor<MachineHistory> {
     public Optional<MachineHistory> findTop1ByMemberIdAndMachineIdOrderByWorkoutDateDesc(Integer memberId, Integer machineId);
}
