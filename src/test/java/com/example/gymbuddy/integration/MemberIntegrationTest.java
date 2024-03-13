package com.example.gymbuddy.integration;

import com.example.gymbuddy.infrastructure.models.AuthRoles;
import com.example.gymbuddy.infrastructure.models.PageRequest;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.models.enums.SortOptions;
import com.example.gymbuddy.infrastructure.models.requests.MemberRequests;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MemberIntegrationTest extends IntegrationBase {

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
        var member1 = MemberRequests.AddRequest.builder()
                .firstName("Bob")
                .lastName("23")
                .phoneNumber("3674647392")
                .email("emai@email.com")
                .password("password1234!")
                .roles(List.of(AuthRoles.MEMBER))
                .build();

        var member2 = MemberRequests.AddRequest.builder()
                .firstName("Bob")
                .lastName("23")
                .phoneNumber("3674747392")
                .email("emai@email.com")
                .password("password1234!")
                .roles(List.of(AuthRoles.MEMBER))
                .build();

        var member3 = MemberRequests.AddRequest.builder()
                .firstName("Bob3")
                .lastName("23")
                .phoneNumber("3674747395")
                .email("emai@email.com")
                .password("password1234!")
                .roles(List.of(AuthRoles.MEMBER))
                .build();

        AuthServiceStubber.builder(authService)
                        .createUser()
                                .when(member1.getEmail(), member1.getPassword())
                                .thenReturn(RandomStringUtils.randomAlphabetic(5))
                        .createUser()
                            .when(member2.getEmail(), member2.getPassword())
                            .thenReturn(RandomStringUtils.randomAlphabetic(5))
                        .createUser()
                            .when(member3.getEmail(), member3.getPassword())
                            .thenReturn(RandomStringUtils.randomAlphabetic(5));

        Member.create(member1, Admin);
        Member.create(member2, Admin);
        Member.create(member3, Admin);

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

