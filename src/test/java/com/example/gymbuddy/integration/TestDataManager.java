package com.example.gymbuddy.integration;

import com.example.gymbuddy.implementation.repositories.MachineHistoryRepository;
import com.example.gymbuddy.implementation.repositories.MachineRepository;
import com.example.gymbuddy.implementation.repositories.MemberRepository;
import com.example.gymbuddy.implementation.repositories.MembershipHistoryRepository;
import com.example.gymbuddy.implementation.repositories.MembershipRepository;

public class TestDataManager {

    private final MembershipHistoryRepository membershipHistoryRepository;
    private final MachineHistoryRepository machineHistoryRepository;
    private final MemberRepository memberRepository;
    private final MachineRepository machineRepository;
    private final MembershipRepository membershipRepository;

    public TestDataManager(MembershipHistoryRepository membershipHistoryRepository,
                           MachineHistoryRepository machineHistoryRepository,
                           MemberRepository memberRepository,
                           MachineRepository machineRepository,
                           MembershipRepository membershipRepository) {
        this.membershipHistoryRepository = membershipHistoryRepository;
        this.machineHistoryRepository = machineHistoryRepository;
        this.memberRepository = memberRepository;
        this.machineRepository = machineRepository;
        this.membershipRepository = membershipRepository;
    }

    public void deleteAllData() {
        membershipHistoryRepository.deleteAll();
        machineHistoryRepository.deleteAll();
        membershipRepository.deleteAll();
        memberRepository.deleteAll();
        machineRepository.deleteAll();
    }
}
