package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.implementation.daos.MembershipHistoryDao;
import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MembershipHistoryServiceTest {

    @Mock
    MembershipHistoryDao membershipHistoryDataProvider;

    @InjectMocks
    MembershipHistoryService membershipHistoryService;

    @Test
    void addHistory() {
        MembershipDto membershipDao = MembershipDto.builder().id(1).memberId(1).active(true).build();
        doNothing().when(membershipHistoryDataProvider).addHistory(any());

        membershipHistoryService.addHistory(membershipDao);
        verify(membershipHistoryDataProvider).addHistory(membershipDao);
    }

    @Test
    void getNumberOfActiveMembershipsBetweenDates() {
        int count = 1;
        var start = LocalDateTime.MAX;
        var end = LocalDateTime.MIN;
        when(membershipHistoryDataProvider.getNumberOfActiveMembershipsBetweenDates(any(), any())).thenReturn(count);

        assertEquals(count, membershipHistoryService.getNumberOfActiveMembershipsBetweenDates(start, end));
        verify(membershipHistoryDataProvider).getNumberOfActiveMembershipsBetweenDates(start, end);
    }
}