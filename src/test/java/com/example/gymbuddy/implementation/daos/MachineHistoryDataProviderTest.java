package com.example.gymbuddy.implementation.daos;

import com.example.gymbuddy.implementation.repositories.MachineHistoryRepository;
import com.example.gymbuddy.infrastructure.entities.Machine;
import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import com.example.gymbuddy.infrastructure.entities.Member;
import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.integration.EntityGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MachineHistoryDataProviderTest {

    @InjectMocks
    private MachineHistoryDao machineHistoryDataProvider;
    @Mock
    private MachineHistoryRepository machineHistoryRepository;
    @Spy
    private ModelMapper modelMapper;

    @Test
    void addMachineHistory() {
        var machineHistory = new MachineHistory();

        when(machineHistoryRepository.save(any())).thenReturn(machineHistory);
        assertNotNull(machineHistoryDataProvider.addMachineHistory(null, null,  MachineHistoryDto.builder().build()));
        verify(machineHistoryRepository).save(machineHistory);
    }

    @Test
    void findBy() {
        Member member = new Member();
        Machine machine = new Machine();
        member.setId(1);
        machine.setId(1);
        MachineHistory machineHistory = new MachineHistory();
        machineHistory.setMember(member);
        machineHistory.setMachine(machine);
        var histories = List.of(machineHistory);

        when(machineHistoryRepository.findAll(ArgumentMatchers.<Specification<MachineHistory>>any(), ArgumentMatchers.<Pageable>any())).thenReturn(new PageImpl<>(histories));
        var results = machineHistoryDataProvider.findBy(1, 1, PageRequest.build(1, 1, Map.of(), Map.of()));
        assertNotNull(results);
        assertEquals(1, results.size());
        assertNotNull(results.get(0));
        assertEquals(machineHistory.getMember().getId(), results.get(0).getMemberId());
        assertEquals(machineHistory.getMachine().getId(), results.get(0).getMachineId());
        assertEquals(machineHistory.getWorkoutDate(), results.get(0).getWorkoutDate());
        verify(machineHistoryRepository).findAll(ArgumentMatchers.<Specification<MachineHistory>>any(), ArgumentMatchers.<Pageable>any());
        verify(modelMapper).map(eq(histories.get(0)), eq(MachineHistoryDto.class));
    }

    @Test
    void findLatestWorkout() {
        var latestMachineHistory = Optional.of(new MachineHistory());
        var machineHistoryDtoOptional = Optional.of(new MachineHistoryDto());

        when(machineHistoryRepository.findTop1ByMemberIdAndMachineIdOrderByWorkoutDateDesc(isNull(), isNull()))
                .thenReturn(latestMachineHistory);

        assertEquals(machineHistoryDtoOptional, machineHistoryDataProvider.findLatestWorkout(null, null));
        verify(machineHistoryRepository).findTop1ByMemberIdAndMachineIdOrderByWorkoutDateDesc(isNull(), isNull());
        verify(modelMapper).map(eq(latestMachineHistory.get()), eq(MachineHistoryDto.class));
    }

    @Test
    void shouldGetNumberOfVisitorsWithinTimeframe() {
        int count = 1;
        LocalDateTime startDate = LocalDateTime.MIN;
        LocalDateTime endDate = LocalDateTime.MAX;

        when(machineHistoryRepository.countFindDistinctMemberIdByWorkoutDateBetween(any(), any())).thenReturn(1);

        assertEquals(count, machineHistoryDataProvider.getNumberOfVisitorsWithinTimeframe(startDate, endDate));
        verify(machineHistoryRepository).countFindDistinctMemberIdByWorkoutDateBetween(startDate, endDate);
    }

    @Test
    void shouldGetMachineUsage() {
        LocalDateTime startDate = LocalDateTime.MIN;
        LocalDateTime endDate = LocalDateTime.MAX;
        Machine machine = new Machine();
        machine.setName("Machine");
        MachineHistory machineHistory = new MachineHistory();
        machineHistory.setMachine(machine);
        var machineHistoryList = List.of(machineHistory);

        Map<String, Long> machineUsage = new HashMap<String, Long>();
        machineUsage.put("Machine", 1L);

        when(machineHistoryRepository.findAll(ArgumentMatchers.<Specification<MachineHistory>> any())).thenReturn(machineHistoryList);

        var results = machineHistoryDataProvider.getMachineUsage(startDate, endDate);
        assertEquals(machineUsage, results);
    }

    @Test
    void getMachineProgress() {
        var member = EntityGenerator.getMember();
        var machine = EntityGenerator.getMachine();
        List<MachineHistory> machineUsageList = List.of(
                EntityGenerator.getMachineHistory(machine, member),
                EntityGenerator.getMachineHistory(machine, member)
        );
        when(machineHistoryRepository.findAll(ArgumentMatchers.<Specification<MachineHistory>> any(), any(Sort.class))).thenReturn(machineUsageList);
        var result = machineHistoryDataProvider.getMachineProgress(LocalDateTime.MIN, LocalDateTime.MAX, member.getId(), machine.getId());
        assertTrue(result.containsKey(machine.getName()));
        assertEquals(1, result.size());
        assertEquals(2, result.get(machine.getName()).size());
    }
    
    @Test
    void shouldGetNumberOfWorkoutDays() {
        var machine = EntityGenerator.getMachine();
        var member = EntityGenerator.getMember();
        var machineHistoryOne = EntityGenerator.getMachineHistory(machine, member);
        var machineHistoryTwo = EntityGenerator.getMachineHistory(machine, member);
        var machineHistoryThree = EntityGenerator.getMachineHistory(machine, member);

        var localDateTime = LocalDateTime.now();

        machineHistoryOne.setWorkoutDate(localDateTime.plusHours(1));
        machineHistoryTwo.setWorkoutDate(localDateTime.plusHours(2));
        machineHistoryTwo.setWorkoutDate(localDateTime.plusDays(1));

        when(machineHistoryRepository.findAll(ArgumentMatchers.<Specification<MachineHistory>>any())).thenReturn(List.of(
                machineHistoryOne,
                machineHistoryTwo,
                machineHistoryThree
        ));

        var result = machineHistoryDataProvider.getNumberOfWorkoutDays(localDateTime.minusDays(1), localDateTime.plusDays(2), member.getId());

        assertEquals(2, result);
    }
}
