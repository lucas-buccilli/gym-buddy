package com.example.gymbuddy.implementation.repositories;

import com.example.gymbuddy.infrastructure.entities.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MembershipRepository extends JpaRepository<Membership, Integer> {
}
