package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.infrastructure.models.daos.MachineDao;
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
    public ResponseEntity<List<MachineDao>> findAll() {
        return ResponseEntity.ok(machineService.findAll());
    }

    @PostMapping
    public ResponseEntity<MachineDao> addMachine(@Valid @RequestBody MachineDao machineDao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(machineService.addMachine(machineDao));
    }

    @DeleteMapping
    public ResponseEntity<MachineDao> deleteMachine(@Valid @RequestBody DeleteRequest request) {
        machineService.deleteMachineByName(request.name);
        return ResponseEntity.noContent().build();
    }

    public record DeleteRequest(String name) {} ;
}
