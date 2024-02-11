package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.implementation.aop.EnforceRls;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import com.example.gymbuddy.infrastructure.models.dtos.UserReportDto;
import com.example.gymbuddy.infrastructure.services.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ReportController {
    private final IReportService reportService;

    @GetMapping(path = "/reports/admin")
    @PreAuthorize("hasRole('Admin')")
    @EnforceRls(noMemberParameter = true)
    public ResponseEntity<AdminReportDto> getAdminReport(@RequestParam LocalDateTime startDate,
                                                         @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(reportService.getAdminReport(startDate, endDate));
    }

    @GetMapping(path = "/reports/user")
    @PreAuthorize("hasPermission('user-reports', 'read') or hasRole('Admin')")
    @EnforceRls(memberIdParameterName = "memberId")
    public ResponseEntity<UserReportDto> getUserReport(@RequestParam LocalDateTime startDate,
                                                       @RequestParam LocalDateTime endDate,
                                                       @RequestParam Integer memberId,
                                                       @RequestParam Integer machineId) {
        return ResponseEntity.ok(reportService.getUserReport(startDate, endDate,  memberId, machineId));
    }
}
