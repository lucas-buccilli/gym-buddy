package com.example.gymbuddy.implementation.daos;

import com.example.gymbuddy.implementation.database.specificationBuilders.SpecificationBuilder;
import com.example.gymbuddy.implementation.repositories.MachineHistoryRepository;
import com.example.gymbuddy.infrastructure.daos.IMachineHistoryDao;
import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class MachineHistoryDao implements IMachineHistoryDao {
    private final MachineHistoryRepository machineHistoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public MachineHistoryDto addMachineHistory(Integer memberId, Integer machineId, MachineHistoryDto machineHistoryDao) {
        machineHistoryDao.setMachineId(machineId);
        machineHistoryDao.setMemberId(memberId);
        var machineHistory = modelMapper.map(machineHistoryDao, MachineHistory.class);
        return modelMapper.map(machineHistoryRepository.save(machineHistory), MachineHistoryDto.class);
    }

    @Override
    public List<MachineHistoryDto> findBy(Integer memberId, Integer machineId, PageRequest pageRequest) {
        var specification = new SpecificationBuilder<MachineHistory>()
                .hasNestedField("machine", "machineId", machineId)
                .hasNestedField("member", "memberId", memberId)
                .build()
                .and(pageRequest.toSpecification());
        var histories = machineHistoryRepository.findAll(specification, pageRequest.toPageable());
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
        return machineHistoryRepository.findAll(new SpecificationBuilder<MachineHistory>()
                .hasFieldLessThanOrEqualTo("workoutDate", endDate)
                .hasFieldGreaterThanOrEqualTo("workoutDate", startDate)
                .build()
        ).stream()
                .collect(Collectors.groupingBy(
                        machineHistory -> machineHistory.getMachine().getName(),
                        Collectors.counting()
                        )
                );
    }

    public Map<String, List<MachineHistoryDto>> getMachineProgress(LocalDateTime startDate, LocalDateTime endDate, Integer memberId, Integer machineId) {

        return machineHistoryRepository.findAll(new SpecificationBuilder<MachineHistory>()
                        .hasNestedField("member", "id", memberId)
                        .hasNestedField("machine", "id", machineId)
                        .hasFieldGreaterThanOrEqualTo("workoutDate", startDate)
                        .hasFieldLessThanOrEqualTo("workoutDate", endDate)
                        .build(),
                        Sort.by("workoutDate")
        ).stream()
                .collect(Collectors.groupingBy(
                        machineHistory -> machineHistory.getMachine().getName(),
                        Collectors.mapping(machineHistory -> modelMapper.map(machineHistory, MachineHistoryDto.class), Collectors.toList())
                        )
                );
    }

    @Override
    public Integer getNumberOfWorkoutDays(LocalDateTime startDate, LocalDateTime endDate, Integer memberId) {

        return machineHistoryRepository.findAll(new SpecificationBuilder<MachineHistory>()
                        .hasNestedField("member", "id", memberId)
                        .hasFieldGreaterThanOrEqualTo("workoutDate", startDate)
                        .hasFieldLessThanOrEqualTo("workoutDate", endDate)
                        .build()
        ).stream()
            .map(machineHistory -> machineHistory.getWorkoutDate().truncatedTo(ChronoUnit.DAYS))
            .collect(toSet()).size();
    }
}