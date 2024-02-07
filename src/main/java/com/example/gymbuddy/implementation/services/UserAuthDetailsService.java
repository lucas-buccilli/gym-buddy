package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.models.AuthRoles;
import com.example.gymbuddy.infrastructure.models.dtos.UserAuthDetails;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class UserAuthDetailsService {
    private UserAuthDetailsService(){};
    public static UserAuthDetails getUserAuthDetails() {
        var securityContext = SecurityContextHolder.getContext();
        var principle = securityContext.getAuthentication().getPrincipal();
        var isAdmin = securityContext.getAuthentication().getAuthorities().stream().anyMatch(x -> x.getAuthority().equals("ROLE_" + AuthRoles.ADMIN.getValue()));
        if (principle instanceof Jwt) {
            var sub = ((Jwt) principle).getClaims().get("sub");
            if (sub instanceof String) {
                return new UserAuthDetails((String) sub, isAdmin);
            }
        }
        throw new AuthenticationCredentialsNotFoundException("Error parsing user details from context");
    }
}
