package com.example.gymbuddy.implementation.converters;

import com.example.gymbuddy.infrastructure.entities.enums.MembershipOperation;
import jakarta.persistence.AttributeConverter;

import java.util.stream.Stream;

public class MembershipOperationConverter implements AttributeConverter<MembershipOperation, String> {

    @Override
    public String convertToDatabaseColumn(MembershipOperation membershipOperation) {
        return membershipOperation.getCode();
    }

    @Override
    public MembershipOperation convertToEntityAttribute(String dbData) {
        return Stream.of(MembershipOperation.values())
                .filter(enumValue -> enumValue.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
