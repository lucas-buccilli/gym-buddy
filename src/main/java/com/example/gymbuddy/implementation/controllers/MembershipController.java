package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;
import com.example.gymbuddy.infrastructure.services.MembershipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/memberships")
@RequiredArgsConstructor
public class MembershipController {
    private final MembershipService membershipService;

    @GetMapping
    public ResponseEntity<List<MembershipDto>> findAll() {
        return ResponseEntity.ok(membershipService.findAll());
    }

    @PostMapping
    public ResponseEntity<MembershipDto> addMembership(@Valid @RequestBody MembershipDto membershipDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(membershipService.addMembership(membershipDto));
    }
}
