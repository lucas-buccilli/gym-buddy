package com.example.gymbuddy.implementation.converters;

import com.example.gymbuddy.infrastructure.entities.enums.MembershipOperation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MembershipOperationConverterTest {

    private final static MembershipOperationConverter converter = new MembershipOperationConverter();

    @Test
    void shouldConvertToDatabaseColumn() {
        assertEquals("A", converter.convertToDatabaseColumn(MembershipOperation.ACTIVATED));
    }

    @Test
    void shouldConvertToEntityAttribute() {
        assertEquals(MembershipOperation.ACTIVATED, converter.convertToEntityAttribute("A"));
    }
}