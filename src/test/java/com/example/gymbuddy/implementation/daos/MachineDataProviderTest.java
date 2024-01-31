package com.example.gymbuddy.implementation.daos;

import com.example.gymbuddy.implementation.repositories.MachineRepository;
import com.example.gymbuddy.infrastructure.entities.Machine;
import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MachineDataProviderTest {

    @InjectMocks
    private MachineDao machineDataProvider;
    @Mock
    private MachineRepository machineRepository;
    @Spy
    private ModelMapper modelMapper;

    @Test
    void shouldFindAll() {
        var machines = List.of(new Machine());
        var machineDtos = List.of(MachineDto.builder().build());

        when(machineRepository.findAll()).thenReturn(machines);
        assertEquals(machineDtos, machineDataProvider.findAll());
        verify(machineRepository).findAll();
        verify(modelMapper).map(eq(machines.get(0)), eq(MachineDto.class));
    }

    @Test
    void shouldAddMachine() {
        var machine = new Machine();
        machine.setName("Smith");

        when(machineRepository.save(any())).thenReturn(machine);
        assertNotNull(machineDataProvider.addMachine(MachineDto.builder().name("Smith").build()));
        verify(machineRepository).save(machine);
    }

    @Test
    void shouldFindById() {
        var machine = new Machine();
        machine.setId(1);
        when(machineRepository.findById(anyInt())).thenReturn(Optional.of(machine));
        var machineDto = machineDataProvider.findById(1);
        assertTrue(machineDto.isPresent());
        assertEquals(machine.getId(), machineDto.get().getId());
        verify(machineRepository).findById(1);
    }

    @Test
    void shouldFindByName() {
        var machine = new Machine();
        machine.setName("smith");

        when(machineRepository.findByName(any())).thenReturn(Optional.of(machine));

        var machineDto = machineDataProvider.findByName("smith");
        assertTrue(machineDto.isPresent());
        assertEquals(machine.getName(), machineDto.get().getName());
        verify(machineRepository).findByName("smith");
    }

    @Test
    void shouldDeleteMachine() {

        machineDataProvider.deleteMachine(1);
        verify(machineRepository).deleteById(1);
    }
}
