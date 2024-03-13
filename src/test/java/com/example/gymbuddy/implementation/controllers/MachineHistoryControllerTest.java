package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.implementation.utils.AuthUtils;
import com.example.gymbuddy.implementation.configurations.SecurityConfig;
import com.example.gymbuddy.implementation.validators.requests.MachineHistoryRequestValidator;
import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.infrastructure.models.enums.SortOptions;
import com.example.gymbuddy.infrastructure.services.IMachineHistoryService;
import com.example.gymbuddy.infrastructure.validation.ValidationError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MachineHistoryController.class)
@Import(SecurityConfig.class)
class MachineHistoryControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    IMachineHistoryService machineHistoryService;

    @MockBean
    MachineHistoryRequestValidator machineHistoryRequestValidator;

    @Test
    void addMachineHistory() throws Exception {
        var machineHistoryDto = MachineHistoryDto.builder()
                .maxWeight(200).numberReps(3).numberSets(2)
                .workoutDate(LocalDateTime.now()).build();
        when(machineHistoryRequestValidator.validate(any(MachineHistoryRequestValidator.AddMachineHistoryRequest.class)))
                .thenReturn(new ArrayList<>());
        when(machineHistoryService.addMachineHistory(anyInt(), anyInt(), any()))
                .thenReturn(machineHistoryDto);

        mockMvc.perform(post("/members/1/machines/1/history")
                .with(AuthUtils.generateAuth0Admin("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(machineHistoryDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(machineHistoryDto)));
    }

    @Test
    public void addMachineHistoryThrowsException() throws Exception {
        var machineHistoryDto = MachineHistoryDto.builder()
                .maxWeight(200).numberReps(3).numberSets(2)
                .workoutDate(LocalDateTime.now()).build();

        when(machineHistoryRequestValidator.validate(any(MachineHistoryRequestValidator.AddMachineHistoryRequest.class)))
                .thenReturn(List.of(new ValidationError("Validation Error")));

        mockMvc.perform(post("/members/1/machines/1/history")
                        .with(AuthUtils.generateAuth0Admin("1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(machineHistoryDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.error").value("Invalid Request Exception"))
                .andExpect(jsonPath("$.message").value("[Validation Error]"))
                .andExpect(jsonPath("$.path").value("/members/1/machines/1/history"));
    }

    @Test
    public void shouldReturnMachineHistoryForMember() throws Exception {
        var machineHistoryDto = MachineHistoryDto.builder()
                .memberId(1).machineId(2).workoutDate(LocalDateTime.now()).build();
        var machineHistoryList = List.of(machineHistoryDto);
        var pageRequest = PageRequest.build(0, 10, Map.of("workoutDate", SortOptions.ASC), Map.of());
        when(machineHistoryService.findBy(anyInt(), anyInt(), any())).thenReturn(machineHistoryList);

        var result = mockMvc.perform(post("/members/1/machines/2/history/search")
                        .with(AuthUtils.generateAuth0Admin("1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pageRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].memberId").value(machineHistoryDto.getMemberId()))
                .andExpect(jsonPath("$[0].machineId").value(machineHistoryDto.getMachineId()))
                .andReturn();

        LocalDateTime actualWorkoutDate = LocalDateTime.parse(JsonPath.read(result.getResponse().getContentAsString(),
                "$[0].workoutDate"));
        assertEquals(machineHistoryDto.getWorkoutDate(), actualWorkoutDate);
    }

    @Test
    public void shouldReturnBadRequestWhenPageRequestContainsInvalidFields() throws Exception {
        var machineHistoryDto = MachineHistoryDto.builder()
                .memberId(1).machineId(2).workoutDate(LocalDateTime.now()).build();
        var machineHistoryList = List.of(machineHistoryDto);
        var pageRequest = PageRequest.build(0, 10, Map.of(), Map.of("Random", "value"));
        when(machineHistoryService.findBy(anyInt(), anyInt(), any())).thenReturn(machineHistoryList);

        mockMvc.perform(post("/members/1/machines/2/history/search")
                        .with(AuthUtils.generateAuth0Admin("1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pageRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid Request Exception"));
    }

    @Test
    public void shouldReturnLatestHistory() throws Exception {
        var machineHistoryDto = MachineHistoryDto.builder()
                .memberId(1).machineId(2).workoutDate(LocalDateTime.now())
                .maxWeight(200).numberReps(3).numberSets(2)
                .workoutDate(LocalDateTime.now()).build();

    when(machineHistoryService.findLatestWorkout(anyInt(), anyInt()))
            .thenReturn(Optional.of(machineHistoryDto));

        var result = mockMvc.perform(get("/members/1/machines/2/history/latest")
                        .with(AuthUtils.generateAuth0Admin("1")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value(machineHistoryDto.getMemberId()))
                .andExpect(jsonPath("$.machineId").value(machineHistoryDto.getMachineId()))
                .andExpect(jsonPath("$.maxWeight").value(machineHistoryDto.getMaxWeight()))
                .andExpect(jsonPath("$.numberReps").value(machineHistoryDto.getNumberReps()))
                .andExpect(jsonPath("$.numberSets").value(machineHistoryDto.getNumberSets()))
                .andReturn();

        LocalDateTime actualWorkoutDate = LocalDateTime.parse(JsonPath.read(result.getResponse().getContentAsString(),
                "$.workoutDate"));
        assertEquals(machineHistoryDto.getWorkoutDate(), actualWorkoutDate);
    }

    @Test
    public void getLatestMachineHistoryTrowsException() throws Exception {

        when(machineHistoryService.findLatestWorkout(anyInt(), anyInt()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/members/1/machines/2/history/latest")
                        .with(AuthUtils.generateAuth0Admin("1")))
                .andExpect(status().isNotFound());
    }
}