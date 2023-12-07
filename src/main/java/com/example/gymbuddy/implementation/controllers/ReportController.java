package com.example.gymbuddy.implementation.controllers;

import com.example.gymbuddy.infrastructure.services.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ReportController {
    private final IReportService reportService;

    @GetMapping(path = "/reports/admin")
    public ResponseEntity<?> getAdminReport(@RequestParam LocalDateTime startDate,
                                            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(reportService.getAdminReport(startDate, endDate));
    }
}
