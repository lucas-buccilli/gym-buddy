package com.example.gymbuddy.implementation.dataproviders;

import com.example.gymbuddy.implementation.configurations.ModelMapperConfig;
import com.example.gymbuddy.implementation.repositories.MembershipRepository;
import com.example.gymbuddy.infrastructure.entities.Member;
import com.example.gymbuddy.infrastructure.entities.Membership;
import com.example.gymbuddy.infrastructure.models.daos.MembershipDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MembershipDataProviderTest {
    @InjectMocks
    private MembershipDataProvider membershipDataProvider;
    @Mock
    private MembershipRepository membershipRepository;
    @Spy
    private ModelMapper modelMapper  = new ModelMapperConfig().modelMapper();

    @Test
    void shouldFindAll() {
        var memberships = List.of(new Membership());
        var membershipDtos = List.of(MembershipDao.builder().build());

        when(membershipRepository.findAll()).thenReturn(memberships);
        assertEquals(membershipDtos, membershipDataProvider.findAll());
        verify(membershipRepository).findAll();
        verify(modelMapper).map(eq(memberships.get(0)), eq(MembershipDao.class));
    }

    @Test
    void shouldAddMembership() {
        var membership = new Membership();
        var member = new Member();
        member.setId(1);
        membership.setMember(member);

        when(membershipRepository.save(any())).thenReturn(membership);

        assertNotNull(membershipDataProvider.addMembership(MembershipDao.builder().memberId(member.getId()).build()));
        verify(membershipRepository).save(membership);
        verify(modelMapper).map(eq(membership), eq(MembershipDao.class));
    }

    @Test
    void isActive() {
        assertTrue(membershipDataProvider.isActive());
    }

    @Test
    void shouldGetNumberOfActiveMemberships() {
        int count = 1;
        when(membershipRepository.countMembershipsByActiveIsTrue()).thenReturn(1);

        assertEquals(count, membershipDataProvider.getNumberOfActiveMemberships());
        verify(membershipRepository).countMembershipsByActiveIsTrue();
    }
}
