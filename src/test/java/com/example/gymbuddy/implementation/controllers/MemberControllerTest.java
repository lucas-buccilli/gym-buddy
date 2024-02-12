package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.implementation.configurations.ModelMapperConfig;
import com.example.gymbuddy.implementation.configurations.SecurityConfig;
import com.example.gymbuddy.implementation.utils.AuthUtils;
import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.services.IMemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@Import({ModelMapperConfig.class, SecurityConfig.class})
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IMemberService memberService;

    @Test
    public void shouldReturnAllMembers() throws Exception {
        var member = MemberDto.builder()
                .firstName("TestFirst").lastName("TestLast").phoneNumber("0000000000").build();
        var pageRequest = PageRequest.build(
                0,
                100,
                Collections.emptyMap(),
                Collections.emptyMap()
        );
        when(memberService.findAll(any())).thenReturn(List.of(member));

        mockMvc.perform(post("/members/search").with(AuthUtils.generateAuth0Admin("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pageRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value(member.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(member.getLastName()))
                .andExpect(jsonPath("$[0].phoneNumber").value(member.getPhoneNumber()));
    }

    @Test
    public void shouldAddMember() throws Exception {
        var memberDto = MemberDto.builder()
                .firstName("TestFirst").lastName("TestLast").phoneNumber("0000000000").authId("1111111").build();
        when(memberService.addMember(any())).thenReturn(memberDto);

        mockMvc.perform(post("/members").with(AuthUtils.generateAuth0Admin("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberDto)))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(content().string(objectMapper.writeValueAsString(memberDto)));
    }

    @Test
    public void shouldEditMember() throws Exception {
        MemberDto memberDao = MemberDto.builder()
                .firstName("FirstName").lastName("SecondName").phoneNumber("0000000000").authId("12345").build();

        when(memberService.replaceMember(anyInt(), any())).thenReturn(memberDao);

        mockMvc.perform(put("/members/10").with(AuthUtils.generateAuth0Admin("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberDao)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(memberDao)));
    }
}
