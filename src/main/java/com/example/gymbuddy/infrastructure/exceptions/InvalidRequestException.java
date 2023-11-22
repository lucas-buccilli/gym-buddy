package com.example.gymbuddy.infrastructure.exceptions;

import com.example.gymbuddy.infrastructure.validation.ValidationError;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

public class InvalidRequestException extends RuntimeException {
    private final List<ValidationError> errors;
    public InvalidRequestException(List<ValidationError> errors) {
        super("Invalid Request");
        this.errors = errors;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public String getMessage() {
        return "[" + String.join(", ", errors.stream().map(ValidationError::message).toList()) + "]";
    }
}
