package com.example.gymbuddy.implementation.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAuthDetailsServiceTest {
    @Test
    public void shouldParseUserAuthDetails() {
        var authentication = mock(Authentication.class);
        var jwt = mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        doReturn(List.of(new SimpleGrantedAuthority("ROLE_Admin"))).when(authentication).getAuthorities();
        when(jwt.getClaims()).thenReturn(Map.of("sub", "sub-value"));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var result = UserAuthDetailsService.getUserAuthDetails();
        assertTrue(result.admin());
        assertEquals("sub-value", result.authId());
    }

    @Test
    public void shouldThrowWhenFailingToParseUserAuthDetails() {
        var authentication = mock(Authentication.class);
        var jwt = mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThrows(AuthenticationCredentialsNotFoundException.class, UserAuthDetailsService::getUserAuthDetails);
    }

    @Test
    public void shouldThrowWhenNotJwt() {
        var authentication = mock(Authentication.class);
        var jwt = mock(Object.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThrows(AuthenticationCredentialsNotFoundException.class, UserAuthDetailsService::getUserAuthDetails);
    }
}