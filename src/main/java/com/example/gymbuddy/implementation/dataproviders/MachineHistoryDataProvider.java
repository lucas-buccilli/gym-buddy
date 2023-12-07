package com.example.gymbuddy.implementation.dataproviders;

import com.example.gymbuddy.implementation.database.specificationBuilders.MachineHistorySpecificationBuilder;
import com.example.gymbuddy.implementation.repositories.MachineHistoryRepository;
import com.example.gymbuddy.infrastructure.dataproviders.IMachineHistoryDataProvider;
import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import com.example.gymbuddy.infrastructure.models.daos.MachineHistoryDao;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MachineHistoryDataProvider implements IMachineHistoryDataProvider {
    private final MachineHistoryRepository machineHistoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MachineHistoryDao> findAll() {
        return machineHistoryRepository.findAll().stream()
                .map(machineHistoryEntity -> modelMapper.map(machineHistoryEntity, MachineHistoryDao.class))
                .toList();
    }

    @Override
    public MachineHistoryDao addMachineHistory(Integer memberId, Integer machineId, MachineHistoryDao machineHistoryDao) {
        machineHistoryDao.setMachineId(machineId);
        machineHistoryDao.setMemberId(memberId);
        var machineHistory = modelMapper.map(machineHistoryDao, MachineHistory.class);
        return modelMapper.map(machineHistoryRepository.save(machineHistory), MachineHistoryDao.class);
    }

    @Override
    public List<MachineHistoryDao> findBy(Integer memberId, Integer machineId, @Nullable LocalDateTime workoutDate) {
        MachineHistorySpecificationBuilder builder = MachineHistorySpecificationBuilder.builder()
                .hasMachineId(machineId)
                .hasMemberId(memberId);
        if (Objects.nonNull(workoutDate)) {
            builder.hasWorkoutDate(workoutDate);
        }
        var histories = machineHistoryRepository.findAll(builder.build());
        return histories.stream()
                .map(machineHistory -> modelMapper.map(machineHistory, MachineHistoryDao.class))
                .toList();
    }

    @Override
    public Optional<MachineHistoryDao> findLatestWorkout(Integer memberId, Integer machineId) {
        return machineHistoryRepository.findTop1ByMemberIdAndMachineIdOrderByWorkoutDateDesc(memberId, machineId)
                .map(machineHistory -> modelMapper.map(machineHistory, MachineHistoryDao.class));
    }

    @Override
    public Integer getNumberOfVisitorsWithinTimeframe(LocalDateTime startDate, LocalDateTime endDate) {
        return machineHistoryRepository.countByWorkoutDateBetween(startDate, endDate);
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
}
