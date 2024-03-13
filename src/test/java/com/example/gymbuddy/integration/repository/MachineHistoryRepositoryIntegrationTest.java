package com.example.gymbuddy.integration.repository;

import com.example.gymbuddy.implementation.database.specificationBuilders.SpecificationBuilder;
import com.example.gymbuddy.implementation.repositories.MachineHistoryRepository;
import com.example.gymbuddy.implementation.repositories.MachineRepository;
import com.example.gymbuddy.implementation.repositories.MemberRepository;
import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import com.example.gymbuddy.integration.DbContainer;
import com.example.gymbuddy.integration.EntityGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Tag("IntegrationTests")
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
        var specification = new SpecificationBuilder<MachineHistory>()
                .hasNestedField("member", "id", member.getId())
                .hasNestedField("machine", "id", machine.getId())
                .hasField("workoutDate", machineHistory.getWorkoutDate())
                .build();
        var machineHistories = machineHistoryRepository.findAll(specification);
        assertEquals(1, machineHistories.size());
        assertEquals(machineHistory, machineHistories.get(0));

        specification = new SpecificationBuilder<MachineHistory>()
                .hasNestedField("machine", "id", machine.getId() + 1)
                .build();
        machineHistories = machineHistoryRepository.findAll(specification);
        assertEquals(0, machineHistories.size());

        specification = new SpecificationBuilder<MachineHistory>()
                .hasNestedField("member", "id", member.getId() + 1)
                .build();
        machineHistories = machineHistoryRepository.findAll(specification);
        assertEquals(0, machineHistories.size());

        specification = new SpecificationBuilder<MachineHistory>()
                .hasField("workoutDate", machineHistory.getWorkoutDate().minusDays(1L))
                .build();
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
        var specification = new SpecificationBuilder<MachineHistory>()
                .hasNestedField("member", "id", member.getId())
                .hasNestedField("machine", "id", machine.getId())
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

        var result = machineHistoryRepository.findAll(
                new SpecificationBuilder<MachineHistory>()
                    .hasFieldGreaterThanOrEqualTo("workoutDate", machineHistory.getWorkoutDate().minus(Duration.ofHours(1)))
                    .hasFieldLessThanOrEqualTo("workoutDate", machineHistory.getWorkoutDate().plus(Duration.ofHours(1)))
                    .build()
        );

        assertEquals(1, result.size());
        assertEquals(machineHistory.getId(), result.get(0).getId());
    }
}
