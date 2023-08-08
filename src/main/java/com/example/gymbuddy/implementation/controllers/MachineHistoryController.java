package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.infrastructure.services.MachineHistoryService;
import com.example.gymbuddy.infrastructure.services.MachineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/machine_history")
@RequiredArgsConstructor
public class MachineHistoryController {
    private final MachineHistoryService machineHistoryService;

    @GetMapping
    public ResponseEntity<List<MachineHistoryDto>> findAll() {
        return ResponseEntity.ok(machineHistoryService.findAll());
    }

    @PostMapping
    public ResponseEntity<MachineHistoryDto> addMachineHistory(@Valid @RequestBody MachineHistoryDto machineHistoryDto) {
        return ResponseEntity.ok().body(machineHistoryService.addMachineHistory(machineHistoryDto));
    }
}
