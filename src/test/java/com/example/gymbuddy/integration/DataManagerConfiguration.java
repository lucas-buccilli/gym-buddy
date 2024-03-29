package com.example.gymbuddy.integration;

import com.example.gymbuddy.implementation.repositories.MachineHistoryRepository;
import com.example.gymbuddy.implementation.repositories.MachineRepository;
import com.example.gymbuddy.implementation.repositories.MemberRepository;
import com.example.gymbuddy.implementation.repositories.MembershipHistoryRepository;
import com.example.gymbuddy.implementation.repositories.MembershipRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DataManagerConfiguration {
    @Bean
    public TestDataManager testDataManager(MembershipHistoryRepository membershipHistoryRepository,
                                           MachineHistoryRepository machineHistoryRepository,
                                           MemberRepository memberRepository,
                                           MachineRepository machineRepository,
                                           MembershipRepository membershipRepository) {
        return new TestDataManager(membershipHistoryRepository, machineHistoryRepository, memberRepository, machineRepository, membershipRepository);
    }
}
