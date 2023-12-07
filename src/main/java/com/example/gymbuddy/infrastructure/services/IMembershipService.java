package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.daos.MembershipDao;

import java.util.List;

public interface IMembershipService {
    List<MembershipDao> findAll();

    MembershipDao addMembership(MembershipDao memberDto);
}
