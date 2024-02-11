package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.implementation.aop.EnforceRls;
import com.example.gymbuddy.infrastructure.models.dtos.MembershipDto;
import com.example.gymbuddy.infrastructure.services.IMembershipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/memberships")
@RequiredArgsConstructor
public class MembershipController {
    private final IMembershipService membershipService;

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    @EnforceRls(noMemberParameter = true)
    public ResponseEntity<List<MembershipDto>> findAll() {
        return ResponseEntity.ok(membershipService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    @EnforceRls(noMemberParameter = true)
    public ResponseEntity<MembershipDto> addMembership(@Valid @RequestBody MembershipDto membershipDao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(membershipService.addMembership(membershipDao));
    }
}
