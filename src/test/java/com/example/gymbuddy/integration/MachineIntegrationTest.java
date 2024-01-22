package com.example.gymbuddy.integration;


import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class MachineIntegrationTest extends IntegrationBase {

    @Test
    void shouldOperateOnMachines() throws Exception {

        var treadmill = createMachine(
                MachineDto.builder()
                        .name("Treadmill")
                        .build()
        );

        deleteMachine(treadmill.getName());

    }

}
