package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.implementation.utils.AuthUtils;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import com.example.gymbuddy.infrastructure.models.dtos.UserReportDto;
import com.example.gymbuddy.infrastructure.services.IReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
class ReportControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    IReportService reportService;

    @Test
    void shouldGetAdminReport() throws Exception {
        var adminReportDto = AdminReportDto.builder().build();

        when(reportService.getAdminReport(any(), any())).thenReturn(adminReportDto);

        mockMvc.perform(get("/reports/admin")
                        .with(AuthUtils.generateAuth0Admin("1"))
                        .param("startDate", LocalDateTime.now().minusDays(1).toString())
                        .param("endDate", LocalDateTime.now().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(adminReportDto)));
    }

    @Test
    void shouldGetUserReport() throws Exception {
        UserReportDto userReportDto = UserReportDto.builder().build();
        var memberId = 1;
        var machineId = 2;

        when(reportService.getUserReport(any(), any(), anyInt(), anyInt())).thenReturn(userReportDto);

        mockMvc.perform(get("/reports/user")
                        .with(AuthUtils.generateAuth0Admin("1"))
                .param("startDate", LocalDateTime.MIN.toString())
                .param("endDate", LocalDateTime.MAX.toString())
                .param("memberId", Integer.toString(memberId))
                .param("machineId", Integer.toString(machineId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(userReportDto)));
        verify(reportService).getUserReport(LocalDateTime.MIN, LocalDateTime.MAX, memberId, machineId);
    }
}