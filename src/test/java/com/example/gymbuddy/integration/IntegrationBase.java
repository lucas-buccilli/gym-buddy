package com.example.gymbuddy.integration;

import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import com.example.gymbuddy.infrastructure.models.dtos.UserReportDto;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public abstract class IntegrationBase {
    @Container
    public static DbContainer dbContainer = DbContainer.getInstance();
    @Autowired
    private MockMvc mvc;
    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;
    @Autowired
    private TestDataManager testDataManager;


    @AfterEach
    void afterEach() {
        testDataManager.deleteAllData();
    }

    public MemberDto createMember(MemberDto memberDao) throws Exception {
        var result = mvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDao)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MemberDto.class);
    }

    public MemberDto createMember() {
        String firstName = RandomStringUtils.randomAlphabetic(6);
        String lastName = RandomStringUtils.randomAlphabetic(6);
        String phoneNumber = "9999999999";
        try {
            return createMember(MemberDto.builder().firstName(firstName).lastName(lastName).phoneNumber(phoneNumber).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MachineDto createMachine(MachineDto machineDao) throws Exception {
        var result = mvc.perform(post("/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(machineDao)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MachineDto.class);
    }

    public MachineHistoryDto createMachineHistory(Integer memberId, Integer machineId) {

        MachineHistoryDto machineHistoryDao = MachineHistoryDto.builder()
                .workoutDate(LocalDateTime.now())
                .numberReps(Integer.valueOf(RandomStringUtils.randomNumeric(1)))
                .maxWeight(Integer.valueOf(RandomStringUtils.randomNumeric(1)))
                .numberSets(Integer.valueOf(RandomStringUtils.randomNumeric(1)))
                .build();
        try {
            return createMachineHistory(memberId, machineId, machineHistoryDao);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public MachineHistoryDto createMachineHistory(int memberId, int machineId, MachineHistoryDto machineHistoryDao) throws Exception {
        var result = mvc.perform(post("/members/" + memberId + "/machines/" + machineId + "/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(machineHistoryDao)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MachineHistoryDto.class);
    }

    public UserReportDto getUserReport(Integer memberId, Integer machineId, LocalDateTime startDate, LocalDateTime endDate) throws Exception {
        var result = mvc.perform(get("/reports/user")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("memberId", Integer.toString(memberId))
                        .param("machineId", Integer.toString(machineId)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), UserReportDto.class);
    }

    public AdminReportDto getAdminReport(LocalDateTime startDate, LocalDateTime endDate) throws Exception {
        var result = mvc.perform(get("/reports/admin")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), AdminReportDto.class);
    }

    public void deleteMachine(String name) throws Exception {
        mvc.perform(delete("/machines")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":  \"" + name + "\"}"))
                .andExpect(status().isNoContent());
    }

    public void deleteMember(int id) throws Exception {
        mvc.perform(delete("/members/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    public MemberDto replaceMember(int id, MemberDto memberDao) throws Exception {
        var result = mvc.perform(put("/members/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberDao)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MemberDto.class);
    }

    public MemberDto modifyMember(int id, MemberDto memberDto) throws Exception {
        var result = mvc.perform(patch("/members/" + id, memberDto)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberDto)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MemberDto.class);
    }
}
