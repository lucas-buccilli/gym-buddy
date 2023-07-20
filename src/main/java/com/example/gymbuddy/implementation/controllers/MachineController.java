package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import com.example.gymbuddy.infrastructure.services.MachineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/machines")
@RequiredArgsConstructor
public class MachineController {
    private final MachineService machineService;

    @GetMapping
    public ResponseEntity<List<MachineDto>> findAll() {
        return ResponseEntity.ok(machineService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> addMachine(@Valid @RequestBody MachineDto machineDto) {
        return ResponseEntity.ok("TODO");
    }
}
