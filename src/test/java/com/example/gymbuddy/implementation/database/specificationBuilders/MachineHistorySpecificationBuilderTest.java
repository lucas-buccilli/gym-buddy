package com.example.gymbuddy.implementation.database.specificationBuilders;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MachineHistorySpecificationBuilderTest {
    private static final int MACHINE_ID = 1;
    private static final int MEMBER_ID = 2;
    private static final LocalDate WORKOUT_DATE = LocalDate.now();

    @Test
    void shouldThrowExceptionWhenMachineIdIsNull() {
        assertThrows(NullPointerException.class, () -> MachineHistorySpecificationBuilder.builder()
                .hasMachineId(null));
    }

    @Test
    void shouldThrowExceptionWhenMemberIdIsNull() {
        assertThrows(NullPointerException.class, () -> MachineHistorySpecificationBuilder.builder()
                .hasMemberId(null));
    }

    @Test
    void shouldThrowExceptionWhenWorkoutDateIsNull() {
        assertThrows(NullPointerException.class, () -> MachineHistorySpecificationBuilder.builder()
                .hasWorkoutDate(null));
    }

    @Test
    void shouldThrowExceptionWhenSpecificationIsNull() {
        assertThrows(NullPointerException.class, () -> MachineHistorySpecificationBuilder.builder()
                .build());
    }
}