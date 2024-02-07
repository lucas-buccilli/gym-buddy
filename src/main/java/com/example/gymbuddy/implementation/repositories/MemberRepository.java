package com.example.gymbuddy.implementation.repositories;

import com.example.gymbuddy.infrastructure.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByAuthId(String authId);
}
