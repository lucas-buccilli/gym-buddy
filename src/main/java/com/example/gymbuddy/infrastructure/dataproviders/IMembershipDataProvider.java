package com.example.gymbuddy.infrastructure.dataproviders;

import com.example.gymbuddy.infrastructure.models.daos.MembershipDao;

import java.util.List;

public interface IMembershipDataProvider {
    List<MembershipDao> findAll();

    MembershipDao addMembership(MembershipDao memberDto);

    boolean isActive();

    Integer getNumberOfActiveMemberships();
}
