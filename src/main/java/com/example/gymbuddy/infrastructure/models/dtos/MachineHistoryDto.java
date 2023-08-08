package com.example.gymbuddy.infrastructure.models.dtos;

import com.example.gymbuddy.infrastructure.entities.Machine;
import com.example.gymbuddy.infrastructure.entities.Member;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineHistoryDto {
    private Integer id;
    @NotNull
    private Integer numberReps;
    @NotNull
    private Integer maxWeight;
    @NotNull
    private Integer numberSets;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate workoutDate;
    @NotNull
    private Integer memberId;
    @NotNull
    private Integer machineId;
}