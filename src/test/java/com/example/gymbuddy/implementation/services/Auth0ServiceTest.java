package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.implementation.values.Auth0Values;
import com.example.gymbuddy.infrastructure.exceptions.AuthCreationException;
import com.example.gymbuddy.infrastructure.models.AuthRoles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.oauth2.sdk.TokenRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Auth0ServiceTest {

    @Mock
    RestService restService;
    @Mock
    Auth0Values auth0Values;

    @InjectMocks
    Auth0Service auth0Service;

    @BeforeEach
    void beforeEach() {
        when(auth0Values.getAudiance()).thenReturn("audience");
        when(auth0Values.getClientId()).thenReturn("clientId");
        when(auth0Values.getDomain()).thenReturn("domain");
        when(auth0Values.getClientSecret()).thenReturn("clientSecret");
    }

    @Test
    void createUser() throws JsonProcessingException, AuthCreationException {
        var accessToken = "accessToken";
        var userId = "12";
        String email = "email";
        String password = "password";
        ArgumentCaptor<Auth0Service.CreateUserRequest> bodyCaptor = ArgumentCaptor.forClass(Auth0Service.CreateUserRequest.class);
        ArgumentCaptor<MultiValueMap<String, String>> headerCaptor = ArgumentCaptor.forClass(MultiValueMap.class);
        ArgumentCaptor<Auth0Service.TokenRequest> tokenCaptor = ArgumentCaptor.forClass(Auth0Service.TokenRequest.class);

        when(restService.post(argThat((String url) -> url != null && url.contains("/oauth/token")), any(), any(), any())).thenReturn(new Auth0Service.TokenResponse(accessToken));
        when(restService.post(argThat((String url) -> url != null && url.contains("/api/v2/users")), any(), any(), any())).thenReturn(new Auth0Service.CreateUserResponse(userId));

        var response = auth0Service.createUser(email, password);
        assertEquals(userId, response);
        verify(restService).post(argThat((String url) -> url.contains("/oauth/token")), tokenCaptor.capture(), ArgumentMatchers.<MultiValueMap<String, String>>any(), ArgumentMatchers.<Class<Auth0Service.TokenResponse>>any());
        assertNotNull(tokenCaptor.getValue());
        assertEquals(new Auth0Service.TokenRequest(auth0Values.getClientId(), auth0Values.getClientSecret(), auth0Values.getAudiance(), "client_credentials"), tokenCaptor.getValue());

        verify(restService).post(argThat((String url) -> url.contains("/api/v2/users")), bodyCaptor.capture(), headerCaptor.capture(), ArgumentMatchers.<Class<Auth0Service.CreateUserResponse>>any());
        assertNotNull(bodyCaptor.getValue());
        assertEquals(new Auth0Service.CreateUserRequest("Username-Password-Authentication", email, password), bodyCaptor.getValue());
        assertNotNull(headerCaptor.getValue());
        assertNotNull(headerCaptor.getValue().get("Authorization"));
        assertEquals("Bearer " + accessToken, headerCaptor.getValue().get("Authorization").get(0));
    }

    @Test
    void addRole() throws JsonProcessingException, AuthCreationException {
        var accessToken = "accessToken";
        var userId = "12";
        ArgumentCaptor<Auth0Service.AddRolesRequest> bodyCaptor = ArgumentCaptor.forClass(Auth0Service.AddRolesRequest.class);
        ArgumentCaptor<MultiValueMap<String, String>> headerCaptor = ArgumentCaptor.forClass(MultiValueMap.class);

        when(restService.post(argThat((String url) -> url != null && url.contains("/oauth/token")), any(), any(), any())).thenReturn(new Auth0Service.TokenResponse(accessToken));

        auth0Service.addRole(userId, List.of(AuthRoles.MEMBER));

        verify(restService).post(argThat((String url) -> url.contains("/api/v2/users/" + userId + "/roles")), bodyCaptor.capture(), headerCaptor.capture());
        assertNotNull(bodyCaptor.getValue());
        assertEquals(new Auth0Service.AddRolesRequest(Set.of("rol_7h0vwp3djjweCNmw")), bodyCaptor.getValue());
        assertNotNull(headerCaptor.getValue());
        assertNotNull(headerCaptor.getValue().get("Authorization"));
        assertEquals("Bearer " + accessToken, headerCaptor.getValue().get("Authorization").get(0));
    }

    @Test
    void addRoleShouldThrowException() throws JsonProcessingException {
        when(restService.post(argThat((String url) -> url != null && url.contains("/oauth/token")), any(), any(), any())).thenThrow(new JsonProcessingException(""){});
        assertThrows(AuthCreationException.class, () -> auth0Service.addRole("user", List.of()));
    }

    @Test
    void createUserShouldThrowException() throws JsonProcessingException{
        when(restService.post(argThat((String url) -> url != null && url.contains("/oauth/token")), any(), any(), any())).thenThrow(new JsonProcessingException(""){});
        assertThrows(AuthCreationException.class, () -> auth0Service.createUser("user", "List.of()"));
    }
}