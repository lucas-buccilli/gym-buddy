package com.example.gymbuddy.integration;


import com.example.gymbuddy.infrastructure.models.daos.MachineDao;
import com.example.gymbuddy.infrastructure.models.daos.MachineHistoryDao;
import com.example.gymbuddy.infrastructure.models.daos.MemberDao;
import com.example.gymbuddy.infrastructure.models.dtos.UserReportDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@IntegrationTest
public class UserReportIntegrationTest {
    @Container
    public static DbContainer dbContainer = DbContainer.getInstance();
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestDataManager testDataManager;

    @AfterEach
    void afterEach() {
        testDataManager.deleteAllData();
    }

    @Test
    void generateUserReport() throws Exception {
        var bob =  createMember(
                MemberDao.builder()
                    .firstName("Bob")
                    .lastName("TestLast")
                    .phoneNumber("0000000000")
                    .build()
        );

        var treadmill = createMachine(
                MachineDao.builder()
                        .name("Treadmill")
                        .build()
        );

        var bench = createMachine(
                MachineDao.builder()
                        .name("Bench")
                        .build()
        );


        LocalDateTime workoutDate = LocalDateTime.now();
        LocalDateTime nextDayWorkoutDate = workoutDate.plusDays(1);

        var firstTreadmillWorkout = createMachineHistory(bob.getId(), treadmill.getId(),
                MachineHistoryDao.builder()
                        .maxWeight(1)
                        .workoutDate(workoutDate)
                        .numberSets(2)
                        .maxWeight(3)
                        .numberReps(1)
                        .build()
        );

        var secondTreadmillWorkout = createMachineHistory(bob.getId(), treadmill.getId(),
                MachineHistoryDao.builder()
                        .maxWeight(4)
                        .workoutDate(nextDayWorkoutDate)
                        .numberSets(5)
                        .maxWeight(6)
                        .numberReps(1)
                        .build()
        );

        var actualBenchWorkoutHistory = createMachineHistory(bob.getId(), bench.getId(),
                MachineHistoryDao.builder()
                        .maxWeight(7)
                        .workoutDate(workoutDate)
                        .numberSets(8)
                        .maxWeight(9)
                        .numberReps(1)
                        .build()
        );

        createMachineHistory(bob.getId(), bench.getId(),
                MachineHistoryDao.builder()
                        .maxWeight(7)
                        .workoutDate(workoutDate.plusYears(1))
                        .numberSets(8)
                        .maxWeight(9)
                        .numberReps(1)
                        .build()
        );

        var reportWithNoHistories = getUserReport(bob.getId(), bench.getId(), workoutDate.minusDays(2), workoutDate.minusDays(1));
        var reportWithTreadmillHistories = getUserReport(bob.getId(), treadmill.getId(), workoutDate.minusDays(2), workoutDate.plusDays(1));
        var reportWithBenchHistories = getUserReport(bob.getId(), bench.getId(), workoutDate.minusDays(2), workoutDate.plusDays(1));

        assertEquals(0, reportWithNoHistories.getNumberOfWorkouts());
        assertEquals(0, reportWithNoHistories.getMachineProgress().size());
        assertEquals(workoutDate.minusDays(2), reportWithNoHistories.getStartDate());
        assertEquals(workoutDate.minusDays(1), reportWithNoHistories.getEndDate());

        assertEquals(2, reportWithTreadmillHistories.getNumberOfWorkouts());
        assertEquals(1, reportWithTreadmillHistories.getMachineProgress().size());
        assertEquals(2, reportWithTreadmillHistories.getMachineProgress().get(treadmill.getName()).size());

        //add asserts for progress
        assertEquals(firstTreadmillWorkout.getWorkoutDate(), reportWithTreadmillHistories.getMachineProgress().get(treadmill.getName()).get(0).getWorkoutDate());
        assertEquals(firstTreadmillWorkout.getNumberReps(), reportWithTreadmillHistories.getMachineProgress().get(treadmill.getName()).get(0).getNumberReps());
        assertEquals(firstTreadmillWorkout.getMaxWeight(), reportWithTreadmillHistories.getMachineProgress().get(treadmill.getName()).get(0).getMaxWeight());
        assertEquals(firstTreadmillWorkout.getNumberSets(), reportWithTreadmillHistories.getMachineProgress().get(treadmill.getName()).get(0).getNumberSets());

        assertEquals(secondTreadmillWorkout.getWorkoutDate(), reportWithTreadmillHistories.getMachineProgress().get(treadmill.getName()).get(1).getWorkoutDate());
        assertEquals(secondTreadmillWorkout.getNumberReps(), reportWithTreadmillHistories.getMachineProgress().get(treadmill.getName()).get(1).getNumberReps());
        assertEquals(secondTreadmillWorkout.getMaxWeight(), reportWithTreadmillHistories.getMachineProgress().get(treadmill.getName()).get(1).getMaxWeight());
        assertEquals(secondTreadmillWorkout.getNumberSets(), reportWithTreadmillHistories.getMachineProgress().get(treadmill.getName()).get(1).getNumberSets());

        assertEquals(workoutDate.minusDays(2), reportWithTreadmillHistories.getStartDate());
        assertEquals(workoutDate.plusDays(1), reportWithTreadmillHistories.getEndDate());

        assertEquals(2, reportWithBenchHistories.getNumberOfWorkouts());
        assertEquals(1, reportWithBenchHistories.getMachineProgress().size());
        assertTrue(reportWithBenchHistories.getMachineProgress().containsKey(bench.getName()));

        //add asserts for progress
        assertEquals(actualBenchWorkoutHistory.getWorkoutDate(), reportWithBenchHistories.getMachineProgress().get(bench.getName()).get(0).getWorkoutDate());
        assertEquals(actualBenchWorkoutHistory.getNumberReps(), reportWithBenchHistories.getMachineProgress().get(bench.getName()).get(0).getNumberReps());
        assertEquals(actualBenchWorkoutHistory.getMaxWeight(), reportWithBenchHistories.getMachineProgress().get(bench.getName()).get(0).getMaxWeight());
        assertEquals(actualBenchWorkoutHistory.getNumberSets(), reportWithBenchHistories.getMachineProgress().get(bench.getName()).get(0).getNumberSets());


        assertEquals(workoutDate.minusDays(2), reportWithBenchHistories.getStartDate());
        assertEquals(workoutDate.plusDays(1), reportWithBenchHistories.getEndDate());
    }

    private MemberDao createMember(MemberDao memberDao) throws Exception {
        var result = mvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberDao)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MemberDao.class);
    }

    private MachineDao createMachine(MachineDao machineDao) throws Exception {
        var result = mvc.perform(post("/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(machineDao)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MachineDao.class);
    }

    private MachineHistoryDao createMachineHistory(int memberId, int machineId, MachineHistoryDao machineHistoryDao) throws Exception {
        var result = mvc.perform(post("/members/" + memberId + "/machines/" + machineId + "/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(machineHistoryDao)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MachineHistoryDao.class);
    }

    private UserReportDto getUserReport(Integer memberId, Integer machineId, LocalDateTime startDate, LocalDateTime endDate) throws Exception {
        var result = mvc.perform(get("/reports/user")
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("memberId", Integer.toString(memberId))
                        .param("machineId", Integer.toString(machineId)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), UserReportDto.class);
    }
}
