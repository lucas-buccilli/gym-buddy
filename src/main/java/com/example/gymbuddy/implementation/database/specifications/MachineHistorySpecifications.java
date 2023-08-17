package com.example.gymbuddy.implementation.database.specifications;

import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

public final class MachineHistorySpecifications {
    private MachineHistorySpecifications() {

    }

    public static Specification<MachineHistory> hasMachineId(int machineId) {
       return (machineHistory, cq, cb) -> cb.equal(machineHistory.get("machine").get("id"), machineId);
    }

    public static Specification<MachineHistory> hasMemberId(int memberId) {
        return (machineHistory, cq, cb) -> cb.equal(machineHistory.get("member").get("id"), memberId);
    }
}
