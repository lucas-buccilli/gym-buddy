package com.example.gymbuddy.integration.repository;

import com.example.gymbuddy.implementation.database.specificationBuilders.MachineHistorySpecificationBuilder;
import com.example.gymbuddy.implementation.repositories.MachineHistoryRepository;
import com.example.gymbuddy.implementation.repositories.MachineRepository;
import com.example.gymbuddy.implementation.repositories.MemberRepository;
import com.example.gymbuddy.integration.DbContainer;
import com.example.gymbuddy.integration.EntityGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
public class MachineHistoryRepositoryIntegrationTest {
    @Container
    public static DbContainer dbContainer = DbContainer.getInstance();

    @Autowired
    private MachineHistoryRepository machineHistoryRepository;

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void afterEach() {
        machineHistoryRepository.deleteAll();
        machineRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void shouldFindMachineHistoryBySpecification() {
        var machine = EntityGenerator.getMachine();
        var member = EntityGenerator.getMember();
        machine = machineRepository.save(machine);
        member = memberRepository.save(member);
        var machineHistory = EntityGenerator.getMachineHistory(machine, member);
        machineHistory = machineHistoryRepository.save(machineHistory);
        var specification = MachineHistorySpecificationBuilder.builder()
                .hasMachineId(machine.getId())
                .hasMemberId(member.getId())
                .hasWorkoutDate(machineHistory.getWorkoutDate())
                .build();
        var machineHistories = machineHistoryRepository.findAll(specification);
        assertEquals(1, machineHistories.size());
        assertEquals(machineHistory, machineHistories.get(0));

        specification = MachineHistorySpecificationBuilder.builder().hasMachineId(machine.getId() + 1).build();
        machineHistories = machineHistoryRepository.findAll(specification);
        assertEquals(0, machineHistories.size());

        specification = MachineHistorySpecificationBuilder.builder().hasMemberId(member.getId() + 1).build();
        machineHistories = machineHistoryRepository.findAll(specification);
        assertEquals(0, machineHistories.size());

        specification = MachineHistorySpecificationBuilder.builder().hasWorkoutDate(LocalDateTime.now().minusDays(1L)).build();
        machineHistories = machineHistoryRepository.findAll(specification);
        assertEquals(0, machineHistories.size());
    }

    @Test
    void shouldFindMachineHistoryBySpecificationWithoutWorkoutDate() {
        var machine = EntityGenerator.getMachine();
        var member = EntityGenerator.getMember();
        machine = machineRepository.save(machine);
        member = memberRepository.save(member);
        var machineHistory = EntityGenerator.getMachineHistory(machine, member);
        machineHistory = machineHistoryRepository.save(machineHistory);
        var specification = MachineHistorySpecificationBuilder.builder()
                .hasMachineId(machine.getId())
                .hasMemberId(member.getId())
                .build();
        var machineHistories = machineHistoryRepository.findAll(specification);
        assertEquals(1, machineHistories.size());
        assertEquals(machineHistory, machineHistories.get(0));
    }

    @Test
    void shouldReturnLatestWorkout() {
        var machine = EntityGenerator.getMachine();
        var member = EntityGenerator.getMember();
        machine = machineRepository.save(machine);
        member = memberRepository.save(member);
        var machineHistory = EntityGenerator.getMachineHistory(machine, member);
        var machineHistoryLatest = EntityGenerator.getMachineHistory(machine, member);
        machineRepository.save(machine);
        memberRepository.save(member);
        machineHistoryRepository.save(machineHistory);
        machineHistoryRepository.save(machineHistoryLatest);

        var result = machineHistoryRepository
                .findTop1ByMemberIdAndMachineIdOrderByWorkoutDateDesc(member.getId(), machine.getId());
        assertTrue(result.isPresent());
        assertEquals(machineHistoryLatest.getWorkoutDate(), result.get().getWorkoutDate());
    }

    @Test
    void shouldReturnWorkoutBetweenDates() {
        var machine = EntityGenerator.getMachine();
        var member = EntityGenerator.getMember();
        machine = machineRepository.save(machine);
        member = memberRepository.save(member);
        var machineHistory = EntityGenerator.getMachineHistory(machine, member);
        machineHistory = machineHistoryRepository.save(machineHistory);
        machineHistoryRepository.save(machineHistory);

        var result = machineHistoryRepository.findAll(MachineHistorySpecificationBuilder.builder()
                .hasWorkoutDateLessOrEqualTo(machineHistory.getWorkoutDate().plus(Duration.ofHours(1)))
                .hasWorkoutDateGreaterOrEqualTo(machineHistory.getWorkoutDate().minus(Duration.ofHours(1)))
                .build());

        assertEquals(1, result.size());
        assertEquals(machineHistory.getId(), result.get(0).getId());
    }
}
