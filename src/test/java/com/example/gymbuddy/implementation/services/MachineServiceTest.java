package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.dataproviders.IMachineDataProvider;
import com.example.gymbuddy.infrastructure.exceptions.AlreadyExistsException;
import com.example.gymbuddy.infrastructure.models.daos.MachineDao;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MachineServiceTest {

    @Mock
    IMachineDataProvider machineDataProvider;

    @InjectMocks
    MachineService machineService;


    @Test
    void findAll() {
        var machineDaos = List.of(new MachineDao());
        when(machineDataProvider.findAll()).thenReturn(machineDaos);
        //when
        var result = machineService.findAll();
        //then
        assertEquals(machineDaos, result);
        verify(machineDataProvider).findAll();
    }

    @Test
    void addMachine() {
        var machineDto = new MachineDao();

        when(machineDataProvider.addMachine(any())).thenReturn(machineDto);
        var result = machineService.addMachine(machineDto);

        assertEquals(machineDto, result);
        verify(machineDataProvider).addMachine(machineDto);
    }

    @Test
    public void addAlreadyExistingMachineThrowsException() {
        var machineDto = MachineDao.builder().id(1).name("smith").build();
        when(machineDataProvider.findByName(any())).thenReturn(Optional.of(machineDto));
        var result = assertThrows(AlreadyExistsException.class, () -> machineService.addMachine(machineDto));
        assertEquals("Machine already exists with identifier: smith", result.getMessage());
    }
}