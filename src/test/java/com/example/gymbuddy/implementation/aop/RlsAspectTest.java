package com.example.gymbuddy.implementation.aop;

import com.example.gymbuddy.implementation.daos.MemberDao;
import com.example.gymbuddy.infrastructure.daos.IMemberDao;
import com.example.gymbuddy.infrastructure.exceptions.MemberNotFoundException;
import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.DefaultAopProxyFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RlsAspectTest {
    private IMemberDao memberDao;
    private TestClass testClassProxy;

    @BeforeEach
    public void beforeEach() {
        memberDao = mock(MemberDao.class);
        RlsAspect aspect = new RlsAspect(memberDao);

        AspectJProxyFactory aspectJProxyFactory = new AspectJProxyFactory(new TestClass());
        aspectJProxyFactory.addAspect(aspect);

        DefaultAopProxyFactory proxyFactory = new DefaultAopProxyFactory();
        AopProxy aopProxy = proxyFactory.createAopProxy(aspectJProxyFactory);

        testClassProxy = (TestClass) aopProxy.getProxy();
    }

    @Test
    public void shouldEnforceRlsForMember() {
        var authentication = mock(Authentication.class);
        var jwt = mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        doReturn(List.of(new SimpleGrantedAuthority("ROLE_Member"))).when(authentication).getAuthorities();
        when(jwt.getClaims()).thenReturn(Map.of("sub", "authId"));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(memberDao.findByAuthId(any())).thenReturn(Optional.of(MemberDto.builder().id(1).build()));

        testClassProxy.methodOne(1);

        verify(memberDao).findByAuthId("authId");
    }

    @Test
    public void shouldEnforceRlsForAdmin() {
        var authentication = mock(Authentication.class);
        var jwt = mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        doReturn(List.of(new SimpleGrantedAuthority("ROLE_Admin"))).when(authentication).getAuthorities();
        when(jwt.getClaims()).thenReturn(Map.of("sub", "authId"));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(memberDao.findByAuthId(any())).thenReturn(Optional.of(MemberDto.builder().id(55).build()));

        testClassProxy.methodOne(1);

        verify(memberDao).findByAuthId("authId");
    }

    @Test
    public void shouldThrowWhenWrongParameterName() {
        var authentication = mock(Authentication.class);
        var jwt = mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        doReturn(List.of(new SimpleGrantedAuthority("ROLE_Admin"))).when(authentication).getAuthorities();
        when(jwt.getClaims()).thenReturn(Map.of("sub", "authId"));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(memberDao.findByAuthId(any())).thenReturn(Optional.of(MemberDto.builder().id(55).build()));

        assertThrows(IllegalStateException.class, () -> testClassProxy.methodTwo(1));
    }

    @Test
    public void shouldThrowWhenWrongParameterType() {
        var authentication = mock(Authentication.class);
        var jwt = mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        doReturn(List.of(new SimpleGrantedAuthority("ROLE_Admin"))).when(authentication).getAuthorities();
        when(jwt.getClaims()).thenReturn(Map.of("sub", "authId"));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(memberDao.findByAuthId(any())).thenReturn(Optional.of(MemberDto.builder().id(55).build()));

        assertThrows(IllegalStateException.class, () -> testClassProxy.methodThree("1"));
    }

    @Test
    public void shouldThrowWhenParameterMissing() {
        var authentication = mock(Authentication.class);
        var jwt = mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        doReturn(List.of(new SimpleGrantedAuthority("ROLE_Admin"))).when(authentication).getAuthorities();
        when(jwt.getClaims()).thenReturn(Map.of("sub", "authId"));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(memberDao.findByAuthId(any())).thenReturn(Optional.of(MemberDto.builder().id(55).build()));

        assertThrows(IllegalStateException.class, () -> testClassProxy.methodFour());
    }

    @Test
    public void shouldThrowWhenMemberNotOperatingOnSelf() {
        var authentication = mock(Authentication.class);
        var jwt = mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        doReturn(List.of(new SimpleGrantedAuthority("ROLE_Member"))).when(authentication).getAuthorities();
        when(jwt.getClaims()).thenReturn(Map.of("sub", "authId"));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(memberDao.findByAuthId(any())).thenReturn(Optional.of(MemberDto.builder().id(55).build()));

        assertThrows(AccessDeniedException.class, () -> testClassProxy.methodOne(1));
    }

    @Test
    public void shouldThrowWhenMemberNotInDB() {
        var authentication = mock(Authentication.class);
        var jwt = mock(Jwt.class);
        when(authentication.getPrincipal()).thenReturn(jwt);
        doReturn(List.of(new SimpleGrantedAuthority("ROLE_Member"))).when(authentication).getAuthorities();
        when(jwt.getClaims()).thenReturn(Map.of("sub", "authId"));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(memberDao.findByAuthId(any())).thenReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class, () -> testClassProxy.methodOne(1));
    }

    public static class TestClass {
        @EnforceRls(memberIdParameterName = "id")
        public void methodOne(Integer id) {
        }

        @EnforceRls(memberIdParameterName = "wrongId")
        public void methodTwo(Integer id) {
        }

        @EnforceRls(memberIdParameterName = "id")
        public void methodThree(String id) {
        }

        @EnforceRls(memberIdParameterName = "id")
        public void methodFour() {
        }
    }
}