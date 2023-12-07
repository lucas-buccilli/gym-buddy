package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.infrastructure.models.daos.MachineDao;
import com.example.gymbuddy.infrastructure.services.IMachineService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MachineController.class)
class MachineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IMachineService machineService;

    @Test
    public void shouldReturnAllMachines() throws Exception {
        var machine = MachineDao.builder().name("name123").build();
        when(machineService.findAll()).thenReturn(List.of(machine));

        mockMvc.perform(get("/machines"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(machine.getName()));
    }

    @Test
    public void shouldAddMachine() throws Exception {
        var machineDto = MachineDao.builder().name("name").build();
        when(machineService.addMachine(any())).thenReturn(machineDto);
        mockMvc.perform(
                        post("/machines")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(machineDto)))
                                .andDo(print())
                                .andExpect(status().isCreated())
                                .andExpect(content().string(objectMapper.writeValueAsString(machineDto)));
    }
}