package com.example.gymbuddy.infrastructure.models;

import java.util.stream.Stream;

public enum AuthRoles {
    ADMIN("Admin"),
    MEMBER("Member");
    private final String value;

    private AuthRoles(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static AuthRoles toRole(String value) {
        return Stream.of(AuthRoles.values())
                .filter(enumValue -> enumValue.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
