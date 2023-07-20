package com.example.gymbuddy.implementation.repositories;

import com.example.gymbuddy.infrastructure.entities.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Integer> {
}
