package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.implementation.aop.EnforceRls;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.models.requests.MemberRequests;
import com.example.gymbuddy.infrastructure.services.IMemberService;
import jakarta.validation.Valid;
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
    @EnforceRls(noMemberParameter = true)
    @GetMapping
    public ResponseEntity<List<MemberDto>> findAll() {
        return ResponseEntity.ok(memberService.findAll());
    }


    @PreAuthorize("hasPermission('members', 'create') or hasRole('Admin')")
    @EnforceRls(noMemberParameter = true)
    @PostMapping
    public ResponseEntity<MemberDto> addMember(@Valid @RequestBody MemberRequests.AddRequest addRequest) {
        var dao = modelMapper.map(addRequest, MemberDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.addMember(dao));
    }

    @PreAuthorize("hasPermission('members', 'delete') or hasRole('Admin')")
    @EnforceRls(memberIdParameterName = "id")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable int id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasPermission('members', 'modify') or hasRole('Admin')")
    @EnforceRls(memberIdParameterName = "id")
    @PatchMapping(path = "/{id}")
    public ResponseEntity<?> modifyMember(@PathVariable int id,
                                          @Valid @RequestBody MemberRequests.UpdateRequest updateRequest) throws IllegalAccessException {
        var modifiedMember = memberService.modifyMember(id, modelMapper.map(updateRequest, MemberDto.class));
        return ResponseEntity.status(HttpStatus.OK).body(modifiedMember);
    }

    @PreAuthorize("hasPermission('members', 'modify') or hasRole('Admin')")
    @EnforceRls(memberIdParameterName = "id")
    @PutMapping(path = "/{id}")
    public ResponseEntity<MemberDto> editMember(@PathVariable int id,
                                                @Valid @RequestBody MemberRequests.ReplaceRequest replaceRequest) {
        var dto = modelMapper.map(replaceRequest, MemberDto.class);
        return ResponseEntity.status(HttpStatus.OK).body(memberService.replaceMember(id, dto));
    }
}
