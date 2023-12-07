package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMemberDataProvider;
import com.example.gymbuddy.infrastructure.models.daos.MemberDao;
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
public class MemberServiceTest {

    @Mock
    IMemberDataProvider memberDataProvider;

    @InjectMocks
    MemberService memberService;

    @Test
    void findAll() {
        var memberDtoList = List.of(new MemberDao());

        when(memberDataProvider.findAll()).thenReturn(memberDtoList);

        var result = memberService.findAll();
        assertNotNull(result);
        assertEquals(memberDtoList, result);
        verify(memberDataProvider).findAll();
    }

    @Test
    void addMember() {
        var memberDto = MemberDao.builder().firstName("Sam").build();

        when(memberDataProvider.addMember(any())).thenReturn(memberDto);

        var result = memberService.addMember(memberDto);
        assertEquals(memberDto, result);
        verify(memberDataProvider).addMember(memberDto);
        assertEquals("Sam", result.getFirstName());
    }
}
