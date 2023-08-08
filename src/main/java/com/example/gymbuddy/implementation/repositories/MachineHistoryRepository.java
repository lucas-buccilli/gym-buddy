package com.example.gymbuddy.implementation.repositories;

import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineHistoryRepository extends JpaRepository<MachineHistory, Integer> {
}
