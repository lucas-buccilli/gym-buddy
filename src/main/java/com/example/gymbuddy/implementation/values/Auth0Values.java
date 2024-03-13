package com.example.gymbuddy.implementation.values;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class Auth0Values {
    @Value("${AUTH0_CLIENT_ID}")
    private String clientId;
    @Value("${AUTH0_CLIENT_SECRET}")
    private String clientSecret;
    @Value("${AUTH0_AUDIANCE}")
    private String audiance;
    @Value("${AUTH0_DOMAIN}")
    private String domain;
}
