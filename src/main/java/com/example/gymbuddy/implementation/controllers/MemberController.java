package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.implementation.aop.EnforceRls;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.services.IMemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final IMemberService memberService;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasRole('Admin')")
    @GetMapping
    public ResponseEntity<List<MemberDto>> findAll() {
        return ResponseEntity.ok(memberService.findAll());
    }


    @PreAuthorize("hasPermission('members', 'create') or hasRole('Admin')")
    @PostMapping
    public ResponseEntity<MemberDto> addMember(@Valid @RequestBody AddRequest addRequest) {
        var dao = modelMapper.map(addRequest, MemberDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.addMember(dao));
    }

    @PreAuthorize("hasPermission('members', 'delete') or hasRole('Admin')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable int id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasPermission('members', 'modify') or hasRole('Admin')")
    @EnforceRls(memberIdParameterName = "id")
    @PatchMapping(path = "/{id}")
    public ResponseEntity<?> modifyMember(@PathVariable int id,
                                          @Valid @RequestBody UpdateRequest updateRequest) throws IllegalAccessException {
        var modifiedMember = memberService.modifyMember(id, modelMapper.map(updateRequest, MemberDto.class));
        return ResponseEntity.status(HttpStatus.OK).body(modifiedMember);
    }

    @PreAuthorize("hasPermission('members', 'modify') or hasRole('Admin')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<MemberDto> editMember(@PathVariable int id,
                                                @Valid @RequestBody ReplaceRequest replaceRequest) {
        var dto = modelMapper.map(replaceRequest, MemberDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(memberService.replaceMember(id, dto));
    }

    public record AddRequest(
            @Size(max = 50, message = "The length of first name must be between less than 50 characters.")
            @NotNull
            String firstName,
            @Size(max = 50, message = "The length of last name must be between less than 50 characters.")
            @NotNull
            String lastName,
            @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "The phone number must be valid.")
            @NotNull
            String phoneNumber,
            @NotNull
            String authId
        ) {}

    public record ReplaceRequest(
            @Size(max = 50, message = "The length of first name must be between less than 50 characters.")
            @NotNull
            String firstName,
            @Size(max = 50, message = "The length of last name must be between less than 50 characters.")
            @NotNull
            String lastName,
            @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "The phone number must be valid.")
            @NotNull
            String phoneNumber
        ) {}

    public record UpdateRequest(
            @Size(max = 50, message = "The length of first name must be between less than 50 characters.")
            String firstName,
            @Size(max = 50, message = "The length of last name must be between less than 50 characters.")
            String lastName,
            @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "The phone number must be valid.")
            String phoneNumber
    ) {}
}
