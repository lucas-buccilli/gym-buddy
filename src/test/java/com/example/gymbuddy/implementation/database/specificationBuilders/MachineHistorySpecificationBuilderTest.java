package com.example.gymbuddy.implementation.database.specificationBuilders;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class MachineHistorySpecificationBuilderTest {
    private static final int MACHINE_ID = 1;
    private static final int MEMBER_ID = 2;
    private static final LocalDate WORKOUT_DATE = LocalDate.now();

    @Test
    void shouldPopulateSpecificationWhenNonNullFields() {
        var specification = MachineHistorySpecificationBuilder.builder()
                .hasMachineId(MACHINE_ID)
                .hasWorkoutDate(WORKOUT_DATE)
                .hasMemberId(MEMBER_ID)
                .build();
        
    }
}