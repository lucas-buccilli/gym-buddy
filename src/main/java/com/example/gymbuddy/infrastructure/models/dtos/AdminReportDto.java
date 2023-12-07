package com.example.gymbuddy.infrastructure.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminReportDto implements IDatedReportDto{
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer numberOfVisitors;
    private Integer activeMemberships;
    private Map<String, Long> machineUsage;
}
