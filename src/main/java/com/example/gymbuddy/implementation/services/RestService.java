package com.example.gymbuddy.implementation.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;

@RequiredArgsConstructor
@Component
public class RestService {
    private final RestTemplate restTemplate;
    public <T, K> T post(String url, K body, MultiValueMap<String, String> headers, Class<T> clazz) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(headers);
        HttpEntity<K> entity = new HttpEntity<>(body, httpHeaders);
        return restTemplate.postForObject(url, entity, clazz);
    }

    public <K> void post(String url, K body, MultiValueMap<String, String> headers) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(headers);
        HttpEntity<K> entity = new HttpEntity<>(body, httpHeaders);
        restTemplate.postForEntity(url, entity, Void.class);
    }

    public <T> T get(URI url, MultiValueMap<String, String> headers, Class<T> clazz) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(headers);
        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url, HttpMethod.GET, entity, clazz).getBody();
    }
}
