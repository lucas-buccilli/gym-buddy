package com.example.gymbuddy.infrastructure.entities;

import com.example.gymbuddy.implementation.converters.DateTimeConverter;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    // Need to configure model mapper to handle mapping member_id to member
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JsonAlias("machine_id")
    private Machine machine;
}
