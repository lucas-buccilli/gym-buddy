package com.example.gymbuddy.implementation.daos;

import com.example.gymbuddy.implementation.database.specificationBuilders.MachineHistorySpecificationBuilder;
import com.example.gymbuddy.implementation.repositories.MachineHistoryRepository;
import com.example.gymbuddy.infrastructure.daos.IMachineHistoryDao;
import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class MachineHistoryDao implements IMachineHistoryDao {
    private final MachineHistoryRepository machineHistoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MachineHistoryDto> findAll() {
        return machineHistoryRepository.findAll().stream()
                .map(machineHistoryEntity -> modelMapper.map(machineHistoryEntity, MachineHistoryDto.class))
                .toList();
    }

    @Override
    public MachineHistoryDto addMachineHistory(Integer memberId, Integer machineId, MachineHistoryDto machineHistoryDao) {
        machineHistoryDao.setMachineId(machineId);
        machineHistoryDao.setMemberId(memberId);
        var machineHistory = modelMapper.map(machineHistoryDao, MachineHistory.class);
        return modelMapper.map(machineHistoryRepository.save(machineHistory), MachineHistoryDto.class);
    }

    @Override
    public List<MachineHistoryDto> findBy(Integer memberId, Integer machineId, @Nullable LocalDateTime workoutDate) {
        MachineHistorySpecificationBuilder builder = MachineHistorySpecificationBuilder.builder()
                .hasMachineId(machineId)
                .hasMemberId(memberId);
        if (Objects.nonNull(workoutDate)) {
            builder.hasWorkoutDate(workoutDate);
        }
        var histories = machineHistoryRepository.findAll(builder.build());
        return histories.stream()
                .map(machineHistory -> modelMapper.map(machineHistory, MachineHistoryDto.class))
                .toList();
    }

    @Override
    public Optional<MachineHistoryDto> findLatestWorkout(Integer memberId, Integer machineId) {
        return machineHistoryRepository.findTop1ByMemberIdAndMachineIdOrderByWorkoutDateDesc(memberId, machineId)
                .map(machineHistory -> modelMapper.map(machineHistory, MachineHistoryDto.class));
    }

    @Override
    public Integer getNumberOfVisitorsWithinTimeframe(LocalDateTime startDate, LocalDateTime endDate) {
        return machineHistoryRepository.countFindDistinctMemberIdByWorkoutDateBetween(startDate, endDate);
    }

    @Override
    public Map<String, Long> getMachineUsage(LocalDateTime startDate, LocalDateTime endDate) {
        return machineHistoryRepository.findAll(MachineHistorySpecificationBuilder.builder()
                .hasWorkoutDateLessOrEqualTo(endDate)
                .hasWorkoutDateGreaterOrEqualTo(startDate)
                .build()
        ).stream()
                .collect(Collectors.groupingBy(
                        machineHistory -> machineHistory.getMachine().getName(),
                        Collectors.counting()
                        )
                );
    }

    public Map<String, List<MachineHistoryDto>> getMachineProgress(LocalDateTime startDate, LocalDateTime endDate, Integer memberId, Integer machineId) {

        return machineHistoryRepository.findAll(MachineHistorySpecificationBuilder.builder()
                        .hasMemberId(memberId)
                        .hasMachineId(machineId)
                    .hasWorkoutDateGreaterOrEqualTo(startDate)
                    .hasWorkoutDateLessOrEqualTo(endDate)
                .build(), Sort.by("workoutDate")
        ).stream()
                .collect(Collectors.groupingBy(
                        machineHistory -> machineHistory.getMachine().getName(),
                        Collectors.mapping(machineHistory -> modelMapper.map(machineHistory, MachineHistoryDto.class), Collectors.toList())
                        )
                );
    }

    @Override
    public Integer getNumberOfWorkoutDays(LocalDateTime startDate, LocalDateTime endDate, Integer memberId) {

        return machineHistoryRepository.findAll(MachineHistorySpecificationBuilder.builder()
                .hasMemberId(memberId)
                .hasWorkoutDateGreaterOrEqualTo(startDate)
                .hasWorkoutDateLessOrEqualTo(endDate)
                .build()
        ).stream()
            .map(machineHistory -> machineHistory.getWorkoutDate().truncatedTo(ChronoUnit.DAYS))
            .collect(toSet()).size();
    }
}