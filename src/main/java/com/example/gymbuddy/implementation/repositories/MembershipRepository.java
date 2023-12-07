package com.example.gymbuddy.implementation.repositories;

import com.example.gymbuddy.infrastructure.entities.Membership;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Integer> {
    public Integer countMembershipsByActiveIsTrue();
}


