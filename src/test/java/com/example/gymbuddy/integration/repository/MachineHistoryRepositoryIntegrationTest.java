package com.example.gymbuddy.integration.repository;

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
        var t = machineHistoryRepository.findAll();
    }
}
