package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.implementation.repositories.MachineHistoryRepository;
import com.example.gymbuddy.infrastructure.entities.Machine;
import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import com.example.gymbuddy.infrastructure.entities.Member;
import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MachineHistoryServiceImplTest {
    @InjectMocks
    private MachineHistoryServiceImpl machineHistoryService;
    @Mock
    private MachineHistoryRepository machineHistoryRepository;
    @Spy
    private ModelMapper modelMapper;

    @Test
    void findAll() {
        var machineHistoryDtoList = List.of(MachineHistoryDto.builder().build());
        var machineHistoryList = List.of(new MachineHistory());

        when(machineHistoryRepository.findAll()).thenReturn(machineHistoryList);
        assertEquals(machineHistoryDtoList, machineHistoryService.findAll());
        verify(machineHistoryRepository).findAll();
        verify(modelMapper).map(eq(machineHistoryList.get(0)), eq(MachineHistoryDto.class));
    }

    @Test
    void addMachineHistory() {
        var machineHistory = new MachineHistory();

        when(machineHistoryRepository.save(any())).thenReturn(machineHistory);
        assertNotNull(machineHistoryService.addMachineHistory(null, null,  MachineHistoryDto.builder().build()));
        verify(machineHistoryRepository).save(machineHistory);
    }

    @Test
    void findByMachineIdAndMemberId() {

        Member member = new Member();
        Machine machine = new Machine();
        member.setId(1);
        machine.setId(1);
        MachineHistory machineHistory = new MachineHistory();
        machineHistory.setMember(member);
        machineHistory.setMachine(machine);
        var histories = List.of(machineHistory);

        when(machineHistoryRepository.findAll(ArgumentMatchers.<Specification<MachineHistory>>any())).thenReturn(histories);
        var results = machineHistoryService.findBy(1, 1, LocalDate.now());
        assertNotNull(results);
        assertEquals(1, results.size());
        assertNotNull(results.get(0));
        assertEquals(machineHistory.getMember().getId(), results.get(0).getMemberId());
        assertEquals(machineHistory.getMachine().getId(), results.get(0).getMachineId());
        assertEquals(machineHistory.getWorkoutDate(), results.get(0).getWorkoutDate());
        verify(machineHistoryRepository).findAll(ArgumentMatchers.<Specification<MachineHistory>>any());
        verify(modelMapper).map(eq(histories.get(0)), eq(MachineHistoryDto.class));
    }

    @Test
    void findLatestWorkout() {
        var latestMachineHistory = List.of(new MachineHistory());
        var machineHistoryDtoList = List.of(new MachineHistoryDto());

        when(machineHistoryRepository.findTop1ByMemberIdAndMachineIdOrderByWorkoutDateDesc(isNull(), isNull()))
                .thenReturn(latestMachineHistory.get(0));

        assertEquals(machineHistoryDtoList, machineHistoryService.findLatestWorkout(null, null));
        verify(machineHistoryRepository).findTop1ByMemberIdAndMachineIdOrderByWorkoutDateDesc(isNull(), isNull());
        verify(modelMapper).map(eq(latestMachineHistory.get(0)), eq(MachineHistoryDto.class));
    }
}