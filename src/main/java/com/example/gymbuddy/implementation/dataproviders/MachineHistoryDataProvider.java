package com.example.gymbuddy.implementation.dataproviders;

import com.example.gymbuddy.implementation.database.specificationBuilders.MachineHistorySpecificationBuilder;
import com.example.gymbuddy.implementation.repositories.MachineHistoryRepository;
import com.example.gymbuddy.infrastructure.dataproviders.IMachineHistoryDataProvider;
import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MachineHistoryDataProvider implements IMachineHistoryDataProvider {
    private final MachineHistoryRepository machineHistoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MachineHistoryDto> findAll() {
        return machineHistoryRepository.findAll().stream()
                .map(machineHistoryEntity -> modelMapper.map(machineHistoryEntity, MachineHistoryDto.class))
                .toList();
    }

    @Override
    public MachineHistoryDto addMachineHistory(Integer memberId, Integer machineId, MachineHistoryDto machineHistoryDto) {
        machineHistoryDto.setMachineId(machineId);
        machineHistoryDto.setMemberId(memberId);
        var machineHistory = modelMapper.map(machineHistoryDto, MachineHistory.class);
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
}
