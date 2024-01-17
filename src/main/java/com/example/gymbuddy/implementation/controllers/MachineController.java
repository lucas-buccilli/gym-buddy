package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import com.example.gymbuddy.infrastructure.services.IMachineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/machines")
@RequiredArgsConstructor
public class MachineController {
    private final IMachineService machineService;

    @GetMapping
    public ResponseEntity<List<MachineDto>> findAll() {
        return ResponseEntity.ok(machineService.findAll());
    }

    @PostMapping
    public ResponseEntity<MachineDto> addMachine(@Valid @RequestBody MachineDto machineDao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(machineService.addMachine(machineDao));
    }

    @DeleteMapping
    public ResponseEntity<MachineDto> deleteMachine(@Valid @RequestBody DeleteRequest request) {
        machineService.deleteMachineByName(request.name);
        return ResponseEntity.noContent().build();
    }

    public record DeleteRequest(String name) {} ;
}
