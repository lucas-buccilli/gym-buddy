package com.example.gymbuddy.implementation.dataproviders;

import com.example.gymbuddy.implementation.repositories.MembershipRepository;
import com.example.gymbuddy.infrastructure.dataproviders.IMembershipDataProvider;
import com.example.gymbuddy.infrastructure.entities.Membership;
import com.example.gymbuddy.infrastructure.models.daos.MembershipDao;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembershipDataProvider implements IMembershipDataProvider {
    private final MembershipRepository membershipRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MembershipDao> findAll() {
        return membershipRepository.findAll().stream()
                .map(membershipEntity -> modelMapper.map(membershipEntity, MembershipDao.class))
                .toList();
    }

    @Override
    public MembershipDao addMembership(MembershipDao membershipDao) {
        var membership = modelMapper.map(membershipDao, Membership.class);
        return modelMapper.map(membershipRepository.save(membership), MembershipDao.class);
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public Integer getNumberOfActiveMemberships() {
        return membershipRepository.countMembershipsByActiveIsTrue();
    }
}
