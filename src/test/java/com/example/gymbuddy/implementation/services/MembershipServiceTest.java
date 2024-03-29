package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.daos.IMembershipDao;
import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;
import com.example.gymbuddy.infrastructure.services.IMembershipHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MembershipServiceTest {

    @InjectMocks
    MembershipService membershipService;

    @Mock
    IMembershipHistoryService membershipHistoryService;

    @Mock
    IMembershipDao membershipDataProvider;

    @Test
    void findAll() {
        var membershipdDtoList = List.of(new MembershipDto());

        when(membershipDataProvider.findAll()).thenReturn(membershipdDtoList);

        var result = membershipService.findAll();
        assertNotNull(result);
        assertEquals(membershipdDtoList, result);
        verify(membershipDataProvider).findAll();
    }

    @Test
    void addMembership() {
        var membershipDao = MembershipDto.builder().active(true).build();

        when(membershipDataProvider.addMembership(any())).thenReturn(membershipDao);

        var result = membershipService.addMembership(membershipDao);
        assertEquals(membershipDao, result);
        verify(membershipDataProvider).addMembership(membershipDao);
        verify(membershipHistoryService).addHistory(membershipDao);
    }
}