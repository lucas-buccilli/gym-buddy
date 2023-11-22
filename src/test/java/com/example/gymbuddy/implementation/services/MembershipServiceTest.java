package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMembershipDataProvider;
import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MembershipServiceTest {

    @InjectMocks
    MembershipService membershipService;

    @Mock
    IMembershipDataProvider membershipDataProvider;

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
        var membershipDto = MembershipDto.builder().active(true).build();

        when(membershipDataProvider.addMembership(any())).thenReturn(membershipDto);

        var result = membershipService.addMembership(membershipDto);
        assertEquals(membershipDto, result);
        verify(membershipDataProvider).addMembership(membershipDto);
        assertEquals(membershipDto.isActive(), result.isActive());
    }

    @Test
    void isActive() {

        boolean result = membershipService.isActive();
        assertTrue(result);
    }
}