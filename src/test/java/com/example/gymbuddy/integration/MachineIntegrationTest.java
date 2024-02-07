package com.example.gymbuddy.integration;


import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MachineIntegrationTest extends IntegrationBase {

    @Test
    void shouldOperateOnMachines() throws Exception {

        var treadmill = createMachine(
                MachineDto.builder()
                        .name("Treadmill")
                        .build(),
                Admin
        );

        deleteMachine(treadmill.getName(), Admin);

    }

    @Test
    void shouldReplaceMachine() throws Exception {
        var treadmill = createMachine(
                MachineDto.builder()
                        .name("Treadmill")
                        .build(),
                Admin
        );
        treadmill.setName("Bench Press");

        var replacedMachine = replaceMachine(treadmill.getId(), treadmill, Admin);
        assertEquals(treadmill.getName(), replacedMachine.getName());
        assertEquals(treadmill.getId(), replacedMachine.getId());
    }

}
