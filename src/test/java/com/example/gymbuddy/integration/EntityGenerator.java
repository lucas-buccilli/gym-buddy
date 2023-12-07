package com.example.gymbuddy.integration;

import com.example.gymbuddy.infrastructure.entities.Machine;
import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import com.example.gymbuddy.infrastructure.entities.Member;

import java.time.LocalDateTime;
import java.util.UUID;

public class EntityGenerator {

    public static Member getMember() {
        var member = new Member();
        member.setFirstName("FirstName" + UUID.randomUUID());
        member.setLastName("LastName" + UUID.randomUUID());
        member.setPhoneNumber("5555555555");
        return member;
    }

    public static Machine getMachine() {
        var machine = new Machine();
        machine.setName("MachineName" + UUID.randomUUID());
        return machine;
    }

    public static MachineHistory getMachineHistory(Machine machine, Member member) {
        var machineHistory = new MachineHistory();
        machineHistory.setMachine(machine);
        machineHistory.setMember(member);
        machineHistory.setNumberReps((int) (Math.random() * 100));
        machineHistory.setMaxWeight((int) (Math.random() * 100));
        machineHistory.setNumberSets((int) (Math.random() * 100));
        machineHistory.setWorkoutDate(LocalDateTime.now());
        return machineHistory;
    }
}
