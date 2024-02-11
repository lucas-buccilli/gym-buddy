package com.example.gymbuddy.integration;

import com.example.gymbuddy.implementation.utils.AuthUtils;
import com.example.gymbuddy.infrastructure.daos.IMemberDao;
import com.example.gymbuddy.infrastructure.entities.Member;
import com.example.gymbuddy.infrastructure.models.AuthRoles;
import com.example.gymbuddy.infrastructure.models.dtos.AdminReportDto;
import com.example.gymbuddy.infrastructure.models.dtos.MachineDto;
import com.example.gymbuddy.infrastructure.models.dtos.MachineHistoryDto;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.models.dtos.UserReportDto;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public abstract class IntegrationBase {
    @Container
    public static DbContainer dbContainer = DbContainer.getInstance();
    @Autowired
    private MockMvc mvc;
    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;
    @Autowired
    private TestDataManager testDataManager;
    @Autowired
    private IMemberDao memberDao;

    public final AuthenticatedUser Admin = new AuthenticatedUser(Collections.emptyList(), List.of(AuthRoles.ADMIN), UUID.randomUUID().toString());

    private final Map<String, SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor> AuthUsers = new HashMap<>();


    @AfterEach
    void afterEach() {
        testDataManager.deleteAllData();
    }

    public MemberDto createMember(MemberDto memberDao, AuthenticatedUser authenticatedUser) throws Exception {
        var result = mvc.perform(post("/members").with(getAuthUser(authenticatedUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDao)))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MemberDto.class);
    }

    public MemberDto createMember(AuthenticatedUser authenticatedUser) {
        String firstName = RandomStringUtils.randomAlphabetic(6);
        String lastName = RandomStringUtils.randomAlphabetic(6);
        String auth0Id = "Auth" + RandomStringUtils.randomAlphabetic(6);
        String phoneNumber = "9999999999";
        try {
            return createMember(MemberDto.builder().firstName(firstName).lastName(lastName).phoneNumber(phoneNumber).authId(auth0Id).build(), authenticatedUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MachineDto createMachine(MachineDto machineDao, AuthenticatedUser authenticatedUser) throws Exception {
        var result = mvc.perform(post("/machines").with(getAuthUser(authenticatedUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(machineDao)))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MachineDto.class);
    }

    public MachineHistoryDto createMachineHistory(Integer memberId, Integer machineId, AuthenticatedUser authenticatedUser) {

        MachineHistoryDto machineHistoryDao = MachineHistoryDto.builder()
                .workoutDate(LocalDateTime.now())
                .numberReps(Integer.valueOf(RandomStringUtils.randomNumeric(1)))
                .maxWeight(Integer.valueOf(RandomStringUtils.randomNumeric(1)))
                .numberSets(Integer.valueOf(RandomStringUtils.randomNumeric(1)))
                .build();
        try {
            return createMachineHistory(memberId, machineId, machineHistoryDao, authenticatedUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public MachineHistoryDto createMachineHistory(int memberId, int machineId, MachineHistoryDto machineHistoryDao, AuthenticatedUser authenticatedUser) throws Exception {
        var result = mvc.perform(post("/members/" + memberId + "/machines/" + machineId + "/history")
                        .with(getAuthUser(authenticatedUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(machineHistoryDao)))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MachineHistoryDto.class);
    }

    public UserReportDto getUserReport(Integer memberId, Integer machineId, LocalDateTime startDate, LocalDateTime endDate, AuthenticatedUser authenticatedUser) throws Exception {
        var result = mvc.perform(get("/reports/user")
                        .with(getAuthUser(authenticatedUser))
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .param("memberId", Integer.toString(memberId))
                        .param("machineId", Integer.toString(machineId)))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), UserReportDto.class);
    }

    public AdminReportDto getAdminReport(LocalDateTime startDate, LocalDateTime endDate, AuthenticatedUser authenticatedUser) throws Exception {
        var result = mvc.perform(get("/reports/admin")
                        .with(getAuthUser(authenticatedUser))
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), AdminReportDto.class);
    }

    public void deleteMachine(String name, AuthenticatedUser authenticatedUser) throws Exception {
        mvc.perform(delete("/machines")
                        .with(getAuthUser(authenticatedUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":  \"" + name + "\"}"))
                .andExpect(status().isNoContent());
    }

    public void deleteMember(int id, AuthenticatedUser authenticatedUser) throws Exception {
        mvc.perform(delete("/members/" + id)
                        .with(getAuthUser(authenticatedUser))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    public MemberDto replaceMember(int id, MemberDto memberDao, AuthenticatedUser authenticatedUser) throws Exception {
        var result = mvc.perform(put("/members/" + id)
                        .with(getAuthUser(authenticatedUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberDao)))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MemberDto.class);
    }

    public MemberDto modifyMember(int id, MemberDto memberDto, AuthenticatedUser authenticatedUser) throws Exception {
        var result = mvc.perform(patch("/members/" + id, memberDto)
                        .with(getAuthUser(authenticatedUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberDto)))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MemberDto.class);
    }

    public MachineDto replaceMachine(int id, MachineDto machineDto, AuthenticatedUser authenticatedUser) throws Exception {
        var result = mvc.perform(put("/machines/" + id, machineDto)
                        .with(getAuthUser(authenticatedUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(machineDto)))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), MachineDto.class);
    }

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor getAuthUser(AuthenticatedUser authenticatedUser) {
        if (!AuthUsers.containsKey(authenticatedUser.auth0Id())) {
            addAuthUserToDb(authenticatedUser.auth0Id);
            AuthUsers.put(authenticatedUser.auth0Id(), AuthUtils.generateAuth0User(authenticatedUser.permissions, authenticatedUser.roles, authenticatedUser.auth0Id));
        }

        return AuthUsers.get(authenticatedUser.auth0Id());
    }

    private void addAuthUserToDb(String auth0Id) {
        var member = new MemberDto();
        member.setAuthId(auth0Id);
        member.setFirstName(UUID.randomUUID().toString());
        member.setLastName(UUID.randomUUID().toString());
        member.setPhoneNumber("5555555555");
        memberDao.saveMember(member);
    }

    public record AuthenticatedUser(List<String> permissions, List<AuthRoles> roles, String auth0Id){};
}
