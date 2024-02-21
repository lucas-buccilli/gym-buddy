package com.example.gymbuddy.implementation.daos;

import com.example.gymbuddy.implementation.configurations.ModelMapperConfig;
import com.example.gymbuddy.implementation.repositories.MemberRepository;
import com.example.gymbuddy.infrastructure.entities.Member;
import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberDataProviderTest {

    @InjectMocks
    private MemberDao memberDataProvider;
    @Mock
    private MemberRepository memberRepository;
    @Spy
    private ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

    @Test
    public void shouldFindAll() {
        var members = List.of(new Member());
        var memberDtos = List.of(MemberDto.builder().build());

        when(memberRepository.findAll(ArgumentMatchers.<Specification<Member>>any(), any(Pageable.class))).thenReturn(new PageImpl<>(members));

        assertEquals(memberDtos, memberDataProvider.findAll(PageRequest.build(0, 1 , Collections.emptyMap(), Collections.emptyMap())));
        verify(memberRepository).findAll(ArgumentMatchers.<Specification<Member>>any(), any(Pageable.class));
        verify(modelMapper).map(eq(members.get(0)), eq(MemberDto.class));
    }

    @Test
    public void shouldAddMember() {
        var member = new Member();
        member.setFirstName("John");
        when(memberRepository.save(any())).thenReturn(member);
        assertNotNull(memberDataProvider.saveMember(MemberDto.builder().firstName("John").build()));
        verify(memberRepository).save(member);
    }

    @Test
    public void shouldFindById() {
        var member = new Member();
        member.setId(111);

        when(memberRepository.findById(anyInt())).thenReturn(Optional.of(member));

        memberDataProvider.findById(111);
        verify(memberRepository).findById(111);
        verify(modelMapper).map(member, MemberDto.class);
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
        var memberDao = modelMapper.map(member, MemberDto.class);
        when(memberRepository.save(any())).thenReturn(member);

        var result = memberDataProvider.saveMember(memberDao);
        verify(memberRepository).save(member);

        assertEquals(memberDao, result);
    }
}
