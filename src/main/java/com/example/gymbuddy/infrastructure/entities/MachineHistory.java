package com.example.gymbuddy.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

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
    private LocalDate workoutDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;
}
