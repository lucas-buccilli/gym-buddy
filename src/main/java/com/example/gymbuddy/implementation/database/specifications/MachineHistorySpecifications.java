package com.example.gymbuddy.implementation.database.specifications;

import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public final class MachineHistorySpecifications {
    private MachineHistorySpecifications() {
    }

    public static Specification<MachineHistory> hasMachineId(int machineId) {
       return (machineHistory, cq, cb) -> cb.equal(machineHistory.get("machine").get("id"), machineId);
    }

    public static Specification<MachineHistory> hasMemberId(int memberId) {
        return (machineHistory, cq, cb) -> cb.equal(machineHistory.get("member").get("id"), memberId);
    }

    public static Specification<MachineHistory> hasWorkoutDate(LocalDate workoutDate) {
        return (machineHistory, cq, cb) -> cb.equal(machineHistory.get("workoutDate"), workoutDate);
    }
}
