package com.example.gymbuddy.implementation.database.specificationBuilders;


import com.example.gymbuddy.implementation.database.specifications.MachineHistorySpecifications;
import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class MachineHistorySpecificationBuilder {
    private Specification<MachineHistory> specification;

    private MachineHistorySpecificationBuilder() {}

    public static MachineHistorySpecificationBuilder builder() {
        return new MachineHistorySpecificationBuilder();
    }

    public Specification<MachineHistory> build() {
        if (this.specification == null) {
            throw new NullPointerException("Specification is null");
        }
        return this.specification;
    }

    public MachineHistorySpecificationBuilder hasMachineId(Integer machineId) {
        if(machineId != null) {
            addSpecification(MachineHistorySpecifications.hasMachineId(machineId));
        } else {
            throw new NullPointerException("Machine id is null");
        }
        return this;
    }

    public MachineHistorySpecificationBuilder hasMemberId(Integer memberId) {
        if(memberId != null) {
            addSpecification(MachineHistorySpecifications.hasMemberId(memberId));
        } else {
            throw new NullPointerException("Member id is null");
        }
        return this;
    }

    public MachineHistorySpecificationBuilder hasWorkoutDate(LocalDate workoutDate) {
        if(workoutDate != null) {
            System.out.println(workoutDate);
            addSpecification(MachineHistorySpecifications.hasWorkoutDate(workoutDate));
        } else {
            throw new NullPointerException("Workout Date is null");
        }
        return this;
    }

    private void addSpecification(Specification<MachineHistory> specification) {
        if(this.specification == null) {
            this.specification = Specification.where(specification);
        } else {
            this.specification = this.specification.and(specification);
        }
    }
}
