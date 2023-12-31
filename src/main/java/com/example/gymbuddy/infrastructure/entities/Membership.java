package com.example.gymbuddy.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "status")
    private boolean active;

    //startedAt datetime
}
