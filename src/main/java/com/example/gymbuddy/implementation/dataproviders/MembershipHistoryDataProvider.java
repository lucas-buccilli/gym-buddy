package com.example.gymbuddy.implementation.dataproviders;

import com.example.gymbuddy.implementation.repositories.MembershipHistoryRepository;
import com.example.gymbuddy.infrastructure.dataproviders.IMembershipHistoryDataProvider;
import com.example.gymbuddy.infrastructure.entities.Membership;
import com.example.gymbuddy.infrastructure.entities.MembershipHistory;
import com.example.gymbuddy.infrastructure.entities.enums.MembershipOperation;
import com.example.gymbuddy.infrastructure.models.daos.MembershipDao;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MembershipHistoryDataProvider implements IMembershipHistoryDataProvider {
    private final MembershipHistoryRepository membershipHistoryRepository;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    @Override
    public void addHistory(MembershipDao membershipDao) {
        var entity = new MembershipHistory();
        entity.setMembership(entityManager.getReference(Membership.class, membershipDao.getId()));
        entity.setOperation(membershipDao.isActive() ? MembershipOperation.ACTIVATED : MembershipOperation.DEACTIVATED);
        entity.setDate(LocalDateTime.now());
        membershipHistoryRepository.save(entity);
    }

    @Override
    public Integer getNumberOfActiveMembershipsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return membershipHistoryRepository.countUniqueMembershipsByDateBetween(startDate, endDate);
    }
}
