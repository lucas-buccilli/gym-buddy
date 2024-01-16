package com.example.gymbuddy.integration;

import com.example.gymbuddy.infrastructure.models.daos.MachineDao;
import com.example.gymbuddy.infrastructure.models.daos.MachineHistoryDao;
import com.example.gymbuddy.infrastructure.models.daos.MemberDao;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import com.example.gymbuddy.infrastructure.models.dtos.UserReportDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
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

    public MemberDao createMember(MemberDao memberDao) throws Exception {
        var result = mvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDao)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MemberDao.class);
    }

    public MemberDao createMember() {
        String firstName = RandomStringUtils.randomAlphabetic(6);
        String lastName = RandomStringUtils.randomAlphabetic(6);
        String phoneNumber = "9999999999";
        try {
            return createMember(MemberDao.builder().firstName(firstName).lastName(lastName).phoneNumber(phoneNumber).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MachineDao createMachine(MachineDao machineDao) throws Exception {
        var result = mvc.perform(post("/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(machineDao)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MachineDao.class);
    }

    public MachineHistoryDao createMachineHistory(Integer memberId, Integer machineId) {

        MachineHistoryDao machineHistoryDao = MachineHistoryDao.builder()
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
    public MachineHistoryDao createMachineHistory(int memberId, int machineId, MachineHistoryDao machineHistoryDao) throws Exception {
        var result = mvc.perform(post("/members/" + memberId + "/machines/" + machineId + "/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(machineHistoryDao)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MachineHistoryDao.class);
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


    public MemberDao editMember(int id, MemberDao memberDao) throws Exception {
        var result = mvc.perform(put("/members/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberDao)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MemberDao.class);
    }
}
