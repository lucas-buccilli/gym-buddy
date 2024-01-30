package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.daos.IMachineDao;
import com.example.gymbuddy.infrastructure.exceptions.AlreadyExistsException;
import com.example.gymbuddy.infrastructure.exceptions.MachineNotFoundException;
import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MachineServiceTest {

    @Mock
    IMachineDao machineDataProvider;

    @InjectMocks
    MachineService machineService;


    @Test
    void findAll() {
        var machineDaos = List.of(new MachineDto());
        when(machineDataProvider.findAll()).thenReturn(machineDaos);
        //when
        var result = machineService.findAll();
        //then
        assertEquals(machineDaos, result);
        verify(machineDataProvider).findAll();
    }

    @Test
    void addMachine() {
        var machineDto = new MachineDto();

        when(machineDataProvider.addMachine(any())).thenReturn(machineDto);
        var result = machineService.addMachine(machineDto);

        assertEquals(machineDto, result);
        verify(machineDataProvider).addMachine(machineDto);
    }

    @Test
    public void addAlreadyExistingMachineThrowsException() {
        var machineDto = MachineDto.builder().id(1).name("smith").build();
        when(machineDataProvider.findByName(any())).thenReturn(Optional.of(machineDto));
        var result = assertThrows(AlreadyExistsException.class, () -> machineService.addMachine(machineDto));
        assertEquals("Machine already exists with identifier: smith", result.getMessage());
    }

    @Test
    public void shouldDeleteMachineByName() {
        var name = "treadmill";
        var machineDao = MachineDto.builder().name(name).id(1).build();

        when(machineDataProvider.findByName(any())).thenReturn(Optional.of(machineDao));

        machineService.deleteMachineByName(name);
        verify(machineDataProvider).findByName(name);
        verify(machineDataProvider).deleteMachine(machineDao.getId());
    }

    @Test
    public void shouldThrowExceptionWhenDeleteMachineNotExists() {
        var name = "treadmill";

        when(machineDataProvider.findByName(any())).thenReturn(Optional.empty());

        var result = assertThrows(MachineNotFoundException.class, () -> machineService.deleteMachineByName(name));
        assertEquals("Machine Not Found: name = treadmill", result.getMessage());
    }

    @Test
    public void shouldEditMachine() {
        var id = 33;
        MachineDto machineDto = MachineDto.builder().id(id).name("Smith").build();
        MachineDto newNameMachineDto = MachineDto.builder().name("Bench Press").build();


        when(machineDataProvider.findById(anyInt())).thenReturn(Optional.of(machineDto));
        when(machineDataProvider.addMachine(any())).thenReturn(newNameMachineDto);

        var result = machineService.replaceMachine(id, newNameMachineDto);

        verify(machineDataProvider).findById(id);
        verify(machineDataProvider).addMachine(newNameMachineDto);

        assertEquals(id, result.getId());
        assertEquals(newNameMachineDto.getName(), result.getName());
    }

    @Test
    public void shouldThrowErrorWhenReplaceMachineDoesntExist() {
        var result = assertThrows(MachineNotFoundException.class, () -> machineService.replaceMachine(88, new MachineDto()));
        assertEquals("Machine Not Found: id = 88", result.getMessage());
    }
}