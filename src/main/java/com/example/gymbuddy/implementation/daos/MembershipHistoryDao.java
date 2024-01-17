package com.example.gymbuddy.implementation.daos;

import com.example.gymbuddy.implementation.repositories.MembershipHistoryRepository;
import com.example.gymbuddy.infrastructure.daos.IMembershipHistoryDao;
import com.example.gymbuddy.infrastructure.entities.Membership;
import com.example.gymbuddy.infrastructure.entities.MembershipHistory;
import com.example.gymbuddy.infrastructure.entities.enums.MembershipOperation;
import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MembershipHistoryDao implements IMembershipHistoryDao {
    private final MembershipHistoryRepository membershipHistoryRepository;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    @Override
    public void addHistory(MembershipDto membershipDao) {
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
