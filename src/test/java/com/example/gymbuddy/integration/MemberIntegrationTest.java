package com.example.gymbuddy.integration;

import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.models.enums.SortOptions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MemberIntegrationTest extends IntegrationBase{

    @Test
    public void shouldDeleteExistingMember() throws Exception {
        var member = Member.create(Admin);
        Member.delete(member.getId(), Admin);
    }

    @Test
    public void shouldReplaceExistingMember() throws Exception {
        var member = Member.create(Admin);
        member.setFirstName("New Name");
        var editedMember = Member.replace(member.getId(),member, Admin);
        assertEquals(member.getId(), editedMember.getId());
        assertEquals(member.getFirstName(), editedMember.getFirstName());
    }

    @Test
    public void shouldModifyExistingMember() throws Exception {
        var member = Member.create(Admin);
        var newMemberInfo = MemberDto.builder().firstName("New Name 2").build();
        var editedMember = Member.modify(member.getId(), newMemberInfo, Admin);
        assertEquals(member.getId(), editedMember.getId());
        assertEquals(newMemberInfo.getFirstName(), editedMember.getFirstName());
        assertEquals(member.getLastName(), editedMember.getLastName());
        assertEquals(member.getPhoneNumber(), editedMember.getPhoneNumber());
    }

    @Test
    public void shouldSearchMembers() throws Exception {
        Member.create(MemberDto.builder()
                .firstName("Bob")
                .lastName("23")
                .phoneNumber("3674647392")
                .authId(UUID.randomUUID().toString())
                .build(),
                Admin);

        Member.create(MemberDto.builder()
                        .firstName("Bob")
                        .lastName("23")
                        .phoneNumber("3674747392")
                        .authId(UUID.randomUUID().toString())
                        .build(),
                Admin);

        Member.create(MemberDto.builder()
                        .firstName("Bob3")
                        .lastName("23")
                        .phoneNumber("3674747395")
                        .authId(UUID.randomUUID().toString())
                        .build(),
                Admin);

        var searchResults = Member.search(PageRequest.build(
                0,
                1,
                Map.of("firstName", SortOptions.ASC),
                Map.of("lastName", "23")
        ), Admin);

        assertEquals(1, searchResults.size());
        assertEquals("Bob", searchResults.get(0).getFirstName() );

        searchResults = Member.search(PageRequest.build(
                1,
                1,
                Map.of("firstName", SortOptions.ASC),
                Map.of("lastName", "23")
        ), Admin);

        assertEquals(1, searchResults.size());
        assertEquals("Bob", searchResults.get(0).getFirstName() );

        searchResults = Member.search(PageRequest.build(
                2,
                1,
                Map.of("firstName", SortOptions.ASC),
                Map.of("lastName", "23")
        ), Admin);

        assertEquals(1, searchResults.size());
        assertEquals("Bob3", searchResults.get(0).getFirstName() );

        searchResults = Member.search(PageRequest.build(
                0,
                100,
                Map.of("firstName", SortOptions.DESC),
                Map.of("lastName", "23")
        ), Admin);

        assertEquals(3, searchResults.size());
        assertEquals("Bob3", searchResults.get(0).getFirstName());
        assertEquals("Bob", searchResults.get(1).getFirstName());
        assertEquals("Bob", searchResults.get(2).getFirstName());

        searchResults = Member.search(PageRequest.build(
                0,
                100,
                Map.of("firstName", SortOptions.DESC),
                Map.of("lastName", "23", "firstName", "Bob3")
        ), Admin);

        assertEquals(1, searchResults.size());
        assertEquals("Bob3", searchResults.get(0).getFirstName());

        searchResults = Member.search(PageRequest.build(
                0,
                100,
                Collections.emptyMap(),
                Collections.emptyMap()
        ), Admin);

        assertNotEquals(0, searchResults.size());
    }
}

