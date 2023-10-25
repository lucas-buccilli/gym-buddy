package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.infrastructure.services.MachineHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(MachineHistoryController.class)
class MachineHistoryControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MachineHistoryService machineHistoryService;

    @Test
    void addMachineHistory() throws Exception {
        var machineHistoryDto = MachineHistoryDto.builder()
                .maxWeight(200).numberReps(3).numberSets(2)
                .workoutDate(LocalDate.now()).build();
        when(machineHistoryService.addMachineHistory(anyInt(), anyInt(), any()))
                .thenReturn(machineHistoryDto);

        mockMvc.perform(post("/members/1/machines/1/history")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(machineHistoryDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(machineHistoryDto)));
    }

    @Test
    public void shouldReturnMachineHistoryForMember() throws Exception {
        var machineHistoryDto = MachineHistoryDto.builder()
                .memberId(1).machineId(2).workoutDate(LocalDate.now()).build();
        var machineHistoryList = List.of(machineHistoryDto);
        when(machineHistoryService.findBy(anyInt(), anyInt(), any())).thenReturn(machineHistoryList);

        mockMvc.perform(get("/members/1/machines/2/history"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].memberId").value(machineHistoryDto.getMemberId()))
                .andExpect(jsonPath("$[0].machineId").value(machineHistoryDto.getMachineId()))
                .andExpect(jsonPath("$[0].workoutDate").value(machineHistoryDto.getWorkoutDate().toString()));
    }
}