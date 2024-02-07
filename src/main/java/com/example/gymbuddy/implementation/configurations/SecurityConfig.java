package com.example.gymbuddy.implementation.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
        This is where we configure the security required for our endpoints and setup our app to serve as
        an OAuth2 Resource Server, using JWT validation.
        */
        return http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                .cors().disable()
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt().jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
                .build();
    }

    @Bean
    static MethodSecurityExpressionHandler expressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new CustomPermissionEvaluator());
        return expressionHandler;
    }

    /**
     * Custom permission evaluator so we can use @PreAuthorize("hasPermission('members', 'delete')")
     */
    private static class CustomPermissionEvaluator implements PermissionEvaluator {

        @Override
        public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
            Set<String> roleSet = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            return roleSet.contains("PERMISSION_" + permission + ":" + targetDomainObject);
        }

        @Override
        public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
            throw new IllegalArgumentException();
        }
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtGrantedAuthoritiesConverter());
        return jwtAuthenticationConverter;
    }

    /**
     * We are storing both roles and permissions as a granted authority.
     * Roles are prefixed with ROLE_, while permissions are prefixed with PERMISSION_
     */
    private static class JwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
        @Override
        public Collection<GrantedAuthority> convert(Jwt source) {
            var permissions = source.getClaim("permissions");
            var roles = source.getClaim("https://gym-buddy.com/roles");
            var grantedAuthorities = new ArrayList<GrantedAuthority>();

            if (roles instanceof Collection<?>) {
                grantedAuthorities.addAll(((Collection<?>) roles).stream()
                                .filter(String.class::isInstance)
                                .map(String.class::cast)
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toSet()));
            }

            if (permissions instanceof Collection<?>) {
                grantedAuthorities.addAll(((Collection<?>) permissions).stream()
                                .filter(String.class::isInstance)
                                .map(String.class::cast)
                                .map(permission -> new SimpleGrantedAuthority("PERMISSION_" + permission))
                        .collect(Collectors.toSet()));
            }

            return grantedAuthorities;
        }
    }
}