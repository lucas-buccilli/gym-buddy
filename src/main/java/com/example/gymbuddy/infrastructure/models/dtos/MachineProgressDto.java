package com.example.gymbuddy.infrastructure.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineProgressDto {
    private LocalDateTime workoutDate;
    private Integer numberReps;
    private Integer maxWeight;
    private Integer numberSets;
}
