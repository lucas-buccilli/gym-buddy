package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.daos.IMemberDao;
import com.example.gymbuddy.infrastructure.exceptions.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthService {
    private final IMemberDao memberDao;
    public void verifyAuthenticatedUserCanAccessUser(Integer memberId) {
        var userDetails = UserAuthDetailsService.getUserAuthDetails();
        if (userDetails.admin()) {
            return;
        }

        var authenticatedMember = memberDao.findByAuthId(userDetails.authId()).orElseThrow(() -> new MemberNotFoundException(userDetails.authId()));

        if (authenticatedMember.getId().equals(memberId)) {
            return;
        }

        throw new AccessDeniedException("User does not have permission");
    }
}
