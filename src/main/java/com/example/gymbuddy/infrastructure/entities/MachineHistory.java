package com.example.gymbuddy.infrastructure.entities;

import com.example.gymbuddy.implementation.converters.DateTimeConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class MachineHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "number_reps")
    private Integer numberReps;

    @Column(name = "max_weight")
    private Integer maxWeight;

    @Column(name = "number_sets")
    private Integer numberSets;

    @Column(name = "workout_date")
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime workoutDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;
}
