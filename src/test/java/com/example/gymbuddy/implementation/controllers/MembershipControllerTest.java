package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;
import com.example.gymbuddy.infrastructure.services.IMembershipService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MembershipController.class)
class MembershipControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    IMembershipService membershipService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        var membership = MembershipDto.builder().memberId(0).active(true).build();

        when(membershipService.findAll()).thenReturn(List.of(membership));
        mockMvc.perform(get("/memberships"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].memberId").value(membership.getMemberId()));
    }

    @Test
    void addMembership() throws Exception {

        var membershipDto = MembershipDto.builder().memberId(0).active(true).build();
        when(membershipService.addMembership(any())).thenReturn(membershipDto);

        mockMvc.perform(
                post("/memberships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(membershipDto)))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(content().string(objectMapper.writeValueAsString(membershipDto)));
    }
}