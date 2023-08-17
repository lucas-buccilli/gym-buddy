package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.services.MachineHistoryService;
import com.example.gymbuddy.infrastructure.services.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MachineHistoryService machineHistoryService;

    @GetMapping
    public ResponseEntity<List<MemberDto>> findAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @PostMapping
    public ResponseEntity<MemberDto> addMember(@Valid @RequestBody MemberDto memberDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.addMember(memberDto));
    }

    @GetMapping(path = "/{member_id}/machine/{machine_id}/history")
    public ResponseEntity<List<MachineHistoryDto>> getHistory(@PathVariable(name = "member_id") int memberId,
                                                              @PathVariable(name = "machine_id") int machineId) {
        return ResponseEntity.ok(machineHistoryService.findByMachineIdAndMemberId(machineId, memberId));
    }
}
