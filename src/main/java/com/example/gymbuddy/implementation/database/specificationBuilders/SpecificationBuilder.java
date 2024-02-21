package com.example.gymbuddy.implementation.database.specificationBuilders;


import com.example.gymbuddy.implementation.database.specifications.Specifications;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class SpecificationBuilder<T> {
    private Specification<T> specification;

    public Specification<T> build() {
        if (this.specification == null) {
            throw new NullPointerException("Specification is null");
        }
        return this.specification;
    }

    public SpecificationBuilder<T> hasField(@NonNull String field, @NonNull String value) {
        addSpecification(Specifications.hasField(field, value));
        return this;
    }

    public SpecificationBuilder<T> hasNestedField(@NonNull String object,@NonNull String field, @NonNull Integer value) {
        addSpecification(Specifications.hasNestedField(object, field, value));
        return this;
    }


    public SpecificationBuilder<T> hasField(@NonNull String field, @NonNull LocalDateTime value) {
        addSpecification(Specifications.hasField(field, value));
        return this;
    }

    public SpecificationBuilder<T> hasFieldLessThanOrEqualTo(String field, LocalDateTime value) {
        addSpecification(Specifications.hasFieldLessThanOrEqualTo(field, value));
        return this;
    }

    public SpecificationBuilder<T> hasFieldGreaterThanOrEqualTo(String field, LocalDateTime value) {
        addSpecification(Specifications.hasFieldGreaterThanOrEqualTo(field, value));
        return this;
    }

    private void addSpecification(Specification<T> specification) {
        if(this.specification == null) {
            this.specification = Specification.where(specification);
        } else {
            this.specification = this.specification.and(specification);
        }
    }
}
