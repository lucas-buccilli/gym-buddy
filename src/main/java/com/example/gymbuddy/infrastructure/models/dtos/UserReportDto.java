package com.example.gymbuddy.infrastructure.models.dtos;

import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReportDto implements IDatedReportDto{
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Map<String, List<MachineProgressDto>> machineProgress;
    private Integer numberOfWorkouts;
}
