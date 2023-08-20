package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.infrastructure.services.MachineHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("machine_history")
@RequiredArgsConstructor
public class MachineHistoryController {
    private final MachineHistoryService machineHistoryService;

    @PostMapping
    public ResponseEntity<MachineHistoryDto> addMachineHistory(@Valid @RequestBody MachineHistoryDto machineHistoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(machineHistoryService.addMachineHistory(machineHistoryDto));
    }
}
