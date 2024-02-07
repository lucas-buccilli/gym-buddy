package com.example.gymbuddy.integration;

import com.example.gymbuddy.infrastructure.entities.Machine;
import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import com.example.gymbuddy.infrastructure.entities.Member;

import java.time.LocalDateTime;
import java.util.UUID;

public class EntityGenerator {
    private static Integer idSeed = 0;

    public static Member getMember() {
        var member = new Member();
        member.setId(getId());
        member.setFirstName("FirstName" + UUID.randomUUID());
        member.setLastName("LastName" + UUID.randomUUID());
        member.setPhoneNumber("5555555555");
        member.setAuthId( UUID.randomUUID().toString());
        return member;
    }

    public static Machine getMachine() {
        var machine = new Machine();
        machine.setId(getId());
        machine.setName("MachineName" + UUID.randomUUID());
        return machine;
    }

    public static MachineHistory getMachineHistory(Machine machine, Member member) {
        var machineHistory = new MachineHistory();
        machineHistory.setId(getId());
        machineHistory.setMachine(machine);
        machineHistory.setMember(member);
        machineHistory.setNumberReps((int) (Math.random() * 100));
        machineHistory.setMaxWeight((int) (Math.random() * 100));
        machineHistory.setNumberSets((int) (Math.random() * 100));
        machineHistory.setWorkoutDate(LocalDateTime.now());
        return machineHistory;
    }

    private static Integer getId() {
        return idSeed++;
    }
}
