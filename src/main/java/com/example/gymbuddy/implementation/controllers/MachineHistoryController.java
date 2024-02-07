package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.implementation.validators.requests.MachineHistoryRequestValidator;
import com.example.gymbuddy.infrastructure.exceptions.InvalidRequestException;
import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.infrastructure.services.IMachineHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "members/{member_id}/machines/{machine_id}/history")
@RequiredArgsConstructor
public class MachineHistoryController {
    private final IMachineHistoryService machineHistoryService;
    private final MachineHistoryRequestValidator validator;

    @GetMapping
    public ResponseEntity<List<MachineHistoryDto>> getHistory(@PathVariable(name = "member_id") int memberId,
                                                              @PathVariable(name = "machine_id") int machineId,
                                                              @Nullable
                                                              @RequestParam(name = "workout_date", required=false) LocalDateTime workoutDate) {
        return ResponseEntity.ok(machineHistoryService.findBy(memberId, machineId, workoutDate));
    }

    @PostMapping
    public ResponseEntity<MachineHistoryDto> addMachineHistory(@PathVariable(name = "member_id") Integer memberId,
                                                               @PathVariable(name = "machine_id") Integer machineId,
                                                               @Valid @RequestBody MachineHistoryDto machineHistoryDao) {
        var errors = validator.validate(new MachineHistoryRequestValidator.AddMachineHistoryRequest(memberId, machineId));
        if(!errors.isEmpty()) {
            throw new InvalidRequestException(errors);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(machineHistoryService.addMachineHistory(memberId, machineId, machineHistoryDao));
    }

    @RequestMapping(path = "/latest", method = RequestMethod.GET)
    public ResponseEntity<MachineHistoryDto> getLatestMachineHistory(@PathVariable(name = "member_id") int memberId,
                                                                     @PathVariable(name = "machine_id") int machineId) {
        return machineHistoryService.findLatestWorkout(memberId, machineId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
