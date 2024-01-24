package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.daos.IMemberDao;
import com.example.gymbuddy.infrastructure.exceptions.MemberNotFoundException;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    IMemberDao memberDataProvider;

    @InjectMocks
    MemberService memberService;

    @Test
    void findAll() {
        var memberDtoList = List.of(new MemberDto());

        when(memberDataProvider.findAll()).thenReturn(memberDtoList);

        var result = memberService.findAll();
        assertNotNull(result);
        assertEquals(memberDtoList, result);
        verify(memberDataProvider).findAll();
    }

    @Test
    void addMember() {
        var memberDto = MemberDto.builder().firstName("Sam").build();

        when(memberDataProvider.saveMember(any())).thenReturn(memberDto);

        var result = memberService.addMember(memberDto);
        assertEquals(memberDto, result);
        verify(memberDataProvider).saveMember(memberDto);
        assertEquals("Sam", result.getFirstName());
    }

    @Test
    void shouldDeleteExistingMember() {
        var memberDto = MemberDto.builder().id(65).build();

        when(memberDataProvider.findById(anyInt())).thenReturn(Optional.of(memberDto));

        memberService.deleteMember(memberDto.getId());
        verify(memberDataProvider).findById(anyInt());
        verify(memberDataProvider).deleteMember(anyInt());
    }

    @Test
    void shouldThrowErrorMemberNotExistOnDelete() {
        int memberId = 34;
        when(memberDataProvider.findById(anyInt())).thenReturn(Optional.empty());
        //revisit this
        var result = assertThrows(MemberNotFoundException.class, () -> memberService.deleteMember(memberId));
        verify(memberDataProvider).findById(memberId);
        assertEquals("Member Not Found: id = 34", result.getMessage());
    }

    @Test
    void shouldReplaceExistingMember() {
        var id = 75;
        MemberDto memberDao = MemberDto.builder().id(id).build();
        MemberDto memberDaoUpdatedInfo = MemberDto.builder().firstName("Name").build();

        when(memberDataProvider.findById(anyInt())).thenReturn(Optional.of(memberDao));
        when(memberDataProvider.saveMember(any())).thenReturn(memberDaoUpdatedInfo);

        var result = memberService.replaceMember(id, memberDaoUpdatedInfo);

        verify(memberDataProvider).findById(id);
        verify(memberDataProvider).saveMember(memberDaoUpdatedInfo);
        assertEquals(id, result.getId());
        assertEquals(memberDaoUpdatedInfo.getFirstName(), result.getFirstName());
    }
    @Test
    void shouldThrowErrorMemberNotExistOnEdit() {
        MemberDto memberDao = MemberDto.builder().id(46).build();

        var result = assertThrows(MemberNotFoundException.class, () -> memberService.replaceMember(memberDao.getId(), memberDao));

        verify(memberDataProvider).findById(memberDao.getId());
        assertEquals("Member Not Found: id = 46", result.getMessage());
    }

    @Test
    void shouldModifyExistingMember() throws IllegalAccessException {
        MemberDto memberDto = MemberDto.builder().id(1).firstName("name").build();
        MemberDto updatedMemberDto = MemberDto.builder().firstName("new-name").build();

        ArgumentCaptor<MemberDto> argumentCaptor = ArgumentCaptor.forClass(MemberDto.class);

        when(memberDataProvider.findById(any())).thenReturn(Optional.of(memberDto));

        memberService.modifyMember(1, updatedMemberDto);
        verify(memberDataProvider).findById(1);
        verify(memberDataProvider).saveMember(argumentCaptor.capture());
        assertNotNull(argumentCaptor.getValue());
        assertEquals(updatedMemberDto.getFirstName(), argumentCaptor.getValue().getFirstName());
    }

    @Test
    void shouldThrowErrorMemberNotExistOnEModify() {

        var result = assertThrows(MemberNotFoundException.class, () -> memberService.modifyMember(12, null));
        assertEquals("Member Not Found: id = 12", result.getMessage());
    }

}
