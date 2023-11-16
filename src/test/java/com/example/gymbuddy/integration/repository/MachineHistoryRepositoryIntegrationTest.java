package com.example.gymbuddy.integration.repository;

import com.example.gymbuddy.implementation.database.specificationBuilders.MachineHistorySpecificationBuilder;
import com.example.gymbuddy.implementation.repositories.MachineHistoryRepository;
import com.example.gymbuddy.implementation.repositories.MachineRepository;
import com.example.gymbuddy.implementation.repositories.MemberRepository;
import com.example.gymbuddy.integration.DbContainer;
import com.example.gymbuddy.integration.EntityGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void temp() {
        var machine = EntityGenerator.getMachine();
        var member = EntityGenerator.getMember();
        var machineHistory = EntityGenerator.getMachineHistory(machine, member);
        machineRepository.save(machine);
        memberRepository.save(member);
        machineHistoryRepository.save(machineHistory);
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
    void temp2() {
        var machine = EntityGenerator.getMachine();
        var member = EntityGenerator.getMember();
        var machineHistory = EntityGenerator.getMachineHistory(machine, member);
        machineRepository.save(machine);
        memberRepository.save(member);
        machineHistoryRepository.save(machineHistory);
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
        var machineHistory = EntityGenerator.getMachineHistory(machine, member);
        var machineHistoryLatest = EntityGenerator.getMachineHistory(machine, member);
        machineRepository.save(machine);
        memberRepository.save(member);
        machineHistoryRepository.save(machineHistory);
        machineHistoryRepository.save(machineHistoryLatest);

        var result = machineHistoryRepository
                .findTop1ByMemberIdAndMachineIdOrderByWorkoutDateDesc(member.getId(), machine.getId());
        assertEquals(machineHistoryLatest.getWorkoutDate(), result.get().getWorkoutDate());
    }
}
