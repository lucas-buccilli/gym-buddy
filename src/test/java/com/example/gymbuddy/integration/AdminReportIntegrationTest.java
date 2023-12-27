package com.example.gymbuddy.integration;


import com.example.gymbuddy.infrastructure.models.daos.MachineDao;
import com.example.gymbuddy.infrastructure.models.daos.MachineHistoryDao;
import com.example.gymbuddy.infrastructure.models.daos.MemberDao;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@IntegrationTest
public class AdminReportIntegrationTest {

    @Container
    public static DbContainer container = DbContainer.getInstance();
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestDataManager testDataManager;

    public AdminReportIntegrationTest() throws Exception {
    }

    @AfterEach
    void afterEach() {
        testDataManager.deleteAllData();
    }

    @Test
    void generateAdminReport() throws Exception {
        var bench = createMachine(
                MachineDao
                        .builder()
                        .name("bench")
                        .build()
        );
        var treadmill = createMachine(
                MachineDao
                        .builder()
                        .name("treadmill")
                        .build()
        );

        List<MemberDao> list = IntStream.range(0, 9).mapToObj(intValue -> createMember()).toList();

        HashMap<Integer, List<MachineHistoryDao>> machineHistoriesMap = new HashMap<>();
        list.forEach(member -> {
            IntStream.range(0, 3)
                    .mapToObj(intValue -> createMachineHistory(member.getId(), bench.getId()))
                    .forEach(machineHistory ->
                            machineHistoriesMap.computeIfAbsent(member.getId(), k -> new ArrayList<MachineHistoryDao>())
                            .add(machineHistory)
                    );
        });

        var adminReport = getAdminReport(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        assertEquals(9, adminReport.getNumberOfVisitors());
        assertEquals(1, adminReport.getMachineUsage().size());
        assertEquals(27, adminReport.getMachineUsage().get(bench.getName()));

        assertTrue(adminReport.getMachineUsage().containsKey(bench.getName()));
        assertFalse(adminReport.getMachineUsage().containsKey(treadmill.getName()));

        var zeroActiveUsersAdminReport = getAdminReport(LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1));
        assertEquals(0, zeroActiveUsersAdminReport.getNumberOfVisitors());
    }

    private MachineDao createMachine(MachineDao machineDao) throws Exception {
        var result = mvc.perform(post("/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(machineDao)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MachineDao.class);
    }

    private MemberDao createMember(MemberDao memberDao) throws Exception {
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

    private MachineHistoryDao createMachineHistory(int memberId, int machineId, MachineHistoryDao machineHistoryDao) throws Exception {
        var result = mvc.perform(post("/members/" + memberId + "/machines/" + machineId + "/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(machineHistoryDao)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MachineHistoryDao.class);
    }

    private MachineHistoryDao createMachineHistory(Integer memberId, Integer machineId) {

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

    private AdminReportDto getAdminReport(LocalDateTime startDate, LocalDateTime endDate) throws Exception {
        var result = mvc.perform(get("/reports/admin")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), AdminReportDto.class);
    }

}
