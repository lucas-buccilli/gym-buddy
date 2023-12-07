package com.example.gymbuddy.implementation.repositories;

import com.example.gymbuddy.infrastructure.entities.MembershipHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MembershipHistoryRepository extends JpaRepository<MembershipHistory, Integer> {
    Integer countUniqueMembershipsByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}


