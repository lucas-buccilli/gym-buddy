package com.example.gymbuddy.infrastructure.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PeriodTest {

    @Test
    void shouldSetStartAndEndDate() {
        var period  = new Period(LocalDateTime.MAX, LocalDateTime.MIN);
        assertEquals(LocalDateTime.MAX, period.startDate());
        assertEquals(LocalDateTime.MIN, period.endDate());
    }
}