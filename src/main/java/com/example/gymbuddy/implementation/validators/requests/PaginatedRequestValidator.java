package com.example.gymbuddy.implementation.validators.requests;

import com.example.gymbuddy.infrastructure.models.Filterable;
import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.Sortable;
import com.example.gymbuddy.infrastructure.validation.ValidationError;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PaginatedRequestValidator {
    private PaginatedRequestValidator() {}

    public static <T> List<ValidationError> isValid(PageRequest value, Class<T> clazz) {
        var validationErrors = new ArrayList<ValidationError>();
        var sortFields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.getAnnotationsByType(Sortable.class).length > 0)
                .map(Field::getName)
                .collect(Collectors.toSet());
        var filterFields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.getAnnotationsByType(Filterable.class).length > 0)
                .map(Field::getName)
                .collect(Collectors.toSet());
        validationErrors.addAll(value.getSort().keySet().stream()
                .filter(field -> !sortFields.contains(field))
                .map(invalidField -> new ValidationError("Invalid sort field {" + invalidField + "}, expecting these options: " + String.join(", ", sortFields)))
                .collect(Collectors.toSet()));
        validationErrors.addAll(value.getFilter().keySet().stream()
                .filter(field -> !filterFields.contains(field))
                .map(invalidField -> new ValidationError("Invalid filter field {" + invalidField + "}, expecting these options: " + String.join(", ", filterFields)))
                .collect(Collectors.toSet()));

        return validationErrors;
    }
}
