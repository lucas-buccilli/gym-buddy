package com.example.gymbuddy.implementation.utils;

import com.example.gymbuddy.infrastructure.models.AuthRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuthUtils {
    public static SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor generateAuth0User(List<String> permissions, List<AuthRoles> roles, String auth0Id) {
        var permissionAuthorities = permissions.stream().map(permission -> new SimpleGrantedAuthority("PERMISSION_" + permission)).collect(Collectors.toSet());
        var roleAuthorities = roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getValue())).collect(Collectors.toSet());
        var grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.addAll(roleAuthorities);
        grantedAuthorities.addAll(permissionAuthorities);
        return SecurityMockMvcRequestPostProcessors.jwt().jwt(builder -> builder.claim("sub", auth0Id)).authorities(grantedAuthorities);
    }

    public static SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor generateAuth0Admin(String auth0Id) {
        return SecurityMockMvcRequestPostProcessors.jwt().jwt(builder -> builder.claim("sub", auth0Id)).authorities(new SimpleGrantedAuthority("ROLE_Admin"));
    }
}
