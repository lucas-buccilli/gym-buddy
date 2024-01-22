package com.example.gymbuddy.integration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MemberIntegrationTest extends IntegrationBase{

    @Test
    public void shouldDeleteExistingMember() throws Exception {
        var member = createMember();
        deleteMember(member.getId());
    }

    @Test
    public void shouldEditExistingMember() throws Exception {
        var member = createMember();
        var newMemberInfo = createMember();
        var editedMember = editMember(member.getId(), newMemberInfo);
        assertEquals(member.getId(), editedMember.getId());
        assertEquals(newMemberInfo.getFirstName(), editedMember.getFirstName());
        assertEquals(newMemberInfo.getLastName(), editedMember.getLastName());
        assertEquals(newMemberInfo.getPhoneNumber(), editedMember.getPhoneNumber());
        assertNotEquals(member.getFirstName(), editedMember.getPhoneNumber());
    }

}

