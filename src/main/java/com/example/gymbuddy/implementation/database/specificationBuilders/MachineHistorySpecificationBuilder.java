package com.example.gymbuddy.implementation.database.specificationBuilders;


import com.example.gymbuddy.implementation.database.specifications.MachineHistorySpecifications;
import com.example.gymbuddy.infrastructure.entities.MachineHistory;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

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

    public MachineHistorySpecificationBuilder hasMachineId(@NonNull Integer machineId) {
        addSpecification(MachineHistorySpecifications.hasMachineId(machineId));
        return this;
    }

    public MachineHistorySpecificationBuilder hasMemberId(@NonNull Integer memberId) {
        addSpecification(MachineHistorySpecifications.hasMemberId(memberId));
        return this;
    }

    public MachineHistorySpecificationBuilder hasWorkoutDate(@NonNull LocalDateTime workoutDate) {
        addSpecification(MachineHistorySpecifications.hasWorkoutDate(workoutDate));
        return this;
    }

    public MachineHistorySpecificationBuilder hasWorkoutDateGreaterOrEqualTo(@NonNull LocalDateTime workoutDate) {
        addSpecification(MachineHistorySpecifications.hasWorkoutDateGreaterThanOrEqualTo(workoutDate));
        return this;
    }

    public MachineHistorySpecificationBuilder hasWorkoutDateLessOrEqualTo(@NonNull LocalDateTime workoutDate) {
        addSpecification(MachineHistorySpecifications.hasWorkoutDateLessThanOrEqualTo(workoutDate));
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
