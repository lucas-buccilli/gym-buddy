package com.example.gymbuddy.integration;


import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MachineIntegrationTest extends IntegrationBase {

    @Test
    void shouldOperateOnMachines() throws Exception {

        var treadmill = Machine.create(
                MachineDto.builder()
                        .name("Treadmill")
                        .build(),
                Admin
        );

        Machine.delete(treadmill.getName(), Admin);

    }

    @Test
    void shouldReplaceMachine() throws Exception {
        var treadmill = Machine.create(
                MachineDto.builder()
                        .name("Treadmill")
                        .build(),
                Admin
        );
        treadmill.setName("Bench Press");

        var replacedMachine = Machine.replace(treadmill.getId(), treadmill, Admin);
        assertEquals(treadmill.getName(), replacedMachine.getName());
        assertEquals(treadmill.getId(), replacedMachine.getId());
    }

}
