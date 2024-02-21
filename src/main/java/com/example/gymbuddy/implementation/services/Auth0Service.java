package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.infrastructure.exceptions.AuthCreationException;
import com.example.gymbuddy.infrastructure.services.IAuthService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class Auth0Service implements IAuthService {
    @Value("${AUTH0_CLIENT_ID}")
    private String clientId;
    @Value("${AUTH0_CLIENT_SECRET}")
    private String clientSecret;
    @Value("${AUTH0_AUDIANCE}")
    private String audiance;
    @Value("${AUTH0_DOMAIN}")
    private String domain;

    private final RestService restService;

    @Override
    public String createUser(String email, String password) throws AuthCreationException {
        try {
            var accessToken = getAccessToken();
            var header = CollectionUtils.toMultiValueMap(Map.of("Authorization", List.of("Bearer " + accessToken)));
            return restService.post(domain + "/api/v2/users", new CreateUserRequest("Username-Password-Authentication", email, password), header, CreateUserResponse.class).user_id;
        } catch (JsonProcessingException e) {
            throw new AuthCreationException(e);
        }
    }

    public String getAccessToken() throws JsonProcessingException {
        return restService.post(domain + "/oauth/token", new TokenRequest(clientId, clientSecret, audiance, "client_credentials"), new HttpHeaders(), TokenResponse.class).accessToken;
    }

    public record CreateUserRequest(@JsonProperty("connection") String connection,
                               @JsonProperty("email") String email,
                               @JsonProperty("password") String password)
    { }

    public record CreateUserResponse(@JsonProperty("user_id") String user_id)
    {}

    public record TokenRequest(@JsonProperty("client_id") String clientId,
                               @JsonProperty("client_secret") String clientSecret,
                               @JsonProperty("audience") String audience,
                               @JsonProperty("grant_type") String grantType) {
    }

    public record TokenResponse(@JsonProperty("access_token") String accessToken) { }
}
