package com.example.gymbuddy.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
