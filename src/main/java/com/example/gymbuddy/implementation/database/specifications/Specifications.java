package com.example.gymbuddy.implementation.database.specifications;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class Specifications {
    public static <T> Specification<T> hasField(String field, String value) {
        return (entity, cq, cb) -> cb.equal(entity.get(field), value);
    }

    public static <T> Specification<T> hasNestedField(String object, String field, Integer value) {
        return (entity, cq, cb) -> cb.equal(entity.get(object).get(field), value);
    }

    public static <T> Specification<T> hasField(String field, LocalDateTime value) {
        return (entity, cq, cb) -> cb.equal(entity.get(field), value);
    }

    public static <T> Specification<T> hasFieldLessThanOrEqualTo(String field, LocalDateTime value) {
        return (entity, cq, cb) -> cb.lessThanOrEqualTo(entity.get(field), value);
    }

    public static <T> Specification<T> hasFieldGreaterThanOrEqualTo(String field, LocalDateTime value) {
        return (entity, cq, cb) -> cb.greaterThanOrEqualTo(entity.get(field), value);
    }
}
