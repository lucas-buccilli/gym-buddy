package com.example.gymbuddy.implementation.validators.requests;

import com.example.gymbuddy.implementation.daos.MachineDao;
import com.example.gymbuddy.implementation.daos.MemberDao;
import com.example.gymbuddy.infrastructure.validation.ValidationError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MachineHistoryRequestValidator {
    private final MemberDao memberDataProvider;
    private final MachineDao machineDataProvider;


    public List<ValidationError> validate(AddMachineHistoryRequest addMachineHistoryRequest) {
        var validationErrors = new ArrayList<ValidationError>();
        if(memberDataProvider.findById(addMachineHistoryRequest.memberId).isEmpty()) {
            validationErrors.add(new ValidationError("Invalid Member ID"));
        }
        if(machineDataProvider.findById(addMachineHistoryRequest.machineId).isEmpty()) {
            validationErrors.add(new ValidationError("Invalid Machine ID"));
        }
        return validationErrors;
    }
    public record AddMachineHistoryRequest(Integer memberId, Integer machineId){}
}
