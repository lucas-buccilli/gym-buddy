package com.example.gymbuddy.integration;


import com.example.gymbuddy.infrastructure.models.AuthRoles;
import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.models.requests.MemberRequests;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserReportIntegrationTest extends IntegrationBase{

    @Test
    void generateUserReport() throws Exception {

        var bobAddRequest = MemberRequests.AddRequest.builder()
                .firstName("Bob")
                .lastName("TestLast")
                .phoneNumber("0000000000")
                .email("emai@email.com")
                .password("password1234!")
                .roles(List.of(AuthRoles.MEMBER))
                .build();

        AuthServiceStubber.builder(authService)
                .createUser()
                    .when(bobAddRequest.getEmail(), bobAddRequest.getPassword())
                    .thenReturn(RandomStringUtils.randomAlphabetic(5));

        var bob =  Member.create(bobAddRequest, Admin);

        var treadmill = Machine.create(
                MachineDto.builder()
                        .name("Treadmill")
                        .build(),
                Admin
        );

        var bench = Machine.create(
                MachineDto.builder()
                        .name("Bench")
                        .build(),
                Admin
        );


        LocalDateTime workoutDate = LocalDateTime.now();
        LocalDateTime nextDayWorkoutDate = workoutDate.plusDays(1);

        var firstTreadmillWorkout = MachineHistory.create(bob.getId(), treadmill.getId(),
                MachineHistoryDto.builder()
                        .maxWeight(1)
                        .workoutDate(workoutDate)
                        .numberSets(2)
                        .maxWeight(3)
                        .numberReps(1)
                        .build(),
                Admin
        );

        var secondTreadmillWorkout = MachineHistory.create(bob.getId(), treadmill.getId(),
                MachineHistoryDto.builder()
                        .maxWeight(4)
                        .workoutDate(nextDayWorkoutDate)
                        .numberSets(5)
                        .maxWeight(6)
                        .numberReps(1)
                        .build(),
                Admin
        );

        var actualBenchWorkoutHistory = MachineHistory.create(bob.getId(), bench.getId(),
                MachineHistoryDto.builder()
                        .maxWeight(7)
                        .workoutDate(workoutDate)
                        .numberSets(8)
                        .maxWeight(9)
                        .numberReps(1)
                        .build(),
                Admin
        );

        MachineHistory.create(bob.getId(), bench.getId(),
                MachineHistoryDto.builder()
                        .maxWeight(7)
                        .workoutDate(workoutDate.plusYears(1))
                        .numberSets(8)
                        .maxWeight(9)
                        .numberReps(1)
                        .build(),
                Admin
        );

        var reportWithNoHistories = Reports.getUserReport(bob.getId(), bench.getId(), workoutDate.minusDays(2), workoutDate.minusDays(1), Admin);
        var reportWithTreadmillHistories = Reports.getUserReport(bob.getId(), treadmill.getId(), workoutDate.minusDays(2), workoutDate.plusDays(1), Admin);
        var reportWithBenchHistories = Reports.getUserReport(bob.getId(), bench.getId(), workoutDate.minusDays(2), workoutDate.plusDays(1), Admin);

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


}
