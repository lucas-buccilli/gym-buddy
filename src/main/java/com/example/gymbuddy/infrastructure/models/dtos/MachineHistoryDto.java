package com.example.gymbuddy.infrastructure.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineHistoryDto {
    @Null
    private Integer id;
    @NotNull
    private Integer numberReps;
    @NotNull
    private Integer maxWeight;
    @NotNull
    private Integer numberSets;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull
    private LocalDateTime workoutDate;
    @Null
    private Integer memberId;
    @Null
    private Integer machineId;
}