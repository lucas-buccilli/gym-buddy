package com.example.gymbuddy.controllers;

import com.example.gymbuddy.entities.Machine;
import com.example.gymbuddy.repositories.MachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final MachineRepository machineRepository;
    @GetMapping("/")
    public ResponseEntity<List<Machine>> test() {
        return ResponseEntity.ok(machineRepository.findAll());
    }
}
