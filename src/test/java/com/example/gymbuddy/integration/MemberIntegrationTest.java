package com.example.gymbuddy.integration;

import org.junit.jupiter.api.Test;

public class MemberIntegrationTest extends IntegrationBase{

    @Test
    public void shouldDeleteExistingMember() throws Exception {
        var member = createMember();
        deleteMember(member.getId());
    }
}
