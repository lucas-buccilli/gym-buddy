package com.example.gymbuddy.implementation.repositories;

import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MachineHistoryRepository extends JpaRepository<MachineHistory, Integer>,
        JpaSpecificationExecutor<MachineHistory> {
   // public List<MachineHistory> findByWorkoutDate();
}
