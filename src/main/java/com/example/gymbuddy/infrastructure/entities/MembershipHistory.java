package com.example.gymbuddy.infrastructure.entities;


import com.example.gymbuddy.implementation.converters.DateTimeConverter;
import com.example.gymbuddy.implementation.converters.MembershipOperationConverter;
import com.example.gymbuddy.infrastructure.entities.enums.MembershipOperation;
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
public class MembershipHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date")
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @Convert(converter =  MembershipOperationConverter.class)
    private MembershipOperation operation;
}
