package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMachineHistoryDataProvider;
import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import com.example.gymbuddy.infrastructure.models.Period;
import com.example.gymbuddy.infrastructure.models.daos.Dao;
import com.example.gymbuddy.infrastructure.models.dtos.MachineProgressDto;
import com.example.gymbuddy.infrastructure.models.dtos.UserReportDto;
import com.example.gymbuddy.infrastructure.services.IReportMachineProgressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportMachineProgressService implements IReportMachineProgressService {
    private final IMachineHistoryDataProvider machineHistoryDataProvider;
    private final ModelMapper modelMapper;
    @Override
    public void addMachineProgress(UserReportDto dto, Period period, Integer memberId, Integer machineId) {

        dto.setMachineProgress(
                machineHistoryDataProvider.getMachineProgress(period.startDate(), period.endDate(), memberId, machineId)
                        .entrySet().stream()
                            .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                daos -> daos.getValue().stream()
                                        .map(dao -> modelMapper.map(dao, MachineProgressDto.class))
                                        .toList()
                                )
                        )
                );
    }
}
