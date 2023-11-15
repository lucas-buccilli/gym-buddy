package com.example.gymbuddy.implementation.converters;

import jakarta.persistence.AttributeConverter;

import java.time.LocalDateTime;

public class DateTimeConverter implements AttributeConverter<LocalDateTime, String> {

    @Override
    public String convertToDatabaseColumn(LocalDateTime attribute) {
        return attribute.toString();
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String dbData) {
        return LocalDateTime.parse(dbData);
    }
}
