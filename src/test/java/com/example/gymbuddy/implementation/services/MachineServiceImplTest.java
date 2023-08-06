package com.example.gymbuddy.implementation.services;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MachineServiceImplTest {
    @InjectMocks
    private MachineServiceImpl machineService;
    @Mock
    private MachineRepository machineRepository;
    @Spy
    private ModelMapper modelMapper;

    @Test
    void shouldFindAll() {
        var machines = List.of(new Machine());
        var machineDtos = List.of(MachineDto.builder().build());

        when(machineRepository.findAll()).thenReturn(machines);
        assertEquals(machineDtos, machineService.findAll());
        verify(machineRepository).findAll();
        verify(modelMapper).map(eq(machines.get(0)), eq(MachineDto.class));
    }

    @Test
    void shouldAddMachine() {
        var machine = new Machine();
        machine.setName("Smith");

        when(machineRepository.save(any())).thenReturn(machine);
        assertNotNull(machineService.addMachine(MachineDto.builder().name("Smith").build()));
        verify(machineRepository).save(machine);
    }
}