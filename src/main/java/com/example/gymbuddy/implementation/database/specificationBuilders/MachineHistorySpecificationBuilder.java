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
        return this.specification;
    }

    public MachineHistorySpecificationBuilder hasMachineId(Integer machineId) {
        if(machineId != null) {
            addSpecification(MachineHistorySpecifications.hasMachineId(machineId));
        }
        return this;
    }

    public MachineHistorySpecificationBuilder hasMemberId(Integer memberId) {
        if(memberId != null) {
            addSpecification(MachineHistorySpecifications.hasMemberId(memberId));
        }
        return this;
    }

    public MachineHistorySpecificationBuilder hasWorkoutDate(LocalDate workoutDate) {
        if(workoutDate != null) {
            System.out.println(workoutDate);
            addSpecification(MachineHistorySpecifications.hasWorkoutDate(workoutDate));
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
