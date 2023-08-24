package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.services.MachineHistoryService;
import com.example.gymbuddy.infrastructure.services.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MachineHistoryService machineHistoryService;

    @Test
    public void shouldReturnAllMembers() throws Exception {
        var member = MemberDto.builder()
                .firstName("TestFirst").lastName("TestLast").phoneNumber("0000000000").build();
        when(memberService.findAll()).thenReturn(List.of(member));

        mockMvc.perform(get("/members"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value(member.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(member.getLastName()))
                .andExpect(jsonPath("$[0].phoneNumber").value(member.getPhoneNumber()));
    }

    @Test
    public void shouldAddMember() throws Exception {
        var memberDto = MemberDto.builder()
                .firstName("TestFirst").lastName("TestLast").phoneNumber("0000000000").build();
        when(memberService.addMember(any())).thenReturn(memberDto);

        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberDto)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(content().string(objectMapper.writeValueAsString(memberDto)));
    }
}
