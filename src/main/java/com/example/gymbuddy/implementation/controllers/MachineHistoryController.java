package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.infrastructure.services.MachineHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "members/{member_id}/machines/{machine_id}/history")
@RequiredArgsConstructor
public class MachineHistoryController {
    private final MachineHistoryService machineHistoryService;

    @GetMapping
    public ResponseEntity<List<MachineHistoryDto>> getHistory(@PathVariable(name = "member_id") int memberId,
                                                              @PathVariable(name = "machine_id") int machineId,
                                                              @RequestParam(name = "workout_date", required=false) LocalDate workoutDate) {

        return ResponseEntity.ok(machineHistoryService.findBy(machineId, memberId, workoutDate));
    }

    @PostMapping
    public ResponseEntity<MachineHistoryDto> addMachineHistory(@PathVariable(name = "member_id") Integer memberId,
                                                               @PathVariable(name = "machine_id") Integer machineId,
                                                               @Valid @RequestBody MachineHistoryDto machineHistoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(machineHistoryService.addMachineHistory(machineId, memberId, machineHistoryDto));
    }
    //getLatestHistory
}
