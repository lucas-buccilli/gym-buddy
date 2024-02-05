package com.example.gymbuddy.infrastructure.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthRolesTest {

    @Test
    public void shouldConvertStringToEnum() {
        assertEquals(AuthRoles.ADMIN, AuthRoles.toRole("Admin"));
        assertThrows(IllegalArgumentException.class, () -> AuthRoles.toRole("dosaghiu"));
    }

}