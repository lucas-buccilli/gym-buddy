package com.example.gymbuddy.infrastructure.entities;

import com.example.gymbuddy.infrastructure.models.Filterable;
import com.example.gymbuddy.infrastructure.models.Sortable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Sortable
    @Filterable
    @Column(name = "first_name")
    private String firstName;

    @Sortable
    @Filterable
    @Column(name = "last_name")
    private String lastName;

    @Sortable
    @Filterable
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "auth_id")
    private String authId;
}
