package com.example.gymbuddy.implementation.services;

import com.example.gymbuddy.implementation.values.Auth0Values;
import com.example.gymbuddy.infrastructure.exceptions.AuthCreationException;
import com.example.gymbuddy.infrastructure.models.AuthRoles;
import com.example.gymbuddy.infrastructure.services.IAuthService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class Auth0Service implements IAuthService {

    private final Auth0Values values;
    private final RestService restService;
    private final Map<AuthRoles, String> roleMap = Map.of(AuthRoles.ADMIN, "rol_dFibb6DmRkIBdarc", AuthRoles.MEMBER, "rol_7h0vwp3djjweCNmw");

    @Override
    public String createUser(String email, String password) throws AuthCreationException {
        AuthFunction<MultiValueMap<String, String>, String> function = (headers) -> restService.post(values.getDomain() + "/api/v2/users", new CreateUserRequest("Username-Password-Authentication", email, password), headers, CreateUserResponse.class).user_id;
        return sendAuthorizedRequest(function);
    }

    @Override
    public void addRole(String authId, List<AuthRoles> roles) throws AuthCreationException {
        AuthSupplier<MultiValueMap<String, String>> supplier = (headers) -> restService.post(values.getDomain() + "/api/v2/users/" + authId + "/roles", new AddRolesRequest(roles.stream().map(roleMap::get).collect(Collectors.toSet())), headers);
        sendAuthorizedRequest(supplier);
    }

    @Override
    public List<Object> searchUsersByEmail(String email) throws AuthCreationException {
        AuthFunction<MultiValueMap<String, String>, List<Object>> function = new AuthFunction<MultiValueMap<String, String>, List<Object>>() {
            @Override
            public List<Object> apply(MultiValueMap<String, String> headers) throws URISyntaxException, JsonProcessingException {
                return List.of(restService.get(new URI(values.getDomain() + "/api/v2/users-by-email?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8)), headers, Object[].class));
            }
        };
        return sendAuthorizedRequest(function);
    }

    private String getAccessToken() throws JsonProcessingException {
        return restService.post(values.getDomain() + "/oauth/token", new TokenRequest(values.getClientId(), values.getClientSecret(), values.getAudiance(), "client_credentials"), new HttpHeaders(), TokenResponse.class).accessToken;
    }

    private <T> T sendAuthorizedRequest(AuthFunction<MultiValueMap<String, String>, T> func) throws AuthCreationException {
        try {
            var accessToken = getAccessToken();
            var header = CollectionUtils.toMultiValueMap(Map.of("Authorization", List.of("Bearer " + accessToken)));
            return func.apply(header);
        } catch (JsonProcessingException | URISyntaxException e) {
            throw new AuthCreationException(e);
        }
    }

    private void sendAuthorizedRequest(AuthSupplier<MultiValueMap<String, String>> func) throws AuthCreationException {
        try {
            var accessToken = getAccessToken();
            var header = CollectionUtils.toMultiValueMap(Map.of("Authorization", List.of("Bearer " + accessToken)));
            func.apply(header);
        } catch (JsonProcessingException | URISyntaxException e) {
            throw new AuthCreationException(e);
        }
    }

    @FunctionalInterface
    interface AuthFunction <T, R> {
        R apply (T args) throws URISyntaxException, JsonProcessingException;
    }

    @FunctionalInterface
    interface AuthSupplier <T> {
        void apply (T args) throws URISyntaxException, JsonProcessingException;
    }

    public record CreateUserRequest(@JsonProperty("connection") String connection,
                               @JsonProperty("email") String email,
                               @JsonProperty("password") String password)
    { }

    public record AddRolesRequest(@JsonProperty("roles") Set<String> roles)
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
