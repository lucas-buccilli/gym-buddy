package com.example.gymbuddy.implementation.dataproviders;

import com.example.gymbuddy.implementation.configurations.ModelMapperConfig;
import com.example.gymbuddy.implementation.repositories.MemberRepository;
import com.example.gymbuddy.infrastructure.entities.Member;
import com.example.gymbuddy.infrastructure.exceptions.MemberNotFoundException;
import com.example.gymbuddy.infrastructure.models.daos.MemberDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberDataProviderTest {

    @InjectMocks
    private MemberDataProvider memberDataProvider;
    @Mock
    private MemberRepository memberRepository;
    @Spy
    private ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

    @Test
    public void shouldFindAll() {
        var members = List.of(new Member());
        var memberDtos = List.of(MemberDao.builder().build());

        when(memberRepository.findAll()).thenReturn(members);

        assertEquals(memberDtos, memberDataProvider.findAll());
        verify(memberRepository).findAll();
        verify(modelMapper).map(eq(members.get(0)), eq(MemberDao.class));
    }

    @Test
    public void shouldAddMember() {
        var member = new Member();
        member.setFirstName("John");
        when(memberRepository.save(any())).thenReturn(member);
        assertNotNull(memberDataProvider.addMember(MemberDao.builder().firstName("John").build()));
        verify(memberRepository).save(member);
    }

    @Test
    public void shouldFindById() {
        var member = new Member();
        member.setId(111);

        when(memberRepository.findById(anyInt())).thenReturn(Optional.of(member));

        memberDataProvider.findById(111);
        verify(memberRepository).findById(111);
        verify(modelMapper).map(member, MemberDao.class);
    }

    @Test
    public void shouldDeleteMemberById() {
        int memberId = 32;

        doNothing().when(memberRepository).deleteById(anyInt());
        memberDataProvider.deleteMember(memberId);
        verify(memberRepository).deleteById(memberId);
    }

    @Test
    public void shouldEditMember() {
        Member member = new Member();
        member.setFirstName("Jim");
        var memberDao = modelMapper.map(member, MemberDao.class);
        when(memberRepository.save(any())).thenReturn(member);

        var result = memberDataProvider.editMember(memberDao);
        verify(memberRepository).save(member);

        assertEquals(memberDao, result);
    }
}
