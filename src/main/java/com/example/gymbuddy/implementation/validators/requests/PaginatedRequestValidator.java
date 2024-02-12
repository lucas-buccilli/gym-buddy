package com.example.gymbuddy.implementation.validators.requests;

import com.example.gymbuddy.infrastructure.models.PageRequest;
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
        var fields = Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).collect(Collectors.toSet());
        validationErrors.addAll(value.getSort().keySet().stream()
                .filter(field -> !fields.contains(field))
                .map(invalidField -> new ValidationError("Invalid sort field {" + invalidField + "}"))
                .collect(Collectors.toSet()));
        validationErrors.addAll(value.getFilter().keySet().stream()
                .filter(field -> !fields.contains(field))
                .map(invalidField -> new ValidationError("Invalid filter field {" + invalidField + "}"))
                .collect(Collectors.toSet()));

        return validationErrors;
    }
}
