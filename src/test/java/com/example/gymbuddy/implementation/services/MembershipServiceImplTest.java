package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.implementation.configurations.ModelMapperConfig;
import com.example.gymbuddy.implementation.repositories.MembershipRepository;
import com.example.gymbuddy.infrastructure.entities.Member;
import com.example.gymbuddy.infrastructure.entities.Membership;
import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;
import com.example.gymbuddy.infrastructure.services.MembershipService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MembershipServiceImplTest {
    @InjectMocks
    private MembershipServiceImpl membershipService;
    @Mock
    private MembershipRepository membershipRepository;
    @Spy
    private ModelMapper modelMapper  = new ModelMapperConfig().modelMapper();

    @Test
    void findAll() {
        var memberships = List.of(new Membership());
        var membershipDtos = List.of(MembershipDto.builder().build());

        when(membershipRepository.findAll()).thenReturn(memberships);
        assertEquals(membershipDtos, membershipService.findAll());
        verify(membershipRepository).findAll();
        verify(modelMapper).map(eq(memberships.get(0)), eq(MembershipDto.class));
    }

    @Test
    void addMembership() {
        var membership = new Membership();
        var member = new Member();
        member.setId(1);
        membership.setMember(member);

        when(membershipRepository.save(any())).thenReturn(membership);
        assertNotNull(membershipService.addMembership(MembershipDto.builder().memberId(member.getId()).build()));
        verify(membershipRepository).save(membership);
    }

    @Test
    void isActive() {
    }
}