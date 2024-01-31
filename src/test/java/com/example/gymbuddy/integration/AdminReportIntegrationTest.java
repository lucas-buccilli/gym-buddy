package com.example.gymbuddy.integration;


import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminReportIntegrationTest extends IntegrationBase {

    @Test
    void generateAdminReport() throws Exception {
        var bench = createMachine(
                MachineDto
                        .builder()
                        .name("bench")
                        .build()
        );
        var treadmill = createMachine(
                MachineDto
                        .builder()
                        .name("treadmill")
                        .build()
        );

        List<MemberDto> list = IntStream.range(0, 9).mapToObj(intValue -> createMember()).toList();

        HashMap<Integer, List<MachineHistoryDto>> machineHistoriesMap = new HashMap<>();
        list.forEach(member -> {
            IntStream.range(0, 3)
                    .mapToObj(intValue -> createMachineHistory(member.getId(), bench.getId()))
                    .forEach(machineHistory ->
                            machineHistoriesMap.computeIfAbsent(member.getId(), k -> new ArrayList<MachineHistoryDto>())
                            .add(machineHistory)
                    );
        });

        var adminReport = getAdminReport(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        assertEquals(9, adminReport.getNumberOfVisitors());
        assertEquals(1, adminReport.getMachineUsage().size());
        assertEquals(27, adminReport.getMachineUsage().get(bench.getName()));

        assertTrue(adminReport.getMachineUsage().containsKey(bench.getName()));
        assertFalse(adminReport.getMachineUsage().containsKey(treadmill.getName()));

        var zeroActiveUsersAdminReport = getAdminReport(LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1));
        assertEquals(0, zeroActiveUsersAdminReport.getNumberOfVisitors());
    }
}
