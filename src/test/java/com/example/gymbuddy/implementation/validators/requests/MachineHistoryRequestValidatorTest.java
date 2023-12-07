package com.example.gymbuddy.implementation.validators.requests;

import com.example.gymbuddy.implementation.dataproviders.MachineDataProvider;
import com.example.gymbuddy.implementation.dataproviders.MemberDataProvider;
import com.example.gymbuddy.infrastructure.models.daos.MachineDao;
import com.example.gymbuddy.infrastructure.models.daos.MemberDao;
import com.example.gymbuddy.infrastructure.validation.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MachineHistoryRequestValidatorTest {
    @InjectMocks
    MachineHistoryRequestValidator machineHistoryRequestValidator;
    @Mock
    MemberDataProvider memberDataProvider;

    @Mock
    MachineDataProvider machineDataProvider;

    @Test
    void shouldReturnValidationErrorsWhenMemberOrMachineDoesntExist() {
        var validationErrors = new ArrayList<>();
        validationErrors.add(new ValidationError("Invalid Member ID"));
        validationErrors.add(new ValidationError("Invalid Machine ID"));
        MachineHistoryRequestValidator.AddMachineHistoryRequest machineHistoryRequest
                = new MachineHistoryRequestValidator.AddMachineHistoryRequest(1,3);

        when(memberDataProvider.findById(any())).thenReturn(Optional.empty());
        when(machineDataProvider.findById(any())).thenReturn(Optional.empty());

        var result = machineHistoryRequestValidator.validate(machineHistoryRequest);
        assertNotNull(result);
        assertEquals(validationErrors, result);
    }

    @Test
    void shouldNotReturnValidationErrors() {
        MachineHistoryRequestValidator.AddMachineHistoryRequest machineHistoryRequest
                = new MachineHistoryRequestValidator.AddMachineHistoryRequest(1,3);

        when(memberDataProvider.findById(any())).thenReturn(Optional.of(new MemberDao()));
        when(machineDataProvider.findById(any())).thenReturn(Optional.of(new MachineDao()));

        var result = machineHistoryRequestValidator.validate(machineHistoryRequest);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(memberDataProvider).findById(1);
        verify(machineDataProvider).findById(3);
    }
}