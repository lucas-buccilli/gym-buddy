package com.example.gymbuddy.implementation.daos;

import com.example.gymbuddy.implementation.repositories.MembershipHistoryRepository;
import com.example.gymbuddy.infrastructure.entities.Membership;
import com.example.gymbuddy.infrastructure.entities.MembershipHistory;
import com.example.gymbuddy.infrastructure.entities.enums.MembershipOperation;
import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MembershipHistoryDataProviderTest {

    @InjectMocks
    private MembershipHistoryDao membershipHistoryDataProvider;

    @Mock
    private MembershipHistoryRepository membershipHistoryRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testAddHistoryMembershipActivated() {
        var membership = new Membership();
        when(entityManager.getReference(any(), anyInt())).thenReturn(membership);

        var membershipDao = MembershipDto.builder()
                        .active(true)
                        .memberId(1)
                        .id(2)
                        .build();
        membershipHistoryDataProvider.addHistory(membershipDao);

        var argumentCaptor = ArgumentCaptor.forClass(MembershipHistory.class);
        verify(membershipHistoryRepository).save(argumentCaptor.capture());
        assertNotNull(argumentCaptor.getValue());
        assertEquals(membership, argumentCaptor.getValue().getMembership());
        assertNotNull(argumentCaptor.getValue().getDate());
        assertEquals(MembershipOperation.ACTIVATED, argumentCaptor.getValue().getOperation());
    }

    @Test
    public void testAddHistoryMembershipDeactivated() {
        var membership = new Membership();
        when(entityManager.getReference(any(), anyInt())).thenReturn(membership);

        var membershipDao = MembershipDto.builder()
                .active(false)
                .memberId(1)
                .id(2)
                .build();
        membershipHistoryDataProvider.addHistory(membershipDao);

        var argumentCaptor = ArgumentCaptor.forClass(MembershipHistory.class);
        verify(membershipHistoryRepository).save(argumentCaptor.capture());
        assertNotNull(argumentCaptor.getValue());
        assertEquals(membership, argumentCaptor.getValue().getMembership());
        assertNotNull(argumentCaptor.getValue().getDate());
        assertEquals(MembershipOperation.DEACTIVATED, argumentCaptor.getValue().getOperation());
    }

    @Test
    public void testGetNumberOfActiveMembershipsBetweenDates() {
        var count = 1;
        var start = LocalDateTime.MAX;
        var end = LocalDateTime.MIN;
        when(membershipHistoryRepository.countUniqueMembershipsByDateBetween(any(), any())).thenReturn(count);

        assertEquals(count, membershipHistoryDataProvider.getNumberOfActiveMembershipsBetweenDates(start, end));
        verify(membershipHistoryRepository).countUniqueMembershipsByDateBetween(start, end);
    }
}
