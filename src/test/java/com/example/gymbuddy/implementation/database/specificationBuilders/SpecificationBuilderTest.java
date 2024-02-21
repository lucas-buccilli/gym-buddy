package com.example.gymbuddy.implementation.database.specificationBuilders;

import com.example.gymbuddy.infrastructure.exceptions.AlreadyExistsException;
import com.example.gymbuddy.integration.IntegrationBase;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SpecificationBuilderTest<T> {

    @Test
    void shouldThrowNulPointerExceptionWhenSpecIsNull() {
        var specBuilder = new SpecificationBuilder<T>();
        var result = assertThrows(NullPointerException.class, specBuilder::build);
        assertEquals("Specification is null", result.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenObjectIsNull() {
        var specBuilder = new SpecificationBuilder<T>();
        var result = assertThrows(NullPointerException.class, () -> specBuilder.hasNestedField(null,"field", 1));
        assertEquals("object is marked non-null but is null", result.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenFieldIsNull() {
        var specBuilder = new SpecificationBuilder<T>();
        var result = assertThrows(NullPointerException.class, () -> specBuilder.hasNestedField("object",null, 1));
        assertEquals("field is marked non-null but is null", result.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        var specBuilder = new SpecificationBuilder<T>();
        var result = assertThrows(NullPointerException.class, () -> specBuilder.hasNestedField("object","field", null));
        assertEquals("value is marked non-null but is null", result.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenFirstArgIsNull() {
        var specBuilder = new SpecificationBuilder<T>();
        var result = assertThrows(NullPointerException.class, () -> specBuilder.hasField(null, new String()));
        assertEquals("field is marked non-null but is null", result.getMessage());
    }
    @Test
    void testHasFieldFieldIsNull() {
        var specBuilder = new SpecificationBuilder<T>();
        var result = assertThrows(NullPointerException.class, () -> specBuilder.hasField(null, LocalDateTime.now()));
        assertEquals("field is marked non-null but is null", result.getMessage());
    }

    @Test
    void testHasFieldStringIsNull() {
        var specBuilder = new SpecificationBuilder<T>();
        var result = assertThrows(NullPointerException.class, () -> specBuilder.hasField("field", (String) null));
        assertEquals("value is marked non-null but is null", result.getMessage());
    }

    @Test
    void testHasFieldDateIsNull() {
        var specBuilder = new SpecificationBuilder<T>();
        var result = assertThrows(NullPointerException.class, () -> specBuilder.hasField("field", (LocalDateTime) null));
        assertEquals("value is marked non-null but is null", result.getMessage());
    }
}