package com.example.gymbuddy.implementation.database.specificationBuilders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MachineHistorySpecificationBuilderTest {

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
    void shouldThrowExceptionWhenWorkoutDateGreaterThanOrEqualToIsNull() {
        assertThrows(NullPointerException.class, () -> MachineHistorySpecificationBuilder.builder()
                .hasWorkoutDateGreaterOrEqualTo(null));
    }

    @Test
    void shouldThrowExceptionWhenWorkoutDateLessThanOrEqualToIsNull() {
        assertThrows(NullPointerException.class, () -> MachineHistorySpecificationBuilder.builder()
                .hasWorkoutDateLessOrEqualTo(null));
    }

    @Test
    void shouldThrowExceptionWhenSpecificationIsNull() {
        assertThrows(NullPointerException.class, () -> MachineHistorySpecificationBuilder.builder()
                .build());
    }
}