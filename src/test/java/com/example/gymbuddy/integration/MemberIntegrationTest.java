package com.example.gymbuddy.integration;

import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberIntegrationTest extends IntegrationBase{

    @Test
    public void shouldDeleteExistingMember() throws Exception {
        var member = createMember();
        deleteMember(member.getId());
    }

    @Test
    public void shouldReplaceExistingMember() throws Exception {
        var member = createMember();
        member.setFirstName("New Name");
        var editedMember = replaceMember(member.getId(),member);
        assertEquals(member.getId(), editedMember.getId());
        assertEquals(member.getFirstName(), editedMember.getFirstName());
    }

    @Test
    public void shouldModifyExistingMember() throws Exception {
        var member = createMember();
        var newMemberInfo = MemberDto.builder().firstName("New Name 2").build();
        var editedMember = modifyMember(member.getId(), newMemberInfo);
        assertEquals(member.getId(), editedMember.getId());
        assertEquals(newMemberInfo.getFirstName(), editedMember.getFirstName());
        assertEquals(member.getLastName(), editedMember.getLastName());
        assertEquals(member.getPhoneNumber(), editedMember.getPhoneNumber());
    }
}

