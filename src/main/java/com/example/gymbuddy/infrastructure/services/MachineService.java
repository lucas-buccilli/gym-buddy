package com.example.gymbuddy.infrastructure.services;

import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;

import java.util.List;

public interface MachineService {
    List<MachineDto> findAll();
}
