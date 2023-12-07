package com.example.gymbuddy.infrastructure.models;

import java.time.LocalDateTime;

public record Period(LocalDateTime startDate, LocalDateTime endDate) implements IStartPeriod, IEndPeriod {
}
