package com.example.gymbuddy.infrastructure.models.dtos;


import java.time.LocalDateTime;

public interface IDatedReportDto {

    void setStartDate(LocalDateTime datetime);
    void setEndDate(LocalDateTime datetime);

}
