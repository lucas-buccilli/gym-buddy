package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import com.example.gymbuddy.infrastructure.services.IMachineService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/machines")
@RequiredArgsConstructor
public class MachineController {
    private final IMachineService machineService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<MachineDto>> findAll() {
        return ResponseEntity.ok(machineService.findAll());
    }

    @PostMapping
    public ResponseEntity<MachineDto> addMachine(@Valid @RequestBody ReplaceOrAddRequest replaceOrAddRequest) {
        var machineDto = modelMapper.map(replaceOrAddRequest, MachineDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(machineService.addMachine(machineDto));
    }

    @DeleteMapping
    public ResponseEntity<MachineDto> deleteMachine(@Valid @RequestBody DeleteRequest request) {
        machineService.deleteMachineByName(request.name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<MachineDto> replaceMachine(@PathVariable int id,
                                                     @Valid @RequestBody ReplaceOrAddRequest replaceOrAddRequest) {
        var machineDto = modelMapper.map(replaceOrAddRequest, MachineDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(machineService.replaceMachine(id, machineDto));
    }

    public record DeleteRequest(String name) {} ;

    public record ReplaceOrAddRequest(
            @Size(max = 500, message = "The length of name must be between less than 500 characters.")
            @NotNull
            String name) {}
}
